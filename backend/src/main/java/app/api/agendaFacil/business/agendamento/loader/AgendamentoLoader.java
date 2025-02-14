package app.api.agendaFacil.business.agendamento.loader;

import app.api.agendaFacil.business.agendamento.DTO.AgendamentoDTO;
import app.api.agendaFacil.business.agendamento.entity.Agendamento;
import app.api.agendaFacil.business.agendamento.repository.AgendamentoRepository;
import app.api.agendaFacil.business.configuradorAgendamento.entity.ConfiguradorAgendamento;
import app.api.agendaFacil.business.configuradorAgendamento.validator.ConfiguradorAgendamentoValidator;
import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.Contexto;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class AgendamentoLoader {

    final AgendamentoLoader agendamentoLoader;
    final ConfiguradorAgendamentoValidator configuradorAgendamentoValidator;
    final AgendamentoRepository agendamentoRepository;

    public AgendamentoLoader(AgendamentoLoader agendamentoLoader, ConfiguradorAgendamentoValidator configuradorAgendamentoValidator, AgendamentoRepository agendamentoRepository) {
        this.agendamentoLoader = agendamentoLoader;
        this.configuradorAgendamentoValidator = configuradorAgendamentoValidator;
        this.agendamentoRepository = agendamentoRepository;
    }

    public static Agendamento findById(Long id) {
        if (BasicFunctions.isNotEmpty(id)) {
            return AgendamentoRepository.findById(id);
        }
        return null;
    }

    public static List<Agendamento> listByIds(List<Long> pListIdAgendamento) {
        return AgendamentoRepository.listByIds(pListIdAgendamento);
    }

    public static PanacheQuery<Agendamento> find(String query) {
        return AgendamentoRepository.find(query);
    }

    public List<Agendamento> loadListAgendamentosByUsuarioDataAgenda(AgendamentoDTO pAgendamento) {
        List<Agendamento> agendamentos = new ArrayList<>();
        if (BasicFunctions.isNotEmpty(pAgendamento, pAgendamento.getOrganizacaoAgendamento(), pAgendamento.getProfissionalAgendamento())
                && BasicFunctions.isValid(pAgendamento.getProfissionalAgendamento())) {
            return AgendamentoRepository.listByProfissionalDataAgendamento(new Agendamento(pAgendamento));
        }
        return agendamentos;
    }

    public List<Agendamento> loadListAgendamentosByDataAgenda(AgendamentoDTO pAgendamento) {
        List<Agendamento> agendamentos = new ArrayList<>();
        if (BasicFunctions.isValid(pAgendamento.getDataAgendamento())) {
            return AgendamentoRepository.listByDataAgendamento(new Agendamento(pAgendamento));
        }
        return agendamentos;
    }

    public Agendamento loadAgendamentoByPessoaDataAgendaHorario(AgendamentoDTO pAgendamento) {
        if (BasicFunctions.isValid(pAgendamento.getDataAgendamento(), pAgendamento.getHorarioAgendamento(), pAgendamento.getPessoaAgendamento())) {
            return AgendamentoRepository.findByPessoaDataHorarioAgendamento(new Agendamento(pAgendamento));
        }
        return null;
    }

    public List<AgendamentoDTO> makeListAgendamentosByProfissionalAgendamento(AgendamentoDTO pAgendamento,
                                                                              ConfiguradorAgendamento pConfigurador) {

        List<AgendamentoDTO> agendamentos = new ArrayList<>();

        agendamentos.addAll(processarHorario(pAgendamento, pConfigurador,
                pConfigurador.getHorarioInicioManha(),
                pConfigurador.getHorarioFimManha(),
                pConfigurador.getAgendaManha(),
                pConfigurador.getAgendaSabadoManha(),
                pConfigurador.getAgendaDomingoManha()));

        agendamentos.addAll(processarHorario(pAgendamento, pConfigurador,
                pConfigurador.getHorarioInicioTarde(),
                pConfigurador.getHorarioFimTarde(),
                pConfigurador.getAgendaTarde(),
                pConfigurador.getAgendaSabadoTarde(),
                pConfigurador.getAgendaDomingoTarde()));

        agendamentos.addAll(processarHorario(pAgendamento, pConfigurador,
                pConfigurador.getHorarioInicioNoite(),
                pConfigurador.getHorarioFimNoite(),
                pConfigurador.getAgendaNoite(),
                pConfigurador.getAgendaSabadoNoite(),
                pConfigurador.getAgendaDomingoNoite()));

        if (BasicFunctions.isNotEmpty(agendamentos)) {
            return agendamentos;
        }
        return Collections.emptyList();
    }

    private List<AgendamentoDTO> processarHorario(AgendamentoDTO pAgendamento,
                                                  ConfiguradorAgendamento pConfigurador,
                                                  LocalTime horarioInicio,
                                                  LocalTime horarioFim,
                                                  Boolean agendaDia,
                                                  Boolean agendaSabado,
                                                  Boolean agendaDomingo) {

        if (BasicFunctions.isNotEmpty(pConfigurador) &&
                BasicFunctions.isValid(horarioInicio) &&
                BasicFunctions.isValid(horarioFim)) {

            LocalTime inicio = LocalTime.of(horarioInicio.getHour(), horarioInicio.getMinute());
            LocalTime fim = LocalTime.of(horarioFim.getHour(), horarioFim.getMinute());
            LocalTime inicioCfg = LocalTime.of(horarioInicio.getHour(), horarioInicio.getMinute());

            DayOfWeek agendamentoDayOfWeek = pAgendamento.getDataAgendamento().getDayOfWeek();

            if (agendamentoDayOfWeek.equals(DayOfWeek.SATURDAY)) {
                agendaDia = agendaSabado;
            }
            if (agendamentoDayOfWeek.equals(DayOfWeek.SUNDAY)) {
                agendaDia = agendaDomingo;
            }

            if (ConfiguradorAgendamentoValidator.verificaMakeHorarioByConfiguradorAgendamento(agendamentoDayOfWeek, pConfigurador)) {

                return makeHorariosDisponiveis(pAgendamento, pConfigurador, inicio, inicioCfg, fim, agendaDia);
            }
        }
        return null;
    }

    public List<AgendamentoDTO> makeAgendamentosLivres(List<AgendamentoDTO> plistaAgendamentosFree,
                                                       List<AgendamentoDTO> pListaAgendamentosPersisted, Boolean reagendar) {

        List<AgendamentoDTO> returnList = new ArrayList<>();

        if (reagendar) {
            returnList = plistaAgendamentosFree.stream()
                    .filter(free -> pListaAgendamentosPersisted.stream()
                            .noneMatch(persisted -> persisted.getProfissionalAgendamento().getId()
                                    .equals(free.getProfissionalAgendamento().getId())
                                    && persisted.getDataAgendamento().isEqual(free.getDataAgendamento())
                                    && persisted.getHorarioAgendamento().equals(free.getHorarioAgendamento()
                            )))
                    .collect(Collectors.toList());
        }
        if (!reagendar) {
            returnList = plistaAgendamentosFree.stream()
                    .filter(free -> pListaAgendamentosPersisted.stream()
                            .noneMatch(persisted -> persisted.getDataAgendamento().isEqual(free.getDataAgendamento())
                                    && persisted.getHorarioAgendamento().equals(free.getHorarioAgendamento())
                                    && persisted.getStatusAgendamento().agendado()))
                    .collect(Collectors.toList());
        }
        return returnList;
    }

    public List<AgendamentoDTO> makeHorariosDisponiveis(AgendamentoDTO pAgendamento, ConfiguradorAgendamento pConfigurador,
                                                        LocalTime horaInicio, LocalTime horaInicioCfg, LocalTime horaFim, Boolean agenda) {

        List<AgendamentoDTO> agendamentos = new ArrayList<>();
        AgendamentoDTO agendamentoNew;
        LocalDate dataContexto = Contexto.dataContexto(pAgendamento.getOrganizacaoAgendamento());
        LocalTime horaContexto = Contexto.horarioContexto(pAgendamento.getOrganizacaoAgendamento());

        if (BasicFunctions.isNotEmpty(pConfigurador) && BasicFunctions.isValid(horaInicio)
                && BasicFunctions.isValid(horaFim)) {

            int hora = pConfigurador.getHoraMinutoIntervalo().getHour();
            int minuto = pConfigurador.getHoraMinutoIntervalo().getMinute();
            int horaTolerancia = pConfigurador.getHoraMinutoTolerancia().getHour();
            int minutoTolerancia = pConfigurador.getHoraMinutoTolerancia().getMinute();

            LocalTime horaMinutoTolerancia = horaContexto.truncatedTo(ChronoUnit.HOURS).plusHours(horaTolerancia)
                    .plusMinutes(minutoTolerancia);

            if (dataContexto.isEqual(pAgendamento.getDataAgendamento()) && horaContexto.isAfter(horaInicio)) {

                horaInicio = horaContexto.truncatedTo(ChronoUnit.HOURS).plusHours(hora).plusMinutes(minuto);

                if (horaContexto.isAfter(horaMinutoTolerancia)) {
                    horaInicio = horaInicio.plusHours(hora).plusMinutes(minuto);
                }
            }

            if (!horaInicio.isBefore(horaInicioCfg) || horaContexto.isBefore(horaInicio) && BasicFunctions.isValid(horaInicio, horaFim) && agenda) {

                for (LocalTime horaAtual = horaInicio; horaAtual.isBefore(horaFim); horaAtual = horaAtual.plusHours(hora).plusMinutes(minuto)) {
                    agendamentoNew = new AgendamentoDTO();
                    agendamentoNew.setStatusAgendamento(pAgendamento.getStatusAgendamento());
                    agendamentoNew.setDataAgendamento(pAgendamento.getDataAgendamento());
                    agendamentoNew.setHorarioAgendamento(horaAtual);
                    agendamentoNew.setProfissionalAgendamento(pAgendamento.getProfissionalAgendamento());
                    agendamentoNew.setPessoaAgendamento(pAgendamento.getPessoaAgendamento());
                    agendamentoNew.setOrganizacaoAgendamento(pAgendamento.getOrganizacaoAgendamento());
                    agendamentoNew.setTipoAgendamento(pAgendamento.getTipoAgendamento());
                    agendamentoNew.setEndereco(pAgendamento.getEndereco());
                    agendamentoNew.setComPreferencia(Boolean.TRUE);
                    agendamentos.add(agendamentoNew);
                }
            }
        }
        return agendamentos;
    }

    public Agendamento loadAgendamentoById(Agendamento pAgendamento) {
        if (BasicFunctions.isNotEmpty(pAgendamento)) {
            return AgendamentoRepository.findById(pAgendamento.getId());
        }
        return null;
    }

    public Agendamento findByPessoaDataAgendamento(Agendamento pAgendamento) {
        if (BasicFunctions.isNotEmpty(pAgendamento, pAgendamento.getPessoaAgendamento(), pAgendamento.getDataAgendamento())) {
            return AgendamentoRepository.findByPessoaDataAgendamento(pAgendamento);
        }
        return null;
    }

    public Agendamento findById(Agendamento pAgendamento) {
        if (BasicFunctions.isNotEmpty(pAgendamento, pAgendamento.getId())) {
            return AgendamentoRepository.findById(pAgendamento.getId());
        }
        return null;
    }

    public List<Agendamento> listByPessoaDataAgendamentoStatusAgendadoAndReagendar(AgendamentoDTO pAgendamento, Boolean reagendar) {
        return agendamentoRepository.listByPessoaDataAgendamentoStatusAgendadoAndReagendar(pAgendamento, reagendar);
    }

    public List<Agendamento> list(QueryFilter queryFilter) {
        return agendamentoRepository.list(queryFilter);
    }

    public Integer count(QueryFilter queryFilter) {
        return agendamentoRepository.count(queryFilter);
    }
}

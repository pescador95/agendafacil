import Sidebar from '@/components/sidebar/Sidebar.vue'
import Header from '@/components/header/Header.vue'
import Filter from '@/components/filter/Filter.vue'
import Select from '@/components/select/Select.vue'
import Button from '@/components/button/Button.vue'
import Table from '@/components/table/Table.vue'
import InputField from '@/components/inputField/InputField.vue'
import Modal from '@/components/modal/Modal.vue'
import { endpoints } from '../../utils/enums/endpoints'
import fetchWrapper from '../../utils/fetchWrapper'
import { getOrganizacaoContexto } from '../../utils/contextHelpers'
import { formatDate, formatTime, formatPhoneNumber } from '@/utils/formatters';

export default {
  name: 'AgendamentoPage',
  components: {
    Sidebar,
    Header,
    Filter,
    Button,
    Table,
    Modal,
    Select,
    InputField

  },
  data() {
    return {
      pageTitle: 'Agendamentos',
      appointments: [],
      columns: [
        'Data',
        'Horário',
        'Nome Completo',
        'Status',
        'Celular',
        'Profissional',
        'Tipo de Atendimento',
        'Organização'
      ],
      nomePessoa: '',
      dataAgendamento: '',
      statusOptions: [],
      tipoAgendamentoOptions: [],
      profissionalOptions: [],
      clienteOptions: [],
      organizacaoOptions: [],
      filterFields: [
        {
          id: 'idStatus',
          type: 'select',
          value: this.idStatus,
          options: [],
        },
        {
          id: 'tipoAgendamentoId',
          type: 'select',
          value: this.tipoAgendamentoId,
          options: [],
        },
      ],
      isModalVisible: false,
      modalData: '',
      modalHorario: '',
      modalProfissional: [],
      modalCliente: [],
      modalStatus: [],
      modalOrganizacao: []
    }
  },
  computed: {
    formattedAppointments() {
      return this.appointments.map((appointment) => ({
        Data: formatDate(appointment.dataAgendamento, appointment.organizacaoAgendamento),
        Horário: formatTime(appointment.horarioAgendamento),
        'Nome Completo': appointment.pessoaAgendamento.nome,
        Status: appointment.statusAgendamento.status,
        Celular: formatPhoneNumber(appointment.pessoaAgendamento.celular),
        Profissional: appointment.profissionalAgendamento.nomeProfissional,
        'Tipo de Atendimento': appointment.tipoAgendamento.tipoAgendamento,
        'Organização': appointment.organizacaoAgendamento.nome
      }))
    },
  },
  methods: {
    search() {
      this.fetchAppointments()
    },
    reset() {
      this.nomePessoa = ''
      this.dataAgendamento = ''
      this.idStatus = null
      this.tipoAgendamentoId = null

      this.$refs.statusSelect.resetSelect()
      this.$refs.tipoAgendamentoSelect.resetSelect()
    },
    async fetchAppointments() {
      try {
        const queryParams = this.makeQueryParams()

        const response = await fetchWrapper(endpoints.agendamento.base, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
          },
          queryParams,
        })

        const data = await response.json()

        if (!response.ok) {
          throw new Error(data.messages[0])
        }

        this.appointments = data
      } catch (error) {
        console.error('Erro ao buscar agendamentos:', error)
      }
    },

    async fetchStatusOptions() {
      try {
        const queryParams = this.makeQueryParams()

        const response = await fetchWrapper(endpoints.statusAgendamento.base, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
          },
          queryParams,
        })
        const data = await response.json()
        if (!response.ok) {
          throw new Error(data.messages[0])
        }
        this.statusOptions = data.map((status) => ({ value: status.id, text: status.status }))
        this.options = this.statusOptions
      } catch (error) {
        console.error('Erro ao buscar opções de status:', error)
      }
    },

    async fetchTipoAgendamentoOptions() {

      try {
        const queryParams = this.makeQueryParams()

        const response = await fetchWrapper(endpoints.tipoAgendamentos.base, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
          },
          queryParams,
        })
        const data = await response.json()
        if (!response.ok) {
          throw new Error(data.messages[0])
        }
        this.tipoAgendamentoOptions = data.map((tipoAgendamento) => ({
          value: tipoAgendamento.id,
          text: tipoAgendamento.tipoAgendamento,
        }))
        this.options = this.tipoAgendamentoOptions
      } catch (error) {
        console.error('Erro ao buscar opções de Tipo Agendamento:', error)
      }
    },

    async fetchOrganizacaoOptions() {

      try {

        const response = await fetchWrapper(endpoints.organizacao.base, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
          },
        })
        const data = await response.json()
        if (!response.ok) {
          throw new Error(data.messages[0])
        }
        this.organizacaoOptions = data.map((organizacao) => ({ value: organizacao.id, text: organizacao.nome }))
        this.options = this.organizacaoOptions
      } catch (error) {
        console.error('Erro ao buscar opções de organização:', error)
      }
    },


    async fetchClienteOptions() {

      try {

        const response = await fetchWrapper(endpoints.pessoa.base, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
          },

        })
        const data = await response.json()
        if (!response.ok) {
          throw new Error(data.messages[0])
        }
        this.clienteOptions = data.map((pessoa) => ({
          value: pessoa.id,
          text: pessoa.nome,
        }))
        this.options = this.clienteOptions
      } catch (error) {
        console.error('Erro ao buscar opções de Tipo Agendamento:', error)
      }
    },

    async fetchProfissionalOptions() {

      try {

        const response = await fetchWrapper(endpoints.profissional.base, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
          },
        })
        const data = await response.json()
        if (!response.ok) {
          throw new Error(data.messages[0])
        }
        this.profissionalOptions = data.map((profissional) => ({
          value: profissional.id,
          text: profissional.nomeProfissional,
        }))
        this.options = this.profissionalOptions
      } catch (error) {
        console.error('Erro ao buscar opções de Tipo Agendamento:', error)
      }
    },

    handleStatusChange(selectedValue) {
      this.filterFields.find((field) => field.id === 'idStatus').value = selectedValue?.target.value
    },
    handleTipoAgendamentoChange(selectedValue) {
      this.filterFields.find((field) => field.id === 'tipoAgendamentoId').value =
        selectedValue?.target.value
    },
    makeQueryParams() {
      const queryParams = {
        organizacaoId: getOrganizacaoContexto(),
      }

      this.filterFields.forEach((field) => {
        if (field.value) {
          queryParams[field.id] = field.value
        }
      })

      if (this.nomePessoa) {
        queryParams.nomePessoa = this.nomePessoa
      }
      if (this.dataAgendamento) {
        queryParams.dataAgendamento = this.dataAgendamento
      }
      if (this.idStatus) {
        queryParams.idStatus = this.idStatus
      }

      return queryParams
    },
    fetchPage() {
      this.fetchAppointments()
      this.fetchStatusOptions()
      this.fetchTipoAgendamentoOptions()
      this.fetchOrganizacaoOptions()
      this.fetchClienteOptions()
      this.fetchProfissionalOptions()
    },
    openModal() {
      this.isModalVisible = true;
    },
    closeModal() {
      this.isModalVisible = false;
    },
    handleCadastrar() {
      //TODO
      this.closeModal();
    },
  },
  mounted() {
    this.fetchPage()
  },
}

package app.api.agendaFacil.management.role.service;

import app.api.agendaFacil.management.role.DTO.RoleDTO;
import app.api.agendaFacil.management.role.entity.Role;
import app.api.agendaFacil.management.role.loader.RoleLoader;
import app.api.agendaFacil.management.role.manager.RoleManager;
import app.api.agendaFacil.management.role.repository.RoleRepository;
import app.api.agendaFacil.management.role.validator.RoleValidator;
import app.core.application.DTO.Responses;
import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static app.api.agendaFacil.management.role.filter.RoleFilters.makeRoleQueryStringByFilters;

@RequestScoped
@Transactional
public class RoleService extends RoleManager {

    private final List<RoleDTO> roleDTOS = new ArrayList<>();
    private Role role;

    public RoleService() {
        super();
    }

    @Inject
    public RoleService(SecurityContext context, RoleLoader roleLoader, RoleValidator roleValidator) {
        super(context, roleLoader, roleValidator);
    }

    public Response addRole(@NotNull RoleDTO pRole) {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            loadByPrivilegio(pRole);

            checkRoleByPrivilegio(pRole);

            validaRole();

            if (BasicFunctions.isEmpty(role)) {

                newInstanceByAddRole(pRole);

                if (!responses.hasMessages()) {

                    role.persist();

                    responses.setData(new RoleDTO(role));
                    responses.setMessages("Role cadastrado com sucesso!");
                    responses.setStatus(201);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response updateRole(@NotNull RoleDTO pRole) {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            loadById(pRole);

            checkNewPrevilegio(pRole);

            newInstanceByUpdateRole(pRole);

            role.persist();

            responses.setData(new RoleDTO(role));
            responses.setMessages("Role atualizado com sucesso!");
            responses.setStatus(200);
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response deleteRole(@NotNull List<Long> pListIdRole) {

        try {

            List<Role> roles;
            List<Role> rolesAux = new ArrayList<>();
            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            roles = roleLoader.listByListIdRole(pListIdRole);
            int count = roles.size();

            validaRoles(roles);

            if (BasicFunctions.isNotEmpty(roles)) {

                roles.forEach((role) -> {
                    rolesAux.add(role);
                    role.delete();
                });
            }

            responses.setMessages(Responses.DELETED, count);
            responses.setStatus(200);
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response findById(Long pId) {

        try {

            responses = new Responses();

            role = RoleRepository.findById(pId);
            return Response.ok(role).status(200).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }


    public Response count(Long id, String nome, String privilegio, Boolean admin, Long usuarioId, String sortQuery, Integer pageIndex, Integer pageSize, String strgOrder) {

        try {

            responses = new Responses();


            QueryFilter queryString = makeRoleQueryStringByFilters(id, nome, privilegio, admin, usuarioId, sortQuery, pageIndex, pageSize, strgOrder);

            Integer count = roleLoader.count(queryString);

            return Response.ok(count).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        } finally {
            return Response.ok(count).status(responses.getStatus()).build();
        }
    }


    public Response list(Long id, String nome, String privilegio, Boolean admin, Long usuarioId, String sortQuery, Integer pageIndex, Integer pageSize, String strgOrder) {

        try {

            responses = new Responses();

            QueryFilter queryString = makeRoleQueryStringByFilters(id, nome, privilegio, admin, usuarioId, sortQuery, pageIndex, pageSize, strgOrder);

            List<Role> roles = roleLoader.list(queryString);

            return Response.ok(toDTO(roles)).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public void checkRoleByPrivilegio(RoleDTO pRole) {
        if (!roleValidator.hasPrivilegio(pRole)) {
            responses.setMessages("O campo 'privilegio' é obrigatório!");
        }
    }

    public void checkNewPrevilegio(RoleDTO pRole) {
        if (!roleValidator.hasPrivilegio(pRole)) {
            throw new BadRequestException("Informe os nome do privilégio para atualizar o mesmo.");
        }
    }

    public void loadById(RoleDTO pRole) {
        role = roleLoader.findById(pRole);
    }

    public void loadByPrivilegio(RoleDTO pRole) {
        role = roleLoader.findByPrivilegio(pRole);
    }

    public void newInstanceByAddRole(RoleDTO pRole) {
        role = new Role();
        role.setPrivilegio(pRole.getPrivilegio());
        if (BasicFunctions.isNotEmpty(pRole.getAdmin())) {
            role.setAdmin(pRole.getAdmin());
        }
    }

    public void newInstanceByUpdateRole(RoleDTO pRole) {
        role.setPrivilegio(pRole.getPrivilegio());
        if (BasicFunctions.isNotEmpty(pRole.getAdmin())) {
            role.setAdmin(pRole.getAdmin());
        }
    }

    private void validaRole() {

        if (BasicFunctions.isNotEmpty(role)) {
            responses.setData(role);
            responses.setMessages("Já existe uma role cadastrada. Verifique as informações!");
            responses.setStatus(400);
        }
    }

    private void validaRoles(List<Role> roles) {
        if (BasicFunctions.isEmpty(roles)) {

            responses.setMessages("Roles não localizados ou já excluídas.");
            responses.setStatus(404);
        }
    }

    private List<RoleDTO> toDTO(List<Role> entityList) {

        List<RoleDTO> entityDTOList = new ArrayList<>();

        if (BasicFunctions.isNotEmpty(entityList)) {
            entityList.forEach(entity -> {
                entityDTOList.add(new RoleDTO(entity));
            });
        }
        return entityDTOList;
    }

}

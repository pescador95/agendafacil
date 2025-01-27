package app.core.application.DTO;

import app.core.helpers.utils.BasicFunctions;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

public class Responses {

    public static final Integer CREATED = 1;
    public static final Integer UPDATED = 2;
    public static final Integer DELETED = 3;
    public static final Integer REACTIVATED = 4;
    public static final Integer ENABLED = 5;
    public static final Integer DISABLED = 6;
    private Integer status;
    private Object data;
    private List<Object> datas;
    private List<String> messages;
    private Boolean ok;


    public Responses() {
        this.status = Response.Status.OK.getStatusCode();
        this.datas = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.ok = Boolean.TRUE;
    }

    public Responses(int status) {
        this.status = getStatus(status);
        this.datas = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.makeMessageByStatus(status);
        this.ok = returnOk(status);
    }

    public Responses(int status, String messages) {
        this.status = getStatus(status);
        this.messages = new ArrayList<>();
        this.messages.add(messages);
        this.ok = returnOk(status);
    }

    public Responses(int status, List<String> messages) {
        this.status = getStatus(status);
        this.messages = new ArrayList<>(messages);
        this.ok = returnOk(status);
    }

    public Responses(int status, Object data, List<String> messages) {
        this.status = getStatus(status);
        this.data = data;
        this.messages = new ArrayList<>(messages);
        this.ok = returnOk(status);
    }

    public Responses(int status, Object data, String messages) {
        this.status = getStatus(status);
        this.data = data;
        this.messages = new ArrayList<>();
        this.messages.add(messages);
        this.ok = returnOk(status);
    }

    public Responses(int status, List<Object> datas, List<String> messages) {
        this.status = getStatus(status);
        this.datas = datas;
        this.messages = new ArrayList<>(messages);
        this.ok = returnOk(status);
    }

    public Responses(int i, Object data, List<Object> datas, List<String> messages) {
        this.status = i;
        this.data = data;
        this.datas = datas;
        this.messages = new ArrayList<>(messages);
        this.ok = returnOk(i);
    }

    public static Integer resolveActionForEnabled(Boolean enabled) {
        if (BasicFunctions.isNotEmpty(enabled) && enabled) {
            return Responses.ENABLED;
        }
        return Responses.DISABLED;
    }

    public boolean returnOk(int status) {
        return status == 200 || status == 201;
    }

    public int getStatus(int status) {

        return switch (status) {
            case 201 -> Response.Status.CREATED.getStatusCode();
            case 400 -> Response.Status.BAD_REQUEST.getStatusCode();
            case 401 -> Response.Status.UNAUTHORIZED.getStatusCode();
            case 403 -> Response.Status.FORBIDDEN.getStatusCode();
            case 404 -> Response.Status.NOT_FOUND.getStatusCode();
            case 409 -> Response.Status.CONFLICT.getStatusCode();
            case 500 -> Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
            default -> Response.Status.OK.getStatusCode();
        };
    }

    public void makeMessageByStatus(Integer status) {

        if (this.messages.isEmpty()) {
            this.messages = new ArrayList<>();

            if (status.equals(200)) {
                this.messages.add("Requisição realizada com sucesso.");
            }
            if (status.equals(201)) {
                this.messages.add("Cadastro realizado com sucesso.");
            }
            if (status.equals(400)) {
                this.messages.add("Requisição inválida.");
            }
            if (status.equals(401)) {
                this.messages.add("Acesso não autorizado.");
            }
            if (status.equals(403)) {
                this.messages.add("Acesso proibido.");
            }
            if (status.equals(404)) {
                this.messages.add("Recurso não encontrado.");
            }
            if (status.equals(409)) {
                this.messages.add("Conflito de dados.");
            }
            if (status.equals(500)) {
                this.messages.add("Ocorreu um erro interno no servidor. Por favor, contate o suporte.");
            }
        }
    }

    public Boolean hasMessages() {
        return BasicFunctions.isNotEmpty(this.messages);
    }

    public Integer getStatus() {
        this.ok = returnOk(status);
        return status;
    }

    public void setStatus(Integer status) {
        this.status = getStatus(status);
        if (this.messages.isEmpty()) {
            this.messages = new ArrayList<>();
            this.makeMessageByStatus(status);
        }
        this.ok = returnOk(status);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List<Object> getDatas() {
        return datas;
    }

    public void setDatas(List<Object> datas) {
        this.datas = datas;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public void setMessages(String messages) {
        this.messages.add(messages);
    }

    public void setMessages(Integer acao, Integer count) {
        this.messages = new ArrayList<>();
        this.messages.add(getMessageByAcaoCode(acao, count, null));
    }

    public void setMessages(Integer acao, Integer count, String message) {
        this.messages = new ArrayList<>();
        this.messages.add(getMessageByAcaoCode(acao, count, message));
    }

    private String getMessageByAcaoCode(Integer acao, Integer count, String message) {

        String prefix = "";

        if (BasicFunctions.isNotEmpty(message)) {
            prefix = message;
        }

        if (count > 1) {

            if (BasicFunctions.isEmpty(message)) {
                prefix = "Registros";
            }

            return switch (acao) {
                case 1 -> prefix + " criados com sucesso.";
                case 2 -> prefix + " atualizados com sucesso.";
                case 3 -> prefix + " excluídos com sucesso.";
                case 4 -> prefix + " reativados com sucesso.";
                case 5 -> prefix + " habilitados com sucesso.";
                case 6 -> prefix + " desabilitados com sucesso.";
                default -> prefix + " efetuados com sucesso";
            };
        }

        if (BasicFunctions.isEmpty(message)) {
            prefix = "Registro";
        }

        return switch (acao) {
            case 1 -> prefix + " criado com sucesso.";
            case 2 -> prefix + " atualizado com sucesso.";
            case 3 -> prefix + " excluído com sucesso.";
            case 4 -> prefix + " reativado com sucesso.";
            case 5 -> prefix + " habilitado com sucesso.";
            case 6 -> prefix + " desabilitado com sucesso.";
            default -> prefix + " efetuado com sucesso";
        };
    }

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }
}

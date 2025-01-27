package app.core.helpers.utils;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class BusinessExceptions implements ExceptionMapper<Exception> {
    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public Response toResponse(Exception error) {

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error.getMessage()).build();
    }
}
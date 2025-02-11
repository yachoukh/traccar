package org.traccar.api.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.traccar.api.BaseResource;
import org.traccar.api.security.PermissionsService;
import org.traccar.model.User;
import org.traccar.storage.StorageException;
import org.traccar.storage.query.Columns;
import org.traccar.storage.query.Condition;
import org.traccar.storage.query.Request;

import java.util.Collection;

@Path("accounts/{accountId}/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountUsersResource extends BaseResource {

    @Inject
    private PermissionsService permissionsService;

    @GET
    public Collection<User> get(@PathParam("accountId") long accountId) throws StorageException {
        permissionsService.checkAccount(getUserId(), accountId);
        return storage.getObjects(User.class, new Request(
                new Columns.All(),
                new Condition.Equals("accountId", accountId)));
    }

    @Path("{userId}")
    @GET
    public Response getSingle(
            @PathParam("accountId") long accountId,
            @PathParam("userId") long userId) throws StorageException {
        permissionsService.checkAccount(getUserId(), accountId);
        User user = storage.getObject(User.class, new Request(
                new Columns.All(),
                new Condition.And(
                    new Condition.Equals("accountId", accountId),
                    new Condition.Equals("id", userId))));
        if (user != null) {
            return Response.ok(user).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
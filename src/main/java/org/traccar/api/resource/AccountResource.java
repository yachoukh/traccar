package org.traccar.api.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.traccar.api.BaseObjectResource;
import org.traccar.model.Account;
import org.traccar.model.User;
import org.traccar.storage.StorageException;
import org.traccar.storage.query.Columns;
import org.traccar.storage.query.Condition;
import org.traccar.storage.query.Order;
import org.traccar.storage.query.Request;

import java.util.Collection;
import java.util.LinkedList;

@Path("accounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountResource extends BaseObjectResource<Account> {

    public AccountResource() {
        super(Account.class);
    }

    @GET
    public Collection<Account> get(
            @QueryParam("all") boolean all, @QueryParam("userId") long userId) throws StorageException {
        
        var conditions = new LinkedList<Condition>();

        // Only sysadmin can see all accounts
        if (!permissionsService.getUser(getUserId()).getAdministrator()) {
            if (userId == 0) {
                userId = getUserId();
            }
            User user = permissionsService.getUser(userId);
            conditions.add(new Condition.Equals("id", user.getAccountId()));
        }

        return storage.getObjects(baseClass, new Request(
                new Columns.All(), Condition.merge(conditions), new Order("name")));
    }

    @POST
    public Response add(Account entity) throws Exception {
        // Only sysadmin can create accounts
        permissionsService.checkAdmin(getUserId());
        
        if (entity.getType() == null) {
            entity.setType(Account.AccountType.SINGLE);
        }

        return super.add(entity);
    }
}

package acme.features.manager.user;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Service;

import acme.client.components.principals.UserAccount;
import acme.client.services.AbstractService;
import acme.realms.Manager;

@Service
public class ManagerUserListService extends AbstractService<Manager, UserAccount> {

	// Internal state ---------------------------------------------------------

	// @Autowired
	//private ManagerUserRepository	repository;

	private Collection<UserAccount> users;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		this.users = new ArrayList<>();
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.users, //
			"username", "identity.name", "identity.surname", "identity.email");
	}

}

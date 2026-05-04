
package acme.features.manager.newMember;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.UserAccount;
import acme.client.services.AbstractService;
import acme.realms.Manager;

@Service
public class ManagerNewMemberListService extends AbstractService<Manager, UserAccount> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerNewMemberRepository	repository;

	private Collection<UserAccount>		users;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		this.users = this.repository.findUsersWithRoles();
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {

		super.unbindObjects(this.users, //
			"username", "identity.email");

	}

}


package acme.features.authenticated.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.components.principals.UserAccount;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.realms.Manager;
import acme.realms.Member;

@Service
public class AuthenticatedManagerCreateService extends AbstractService<Authenticated, Manager> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedManagerRepository	repository;

	private Manager							manager;

	// AbstractService<Authenticated, Consumer> ---------------------------


	@Override
	public void load() {
		int userAccountId;
		UserAccount userAccount;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		userAccount = this.repository.findUserAccountById(userAccountId);

		this.manager = new Manager();
		this.manager.setUserAccount(userAccount);
	}

	@Override
	public void authorise() {
		boolean status;

		status = !super.getRequest().getPrincipal().hasRealmOfType(Manager.class);
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.manager, "position", "skills", "flag");
		UserAccount cuenta = this.manager.getUserAccount();

		if (cuenta != null) {
			Member member = this.repository.findMemberById(cuenta.getId());
			if (member == null) {
				member = super.newObject(Member.class);
				member.setUserAccount(cuenta);
				this.repository.save(member);
			}
		}
	}

	@Override
	public void validate() {
		super.validateObject(this.manager);
	}

	@Override
	public void execute() {
		this.repository.save(this.manager);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.manager, "position", "skills", "flag");
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}

}

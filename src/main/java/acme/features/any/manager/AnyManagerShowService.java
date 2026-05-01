
package acme.features.any.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.realms.Manager;

@Service
public class AnyManagerShowService extends AbstractService<Any, Manager> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyManagerRepository	repository;

	private Manager					manager;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", Integer.class);
		this.manager = this.repository.findManagerById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.manager != null;

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.manager, "position", "skills", "flag");
	}

}

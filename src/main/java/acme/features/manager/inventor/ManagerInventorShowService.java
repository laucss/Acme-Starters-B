
package acme.features.manager.inventor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.realms.Inventor;
import acme.realms.Manager;

@Service
public class ManagerInventorShowService extends AbstractService<Manager, Inventor> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerInventorRepository	repository;

	private Inventor					inventor;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);

		this.inventor = this.repository.findInventorById(id);

	}

	@Override
	public void authorise() {
		boolean status;
		status = this.inventor != null;
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {

		super.unbindObject(this.inventor, "bio", "keyWords", "licensed");

	}

}

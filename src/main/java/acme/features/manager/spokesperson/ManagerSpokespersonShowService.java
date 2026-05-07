
package acme.features.manager.spokesperson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.realms.Manager;
import acme.realms.Spokesperson;

@Service
public class ManagerSpokespersonShowService extends AbstractService<Manager, Spokesperson> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerSpokespersonRepository	repository;

	private Spokesperson					spokesperson;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);

		this.spokesperson = this.repository.findSpokespersonById(id);

	}

	@Override
	public void authorise() {
		boolean status;
		status = this.spokesperson != null;
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {

		super.unbindObject(this.spokesperson, "cv", "achievements", "licensed");

	}

}

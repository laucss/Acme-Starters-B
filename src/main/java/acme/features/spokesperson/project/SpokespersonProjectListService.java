
package acme.features.spokesperson.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.realms.Spokesperson;

@Service
public class SpokespersonProjectListService extends AbstractService<Spokesperson, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SpokespersonProjectRepository	repository;

	private Collection<Project>				projects;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int userAccountId;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.projects = this.repository.findProjectsBySpokesperson(userAccountId);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.projects, //
			"title", "keyWords", "draftMode");
	}

}

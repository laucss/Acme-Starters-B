
package acme.features.manager.dashboard;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.forms.Dashboard;
import acme.realms.Manager;

@Service
public class ManagerDashboardShowService extends AbstractService<Manager, Dashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerDashboardRepository	repository;

	private Dashboard					dashboard;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {

		int managerId;

		managerId = super.getRequest().getPrincipal().getActiveRealm().getId();

		Integer totalProjects;
		Double projectDeviation;
		Double minEffort;
		Double maxEffort;
		Double avgEffort;
		Double effortDeviation;

		Collection<Project> managerProjects = this.repository.findProjectsByManagerId(managerId);
		Double avgProjectsByOthers = this.repository.avgProjectsByOtherManagers(managerId);

		// en el caso de que solo haya un manager en toda la aplicación 
		if (avgProjectsByOthers == null)
			avgProjectsByOthers = 0.;

		totalProjects = this.repository.totalNumberOfProjectByManager(managerId);
		projectDeviation = Math.abs(totalProjects - avgProjectsByOthers);
		minEffort = managerProjects.stream().mapToDouble(Project::getPersonMonths).min().orElse(0.0);
		maxEffort = managerProjects.stream().mapToDouble(Project::getPersonMonths).max().orElse(0.0);
		avgEffort = managerProjects.stream().mapToDouble(Project::getPersonMonths).average().orElse(0.0);
		effortDeviation = Math.sqrt(managerProjects.stream().mapToDouble(p -> Math.pow(p.getPersonMonths() - avgEffort, 2)).average().orElse(0.0));

		this.dashboard = super.newObject(Dashboard.class);
		this.dashboard.setTotalProjects(totalProjects);
		this.dashboard.setProjectDeviation(projectDeviation);
		this.dashboard.setMinEffort(minEffort);
		this.dashboard.setMaxEffort(maxEffort);
		this.dashboard.setAvgEffort(avgEffort);
		this.dashboard.setEffortDeviation(effortDeviation);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.dashboard, //
			"totalProjects", "projectDeviation", // 
			"minEffort", "maxEffort", //
			"avgEffort", "effortDeviation");
	}

}

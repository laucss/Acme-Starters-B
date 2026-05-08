
package acme.features.member.strategy;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.strategies.Strategy;
import acme.features.member.project.MemberProjectRepository;
import acme.realms.Fundraiser;
import acme.realms.Member;

@Service
public class MemberStrategyListService extends AbstractService<Member, Strategy> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private MemberStrategyRepository	repository;

	private Collection<Strategy>		strategies;

	@Autowired
	private MemberProjectRepository		projectRepository;
	private Project						project;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int projectId;

		projectId = super.getRequest().getData("projectId", int.class);
		this.strategies = this.repository.findStrategiesByProjectId(projectId);
		this.project = this.projectRepository.findProjectById(projectId);
	}

	@Override
	public void authorise() {
		boolean status = false;

		int memberId = super.getRequest().getPrincipal().getActiveRealm().getId();

		if (this.project != null) {

			boolean isPublished = !this.project.getDraftMode();

			Integer count = this.repository.checkProjectBelongsToMember(this.project.getId(), memberId);
			boolean isMember = count != null && count > 0;

			status = isPublished || isMember;
		}

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.strategies, //
			"ticker", "name", "draftMode", "expectedPercentage");
		super.unbindGlobal("draftMode", this.project.getDraftMode());
		boolean isFundraiser = super.getRequest().getPrincipal().getRealms().stream().anyMatch(Fundraiser.class::isInstance);
		if (super.getRequest().hasData("projectId")) {
			super.unbindGlobal("isFundraiser", isFundraiser);
			super.unbindGlobal("projectId", this.project.getId());
		}
	}

}

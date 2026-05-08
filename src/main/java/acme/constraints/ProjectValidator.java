
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.projects.Project;
import acme.entities.projects.ProjectRepository;

@Validator
public class ProjectValidator extends AbstractValidator<ValidProject, Project> {

	// Internal state ---------------------------------------------------------
	@Autowired
	private ProjectRepository repository;

	// ConstraintValidator interface ------------------------------------------


	@Override
	protected void initialise(final ValidProject annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Project project, final ConstraintValidatorContext context) {
		// HINT: job can be null
		assert context != null;

		boolean result;

		if (project == null)
			result = true;
		else {

			{
				boolean kickOffIsBeforecloseOut;
				if (project.getKickOff() != null && project.getCloseOut() != null) {
					kickOffIsBeforecloseOut = MomentHelper.isBefore(project.getKickOff(), project.getCloseOut());

					super.state(context, kickOffIsBeforecloseOut, "*", "acme.validation.invalid-project-time-interval.message");
				}

				{
					boolean publishedProjectsHasAtLeastOneInvention;
					Long totalInventions = this.repository.totalInventionsByProject(project.getId());

					Long inventions = totalInventions == null ? 0 : totalInventions;
					publishedProjectsHasAtLeastOneInvention = Boolean.TRUE.equals(project.getDraftMode()) || inventions > 0;

					super.state(context, publishedProjectsHasAtLeastOneInvention, "*", "acme.validation.project.published-without-invention.message");
				}

				{
					boolean onlyPublishedProjectsHasReports;
					Long totalReports = this.repository.totalReportsByProject(project.getId());

					Long reports = totalReports == null ? 0 : totalReports;
					onlyPublishedProjectsHasReports = Boolean.TRUE.equals(!project.getDraftMode()) || reports == 0;

					super.state(context, onlyPublishedProjectsHasReports, "*", "acme.validation.project.only-published-with-reports.message");
				}

				{
					boolean onlyPublishedProjectsHasSponsorships;
					Long totalSponsorships = this.repository.totalSponsorshipsByProject(project.getId());

					Long sponsorships = totalSponsorships == null ? 0 : totalSponsorships;
					onlyPublishedProjectsHasSponsorships = Boolean.TRUE.equals(!project.getDraftMode()) || sponsorships == 0;

					super.state(context, onlyPublishedProjectsHasSponsorships, "*", "acme.validation.project.only-published-with-sponsorships.message");
				}

				{
					boolean onlyProjectsCanHaveComponentsIftThereAreMembers;
					Long totalMembers = this.repository.totalMembersByProjectId(project.getId());
					if (totalMembers <= 1) {

						Long totalStrategies = this.repository.totalStrategiesByProject(project.getId());
						Long totalCampaigns = this.repository.totalCampaignsByProject(project.getId());
						Long totalInventions = this.repository.totalInventionsByProject(project.getId());

						Long totalComponents = totalStrategies + totalCampaigns + totalInventions;
						Long components = totalComponents == null ? 0 : totalComponents;
						onlyProjectsCanHaveComponentsIftThereAreMembers = components == 0;

						super.state(context, onlyProjectsCanHaveComponentsIftThereAreMembers, "*", "acme.validation.project.effortNotCero-without-members.message");

					}

				}

			}

			result = !super.hasErrors(context);
		}

		return result;
	}

}

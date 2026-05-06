
package acme.features.any.report;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audits.Report;
import acme.entities.projects.Project;

@Repository
public interface AnyReportRepository extends AbstractRepository {

	@Query("select ar from Report ar where ar.draftMode = false")
	Collection<Report> findPublishedReports();

	@Query("select ar from Report ar where ar.id = :id")
	Report findReportById(int id);

	@Query("select r from Report r where r.project.id = :projectId ")
	Collection<Report> findReportsByProjectId(int projectId);

	@Query("select p from Project p where p.id = :projectId")
	Project findProjectById(int projectId);

}

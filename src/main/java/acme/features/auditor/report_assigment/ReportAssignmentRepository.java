
package acme.features.auditor.report_assigment;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audits.Report;
import acme.entities.projects.Project;

@Repository
public interface ReportAssignmentRepository extends AbstractRepository {

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);

	@Query("select r from Report r where r.id = :id")
	Report findReportById(int id);

	@Query("select r from Report r where r.auditor.id = :auditorId and r.project is null and r.draftMode = false ")
	Collection<Report> findAvailableReportsByAuditorId(int auditorId);
}

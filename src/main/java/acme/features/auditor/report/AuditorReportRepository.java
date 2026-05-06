
package acme.features.auditor.report;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audits.Report;
import acme.entities.audits.Section;
import acme.entities.projects.Project;

@Repository
public interface AuditorReportRepository extends AbstractRepository {

	@Query("select r from Report r where r.id = :id")
	Report findReportById(int id);

	@Query("select r from Report r where r.auditor.id = :auditorId")
	Collection<Report> findReportsByAuditorId(int auditorId);

	@Query("select s from Section s where s.report.id = :reportId")
	Collection<Section> findSectionsByReportId(int reportId);

	@Query("select count(s) from Section s where s.report.id = :reportId")
	Long computeReportSections(int reportId);

	@Query("select r from Report r where r.auditor.id = :auditorId and r.project is null and r.draftMode = false")
	Collection<Report> findAvailableReportsByAuditorId(int auditorId);

	@Query("select p from Project p where p.id = :projectId")
	Project findProjectById(int projectId);

}

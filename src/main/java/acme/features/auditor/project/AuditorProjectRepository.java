
package acme.features.auditor.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.audits.Report;
import acme.entities.projects.Project;

public interface AuditorProjectRepository extends AbstractRepository {

	@Query("select r.project from Report r where r.auditor.id = :auditorId and r.project is not null")
	Collection<Project> findProjectsByAuditorId(int auditorId);

	@Query("select p from Project p where p.id = :projectId")
	Project findProjectById(int projectId);

	@Query("select r from Report r where r.project.id = :projectId")
	Collection<Report> findReportsByProjectId(int projectId);

}

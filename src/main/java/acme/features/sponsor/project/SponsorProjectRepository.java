
package acme.features.sponsor.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;

public interface SponsorProjectRepository extends AbstractRepository {

	@Query("select s.project from Sponsorship s where s.sponsor.id = :sponsorId and s.project is not null")
	Collection<Project> findProjectsBySponsorId(int sponsorId);

	@Query("select p from Project p where p.id = :projectId")
	Project findProjectById(int projectId);

}

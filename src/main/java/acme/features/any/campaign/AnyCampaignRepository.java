
package acme.features.any.campaign;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaigns.Campaign;
import acme.entities.projects.Project;

@Repository
public interface AnyCampaignRepository extends AbstractRepository {

	@Query("select c from Campaign c where c.draftMode = false")
	Collection<Campaign> findPublishedCampaigns();

	@Query("select c from Campaign c where c.id = :id")
	Campaign findCampaignById(int id);

	@Query("select c from Campaign c where c.project.id = :projectId")
	Collection<Campaign> findCampaignsByProjectId(int projectId);

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);

}

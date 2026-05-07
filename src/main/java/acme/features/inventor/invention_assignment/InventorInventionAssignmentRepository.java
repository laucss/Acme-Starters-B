
package acme.features.inventor.invention_assignment;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.inventions.Invention;
import acme.entities.projects.Project;

@Repository
public interface InventorInventionAssignmentRepository extends AbstractRepository {

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);

	@Query("select i from Invention i where i.id = :id")
	Invention findInventionById(int id);

	@Query("select i from Invention i where i.inventor.id = :inventorId and i.project is null")
	Collection<Invention> findAvailableInventionByInventorId(int inventorId);
}

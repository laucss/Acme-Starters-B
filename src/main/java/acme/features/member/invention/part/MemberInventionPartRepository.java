
package acme.features.member.invention.part;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.inventions.Part;

@Repository
public interface MemberInventionPartRepository extends AbstractRepository {

	@Query("select p from Part p where p.invention.id = :id")
	Collection<Part> findAllPartByInventionId(int id);

	@Query("select p from Part p where p.id=:id")
	Part findPartById(int id);
}

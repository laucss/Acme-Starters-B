
package acme.features.manager.user;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.components.principals.UserAccount;
import acme.client.repositories.AbstractRepository;

@Repository
public interface ManagerUserRepository extends AbstractRepository {

	@Query("select distinct r.userAccount from AbstractRole r where type(r) in (Fundraiser, Inventor, Spokesperson)")
	Collection<UserAccount> findUsersWithRoles();

}

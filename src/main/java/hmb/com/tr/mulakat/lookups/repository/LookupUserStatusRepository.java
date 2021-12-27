
package hmb.com.tr.mulakat.lookups.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import hmb.com.tr.mulakat.lookups.entity.LookupUserStatus;

@RepositoryRestResource(collectionResourceRel = "userstatus", path = "userstatus", exported = true)
public interface LookupUserStatusRepository
		extends
			CrudRepository<LookupUserStatus, Integer> {

}


package hmb.com.tr.mulakat.lookups.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import hmb.com.tr.mulakat.lookups.entity.LookupTodoStatus;

@RepositoryRestResource(collectionResourceRel = "todostatus", path = "todostatus", exported = true)
public interface LookupTodoStatusRepository
		extends
			CrudRepository<LookupTodoStatus, Integer> {

}

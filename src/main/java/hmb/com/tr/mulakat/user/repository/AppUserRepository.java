package hmb.com.tr.mulakat.user.repository;

import java.util.List;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;

import hmb.com.tr.mulakat.user.entity.AppUser;
import hmb.com.tr.mulakat.user.entity.QAppUser;

@RepositoryRestResource(collectionResourceRel = "users", path = "users", exported = true)
public interface AppUserRepository
		extends
			QuerydslPredicateExecutor<AppUser>,
			PagingAndSortingRepository<AppUser, Long>,
			QuerydslBinderCustomizer<QAppUser> {

	default void customize(QuerydslBindings bindings, QAppUser obj) {
		// Exclude id from filtering
		bindings.excluding(obj.id);
		// Make case-insensitive 'like' filter for all string properties
		bindings.bind(String.class).first(
				(SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
	}
	List<AppUser> findByEmail(String email);
}
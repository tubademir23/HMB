package hmb.com.tr.mulakat.user.repository;

import java.util.List;

import org.bitbucket.gt_tech.spring.data.querydsl.value.operators.ExpressionProviderFactory;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;

import hmb.com.tr.mulakat.user.entity.AppUser;
import hmb.com.tr.mulakat.user.entity.QTodo;
import hmb.com.tr.mulakat.user.entity.Todo;

@RepositoryRestResource(collectionResourceRel = "todos", path = "todos", exported = true)
public interface TodoRepository
		extends
			QuerydslPredicateExecutor<Todo>,
			PagingAndSortingRepository<Todo, Long>,
			QuerydslBinderCustomizer<QTodo> {

	default void customize(QuerydslBindings bindings, QTodo obj) {
		bindings.excluding(obj.id);
		// Make case-insensitive 'like' filter for all string properties
		bindings.bind(String.class).first(
				(SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);

		bindings.bind(obj.status.id).all((path,
				value) -> ExpressionProviderFactory.getPredicate(path, value));
	}
	List<Todo> findByUser(AppUser user);
}

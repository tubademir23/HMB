package hmb.com.tr.mulakat.user.projection;

import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import hmb.com.tr.mulakat.user.entity.AppUser;
import hmb.com.tr.mulakat.user.entity.Todo;

@Projection(name = "appUser", types = {AppUser.class})
public interface AppUserProjection {

	String getName();
	String getEmail();

	@Value("#{(target.gender != null ? target.gender.name : '') }")
	String getGenderName();

	@Value("#{(target.status != null ? target.status.value : '') }")
	String getStatusName();

	@Value("#{(target.todos != null ? target.todos.size : 0) }")
	String getTodoCount();

	Set<Todo> getTodos();
}

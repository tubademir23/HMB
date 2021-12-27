package hmb.com.tr.mulakat.user.events;

import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.querydsl.core.util.StringUtils;

import hmb.com.tr.mulakat.lookups.repository.LookupTodoStatusRepository;
import hmb.com.tr.mulakat.user.entity.Todo;
import hmb.com.tr.mulakat.user.repository.AppUserRepository;
import hmb.com.tr.mulakat.user.repository.TodoRepository;

@Component
@RepositoryEventHandler
public class TodoEventHandler {

	private static String validate(Todo todo, TodoRepository todoRepository,
			AppUserRepository userRepository,
			LookupTodoStatusRepository todoStatusRepository) {
		if (StringUtils.isNullOrEmpty(todo.getTitle())) {
			return "Title is necessary";
		}
		if (ObjectUtils.isEmpty(todo.getStatus()))
			return "Status is necessary";
		if (ObjectUtils.isEmpty(
				todoStatusRepository.findById(todo.getStatus().getId())))
			return "Invalid status";
		if (ObjectUtils.isEmpty(todo.getUser()))
			return "User is necessary";
		if (ObjectUtils
				.isEmpty(userRepository.findById(todo.getUser().getId())))
			return "Invalid user";
		return "";
	}

	@HandleBeforeSave
	public static String handleBeforeSave(Todo todo,
			TodoRepository todoRepository, AppUserRepository userRepository,
			LookupTodoStatusRepository todoStatusRepository) {
		String msg = validate(todo, todoRepository, userRepository,
				todoStatusRepository);
		return msg;
	}

}

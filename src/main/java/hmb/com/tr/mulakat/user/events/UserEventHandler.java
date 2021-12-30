package hmb.com.tr.mulakat.user.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.querydsl.core.util.StringUtils;

import hmb.com.tr.mulakat.exception.DependencyException;
import hmb.com.tr.mulakat.exception.InvalidInputException;
import hmb.com.tr.mulakat.exception.entity.ErrorInfo;
import hmb.com.tr.mulakat.lookups.repository.LookupUserStatusRepository;
import hmb.com.tr.mulakat.user.entity.AppUser;
import hmb.com.tr.mulakat.user.entity.Todo;
import hmb.com.tr.mulakat.user.repository.AppUserRepository;

@Component
@RepositoryEventHandler
public class UserEventHandler {

	@Autowired
	private AppUserRepository userRepository;
	@Autowired
	private LookupUserStatusRepository userStatusRepository;

	public UserEventHandler() {
		super();
	}

	private void validateUser(AppUser user) {
		List<ErrorInfo> data = new ArrayList<>();
		if (StringUtils.isNullOrEmpty(user.getName())) {

			// "code": "UNPROCESSABLE_ENTITY",
			// "field": "name",
			// "message": "can't be blank"
			data.add(new ErrorInfo(HttpStatus.UNPROCESSABLE_ENTITY.name(),
					"name", "can't be blank"));
		}
		if (StringUtils.isNullOrEmpty(user.getEmail())) {
			data.add(new ErrorInfo(HttpStatus.UNPROCESSABLE_ENTITY.name(),
					"email", "can't be blank"));
		}
		if (!StringUtils.isNullOrEmpty(user.getEmail()) && CollectionUtils
				.isNotEmpty(userRepository.findByEmail(user.getEmail()))) {
			data.add(new ErrorInfo(HttpStatus.CONFLICT.name(), "email",
					"email is used"));
		}
		if (ObjectUtils.isEmpty(user.getStatus())) {
			data.add(new ErrorInfo(HttpStatus.UNPROCESSABLE_ENTITY.name(),
					"status", "can't be blank"));
		}
		if (!ObjectUtils.isEmpty(user.getStatus()) && ObjectUtils.isEmpty(
				userStatusRepository.findById(user.getStatus().getId())))
			data.add(new ErrorInfo(HttpStatus.UNPROCESSABLE_ENTITY.name(),
					"status", "invalid record"));
		if (!CollectionUtils.isEmpty(data)) {
			throw new InvalidInputException(data);
		}
	}

	@HandleBeforeSave
	public void handleAppUserBeforeSave(AppUser user) {
		validateUser(user);
	}
	// @TODO look for why not invoked
	@HandleBeforeCreate
	public void handleAppUserBeforeCreate(AppUser user) {
		validateUser(user);
	}
	@HandleBeforeDelete
	public void handleAppUserBeforeDelete(AppUser user) {
		Set<Todo> todos = user.getTodos();
		if (CollectionUtils.isNotEmpty(todos)) {
			ErrorInfo error = new ErrorInfo(HttpStatus.FAILED_DEPENDENCY.name(),
					"todos", "found child records");
			throw new DependencyException(error);
		}
	}
	private void validateTodo(Todo todo) {
		List<ErrorInfo> data = new ArrayList<>();
		if (StringUtils.isNullOrEmpty(todo.getTitle())) {
			data.add(new ErrorInfo(HttpStatus.UNPROCESSABLE_ENTITY.name(),
					"title", "can't be blank"));
		}
		if (ObjectUtils.isEmpty(todo.getStatus())) {
			data.add(new ErrorInfo(HttpStatus.UNPROCESSABLE_ENTITY.name(),
					"status", "can't be blank"));
		}
		if (!ObjectUtils.isEmpty(todo.getStatus()) && ObjectUtils.isEmpty(
				userStatusRepository.findById(todo.getStatus().getId())))
			data.add(new ErrorInfo(HttpStatus.UNPROCESSABLE_ENTITY.name(),
					"status", "invalid record"));
		if (ObjectUtils.isEmpty(todo.getUser())) {
			data.add(new ErrorInfo(HttpStatus.UNPROCESSABLE_ENTITY.name(),
					"user", "can't be blank"));
		}
		if (!ObjectUtils.isEmpty(todo.getUser()) && ObjectUtils
				.isEmpty(userRepository.findById(todo.getUser().getId())))
			data.add(new ErrorInfo(HttpStatus.UNPROCESSABLE_ENTITY.name(),
					"user", "invalid record"));
		if (!CollectionUtils.isEmpty(data)) {
			throw new InvalidInputException(data);
		}
	}

	@HandleBeforeSave
	public void handleTodoBeforeSave(Todo todo) {
		validateTodo(todo);
	}
	// @TODO look for why not invoked
	@HandleBeforeCreate
	public void handleTodoBeforeCreate(Todo todo) {
		validateTodo(todo);
	}
}

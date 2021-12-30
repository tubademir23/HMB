package hmb.com.tr.mulakat.user.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hmb.com.tr.mulakat.user.entity.AppUser;
import hmb.com.tr.mulakat.user.entity.Todo;
import hmb.com.tr.mulakat.user.repository.AppUserRepository;
import hmb.com.tr.mulakat.user.repository.TodoRepository;
@RestController
@RequestMapping("/api/users")
public class AppUserController {
	@Autowired
	private AppUserRepository userRepository;
	@Autowired
	private TodoRepository todoRepository;

	public AppUserController() {
	}

	/* add todos as array */
	@Transactional
	@RequestMapping(value = "/{userId}/todos", method = {RequestMethod.POST,
			RequestMethod.PATCH,
			RequestMethod.PUT}, consumes = "application/json")
	public ResponseEntity<?> kitap(HttpServletRequest request,
			@PathVariable("userId") Long userId,
			@RequestBody() List<Todo> todos) {
		try {

			AppUser user = userRepository.findById(userId).orElseThrow(
					() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
							"User Not Found"));
			// for put operation, delete before records
			String op = request.getMethod();
			if (op.equalsIgnoreCase("PUT")) {
				List<Todo> removedTodos = todoRepository.findByUser(user);
				removedTodos.stream().forEach(item -> {
					item.setUser(null);
					todoRepository.delete(item);
				});
			}
			Set<Todo> todoSaved = new HashSet<Todo>();
			if (todos != null) {
				for (Todo item : todos) {
					item.setUser(user);
					Todo saved = todoRepository.save(item);
					todoSaved.add(saved);
				} ;
			}
			Map<String, Object> result = new HashMap<>();
			result.put("success", true);
			result.put("data", todoSaved);
			result.put("message", "Todo list saved succesfully.");
			return new ResponseEntity<>(result, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
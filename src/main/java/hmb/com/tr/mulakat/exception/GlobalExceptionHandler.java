package hmb.com.tr.mulakat.exception;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolationException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Global Exception Handler
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(TransactionSystemException.class)
	protected ResponseEntity<List<String>> handleTransactionException(
			TransactionSystemException ex) throws Throwable {
		Throwable cause = ex.getCause();
		if (!(cause instanceof RollbackException))
			throw cause;
		if (!(cause.getCause() instanceof ConstraintViolationException))
			throw cause.getCause();
		ConstraintViolationException validationException = (ConstraintViolationException) cause
				.getCause();
		List<String> messages = validationException.getConstraintViolations()
				.stream()
				.map(constraintViolation -> constraintViolation.getMessage())
				.collect(Collectors.toList());
		return new ResponseEntity<>(messages, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	protected ResponseEntity<?> handleResourceNotFoundException(
			ResourceNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("Resource not found");
	}

	@ExceptionHandler(ConflictException.class)
	protected ResponseEntity<?> handleConflictException(ConflictException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}

	@ExceptionHandler(ResponseStatusException.class)
	protected ResponseEntity<?> handleResponseStatusException(
			ResponseStatusException ex) {
		return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
	}
}

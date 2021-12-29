package hmb.com.tr.mulakat.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import hmb.com.tr.mulakat.exception.entity.ErrorInfo;

@ResponseStatus(value = HttpStatus.FAILED_DEPENDENCY)
public class DependencyException extends GlobalException {

	private static final long serialVersionUID = -2L;
	public DependencyException(ErrorInfo error) {
		super(error);
	}
}
package hmb.com.tr.mulakat.exception;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import hmb.com.tr.mulakat.exception.entity.ErrorInfo;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidInputException extends GlobalException {

	private static final long serialVersionUID = -2L;
	public InvalidInputException(List<ErrorInfo> data) {
		super(data);
	}
}
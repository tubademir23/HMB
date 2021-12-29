package hmb.com.tr.mulakat.exception.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"code", "field", "message"})
public class ErrorInfo {

	private String code;

	private String field;

	private String message;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ErrorInfo(String code, String field, String message) {
		super();
		this.code = code;
		this.field = field;
		this.message = message;
	}

}
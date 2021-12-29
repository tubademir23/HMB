package hmb.com.tr.mulakat.exception;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hmb.com.tr.mulakat.exception.entity.ErrorInfo;
public abstract class GlobalException extends RuntimeException {

	private static final long serialVersionUID = -1L;

	private List<ErrorInfo> data;

	public List<ErrorInfo> getData() {
		return this.data;
	}
	public GlobalException() {

	}
	public GlobalException(List<ErrorInfo> data) {
		super();
		this.data = data;
	}
	public GlobalException(ErrorInfo error) {
		super();
		this.data = new ArrayList<ErrorInfo>();
		this.data.add(error);
	}
	public GlobalException(String message, List<ErrorInfo> errors) {
		super(message);
		this.data = errors;
	}
	public HashMap<String, Object> getDataJson() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("data", data);
		return map;
	}
}
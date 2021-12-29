package hmb.com.tr.mulakat.user.events;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

import com.querydsl.core.util.StringUtils;

import hmb.com.tr.mulakat.lookups.repository.LookupUserStatusRepository;
import hmb.com.tr.mulakat.user.entity.AppUser;
import hmb.com.tr.mulakat.user.repository.AppUserRepository;

@Component
@RepositoryEventHandler
public class AppUserEventHandler {

	public AppUserEventHandler() {
		super();
	}

	private static String validate(AppUser user,
			AppUserRepository userRepository,
			LookupUserStatusRepository userStatusRepository) {
		if (StringUtils.isNullOrEmpty(user.getName())) {
			return "Name is necessary";
		}
		if (StringUtils.isNullOrEmpty(user.getEmail())) {
			return "Email is necessary";
		}
		if (CollectionUtils
				.isNotEmpty(userRepository.findByEmail(user.getEmail())))
			return "Email is used";
		if (ObjectUtils.isEmpty(user.getStatus()))
			return "Status is necessary";
		if (ObjectUtils.isEmpty(
				userStatusRepository.findById(user.getStatus().getId())))
			return "Invalid status";
		return "";
	}

	@HandleBeforeSave
	public static String handleAppUserBeforeSave(AppUser user,
			AppUserRepository userRepository,
			LookupUserStatusRepository userStatusRepository) {
		String msg = validate(user, userRepository, userStatusRepository);
		return msg;
	}
	// @TODO look for why not invoked
	@HandleBeforeCreate
	public static void handleAppUserBeforeCreate(AppUser user,
			AppUserRepository userRepository,
			LookupUserStatusRepository userStatusRepository) {
		String msg = validate(user, userRepository, userStatusRepository);
		if (!StringUtils.isNullOrEmpty(msg)) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, msg);
		}
	}
}

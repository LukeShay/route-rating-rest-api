package com.lukeshay.restapi.config;

import com.lukeshay.restapi.utils.AuthenticationUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		String userId = "";

		try {
			userId = AuthenticationUtils.getUser(SecurityContextHolder.getContext().getAuthentication()).getId();
		} catch (Exception ignore) {
		}
		return Optional.of(userId);
	}
}

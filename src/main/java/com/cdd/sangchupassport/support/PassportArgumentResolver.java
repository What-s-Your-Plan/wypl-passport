package com.cdd.sangchupassport.support;

import static com.cdd.sangchupassport.support.SangchuHeader.*;

import java.util.Objects;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.cdd.sangchupassport.Passport;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PassportArgumentResolver implements HandlerMethodArgumentResolver {
	private final ObjectMapper mapper;
	private final PassportValidator passportValidator;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		boolean hasParameterAnnotation = parameter.hasParameterAnnotation(RequestPassport.class);
		boolean assignableFrom = Passport.class.isAssignableFrom(parameter.getParameterType());
		return hasParameterAnnotation && assignableFrom;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
		ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest,
		WebDataBinderFactory binderFactory) throws Exception {
		Passport passport = mapper.readValue(
			Objects.requireNonNull(
				webRequest.getNativeRequest(
					HttpServletRequest.class)
			).getHeader(SANGCHU_PASSPORT.getName()),
			Passport.class);
		passportValidator.validatePassport(passport);
		passport.makeHttpHeaders(mapper);
		return passport;
	}
}

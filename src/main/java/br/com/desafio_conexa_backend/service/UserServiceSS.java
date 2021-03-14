package br.com.desafio_conexa_backend.service;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.desafio_conexa_backend.security.UserSS;

public class UserServiceSS {

	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch (Exception e) {
			return null;
		}
	}
}

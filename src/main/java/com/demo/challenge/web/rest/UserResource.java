package com.demo.challenge.web.rest;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.demo.challenge.domain.UserSession;
import com.demo.challenge.security.TokenProvider;
import com.demo.challenge.service.UserService;
import com.demo.challenge.util.HeaderUtil;
import com.demo.challenge.web.rest.vm.UserLoginVM;

/**
 * REST controller for managing user authentication
 * 
 * @author Prashant
 *
 */

@RestController
@RequestMapping("/api/user")
public class UserResource {

	private final Logger log = LoggerFactory.getLogger(UserResource.class);

	private final TokenProvider tokenProvider;
	private final AuthenticationManager authenticationManager;
	private final UserService userService;

	public UserResource(TokenProvider tokenProvider,
			AuthenticationManager authenticationManager, UserService userService) {
		this.tokenProvider = tokenProvider;
		this.authenticationManager = authenticationManager;
		this.userService = userService;
	}

	/**
	 * POST /login : User login.
	 *
	 * @param userLoginVM
	 *            the user login View Model
	 * @return the ResponseEntity with Authorization Token with status 200
	 *         (Success) if the user is logged in or 400 (Bad Request) if the
	 *         token generation fails
	 */
	@PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<JWTToken> login(
			@Valid @RequestBody UserLoginVM userLoginVM,
			HttpServletResponse response) {

		log.debug("REST request to login with data : {}", userLoginVM);

		/** Creating JWT token */
		/*
		 * UsernamePasswordAuthenticationToken authenticationToken = new
		 * UsernamePasswordAuthenticationToken(userLoginVM.getUsername(),
		 * userLoginVM.getDeviceType());
		 */
		// Authentication authentication =
		// this.authenticationManager.authenticate(authenticationToken);
		// SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = tokenProvider.createToken(userLoginVM.getUsername());

		/** Save User Session object */
		userService.persistSession(userLoginVM, jwt);

		response.addHeader("Authorization", "Bearer " + jwt);
		return ResponseEntity.ok(new JWTToken(jwt));
	}

	/**
	 * DELETE /user/:logout : delete the "login" User session
	 *
	 * @param authorization
	 *            the authorization token of the user to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping(path = "/logout")
	@Timed
	public ResponseEntity<Void> logout(
			@Valid @RequestHeader("Authorization") String authorization) {
		log.debug("REST request to delete User Session for key: {}",
				authorization);

		userService.deleteSession(authorization);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createAlert("session.deleted", "")).build();
	}

	/**
	 * GET /user/:details : get the "login" user details.
	 *
	 * @param authorization
	 *            the token of the user to find
	 * @return the ResponseEntity with status 200 (OK) and with body the "login"
	 *         user, or with status 404 (Not Found)
	 */
	@GetMapping(path = "/details", produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<UserSession> getCurrentUserDetails(
			@Valid @RequestHeader("Authorization") String authorization) {
		log.debug("REST request to get Current user details for key: {}",
				authorization);
		UserSession userSession = userService.getDetails(authorization);

		if (userSession != null) {
			return ResponseEntity.ok(userSession);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}

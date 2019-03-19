package com.itsol.smartweb32.controller;

import java.net.URI;
import java.util.Collections;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.itsol.smartweb32.exception.AppException;
import com.itsol.smartweb32.model.RoleName;
import com.itsol.smartweb32.model.Roles;
import com.itsol.smartweb32.model.Users;
import com.itsol.smartweb32.payload.ApiResponse;
import com.itsol.smartweb32.payload.JwtAuthenticationResponse;
import com.itsol.smartweb32.payload.LoginRequest;
import com.itsol.smartweb32.payload.SignUpByAdminRequest;
import com.itsol.smartweb32.payload.SignUpRequest;
import com.itsol.smartweb32.repository.RoleRepository;
import com.itsol.smartweb32.repository.UserRepository;
import com.itsol.smartweb32.security.JwtTokenProvider;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtTokenProvider tokenProvider;

	@CrossOrigin
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenProvider.generateToken(authentication);
		Long userID = tokenProvider.getUserIdFromJWT(jwt);
		// return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, userID));
	}

	@CrossOrigin
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return new ResponseEntity(new ApiResponse(false, "Username is already taken!"), HttpStatus.BAD_REQUEST);
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
		}
		Users user = new Users(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
				signUpRequest.getPassword());

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		Roles userRole = roleRepository.findByName(RoleName.ROLE_USER)
				.orElseThrow(() -> new AppException("User Role not set."));

		user.setRoleses(Collections.singleton(userRole));

		Users result = userRepository.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{username}")
				.buildAndExpand(result.getUsername()).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@CrossOrigin
	@PostMapping("/signupbyadmin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> registerByAdmin(@Valid @RequestBody SignUpByAdminRequest sigupByAdminRequest)
			throws Exception {
		if (userRepository.existsByUsername(sigupByAdminRequest.getUsername())) {
			return new ResponseEntity(new ApiResponse(false, "Username is already taken!"), HttpStatus.BAD_REQUEST);
		}

		if (userRepository.existsByEmail(sigupByAdminRequest.getEmail())) {
			return new ResponseEntity(new ApiResponse(false, "Email is alreadyy taken!"), HttpStatus.BAD_REQUEST);
		}

		Users users = new Users(sigupByAdminRequest.getName(), sigupByAdminRequest.getUsername(),
				sigupByAdminRequest.getFullname(), sigupByAdminRequest.getEmail(), sigupByAdminRequest.getPassword(),
				sigupByAdminRequest.getMobile(), sigupByAdminRequest.getAddress(), sigupByAdminRequest.getAddress(),
				sigupByAdminRequest.getRoles(), sigupByAdminRequest.getSex(), sigupByAdminRequest.getBirthday());

		users.setPassword(passwordEncoder.encode(users.getPassword()));

		Roles userRole = new Roles();

		if (sigupByAdminRequest.getRoles().toString().equals(RoleName.ROLE_ADMIN.toString())) {
			userRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
					.orElseThrow(() -> new Exception("User role not set"));
		} else if (sigupByAdminRequest.getRoles().toString().equals(RoleName.ROLE_USER.toString())) {
			userRole = roleRepository.findByName(RoleName.ROLE_USER)
					.orElseThrow(() -> new Exception("User role not set"));
		}

		users.setRoleses(Collections.singleton(userRole));

		Users result = userRepository.save(users);

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{username}")
				.buildAndExpand(result.getUsername()).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(true, "User register success"));

	}
}

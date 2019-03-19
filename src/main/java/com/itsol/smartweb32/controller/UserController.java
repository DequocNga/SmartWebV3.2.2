package com.itsol.smartweb32.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itsol.smartweb32.model.Users;
import com.itsol.smartweb32.payload.PagedResponse;
import com.itsol.smartweb32.payload.UserPayloadExtend;
import com.itsol.smartweb32.payload.UserSummary;
import com.itsol.smartweb32.repository.RoleRepository;
import com.itsol.smartweb32.repository.UserRepository;
import com.itsol.smartweb32.security.CurrentUser;
import com.itsol.smartweb32.security.UserPrincipal;
import com.itsol.smartweb32.util.AppConstants;

@Controller
@RestController
@RequestMapping("/api")
public class UserController {

	// private static final Logger logger =
	// LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserPayloadExtend userPayloadExtend;

	@GetMapping("/user/me")
	@PreAuthorize("hasRole('USER')")
	public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
		UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(),
				currentUser.getName());
		return userSummary;
	}

	@GetMapping("/user/{id}")
	@PreAuthorize("hasRole('USER')")
	public UserSummary getCurrentUserID(@PathVariable(value = "id") long id, @CurrentUser UserPrincipal currentUser) {
		UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(),
				currentUser.getName());
		return userSummary;
	}

	@PutMapping("/user/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Users> updateCurrentUserById(@PathVariable(value = "id") Long id,
			@Valid @RequestBody Users userDetails, @CurrentUser UserPrincipal currentUsers) throws Exception {
		if (currentUsers.getId() == id) {
			Users users = userRepository.findById(id)
					.orElseThrow(() -> new Exception("Not found user with ID " + id + "."));
			users.setName(userDetails.getName());
			users.setUsername(userDetails.getUsername());
			users.setEmail(userDetails.getEmail());
			String password = passwordEncoder.encode(userDetails.getPassword());
			users.setPassword(password);
			users.setMobile(userDetails.getMobile());
			users.setAddress(userDetails.getAddress());
			users.setSkill(userDetails.getSkill());
			final Users userUpdate = userRepository.save(users);
			return ResponseEntity.ok(userUpdate);
		}
		return null;
	}

	// ROLE ADMIN

	// Delete user by ID
	@DeleteMapping("/userany/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Map<String, Boolean> deleteUserByID(@PathVariable(value = "id") Long id) throws Exception {
		Users users = userRepository.findById(id)
				.orElseThrow(() -> new Exception("Not found user with ID " + id + "."));
		userRepository.delete(users);
		Map<String, Boolean> response = new HashMap<>();
		response.put("delete", Boolean.TRUE);
		return response;
	}

	// Get any user by ID
	@GetMapping("/userany/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Users getAnyUserByID(@PathVariable(value = "id") Long id) {
		Users users = userRepository.findAnyUserByID(id);
		return users;
	}

	// Show all user

	/*
	 * @GetMapping("/userany/all")
	 * 
	 * @PreAuthorize("hasRole('ADMIN')") public ArrayList<Users> getAllUsers() {
	 * ArrayList<Users> arrayList = (ArrayList<Users>) userRepository.findAll();
	 * return arrayList; }
	 */

	@PutMapping("/userany/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Users> updateAnyUserById(@PathVariable(value = "id") Long id,
			@Valid @RequestBody Users userDetails) throws Exception {
		Users users = userRepository.findById(id).orElseThrow(() -> new Exception("Not found use with ID " + id + "."));
		users.setName(userDetails.getName());
		users.setUsername(userDetails.getUsername());
		users.setEmail(userDetails.getEmail());
		users.setPassword(passwordEncoder.encode(userDetails.getPassword()));
		users.setMobile(userDetails.getMobile());
		users.setAddress(userDetails.getAddress());
		users.setSkill(userDetails.getSkill());
		final Users users2 = userRepository.save(users);
		return ResponseEntity.ok(users2);
	}

	@GetMapping("/userany/all")
	@PreAuthorize("hasRole('ADMIN')")
	public PagedResponse<Users> getAllUsers(@CurrentUser UserPrincipal currentUser,

			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,

			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
		return userPayloadExtend.getAllUsers(currentUser, page, size);
	}
	
	@GetMapping("/userany/find/{keyword}")
	@PreAuthorize("hasRole('ADMIN')")
	public ArrayList<Users> findByKeyword(@PathVariable(value = "keyword") String keyword){
		String key = keyword + "*";
		return (ArrayList<Users>) userRepository.findByKeyword(key);
	}

}

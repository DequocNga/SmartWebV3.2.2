package com.itsol.smartweb32.payload;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.itsol.smartweb32.exception.BadRequestException;
import com.itsol.smartweb32.model.Users;
import com.itsol.smartweb32.repository.UserRepository;
import com.itsol.smartweb32.security.UserPrincipal;
import com.itsol.smartweb32.util.AppConstants;

@Service
public class UserPayloadExtend {
	

	@Autowired
	private UserRepository userRepository;

	public PagedResponse<Users> getAllUsers(UserPrincipal currentUser, int page, int size) {
		// TODO Auto-generated method stub
		validatePageNumberAndSize(page, size);
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
		Page<Users> users = userRepository.findAll(pageable);

		if (users.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), users.getNumber(), users.getSize(),
					users.getTotalElements(), users.getTotalPages(), users.isLast());
		}

		List<Users> users2 = users.getContent();

		return new PagedResponse<>(users2, users.getNumber(), users.getSize(), users.getTotalElements(),
				users.getTotalPages(), users.isLast());
	}

	private void validatePageNumberAndSize(int page, int size) {
		// TODO Auto-generated method stub
		if (page < 0) {
			throw new BadRequestException("Page number cannot be less than zero");
		}
		if (size > AppConstants.MAX_PAGE_SIZE) {
			throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
		}
	}
}

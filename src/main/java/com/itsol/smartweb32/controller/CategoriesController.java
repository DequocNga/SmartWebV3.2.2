package com.itsol.smartweb32.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itsol.smartweb32.model.Categories;
import com.itsol.smartweb32.repository.CategoriesRepository;
import com.itsol.smartweb32.security.CurrentUser;
import com.itsol.smartweb32.security.UserPrincipal;

import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/api")
public class CategoriesController {

	@Autowired
	CategoriesRepository categoriesRepository;
	
	@PostMapping("/user/addcategories")
	@PreAuthorize("hasRole('ADMIN')")
	public Categories addCategories(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody Categories categories) {
		return categoriesRepository.save(categories);
	}
}

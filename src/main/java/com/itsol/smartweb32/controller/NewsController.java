package com.itsol.smartweb32.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itsol.smartweb32.model.News;
import com.itsol.smartweb32.payload.AddNewsRequest;
import com.itsol.smartweb32.repository.CategoriesRepository;
import com.itsol.smartweb32.repository.NewsRepository;
import com.itsol.smartweb32.security.CurrentUser;
import com.itsol.smartweb32.security.UserPrincipal;

@Controller
@RestController
@RequestMapping("/api")
public class NewsController {

	@Autowired
	NewsRepository newsRepository;
	
	@Autowired
	CategoriesRepository categoriesRepository;
	
	@GetMapping("/allnews/")
	@PreAuthorize("hasRole('USER')")
	public News getAllNews() {
		News news = (News) newsRepository.findAll();
		return news;
	}
	
	@PostMapping("/addnews")
	@PreAuthorize("hasRole('USER')")
	public News addNews(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody AddNewsRequest addNewsRequest) {
		Long userId = currentUser.getId();
		News news2 = new News(addNewsRequest.getTitle(), addNewsRequest.getAvatar(), addNewsRequest.getText(), userId);
		return newsRepository.save(news2);
	}
	
	@GetMapping("/news/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<News> getNewsById(@PathVariable(value="id") Long id) throws Exception {
		News news = newsRepository.findById(id).orElseThrow(() -> new Exception("Not found news with id " + id + "."));
		return ResponseEntity.ok().body(news);
	}
}

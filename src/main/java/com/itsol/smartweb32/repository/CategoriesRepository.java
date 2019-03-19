package com.itsol.smartweb32.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itsol.smartweb32.model.Categories;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> {

}

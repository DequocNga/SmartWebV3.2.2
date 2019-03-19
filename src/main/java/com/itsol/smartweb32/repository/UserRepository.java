package com.itsol.smartweb32.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.itsol.smartweb32.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
	Optional<Users> findByEmail(String email);

	Optional<Users> findByUsernameOrEmail(String username, String email);

	List<Users> findByIdIn(long id);

	Optional<Users> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	@Query(value = "select * from users where id = ?1", nativeQuery = true)
	Users findAnyUserByID(Long id);

}

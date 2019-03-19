package com.itsol.smartweb32.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itsol.smartweb32.model.RoleName;
import com.itsol.smartweb32.model.Roles;


@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findByName(RoleName roleName);
}

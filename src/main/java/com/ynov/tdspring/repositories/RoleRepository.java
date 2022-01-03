package com.ynov.tdspring.repositories;

import com.ynov.tdspring.entities.Project;
import com.ynov.tdspring.entities.Role;
import com.ynov.tdspring.entities.User;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {


}

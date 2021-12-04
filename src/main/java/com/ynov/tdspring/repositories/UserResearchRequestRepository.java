package com.ynov.tdspring.repositories;

import com.ynov.tdspring.entities.UserRequest;
import com.ynov.tdspring.entities.UserResearchRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserResearchRequestRepository extends JpaRepository<UserResearchRequest, UUID> {

}

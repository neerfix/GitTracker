package com.ynov.tdspring.repositories;

import com.ynov.tdspring.entities.Issue;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueRepository extends JpaRepository<Issue, UUID> {

}

package com.ynov.tdspring.repositories;

import java.util.UUID; 

import ynov.tdspring.entities.Comment; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface CommentRepository extends JpaRepository<Comment, UUID> {

}
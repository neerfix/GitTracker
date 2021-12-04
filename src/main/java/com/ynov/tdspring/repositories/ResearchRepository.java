package com.ynov.tdspring.repositories;

import com.ynov.tdspring.entities.Research;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ResearchRepository extends JpaRepository<Research, UUID> {

}

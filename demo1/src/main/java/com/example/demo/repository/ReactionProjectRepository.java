package com.example.demo.repository;

import com.example.demo.entity.ReactionProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionProjectRepository extends JpaRepository<ReactionProject,Integer> {
}

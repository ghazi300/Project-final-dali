package com.example.demo.repository;

import com.example.demo.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Integer> {
    @Query("SELECT p.likes FROM Project p WHERE p.idProject = :projectId")
    int getLikesById(Integer projectId);
    @Query("SELECT p.dislikes FROM Project p WHERE p.idProject = :projectId")
    int findDislikesById(Integer projectId);
}

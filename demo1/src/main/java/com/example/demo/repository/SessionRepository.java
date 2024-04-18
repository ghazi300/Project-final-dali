package com.example.demo.repository;

import com.example.demo.dto.SessionDetails;
import com.example.demo.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    @Query("SELECT new com.example.demo.dto.SessionDetails(u.email, t.title) FROM Session s JOIN s.users u JOIN s.task t")
    List<SessionDetails> findAllSessionsWithDetails();

    List<Session> findAllByEndTimeAfter(LocalDateTime endTime);

    List<Session> findAllByEndTimeBefore(LocalDateTime endTime);
}

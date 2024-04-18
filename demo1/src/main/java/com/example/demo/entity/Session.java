package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;  // Ajout de cette ligne
    private String taskName;
    private String name;
    public Session(Users users, Task task, LocalDateTime endTime) {
        this.users = users;
        this.task = task;

        this.endTime = endTime;
    }
    // Ajoutez ces méthodes
    public Session(Long id, Users users, Task task, LocalDateTime endTime) {
        // Constructeur avec tous les champs, y compris l'ID
        this.id = id;
        this.users = users;
        this.task = task;
        this.endTime = endTime;
    }
    public String getName() {
        return this.name;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }


    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference("user-session")
    private Users users;

    @ManyToOne
    @JoinColumn(name = "task_id")
    @JsonBackReference("task-session")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "poker_card_id")
    private Poker_Cards pokerCard;
    @JsonBackReference

    private LocalDateTime endTime;
    @ManyToMany
    @JoinTable(
            name = "session_user",
            joinColumns = @JoinColumn(name = "session_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<Users> participants = new HashSet<>();
    public Users getUser() {
        return this.users;
    }

}
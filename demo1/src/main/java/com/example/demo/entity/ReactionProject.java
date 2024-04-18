package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ReactionProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reactionId;
    private Boolean isliked;
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestampp;
    @ManyToOne
    @JsonIgnore

    private  Project project;
    @ManyToOne
    @JsonIgnore
    private  User user;
}

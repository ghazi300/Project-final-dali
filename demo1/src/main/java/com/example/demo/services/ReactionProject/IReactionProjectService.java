package com.example.demo.services.ReactionProject;

import com.example.demo.entity.ReactionProject;
import org.springframework.http.ResponseEntity;

public interface IReactionProjectService{
    ResponseEntity<?> addReactionToProject (Integer idProject , ReactionProject reactionProject, Integer idUser );
}

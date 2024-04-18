package com.example.demo.controller.ReactionProject;


import com.example.demo.entity.ReactionProject;
import com.example.demo.services.ReactionProject.IReactionProjectService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController


public class ReactionProjectController {
    @Autowired
    IReactionProjectService iReactionProjectService;
    @PostMapping("/addReaction/{idProject}/{idUser}")
    public ResponseEntity<?> addReactionProjectToPrject(
            @PathVariable Integer idProject,
            @RequestBody ReactionProject reactionProject,
            @PathVariable Integer idUser) {
        try {
            return iReactionProjectService.addReactionToProject( idProject,reactionProject, idUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de l'ajout du commentaire : " + e.getMessage());
        }
    }
}



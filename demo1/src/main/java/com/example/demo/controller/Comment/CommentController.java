package com.example.demo.controller.Comment;


import com.example.demo.entity.Comment;
import com.example.demo.services.Comment.ICommentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/comment")

public class CommentController {
    @Autowired
    ICommentService iCommentService;
    @PostMapping("/addComment/{idProject}")
    public ResponseEntity<?> addCommentToProject(
                                                 @PathVariable Integer idProject,
                                                 @RequestBody Comment comment
                                                 ) {
        try {
            return iCommentService.addCommentToProject( idProject,comment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de l'ajout du commentaire : " + e.getMessage());
        }
    }
}

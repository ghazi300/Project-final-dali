package com.example.demo.services.Comment;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Project;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentService implements  ICommentService{
    @Autowired
    CommentRepository commentRepository;
    UserRepository userRepository;
    ProjectRepository projectRepository;

    @Override
    public ResponseEntity<?> addCommentToProject(Integer idProject, Comment comment) {

            Project p = projectRepository.findById(idProject).orElse(null);

            comment.setProject(p);

            commentRepository.save(comment);
            return ResponseEntity.ok().body(comment);

        }
    @Override
    public void deleteComment(Integer commentId) {
        commentRepository.deleteById(commentId);
    }

}


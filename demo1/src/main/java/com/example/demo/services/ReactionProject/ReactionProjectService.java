package com.example.demo.services.ReactionProject;


import com.example.demo.entity.Project;
import com.example.demo.entity.ReactionProject;
import com.example.demo.entity.User;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.ReactionProjectRepository;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReactionProjectService  implements IReactionProjectService{
    @Autowired
    ReactionProjectRepository reactionProjectRepository;
    UserRepository userRepository;
    ProjectRepository projectRepository;
    @Override
    public ResponseEntity<?> addReactionToProject(Integer idProject, ReactionProject reactionProject, Integer idUser) {

        Project p = projectRepository.findById(idProject).orElse(null);
        User u = userRepository.findById(idUser).orElse(null);

        reactionProject.setUser(u);
        reactionProject.setProject(p);

        reactionProjectRepository.save(reactionProject);
        return ResponseEntity.ok().body(reactionProject);
    }
}

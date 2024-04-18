package com.example.demo.controller.Project;

import com.example.demo.entity.Project;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.services.Prject.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin("*")
public class ProjectController {

    @Autowired
    ProjectService projectService;
    private final ProjectRepository projectRepository;

    @PostMapping("/{projectId}/image")
    public ResponseEntity<Project> addImage(@PathVariable Integer projectId, @RequestParam("image") MultipartFile image) {
        try {
            Project training = projectService.addImage(projectId, image);
            return new ResponseEntity<>(training, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{projectId}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable Integer projectId) {
        try {
            byte[] imageData = projectService.getImage(projectId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/add-Project")
    public Project addProject(@RequestBody Project b) {
        return projectService.add(b);
    }

    @GetMapping("/get_all_Projects")
    public List<Project> findAll() {
        return projectService.findAll();
    }

    @PutMapping("/updateProject/{id}")
    public Project update(@RequestBody Project project, @PathVariable("id") Integer id) {
        return projectService.update(project, id);
    }

    @GetMapping("/getProject/{projectId}")
    public Project findProjectById(@PathVariable("projectId") Integer projectId) {
        return projectService.retrieveItem(projectId);
    }

    @DeleteMapping("/deleteProject/{ProjectId}")
    public void delete(@PathVariable("ProjectId") Integer projectId) {
        projectService.delete(projectId);
    }

    @DeleteMapping("/projects/{projectId}/comments/{commentId}")
    public void deleteComment(@PathVariable("projectId") Integer projectId, @PathVariable("commentId") Integer commentId) {
        projectService.deleteComment(projectId, commentId);
    }

    @PostMapping("/projects/{projectId}/like")
    public void likeProject(@PathVariable("projectId") Integer projectId) {
        projectService.likeProject(projectId);
    }

    @PostMapping("/projects/{projectId}/dislike")
    public void dislikeProject(@PathVariable("projectId") Integer projectId) {
        projectService.dislikeProject(projectId);
    }
    @GetMapping("/projects/{projectId}/likes")
    public int getLikes(@PathVariable("projectId") Integer projectId) {
        return projectService.getLikes(projectId);
    }

    @GetMapping("/projects/{projectId}/dislikes")
    public int getDislikes(@PathVariable("projectId") Integer projectId) {
        return projectService.getDislikes(projectId);
    }
    @PostMapping("/projects/addFilesFromGitHub")
    public ResponseEntity<String> addFilesFromGitHub(@RequestParam String githubRepoUrl) {
        try {
            projectService.addFilesFromGitHub(githubRepoUrl);
            return ResponseEntity.ok("Files added successfully from GitHub repository.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to add files from GitHub repository: " + e.getMessage());
        }
    }
    @GetMapping("/ranked")
    public List<Project> getRankedProjects() {
        return projectService.getRankedProjects();
    }
}

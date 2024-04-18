package com.example.demo.services.Prject;


import com.example.demo.entity.Project;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.services.Comment.CommentService;
import com.example.demo.services.ICrudeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectService implements ICrudeService<Project,Integer>, IProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentService commentService;

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }
    public Project addImage(Integer projectId, MultipartFile image) throws IOException {
        Project project = projectRepository.findById(projectId).orElse(null);
        if (project != null) {
            String imageName = StringUtils.cleanPath(image.getOriginalFilename());
            String imageExtension = StringUtils.getFilenameExtension(imageName);
            String generatedImageName = UUID.randomUUID().toString() + "." + imageExtension;

            Path imagePath = Paths.get("uploads").resolve(generatedImageName);
            Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

            project.setImageName(imageName);
            project.setImagePath(imagePath.toString());

            projectRepository.save(project);
        }
        return project;
    }
    public byte[] getImage(Integer projectId) throws IOException {
        Project project = projectRepository.findById(projectId).orElse(null);
        if (project != null && project.getImagePath() != null) {
            Path imagePath = Paths.get(project.getImagePath());
            return Files.readAllBytes(imagePath);
        } else {
            throw new IOException("Image not found for project with ID: " + projectId);
        }
    }
    @Cacheable("projects")
    @Override
    public Project retrieveItem(Integer idItem) {
        Optional<Project> projectOptional = projectRepository.findById(idItem);
        if (projectOptional.isPresent()) {
            return projectOptional.get();
        } else {
            throw new RuntimeException("Project not found with id: " + idItem);
        }
    }

    @Override
    public Project add(Project project) {
        return projectRepository.save(project);
    }

    @CacheEvict(value = "projects", allEntries = true)
    @Override
    public void delete(Integer id) {
        projectRepository.deleteById(id);
    }

    @CacheEvict(value = "projects", allEntries = true)
    @Override
    public Project update(Project project, Integer id) {
        Optional<Project> existingProjectOptional = projectRepository.findById(id);

        if (existingProjectOptional.isPresent()) {
            Project existingProject = existingProjectOptional.get();
            existingProject.setTitle(project.getTitle());
            existingProject.setDescription(project.getDescription());
            existingProject.setPrototype(project.getPrototype());
            existingProject.setDate_debut(project.getDate_debut());
            existingProject.setDatefin_prevu(project.getDatefin_prevu());
            existingProject.setEtatProject(project.getEtatProject());

            return projectRepository.save(existingProject);
        } else {
            throw new RuntimeException("Project not found with id: " + id);
        }
    }
    public List<Project> getRankedProjects() {
        // Récupérer tous les projets
        List<Project> allProjects = projectRepository.findAll();

        // Trier les projets par un critère de classement (par exemple, le nombre de likes)
        List<Project> rankedProjects = allProjects.stream()
                .sorted(Comparator.comparingInt(Project::getLikes).reversed()) // Trier par le nombre de likes, du plus grand au plus petit
                .collect(Collectors.toList());

        // Retourner la liste des projets classés
        return rankedProjects;
    }
    public void deleteComment(Integer projectId, Integer commentId) {
        commentService.deleteComment(commentId);
    }

    public void likeProject(Integer projectId) {
        Project project = retrieveItem(projectId);
        project.setLikes(project.getLikes() + 1);
        project.setLiked(true);
        projectRepository.save(project);
    }

    public void dislikeProject(Integer projectId) {
        Project project = retrieveItem(projectId);
        project.setDislikes(project.getDislikes() + 1);
        project.setDisliked(true);
        projectRepository.save(project);
    }

    public void toggleLikeOrDislike(Integer projectId, boolean isLike) {
        Project project = retrieveItem(projectId);
        if (project.isLiked()) {
            if (isLike) {
                project.setLiked(false);
                project.setLikes(project.getLikes() - 1);
            } else {
                project.setLiked(false);
                project.setLikes(project.getLikes() - 1);
                project.setDisliked(true);
                project.setDislikes(project.getDislikes() + 1);
            }
        } else if (project.isDisliked()) {
            if (!isLike) {
                project.setDisliked(false);
                project.setDislikes(project.getDislikes() - 1);
            } else {
                project.setDisliked(false);
                project.setDislikes(project.getDislikes() - 1);
                project.setLiked(true);
                project.setLikes(project.getLikes() + 1);
            }
        } else {
            if (isLike) {
                project.setLiked(true);
                project.setLikes(project.getLikes() + 1);
            } else {
                project.setDisliked(true);
                project.setDislikes(project.getDislikes() + 1);
            }
        }
        projectRepository.save(project);
    }

    @Cacheable("projects")
    public int getLikes(Integer projectId) {
        return projectRepository.getLikesById(projectId);
    }
    @Cacheable("projects")
    public int getDislikes(Integer projectId) {
        return projectRepository.findDislikesById(projectId);
    }
    public void addFilesFromGitHub(String githubRepoUrl) {
        try {
            // Créer une instance de RestTemplate
            RestTemplate restTemplate = new RestTemplate();

            // Envoyer une requête GET à l'API GitHub pour récupérer les fichiers
            ResponseEntity<String> response = restTemplate.getForEntity(githubRepoUrl + "/contents", String.class);

            // Vérifier si la requête a réussi
            if (response.getStatusCode().is2xxSuccessful()) {
                String responseBody = response.getBody();
                // Traitez la réponse comme nécessaire
                System.out.println("Files retrieved successfully from GitHub repository: " + responseBody);
            } else {
                // Gérer les cas où la requête n'a pas réussi
                System.err.println("Failed to retrieve files from GitHub repository. Status code: " + response.getStatusCodeValue());
            }
        } catch (Exception e) {
            // Gérer les exceptions
            throw new RuntimeException("Failed to fetch files from GitHub repository: " + e.getMessage());
        }
    }
}

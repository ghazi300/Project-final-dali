package com.example.demo.controller;

import com.example.demo.entity.UserStory;
import com.example.demo.exceptions.UserStoryNotFoundException;
import com.example.demo.repository.UserStoryRepository;
import org.springframework.web.bind.annotation.*;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/userstories")

public class UserStoryController {

    public static final String ACCOUNT_SID = "AC6ff15b9fefe5f86307a4f7b42bd8dd27";
    public static final String AUTH_TOKEN = "40c7e520367b8043c97b6841cdabdd73";
    private final UserStoryRepository repository;

    public UserStoryController(UserStoryRepository repository) {
        this.repository = repository;
    }
    @GetMapping
    List<UserStory> getAll() {
        return repository.findAll();
    }

    @PostMapping
    UserStory newUserStory(@RequestBody UserStory story) {
        UserStory savedStory = repository.save(story);
        //sendSms(savedStory);
        return savedStory;
    }

    @GetMapping("/{id}")
    UserStory getOne(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new UserStoryNotFoundException(id));
    }
    @PutMapping("/{id}")
    UserStory replaceUserStory(@RequestBody UserStory newUserStory, @PathVariable Long id) {

        return repository.findById(id)
                .map(story -> {
                    // NotifyAssignedTo(story);
                    story.setTitle(newUserStory.getTitle());
                    story.setDescription(newUserStory.getDescription());
                    story.setPriority(newUserStory.getPriority());
                    story.setAssigned_to(newUserStory.getAssigned_to());
                    story.setAcceptance_criteria(newUserStory.getAcceptance_criteria());
                    return repository.save(story);
                })
                .orElseThrow(() -> new UserStoryNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    void deleteUserStory(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @GetMapping("/search")
    List<UserStory> searchUserStories(@RequestParam(value = "keyword") String keyword) {
        List<UserStory> allUserStories = repository.findAll();
        return allUserStories.stream()
                .filter(story -> story.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                        story.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }


    void sendSms(UserStory story) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
                        new PhoneNumber(story.getAssigned_to().getNumber()), // to
                        new PhoneNumber("+12565989234"), // from
                        "UserStory '" + story.getTitle() + "' has been added successfully.")
                .create();

        System.out.println("SMS sent: " + message.getSid());
    }
}

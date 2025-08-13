package com.foro.controller;

import com.foro.dto.TopicRequest;
import com.foro.dto.TopicResponse;
import com.foro.model.Topic;
import com.foro.model.User;
import com.foro.repository.TopicRepository;
import com.foro.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class TopicController {
    private final TopicRepository topicRepo;
    private final UserRepository userRepo;

    public TopicController(TopicRepository tr, UserRepository ur) {
        this.topicRepo = tr; this.userRepo = ur;
    }

    @GetMapping
    public List<TopicResponse> listAll() {
        return topicRepo.findAllByOrderByCreatedAtDesc().stream()
                .map(TopicResponse::fromEntity).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicResponse> getOne(@PathVariable Long id) {
        return topicRepo.findById(id)
                .map(t -> ResponseEntity.ok(TopicResponse.fromEntity(t)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TopicRequest req) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User author = userRepo.findByUsername(username).orElseThrow();
        Topic t = new Topic();
        t.setTitle(req.title);
        t.setContent(req.content);
        t.setAuthor(author);
        topicRepo.save(t);
        return ResponseEntity.status(HttpStatus.CREATED).body(TopicResponse.fromEntity(t));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody TopicRequest req) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return topicRepo.findById(id).map(topic -> {
            if (!topic.getAuthor().getUsername().equals(username))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No eres el autor");
            topic.setTitle(req.title);
            topic.setContent(req.content);
            topicRepo.save(topic);
            return ResponseEntity.ok(TopicResponse.fromEntity(topic));
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Topic not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return topicRepo.findById(id).map(topic -> {
            if (!topic.getAuthor().getUsername().equals(username))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No eres el autor");
            topicRepo.delete(topic);
            return ResponseEntity.ok("Deleted");
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Topic not found"));
    }
}
package com.foro.dto;

import com.foro.model.Topic;

import java.time.LocalDateTime;

public class TopicResponse {
    public Long id;
    public String title;
    public String content;
    public String author;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;

    // constructor desde Topic...


    public TopicResponse(Long id, String title, String content, String author, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static TopicResponse fromEntity(Topic topic) {
        String authorUsername = topic.getAuthor() != null ? topic.getAuthor().getUsername() : "Autor desconocido";
        return new TopicResponse(
                topic.getId(),
                topic.getTitle(),
                topic.getContent(),
                authorUsername,
                topic.getCreatedAt(),
                topic.getUpdatedAt()
        );
    }
}

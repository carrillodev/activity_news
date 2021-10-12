package com.example.news.domain.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewDetailDto {
    private UUID id;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private boolean enabled;
    private AuthorDto idAuthor;
    private String headline;
    private String summary;
    private String body;
    private String image;
}

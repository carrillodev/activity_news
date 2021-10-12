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
public class NewShortDto {
    private UUID id;
    private String image;
    private LocalDateTime creationDate;
    private String headline;
    private String summary;
    private String body;
}

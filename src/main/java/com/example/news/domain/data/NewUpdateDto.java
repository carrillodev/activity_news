package com.example.news.domain.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewUpdateDto {
    private MultipartFile image;
    private String headline;
    private String summary;
    private String body;
    private boolean enabled;
}

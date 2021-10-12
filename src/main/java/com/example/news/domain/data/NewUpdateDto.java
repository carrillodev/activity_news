package com.example.news.domain.data;

import lombok.*;
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
    @Getter
    private Boolean enabled;
}

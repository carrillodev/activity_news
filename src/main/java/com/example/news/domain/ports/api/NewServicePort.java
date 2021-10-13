package com.example.news.domain.ports.api;

import com.example.news.domain.data.NewCreateDto;
import com.example.news.domain.data.NewDetailDto;
import com.example.news.domain.data.NewShortDto;
import com.example.news.domain.data.NewUpdateDto;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.UUID;

public interface NewServicePort {
    NewDetailDto getNew(UUID id);
    List<NewShortDto> getNews(Integer page, Integer size, String sort);
    NewDetailDto updateNew(NewUpdateDto newUpdateDto, UUID id);
    NewDetailDto createNew(NewCreateDto newCreateDto);
    Resource getImage(String filename);
}

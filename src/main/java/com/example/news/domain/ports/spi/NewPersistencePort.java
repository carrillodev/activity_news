package com.example.news.domain.ports.spi;

import com.example.news.domain.data.NewCreateDto;
import com.example.news.domain.data.NewUpdateDto;
import com.example.news.infraestructure.entity.New;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface NewPersistencePort {
    New getNew(UUID id);
    Page<New> getNews(Pageable pageable);
    New updateNew(NewUpdateDto newUpdateDto, UUID id);
    New createNew(NewCreateDto newCreateDto);
}

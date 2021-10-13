package com.example.news.domain.service;

import com.example.news.domain.data.*;
import com.example.news.domain.ports.api.NewServicePort;
import com.example.news.domain.ports.spi.NewPersistencePort;
import com.example.news.infraestructure.entity.New;
import com.example.news.infraestructure.mappers.NewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

public class NewServiceImpl implements NewServicePort {
    private NewPersistencePort newPersistencePort;

    @Autowired
    public NewServiceImpl(NewPersistencePort newPersistencePort) {
        this.newPersistencePort = newPersistencePort;
    }

    @Override
    public NewDetailDto getNew(UUID id) {
        New newItem = newPersistencePort.getNew(id);
        AuthorDto author = getAuthor(newItem.getAuthorId());
        NewDetailDto newDetail = NewMapper.INSTANCE.newToNewDetailDto(newItem);
        newDetail.setIdAuthor(author);
        return newDetail;
    }

    @Override
    public List<NewShortDto> getNews(Integer page, Integer size, String sort) {
        String sortField, sortDirection;
        String[] options = sort.split(",");
        sortField = options[0];
        sortDirection = options[1];

        Pageable pageable;
        if (sortDirection.equals("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortField).descending());
        } else if (sortDirection.equals("asc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortField).ascending());
        } else {
            pageable = PageRequest.of(page, size);
        }

        Page<New> newList = newPersistencePort.getNews(pageable);
        List<NewShortDto> newShortDtoList = NewMapper.INSTANCE.newListToNewShortDtoList(newList.getContent());
        return newShortDtoList;
    }

    @Override
    public NewDetailDto updateNew(NewUpdateDto newUpdateDto, UUID id) {
        New updatedNew = newPersistencePort.updateNew(newUpdateDto, id);
        AuthorDto author = getAuthor(updatedNew.getAuthorId());
        NewDetailDto newDetail = NewMapper.INSTANCE.newToNewDetailDto(updatedNew);
        newDetail.setIdAuthor(author);
        return newDetail;
    }

    @Override
    public NewDetailDto createNew(NewCreateDto newCreateDto) {
        New createdNew = newPersistencePort.createNew(newCreateDto, getLoggedUser());
        AuthorDto author = getAuthor(getLoggedUser());
        NewDetailDto newDetail = NewMapper.INSTANCE.newToNewDetailDto(createdNew);
        newDetail.setIdAuthor(author);
        return newDetail;
    }

    private AuthorDto getAuthor(UUID id) {
        String uri = "http://localhost:8080/users/" + id;
        return new RestTemplate().getForObject(uri, AuthorDto.class);
    }

    private UUID getLoggedUser() {
        return UUID.fromString("61663339-6234-3936-2d32-6161662d3131");
    }
}

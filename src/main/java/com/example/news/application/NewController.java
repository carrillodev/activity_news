package com.example.news.application;

import com.example.news.domain.data.NewCreateDto;
import com.example.news.domain.data.NewDetailDto;
import com.example.news.domain.data.NewShortDto;
import com.example.news.domain.data.NewUpdateDto;
import com.example.news.domain.ports.api.NewServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/news")
public class NewController {
    private NewServicePort newServicePort;

    @Autowired
    public NewController(NewServicePort newServicePort) {
        this.newServicePort = newServicePort;
    }

    @GetMapping
    public ResponseEntity<List<NewShortDto>> getAllNews(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size,
            @RequestParam(required = false, defaultValue = "creationDate,desc") String sort) {
        List<NewShortDto> userShortDtoList = newServicePort.getNews(page, size, sort);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Total-Elements", String.valueOf(userShortDtoList.size()));
        return new ResponseEntity<>(userShortDtoList, headers, HttpStatus.OK);
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<NewDetailDto> createNew(NewCreateDto newCreateDto) {
        return new ResponseEntity<>(newServicePort.createNew(newCreateDto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewDetailDto> getNewById(@PathVariable UUID id) {
        return new ResponseEntity<>(newServicePort.getNew(id), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<NewDetailDto> updateNew(@ModelAttribute NewUpdateDto newUpdateDto, @PathVariable UUID id) {
        return new ResponseEntity<>(newServicePort.updateNew(newUpdateDto, id), HttpStatus.OK);
    }
}

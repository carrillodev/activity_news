package com.example.news.infraestructure.adapters;

import com.example.news.domain.data.NewCreateDto;
import com.example.news.domain.data.NewUpdateDto;
import com.example.news.domain.ports.spi.NewPersistencePort;
import com.example.news.exception.BodyAlreadyExistsException;
import com.example.news.exception.HeadlineAlreadyExistsException;
import com.example.news.exception.NewNotFoundException;
import com.example.news.exception.SummaryAlreadyExistsException;
import com.example.news.infraestructure.entity.New;
import com.example.news.infraestructure.repository.NewRepository;
import com.example.news.storage.StorageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class NewJpaAdapter implements NewPersistencePort {
    @Autowired
    private NewRepository newRepository;

    @Autowired
    private StorageService storageService;

    @Override
    public New getNew(UUID id) {
        if (newRepository.findById(id).isPresent()) {
            return newRepository.findById(id).get();
        }
        throw new NewNotFoundException();
    }

    @Override
    public Page<New> getNews(Pageable pageable) {
        return newRepository.findAll(pageable);
    }

    @Override
    public New updateNew(NewUpdateDto newUpdateDto, UUID id) {
        if (newRepository.findById(id).isPresent()) {
            New currentNew = newRepository.findById(id).get();
            checkAndSetNewContent(newUpdateDto.getHeadline(), newUpdateDto.getSummary(), newUpdateDto.getBody(), currentNew);

            if (newUpdateDto.getEnabled() != null) currentNew.setEnabled(newUpdateDto.getEnabled());
            if (newUpdateDto.getImage() != null) storeAndSetNewImage(newUpdateDto.getImage(), currentNew);

            currentNew.setModificationDate(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
            newRepository.save(currentNew);
            return currentNew;
        }
        throw new NewNotFoundException();
    }

    @Override
    public New createNew(NewCreateDto newCreateDto, UUID authorId) {
        New createdNew = new New();

        checkAndSetNewContent(newCreateDto.getHeadline(), newCreateDto.getSummary(), newCreateDto.getBody(), createdNew);
        if (newCreateDto.getImage() != null) storeAndSetNewImage(newCreateDto.getImage(), createdNew);

        createdNew.setId(UUID.randomUUID());
        createdNew.setAuthorId(authorId);
        createdNew.setCreationDate(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        createdNew.setModificationDate(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        createdNew.setEnabled(false);
        newRepository.save(createdNew);
        return createdNew;
    }

    private void storeAndSetNewImage(MultipartFile image, New newItem) {
        String extension = FilenameUtils.getExtension(image.getOriginalFilename());
        String filename = UUID.randomUUID() + "." + extension;
        storageService.store(image, filename);
        newItem.setImage("localhost:8081/files/" + filename);
    }

    private void setNewContent(String headline, String summary, String body, New newItem) {
        if (headline != null) newItem.setHeadline(headline);
        if (summary != null) newItem.setSummary(summary);
        if (body != null) newItem.setBody(body);
    }

    private void checkAndSetNewContent(String headline, String summary, String body, New newItem) {
        if (newRepository.findByHeadlineIgnoreCase(headline) != null) {
            throw new HeadlineAlreadyExistsException();
        }
        if (newRepository.findBySummaryIgnoreCase(summary) != null) {
            throw new SummaryAlreadyExistsException();
        }
        if (newRepository.findByBodyIgnoreCase(body) != null) {
            throw new BodyAlreadyExistsException();
        }
        setNewContent(headline, summary, body, newItem);
    }
}

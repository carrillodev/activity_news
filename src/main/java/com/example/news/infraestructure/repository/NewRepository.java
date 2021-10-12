package com.example.news.infraestructure.repository;

import com.example.news.infraestructure.entity.New;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NewRepository extends JpaRepository<New, UUID> {
    New findByHeadlineIgnoreCase(String headline);
    New findBySummaryIgnoreCase(String summary);
    New findByBodyIgnoreCase(String body);
}
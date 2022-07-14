package com.project.comic.repository;

import com.project.comic.model.Publisher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublisherRepository extends CrudRepository<Publisher, Integer> {
    List<Publisher> findByIsActive(Boolean isActive);
    Publisher findByPublisherIdAndIsActive(Integer id, Boolean isActive);
    Publisher findByPublisherNameAndIsActive(String name, Boolean isActive);
    Publisher findByPublisherId(Integer id);
}
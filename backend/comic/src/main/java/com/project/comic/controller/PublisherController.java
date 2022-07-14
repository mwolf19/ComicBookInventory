package com.project.comic.controller;

import com.project.comic.model.Publisher;
import com.project.comic.repository.PublisherRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/publisher")
public class PublisherController {

    private PublisherRepository publisherRepository;

    public PublisherController(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @GetMapping(path="/listAllActive", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Iterable<Publisher> findByIsActive() {
        return publisherRepository.findByIsActive(true);
    }

    @GetMapping(path="/listById/{id}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Publisher findByPublisherId(@PathVariable("id") int id) {
        return publisherRepository.findByPublisherIdAndIsActive(id, true);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path="/savePublisher", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public int savePublisher(@RequestBody Publisher publisherModel) {
        try {
            Publisher existingCheck = publisherRepository.findByPublisherNameAndIsActive(publisherModel.getPublisherName(), true);

            int newId = 0;

            if (existingCheck == null || !publisherModel.getPublisherName().equalsIgnoreCase(existingCheck.getPublisherName())) {
                publisherModel.setIsActive(true);
                newId = publisherRepository.save(publisherModel).getPublisherId();
            }

            return newId;
        } catch (Exception e) {
            return -1;
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping(path="/updatePublisher", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public int updatePublisher(@RequestBody Publisher publisherModel) {
        try {
            Publisher existingCheck = publisherRepository.findByPublisherNameAndIsActive(publisherModel.getPublisherName().trim(), true);

            int id = 0;

            if (existingCheck == null || publisherModel.getPublisherId().equals(existingCheck.getPublisherId())) {
                Publisher current = publisherRepository.findByPublisherIdAndIsActive(publisherModel.getPublisherId(), true);

                if (current != null) {
                    current.setPublisherName(publisherModel.getPublisherName());

                    System.out.println(current.getPublisherId());

                    id = publisherRepository.save(current).getPublisherId();
                }
            }

            return id;
        } catch (Exception e) {
            return -1;
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping(path="/deletePublisher/{id}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public int deletePublisher(@PathVariable("id") int publisherId) {
        try {
            int id = 0;

            Publisher current = publisherRepository.findByPublisherIdAndIsActive(publisherId, true);

            if (current != null) {
                current.setIsActive(false);

                id = publisherRepository.save(current).getPublisherId();
            }

            return id;
        } catch (Exception e) {
            return -1;
        }
    }
}
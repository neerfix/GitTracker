package com.ynov.tdspring.services;

import com.ynov.tdspring.entities.Event;
import com.ynov.tdspring.entities.Research;
import com.ynov.tdspring.entities.User;
import com.ynov.tdspring.repositories.ResearchRepository;
import com.ynov.tdspring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class ResearchService
{
    @Autowired
    private ResearchRepository researchRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventService eventService;

    // --------------------- >

    public Research createOrUpdate(Research research) {
        Event event = new Event();
        this.eventService.create(event.EVENT_RESEARCH, research.getAuthor(), event.EVENT_ACTION_CREATE, research);

        return researchRepository.save(research);
    }

    public Research getResearchByResearchId(UUID id) {
        return researchRepository.findById(id).orElse(null);
    }

    public String getResearchSearchTextByResearchId(UUID id) {
        return researchRepository.findById(id).orElse(null).getSearch_text();
    }
    public String getResearchStatusByResearchId(UUID id) {
        return researchRepository.findById(id).orElse(null).getStatus();
    }
    public User getResearchAuthorByResearchId(UUID id) {
        return researchRepository.findById(id).orElse(null).getAuthor();
    }

    public List<Research> getAllResearchs() { return researchRepository.findAll(); }

    public void delete(UUID id) {
        Research deleteResearch = this.getResearchByResearchId(id);

        if (deleteResearch == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Exit not found"
            );
        }

        Event event = new Event();
        this.eventService.create(event.EVENT_RESEARCH, deleteResearch.getAuthor(), event.EVENT_ACTION_DELETE, deleteResearch);

        researchRepository.deleteById(deleteResearch.getId());
    }
}

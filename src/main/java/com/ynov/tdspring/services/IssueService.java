package com.ynov.tdspring.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ynov.tdspring.entities.Comment;
import com.ynov.tdspring.entities.Event;
import com.ynov.tdspring.entities.Issue;
import com.ynov.tdspring.entities.User;
import com.ynov.tdspring.repositories.IssueRepository;
import org.springframework.web.server.ResponseStatusException;

@Service
public class IssueService {

	@Autowired
	private IssueRepository issueRepository;
	
	@Autowired
	private EventService eventService;

    public Issue createOrUpdate(Issue issue) {
        Event event = new Event();
        this.eventService.create(event.EVENT_ISSUE, issue.getAuthor(), event.EVENT_ACTION_CREATE, issue.getId());

        return issueRepository.save(issue);
    }

    public Issue getIssueByIssueId(UUID id) {
        return issueRepository.findById(id).orElse(null);
    }
    
    public String getIssueCriticityByIssueId(UUID id) {
        return issueRepository.findById(id).orElse(null).getCriticity();
    }
    public User getResearchAuthorByResearchId(UUID id) {
        return issueRepository.findById(id).orElse(null).getAuthor();
    }

    public List<Issue> getAllIssues() { return issueRepository.findAll(); }

    public void delete(UUID id) {
        Issue deleteIssue = this.getIssueByIssueId(id);

        if (deleteIssue == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Exit not found"
            );
        }

        Event event = new Event();
        this.eventService.create(event.EVENT_ISSUE, deleteIssue.getAuthor(), event.EVENT_ACTION_DELETE, deleteIssue.getAuthor().getId());

        issueRepository.deleteById(deleteIssue.getId());
    }
    
    
}

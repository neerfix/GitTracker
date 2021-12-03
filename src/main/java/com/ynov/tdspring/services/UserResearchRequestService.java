package com.ynov.tdspring.services;

import com.ynov.tdspring.entities.Project;
import com.ynov.tdspring.entities.Research;
import com.ynov.tdspring.entities.User;
import com.ynov.tdspring.entities.UserRequest;
import com.ynov.tdspring.entities.UserResearchRequest;
import com.ynov.tdspring.repositories.UserRequestRepository;
import com.ynov.tdspring.repositories.UserResearchRequestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserResearchRequestService
{
    
    @Autowired
    private UserResearchRequestRepository userResearchRequestRepository;

    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private ResearchService researchService;

    // --------------------- >

    public UserResearchRequest getUserRequestByUuid(UUID id) {
        return userResearchRequestRepository.findById(id).orElse(null);
    }

    public List<Research> getUserResearchsByUserAndProject(User user, Project project) {
    	List<Research> researchs = new ArrayList<Research>();
        for (UserResearchRequest userResearchRequest : this.userResearchRequestRepository.findAll()) {
            if (user == userResearchRequest.getUser()) {
                if (project == userResearchRequest.getProject()) {
                    researchs.add(userResearchRequest.getResearch());
                }
            }
        }
        return researchs;
    }
    
    public UserResearchRequest getResearchsRequestByResearchAndProject(Research research, Project project) {
    	for (UserResearchRequest userResearchRequest : this.userResearchRequestRepository.findAll()) {
            if (research == userResearchRequest.getResearch()) {
                if (project == userResearchRequest.getProject()) {
                    return userResearchRequest;
                }
            }
        }
		return null;
    }

    public void proposeRequestToProjectByUser(Project project, User user, Research research) {
        List<UserResearchRequest> userResearchRequests =  this.userResearchRequestRepository.findAll();

        for (UserResearchRequest userResearchRequest : userResearchRequests) {
            if (userResearchRequest.getUser() == user) {
                if (userResearchRequest.getProject() == project) {
                    if (userResearchRequest.getResearch() == research) {
                    	return;
                    }
                }
            }
        }
            

        UserResearchRequest userResearchRequest = new UserResearchRequest();
        userResearchRequest.setStatus(userResearchRequest.STATUS_PENDING);
        userResearchRequest.setUser(user);
        userResearchRequest.setProject(project);
        userResearchRequest.setResearch(research);
        this.userResearchRequestRepository.save(userResearchRequest);
    }

    public Project acceptApplication(UserResearchRequest userResearchRequest) {
    	userResearchRequest.setStatus(userResearchRequest.STATUS_ACCEPTED);

        this.userResearchRequestRepository.save(userResearchRequest);
        return this.projectService.addUserResearchToProject(userResearchRequest.getProject(), userResearchRequest.getResearch());
    }

    public Project refuseApplication(UserResearchRequest userResearchRequest) {
    	userResearchRequest.setStatus(userResearchRequest.STATUS_REFUSED);

        this.userResearchRequestRepository.save(userResearchRequest);
        return this.projectService.deleteResearchForProject(userResearchRequest.getProject(), userResearchRequest.getUser(), userResearchRequest.getResearch());
    }
}

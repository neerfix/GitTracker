package com.ynov.tdspring.services;

import com.ynov.tdspring.entities.Event;
import com.ynov.tdspring.entities.Project;
import com.ynov.tdspring.entities.Research;
import com.ynov.tdspring.entities.User;
import com.ynov.tdspring.entities.UserRequest;
import com.ynov.tdspring.entities.UserResearchRequest;
import com.ynov.tdspring.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectService
{
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRequestService userRequestService;
    
    @Autowired
    private UserResearchRequestService userResearchRequestService;

    @Autowired
    private EventService eventService;

    // --------------------- >

    public Project create(Project project) {
        Event event = new Event();
        this.eventService.create(event.EVENT_PROJECT,project.getAuthor(), event.EVENT_ACTION_CREATE, project);

        return projectRepository.save(project);
    }

    public Project update(Project project) {
        Event event = new Event();
        this.eventService.create(event.EVENT_PROJECT,project.getAuthor(), event.EVENT_ACTION_UPDATE, project);

        return projectRepository.save(project);
    }

    public Project getProjectByProjectId(UUID id) {
        return projectRepository.findById(id).orElse(null);
    }

    public Project addUserToProject(Project project, User user) {
        if (project != null) {
            List<User> listUsers = project.getParticipants();

            if (user != null) {
                listUsers.add(user);
                project.setParticipants(listUsers);
            }

            projectRepository.save(project);
        }

        return project;
    }

    public Project deleteUserForProject(Project project, User user) {
        if (project != null) {
            List<User> listUsers = project.getParticipants();

            if (user != null) {
                listUsers.remove(user);
                project.setParticipants(listUsers);
            }

            projectRepository.save(project);
        }

        Event event = new Event();
        this.eventService.create(event.EVENT_USER, user, event.EVENT_ACTION_DELETE, project);

        return project;
    }

    public List<Project> getAllProjects() { return projectRepository.findAll(); }

    public void delete(UUID id) {
    	Project deleteExit = this.getProjectByProjectId(id);

        if (deleteExit == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Exit not found"
            );
        }

        projectRepository.delete(deleteExit);
    }

    public Project acceptUserInProject(Project project, User user) throws Exception {
        UserRequest userRequest = this.userRequestService.getUserRequestByUserAndProject(user, project);

        if (userRequest == null) {
            throw new Exception("userRequest");
        }

        Event event = new Event();
        this.eventService.updateUser(event.EVENT_USER, user, event.EVENT_ACTION_CREATE, project, user.getUsername());

        return this.userRequestService.acceptApplication(userRequest);
    }

    public Project refuseUserInProject(Project project, User user) throws Exception {
        UserRequest userRequest = this.userRequestService.getUserRequestByUserAndProject(user, project);

        if (userRequest == null) {
            throw new Exception("userRequest");
        }

        Event event = new Event();
        this.eventService.updateUser(event.EVENT_USER, user, event.EVENT_ACTION_REFUSE, project, user.getUsername());

        return this.userRequestService.refuseApplication(userRequest);
    }

    public Project userApplyToProject(Project project, User user) throws Exception {
        UserRequest userRequest = this.userRequestService.getUserRequestByUserAndProject(user, project);

        if (userRequest == null) {
            throw new Exception("userRequest");
        }

        this.userRequestService.applicationToJoinProject(project, user);

        Event event = new Event();
        this.eventService.updateUser(event.EVENT_USER, user, event.EVENT_ACTION_REQUEST_JOIN, project, user.getUsername());

        return project;
    }
    
    public Project userProposeRequestToProject(Project project, User user, Research research) throws Exception {
        UserRequest userRequest = this.userRequestService.getUserRequestByUserAndProject(user, project);
        
        if (userRequest == null) {
            throw new Exception("userRequest");
        }
        if (userRequest == null) {
            throw new Exception("userRequest");
        }

        this.userRequestService.applicationToJoinProject(project, user);

        Event event = new Event();
        this.eventService.updateUser(event.EVENT_USER, user, event.EVENT_ACTION_RESEARCH_REQUEST_ADD, project, user.getUsername());

        return project;
    }

	public Project addUserResearchToProject(Project project, Research research) {
		if (project != null) {
            List<Research> listResearch = project.getResearchs();

            if (research != null) {
            	listResearch.add(research);
                project.setResearchs(listResearch);
            }

            projectRepository.save(project);
        }

        return project;
	}

	public Project deleteResearchForProject(Project project, User user, Research research) {
		if (project != null) {
			 List<Research> listResearch = project.getResearchs();

            if (research != null) {
            	listResearch.remove(research);
                project.setResearchs(listResearch);
            }

            projectRepository.save(project);
        }

        Event event = new Event();
        this.eventService.create(event.EVENT_USER, user, event.EVENT_ACTION_RESEARCH_DELETE, project);

        return project;
	}

	public Project acceptResearchInProject(Project project, Research research) throws Exception {
		UserResearchRequest userResearchRequest = this.userResearchRequestService.getResearchsRequestByResearchAndProject(research, project);

        if (userResearchRequest == null) {
            throw new Exception("userResearchRequest");
        }

        Event event = new Event();
        this.eventService.updateUser(event.EVENT_USER, userResearchRequest.getUser(), event.EVENT_ACTION_CREATE_RESEARCH, project, userResearchRequest.getUser().getUsername());

        return this.userResearchRequestService.acceptApplication(userResearchRequest);
	}
	
	public Project refuseResearchInProject(Project project, Research research) throws Exception {
        UserResearchRequest userResearchRequest = this.userResearchRequestService.getResearchsRequestByResearchAndProject(research, project);
        if (userResearchRequest == null) {
            throw new Exception("userResearchRequest");
        }

        Event event = new Event();
        this.eventService.updateUser(event.EVENT_USER, userResearchRequest.getUser(), event.EVENT_ACTION_RESEARCH_REQUEST_REFUSE, project, userResearchRequest.getUser().getUsername());

        return this.userResearchRequestService.refuseApplication(userResearchRequest);
    }
}

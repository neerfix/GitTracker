package com.ynov.tdspring.services;

import com.ynov.tdspring.entities.Project;
import com.ynov.tdspring.entities.User;
import com.ynov.tdspring.entities.UserRequest;
import com.ynov.tdspring.repositories.ProjectRepository;
import com.ynov.tdspring.repositories.UserRepository;
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
    private UserRepository userRepository;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserRequestService userRequestService;

    // --------------------- >

    public Project create(Project project) {
        return projectRepository.save(project);
    }

    public Project update(Project project) throws Exception {
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

    public Project acceptUserInProject(Project projectId, User user) {
        UserRequest userRequest = this.userRequestService.getUserRequestByUserAndProject(user, projectId);
        return this.userRequestService.acceptApplication(userRequest);
    }

    public Project refuseUserInProject(Project projectId, User user) {
        UserRequest userRequest = this.userRequestService.getUserRequestByUserAndProject(user, projectId);
        return this.userRequestService.refuseApplication(userRequest);
    }
}

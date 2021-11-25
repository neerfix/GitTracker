package com.ynov.tdspring.services;

import com.ynov.tdspring.entities.Project;
import com.ynov.tdspring.entities.User;
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

    // --------------------- >

    public Project createOrUpdate(Project project) {
        return projectRepository.save(project);
    }

    public Project getProjectByProjectId(UUID id) {
        return projectRepository.findById(id).orElse(null);
    }

    public Project addUserToProject(UUID id, UUID userId) {
    	Project project = this.getProjectByProjectId(id);

        if (project != null) {
            List<User> listUsers = project.getParticipants();
            User userToAdd = userRepository.findById(userId).orElse(null);

            if (userToAdd != null) {
                listUsers.add(userToAdd);
                project.setParticipants(listUsers);
            }

            projectRepository.save(project);
        }

        return project;
    }

    public Project deleteUserForProject(UUID id, UUID userId) {
    	Project project = this.getProjectByProjectId(id);

        if (project != null) {
            List<User> listUsers = project.getParticipants();
            User userToAdd = userRepository.findById(userId).orElse(null);

            if (userToAdd != null) {
                listUsers.remove(userToAdd);
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
}

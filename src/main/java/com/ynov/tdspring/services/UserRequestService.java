package com.ynov.tdspring.services;

import com.ynov.tdspring.entities.Project;
import com.ynov.tdspring.entities.User;
import com.ynov.tdspring.entities.UserRequest;
import com.ynov.tdspring.repositories.UserRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserRequestService
{
    @Autowired
    private UserRequestRepository userRequestRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    // --------------------- >

    public UserRequest getUserRequestByUuid(UUID id) {
        return userRequestRepository.findById(id).orElse(null);
    }

    public UserRequest getUserRequestByUserAndProject(User user, Project project) {
        for (UserRequest userRequest : this.userRequestRepository.findAll()) {
            if (user == userRequest.getUser()) {
                if (project == userRequest.getProject()) {
                    return userRequest;
                }
            }
        }

        return null;
    }

    private UserRequest applicationToJoinProject(UUID projectId, String username) {
        List<UserRequest> userRequests =  this.userRequestRepository.findAll();
        User user = this.userService.getUserByUsername(username);
        Project project = this.projectService.getProjectByProjectId(projectId);

        for (UserRequest userRequest : userRequests) {
            if (userRequest.getUser() == user) {
                if (userRequest.getProject() == project) {
                    return null;
                }
            }
        }

        UserRequest userRequest = new UserRequest();
        userRequest.setStatus(userRequest.STATUS_PENDING);
        userRequest.setUser(user);
        userRequest.setProject(project);

        return this.userRequestRepository.save(userRequest);
    }

    public Project acceptApplication(UserRequest userRequest) {
        userRequest.setStatus(userRequest.STATUS_ACCEPTED);

        this.userRequestRepository.save(userRequest);
        return this.projectService.addUserToProject(userRequest.getProject(), userRequest.getUser());
    }

    public Project refuseApplication(UserRequest userRequest) {
        userRequest.setStatus(userRequest.STATUS_REFUSED);

        this.userRequestRepository.save(userRequest);
        return this.projectService.deleteUserForProject(userRequest.getProject(), userRequest.getUser());
    }
}

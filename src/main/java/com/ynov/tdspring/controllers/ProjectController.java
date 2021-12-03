package com.ynov.tdspring.controllers;

import com.ynov.tdspring.entities.Project;
import com.ynov.tdspring.entities.User;
import com.ynov.tdspring.services.ProjectService;
import com.ynov.tdspring.services.SecurityService;
import com.ynov.tdspring.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.List;
import java.util.UUID;

@RestController
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    // --------------------- >

    @RequestMapping(path = "/add-test-project", method = RequestMethod.GET)
    public void addTestProject() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.getUserByUsername(authentication.getName());

        Project project = new Project();
        project.setName("First project");
        project.setAuthor(loggedUser);
        project.setDescription("");
        project.setParticipants(null);
        projectService.create(project);
    }

    @Operation(summary = "Récupération d'un projet")
    @RequestMapping(path = "/project", method = RequestMethod.GET)
    public Project getExit(@RequestParam(value = "id") UUID id) {
        return projectService.getProjectByProjectId(id);
    }

    @Operation(summary = "Création d'un projet")
    @RequestMapping(path = "/project", method = RequestMethod.POST)
    public Project addProject(@Valid @RequestBody Project project) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication.getName());

        project.setAuthor(user);
        return projectService.create(project);
    }

    @Operation(summary = "Mise à jour d'un projet")
    @RequestMapping(path = "/project", method = RequestMethod.PUT)
    public Object updateProject(@Valid @RequestBody Project project) throws Exception {
        Authentication loggedUser = this.securityService.getLoggedUser();
        User user = userService.getUserByUsername(loggedUser.getName());

        if (user != project.getAuthor()){
            throw new Exception("Vous n'êtes pas l'auteur de ce projet");
        }

        return projectService.update(project);
    }

    @Operation(summary = "Listes des participants du projet")
    @RequestMapping(path = "/project/users", method = RequestMethod.GET)
    public List<User> getUserByProject(@RequestParam(value = "id") UUID id) {
        return projectService.getProjectByProjectId(id).getParticipants();
    }

    @Operation(summary = "Ajouter un utilisateur au projet")
    @RequestMapping(path = "/project/user", method = RequestMethod.PUT)
    public Project addUserForProject(@Valid @RequestParam(value = "id") UUID id, @RequestParam(value = "user_id") String username) throws Exception {
        User user = this.userService.getUserByUsername(username);
        Project project = this.projectService.getProjectByProjectId(id);

        if (user == null) {
            throw new Exception("user not found");
        }

        if (project == null) {
            throw new Exception("project not found");
        }

        return projectService.addUserToProject(project, user);
    }

    @Operation(summary = "Supprimer un utilisateur du projet")
    @RequestMapping(path = "/project/user", method = RequestMethod.DELETE)
    public Project deleteUserForProject(@RequestParam(value = "id") UUID id, @RequestParam(value = "user_id") String username) throws Exception {
        User user = this.userService.getUserByUsername(username);
        Project project = this.projectService.getProjectByProjectId(id);

        if (user == null) {
            throw new Exception("user not found");
        }

        if (project == null) {
            throw new Exception("project not found");
        }

        return projectService.refuseUserInProject(project, user);
    }

    @Operation(summary = "Supprimer un utilisateur du projet")
        @RequestMapping(path = "/project/user/accept", method = RequestMethod.DELETE)
        public Project acceptUserForProject(@RequestParam(value = "id") UUID id, @RequestParam(value = "user_id") String username) throws Exception {
            User user = this.userService.getUserByUsername(username);
            Project project = this.projectService.getProjectByProjectId(id);

            if (user == null) {
                throw new Exception("user not found");
            }

            if (project == null) {
                throw new Exception("project not found");
            }

            return projectService.acceptUserInProject(project, user);
        }

    @Operation(summary = "Récupération de touts les projets")
    @RequestMapping(path = "/projects", method = RequestMethod.GET)
    public List<Project> getProjects() {
        return (List<Project>) projectService.getAllProjects();
    }

    @Operation(summary = "Suppression d'un projet à partir de son id")
    @RequestMapping(path = "/project", method = RequestMethod.DELETE)
    public void deleteProject(@RequestParam(value = "id") UUID id) {
    	projectService.delete(id);
    }
}

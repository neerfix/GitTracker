package com.ynov.tdspring.controllers;

import com.ynov.tdspring.entities.Project;
import com.ynov.tdspring.entities.User;
import com.ynov.tdspring.services.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // --------------------- >

    @RequestMapping(path = "/add-test-project", method = RequestMethod.GET)
    public void addTestProject() {
        Project project = new Project();
        project.setName("");
        project.setAuthor(null);
        project.setDescription("");
        project.setParticipants(null);
        projectService.createOrUpdate(project);
    }

    @Operation(summary = "Récupération d'un projet")
    @RequestMapping(path = "/project", method = RequestMethod.GET)
    public Project getExit(@RequestParam(value = "id") UUID id) {
        return projectService.getProjectByProjectId(id);
    }

    @Valid
    @Operation(summary = "Création ou mise à jour d'un projet")
    @RequestMapping(path = "/project", method = RequestMethod.PUT)
    public Project addOrUpdateProject(@RequestBody Project project) {
        return projectService.createOrUpdate(project);
    }

    @Operation(summary = "Listes des participants du projet")
    @RequestMapping(path = "/project/users", method = RequestMethod.GET)
    public List<User> getUserByProject(@RequestParam(value = "id") UUID id) {
        return projectService.getProjectByProjectId(id).getParticipants();
    }

    @Valid
    @Operation(summary = "Ajouter un utilisateur au projet")
    @RequestMapping(path = "/project/user", method = RequestMethod.PUT)
    public Project addUserForProject(@RequestParam(value = "id") UUID id, @RequestParam(value = "user_id") UUID userId) {
        return projectService.addUserToProject(id, userId);
    }

    @Operation(summary = "Supprimer un utilisateur du projet")
    @RequestMapping(path = "/project/user", method = RequestMethod.DELETE)
    public Project deleteUserForProject(@RequestParam(value = "id") UUID id, @RequestParam(value = "user_id") UUID userId) {
        return projectService.deleteUserForProject(id, userId);
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

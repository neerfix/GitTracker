package com.ynov.tdspring.controllers;

import com.ynov.tdspring.entities.Issue;
import com.ynov.tdspring.entities.Project;
import com.ynov.tdspring.entities.Role;
import com.ynov.tdspring.entities.User;
import com.ynov.tdspring.services.IssueService;
import com.ynov.tdspring.services.ProjectService;
import com.ynov.tdspring.services.RoleService;
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
    private RoleService roleService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private IssueService issueService;

    @Autowired
    private SecurityService securityService;

    // --------------------- >

    @RequestMapping(path = "/add-test-project", method = RequestMethod.GET)
    public void addTestProject() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.getUserByUsername(authentication.getName());

        Project project = new Project();
        project.setName("First project");
        project.setProjectManager(loggedUser);
        project.setShortDescription("setShortDescription");
        project.setContent("setContent");
        project.setStatus("setStatus");
        project.setGitUrl("UnUrlGIT");
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
        return projectService.create(project);
    }

    @Operation(summary = "Mise à jour d'un projet")
    @RequestMapping(path = "/project", method = RequestMethod.PUT)
    public Object updateProject(@Valid @RequestBody Project project) throws Exception {
        Authentication loggedUser = this.securityService.getLoggedUser();
        User user = userService.getUserByUsername(loggedUser.getName());
        Role role = roleService.getRoleByProjectAndUsername(project,user.getUsername());
        if (role.getRole() != "admin"){
            throw new Exception("Vous n'êtes pas admin de ce projet");
        }
        return projectService.update(project);
    }
    
    

    @Operation(summary = "Ajout d'une issue à un projet")
    @RequestMapping(path = "/project/issues", method = RequestMethod.PUT)
    public Project proposeRequestForProject(@Valid @RequestParam(value = "id") UUID id, @RequestParam(value = "user_id") String username, @RequestParam(value = "request_id") UUID request_id) throws Exception {
        User user = this.userService.getUserByUsername(username);
        Project project = this.projectService.getProjectByProjectId(id);
        Issue issue = this.issueService.getIssueByIssueId(request_id);
        
        if (user == null) {
            throw new Exception("user not found");
        }

        if (project == null) {
            throw new Exception("project not found");
        }
        
        if (issue == null) {
            throw new Exception("issue not found");
        }
        
        return projectService.addUserIssueToProject(project, issue);
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
    
    @Operation(summary = "Ajout du role utilisateur à un projet")
    @RequestMapping(path = "/project/roles", method = RequestMethod.PUT)
    public Object updateProject(@Valid @RequestParam(value = "id") UUID id, @RequestParam(value = "username") String username, @RequestParam(value = "role_string") String role_string) throws Exception {
        Authentication loggedUser = this.securityService.getLoggedUser();
        User user = userService.getUserByUsername(loggedUser.getName());
        User userToAdd = userService.getUserByUsername(username);
        Project project = projectService.getProjectByProjectId(id);
        Role roletoAdd = new Role();
        roletoAdd.setProject(project);
        roletoAdd.setUser(userToAdd);
        roleService.createOrUpdate(roletoAdd);
        Role role = roleService.getRoleByProjectAndUsername(project,user.getUsername());
        
        if (role.getRole() != "admin"){
            throw new Exception("Vous n'êtes pas admin de ce projet");
        }
        
        return projectService.update(project);
    }
}

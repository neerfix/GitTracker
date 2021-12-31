package com.ynov.tdspring.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.ynov.tdspring.entities.Comment;
import com.ynov.tdspring.entities.Event;
import com.ynov.tdspring.entities.Issue;
import com.ynov.tdspring.entities.Project;
import com.ynov.tdspring.entities.Role;
import com.ynov.tdspring.entities.User;
import com.ynov.tdspring.repositories.RoleRepository;

import org.springframework.web.server.ResponseStatusException;

public class RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserService userService;
	
	@Autowired
	private EventService eventService;

    public Role createOrUpdate(Role role) {
        Event event = new Event();
        this.eventService.create(event.EVENT_ROLE, role.getUser(), event.EVENT_ACTION_CREATE, role);

        return roleRepository.save(role);
    }

    public List<Role> getRoleByUsername(String username) {
    	User user = userService.getUserByUsername(username);
        return roleRepository.findAllByUser(user);
    }
    
    public List<Role> getRolesByProject(Project project) {
        return roleRepository.findAllByProject(project);
    }
    
    public Role getRoleByProjectAndUsername(Project project,String username) {
    	List<Role> roleByProject = this.getRolesByProject(project);
    	User user = userService.getUserByUsername(username);
    	Role roleToFind = new Role();
    	for(int i=0; i<roleByProject.size();i++) {
    		if(roleByProject.get(i).getUser()==user) {
    			roleToFind = roleByProject.get(i);
    		}
    	}
        return roleToFind;
    }
    

    public void delete(Project project,String username) {
        Role deleteRole = this.getRoleByProjectAndUsername(project,username);

        if (deleteRole == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Exit not found"
            );
        }
        roleRepository.delete(deleteRole);
    }
}

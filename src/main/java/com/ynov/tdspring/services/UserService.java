package com.ynov.tdspring.services;

import com.ynov.tdspring.entities.Exit;
import com.ynov.tdspring.entities.Research;
import com.ynov.tdspring.entities.User;
import com.ynov.tdspring.repositories.ResearchRepository;
import com.ynov.tdspring.repositories.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

@Service
public class UserService implements UserDetailsService
{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ResearchRepository researchRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // --------------------- >

    public User createOrUpdate(User user) {
        if (StringUtils.isNotEmpty(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findById(username).orElse(null);
    }

    public List<User> getAllUsers() { return userRepository.findAll(); }

    public void delete(String username) {
        User deleteUser = this.getUserByUsername(username);

        if (deleteUser == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found"
            );
        }

        userRepository.delete(deleteUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws
            UsernameNotFoundException {
        User user = this.getUserByUsername(username);
        if (user != null) {
            List<GrantedAuthority> authorities = new
                    ArrayList<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority("ROLE_" +
                    user.getRole()));
            return new
                    org.springframework.security.core.userdetails.User(user.getUsername(),
                    user.getPassword(),
                    authorities);
        }
        throw new UsernameNotFoundException("User '" + username + "' not found or inactive");
    }

    public void setPassword(String username, String oldPassword, String newPassword) throws IllegalAccessException {
        User user = this.getUserByUsername(username);
        if (user != null) {
            String encodedOldPassword = passwordEncoder.encode(oldPassword);
            String encodedNewPassword = passwordEncoder.encode(newPassword);
            if (StringUtils.isEmpty(user.getPassword()) ||
                    StringUtils.equals(user.getPassword(), encodedOldPassword)) {
                user.setPassword(encodedNewPassword);
                userRepository.save(user);
            } else {
                throw new IllegalAccessException("Invalid old password");
            }
        }
    }

	public List<Research> getResearchsByUserId(String id) {
		return userRepository.findById(id).orElse(null).getResearchs();
	}

	public @Valid User addResearchForUser(String id, UUID research) {
		User user = this.getUserByUsername(id);

        if (user != null) {
            List<Research> listResearchs = user.getResearchs();
            Research researchToAdd = researchRepository.findById(research).orElse(null);

            if (researchToAdd != null) {
            	listResearchs.add(researchToAdd);
                user.setResearchs(listResearchs);
            }

            userRepository.save(user);
        }

        return user;
	}
}

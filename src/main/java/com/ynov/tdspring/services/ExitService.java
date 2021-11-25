package com.ynov.tdspring.services;

import com.ynov.tdspring.entities.Exit;
import com.ynov.tdspring.entities.User;
import com.ynov.tdspring.repositories.ExitRepository;
import com.ynov.tdspring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class ExitService
{
    @Autowired
    private ExitRepository ExitRepository;

    @Autowired
    private UserRepository userRepository;

    // --------------------- >

    public Exit createOrUpdate(Exit exit) {
        return ExitRepository.save(exit);
    }

    public Exit getExitByExitId(UUID id) {
        return ExitRepository.findById(id).orElse(null);
    }

    public Exit addUserForExit(UUID id, String username) {
        Exit exit = this.getExitByExitId(id);

        if (exit != null) {
            List<User> listUsers = exit.getParticipants();
            User userToAdd = userRepository.findById(username).orElse(null);

            if (userToAdd != null) {
                listUsers.add(userToAdd);
                exit.setParticipants(listUsers);
            }

            ExitRepository.save(exit);
        }

        return exit;
    }

    public Exit deleteUserForExit(UUID id, String username) {
        Exit exit = this.getExitByExitId(id);

        if (exit != null) {
            List<User> listUsers = exit.getParticipants();
            User userToAdd = userRepository.findById(username).orElse(null);

            if (userToAdd != null) {
                listUsers.remove(userToAdd);
                exit.setParticipants(listUsers);
            }

            ExitRepository.save(exit);
        }

        return exit;
    }

    public List<Exit> getAllExits() { return ExitRepository.findAll(); }

    public void delete(UUID id) {
        Exit deleteExit = this.getExitByExitId(id);

        if (deleteExit == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Exit not found"
            );
        }

        ExitRepository.delete(deleteExit);
    }
}

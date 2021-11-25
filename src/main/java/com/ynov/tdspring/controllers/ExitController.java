package com.ynov.tdspring.controllers;

import com.ynov.tdspring.entities.Exit;
import com.ynov.tdspring.entities.User;
import com.ynov.tdspring.services.ExitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
public class ExitController {

    @Autowired
    private ExitService exitService;

    // --------------------- >

    @RequestMapping(path = "/add-test-exit", method = RequestMethod.GET)
    public void addTestUser() {
        Exit exit = new Exit();
        exit.setName("Bar");
        exit.setDescription("Aller boire un verre a la kolok");
        exit.setComplexity(0);
        exit.setLocation("Kolok, 6 rue des champs de patatee");
        exit.setDuring("360");
        exit.setDate(new Date());
        exitService.createOrUpdate(exit);
    }

    @Operation(summary = "Récupeération d'une sortie")
    @RequestMapping(path = "/exit", method = RequestMethod.GET)
    public Exit getExit(@RequestParam(value = "id") UUID id) {
        return exitService.getExitByExitId(id);
    }

    @Valid
    @Operation(summary = "Création ou mise à jour d'une sortie")
    @RequestMapping(path = "/exit", method = RequestMethod.PUT)
    public Exit addOrUpdateExit(@RequestBody Exit exit) {
        return exitService.createOrUpdate(exit);
    }

    @Operation(summary = "Listes des utilisateurs de la sortie")
    @RequestMapping(path = "/exit/users", method = RequestMethod.GET)
    public List<User> getUserByExit(@RequestParam(value = "id") UUID id) {
        return exitService.getExitByExitId(id).getParticipants();
    }

    @Valid
    @Operation(summary = "Ajouter un utilisateur à la sortie")
    @RequestMapping(path = "/exit/user", method = RequestMethod.PUT)
    public Exit addUserForExit(@RequestParam(value = "id") UUID id, @RequestParam(value = "username") String username) {
        return exitService.addUserForExit(id, username);
    }

    @Operation(summary = "Supprimer un utilisateur à la sortie")
    @RequestMapping(path = "/exit/user", method = RequestMethod.DELETE)
    public Exit deleteUserForExit(@RequestParam(value = "id") UUID id, @RequestParam(value = "username") String username) {
        return exitService.deleteUserForExit(id, username);
    }

    @Operation(summary = "Récupération de toutes les sorties")
    @RequestMapping(path = "/exits", method = RequestMethod.GET)
    public List<Exit> getExits() {
        return (List<Exit>) exitService.getAllExits();
    }

    @Operation(summary = "Suppression d'une sortie à partir de son id")
    @RequestMapping(path = "/exit", method = RequestMethod.DELETE)
    public void deleteExit(@RequestParam(value = "id") UUID id) {
        exitService.delete(id);
    }
}

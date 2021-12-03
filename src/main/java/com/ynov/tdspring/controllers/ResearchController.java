package com.ynov.tdspring.controllers;

import com.ynov.tdspring.entities.Research;
import com.ynov.tdspring.entities.User;
import com.ynov.tdspring.services.ResearchService;
import com.ynov.tdspring.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
public class ResearchController {

    @Autowired
    private ResearchService researchService;
    private UserService userService;

    // --------------------- >

    @RequestMapping(path = "/add-test-research", method = RequestMethod.GET)
    public void addTestUser() {
        Research research = new Research();
        User author = userService.getUserByUsername("neerfix");
        research.setName("Bar");
        research.setSearch_text("Aller boire un verre a la kolok");
        research.setAuthor(author);
        research.setStatus("Ouvert");
        researchService.createOrUpdate(research);
    }

    @Operation(summary = "Récupération d'une recherche")
    @RequestMapping(path = "/research", method = RequestMethod.GET)
    public Research getResearch(@RequestParam(value = "id") UUID id) {
        return researchService.getResearchByResearchId(id);
    }

    @Valid
    @Operation(summary = "Création ou mise à jour d'une recherche")
    @RequestMapping(path = "/research", method = RequestMethod.PUT)
    public Research addOrUpdateResearch(@RequestBody Research research) {
        return researchService.createOrUpdate(research);
    }

    @Operation(summary = "Auteur de la recherche")
    @RequestMapping(path = "/research/author", method = RequestMethod.GET)
    public User getAuthorByResearch(@RequestParam(value = "id") UUID id) {
        return researchService.getResearchByResearchId(id).getAuthor();
    }

    @Operation(summary = "Récupération de toutes les recherches")
    @RequestMapping(path = "/researchs", method = RequestMethod.GET)
    public List<Research> getResearch() {
        return (List<Research>) researchService.getAllResearchs();
    }

    @Operation(summary = "Suppression d'une recherche à partir de son id")
    @RequestMapping(path = "/research", method = RequestMethod.DELETE)
    public void deleteResearch(@RequestParam(value = "id") UUID id) {
    	researchService.delete(id);
    }
}

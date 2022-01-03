package com.ynov.tdspring.services;

import com.ynov.tdspring.entities.Event;
import com.ynov.tdspring.entities.Project;
import com.ynov.tdspring.entities.User;
import com.ynov.tdspring.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class EventService
{
    @Autowired
    private EventRepository eventRepository;

    public void create(String type, User author, String action, Object entity) {

        Event event = new Event();
        event.setCreatedAt(new Date());
        event.setUpdateAt(new Date());
        event.setTitle("New " + type +  action + " by : " + author.getUsername() + " at " + event.getCreatedAt() + " on this entity : " + entity);
        event.setAuthor(author);
        event.setEntity(entity.getClass().getName());
        event.setEntityId(entity);

        this.eventRepository.save(event);
    }

    public void updateUser(String type, User author, String action, Object entity, String entityToAdd) {

        Event event = new Event();
        event.setCreatedAt(new Date());
        event.setUpdateAt(new Date());
        event.setTitle("New " + type + "("+entityToAdd+")" +  action + " by : " + author + " at " + event.getCreatedAt() + " on this entity : " + entity);
        event.setAuthor(author);
        event.setEntity(entity.getClass().getName());
        event.setEntityId(entity);

        this.eventRepository.save(event);
    }
    
}

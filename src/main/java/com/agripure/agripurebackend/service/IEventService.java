package com.agripure.agripurebackend.service;

import com.agripure.agripurebackend.entities.Event;

import java.time.LocalDate;
import java.util.List;

public interface IEventService extends CrudService<Event> {

    List<Event> findAllByDate(String date) throws Exception;
    List<Event> findAllByUsername(String username) throws Exception;
}
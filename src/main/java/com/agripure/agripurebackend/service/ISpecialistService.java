package com.agripure.agripurebackend.service;

import com.agripure.agripurebackend.entities.Specialist;

public interface ISpecialistService extends CrudService<Specialist> {
    Specialist findByName(String name) throws Exception;
}

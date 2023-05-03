package com.agripure.agripurebackend.service;

import com.agripure.agripurebackend.entities.Plant;

import java.util.List;

public interface IPlantService extends CrudService<Plant>{

    Plant findByName(String name) throws Exception;
    List<Plant> listPlantsByUsername(String username) throws Exception;
}

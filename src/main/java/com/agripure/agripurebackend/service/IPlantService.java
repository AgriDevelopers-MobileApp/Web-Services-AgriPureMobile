package com.agripure.agripurebackend.service;

import com.agripure.agripurebackend.entities.Plant;

import java.util.List;
import java.util.Optional;

public interface IPlantService extends CrudService<Plant>{

    Plant findByName(String name) throws Exception;

    Optional<Plant> getById(Long id) throws Exception;

    List<Plant> listPlantsByUsername(String username) throws Exception;
}

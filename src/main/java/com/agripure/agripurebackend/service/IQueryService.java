package com.agripure.agripurebackend.service;

import com.agripure.agripurebackend.entities.Query;

import java.util.List;

public interface IQueryService extends CrudService<Query>{
    List<Query> findByUsername(String username) throws Exception;
}

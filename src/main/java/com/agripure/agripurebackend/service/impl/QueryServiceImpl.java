package com.agripure.agripurebackend.service.impl;

import com.agripure.agripurebackend.entities.Query;
import com.agripure.agripurebackend.repository.IQueryRepository;
import com.agripure.agripurebackend.service.IQueryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class QueryServiceImpl implements IQueryService {

    private final IQueryRepository queryRepository;

    public QueryServiceImpl(IQueryRepository queryRepository) {
        this.queryRepository = queryRepository;
    }

    @Override
    @Transactional
    public Query save(Query query) throws Exception {
        return queryRepository.save(query);
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        queryRepository.deleteById(id);
    }

    @Override
    public List<Query> getAll() throws Exception {
        return queryRepository.findAll();
    }

    @Override
    public Optional<Query> getById(Long id) throws Exception {
        return queryRepository.findById(id);
    }

    @Override
    public List<Query> findByUsername(String username) throws Exception {
        return queryRepository.findByUser_UserName(username);
    }
}

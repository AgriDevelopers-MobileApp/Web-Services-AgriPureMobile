package com.agripure.agripurebackend.service.impl;

import com.agripure.agripurebackend.entities.Specialist;
import com.agripure.agripurebackend.repository.ISpecialistRepository;
import com.agripure.agripurebackend.service.ISpecialistService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SpecialistServiceImpl implements ISpecialistService {

    private final ISpecialistRepository specialistRepository;

    public SpecialistServiceImpl(ISpecialistRepository specialistRepository) {
        this.specialistRepository = specialistRepository;
    }

    @Override
    @Transactional
    public Specialist save(Specialist specialist) throws Exception {
        return specialistRepository.save(specialist);
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        specialistRepository.deleteById(id);
    }

    @Override
    public List<Specialist> getAll() throws Exception {
        return specialistRepository.findAll();
    }

    @Override
    public Optional<Specialist> getById(Long id) throws Exception {
        return specialistRepository.findById(id);
    }

    @Override
    public Specialist findByName(String name) throws Exception {
        return specialistRepository.findByName(name);
    }
}

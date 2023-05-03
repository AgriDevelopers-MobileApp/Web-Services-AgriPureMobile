package com.agripure.agripurebackend.repository;

import com.agripure.agripurebackend.entities.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISpecialistRepository extends JpaRepository<Specialist, Long> {
    Specialist findByName(String name);
}

package com.agripure.agripurebackend.repository;

import com.agripure.agripurebackend.entities.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPlantRepository extends JpaRepository<Plant, Long> {

    Plant findByName(String name);

    Optional<Plant> findById(Long id);

    @Query("SELECT p FROM User u JOIN u.plants p WHERE u.userName = :username")
    List<Plant> findPlantsByUsername(@Param("username") String username);
}

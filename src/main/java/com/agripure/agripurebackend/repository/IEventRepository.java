package com.agripure.agripurebackend.repository;

import com.agripure.agripurebackend.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByDate(String date);
    @Query("SELECT p FROM User u JOIN u.events p WHERE u.userName = :username")
    List<Event> findEventsByUsername(@Param("username") String username);
}
package com.agripure.agripurebackend.repository;

import com.agripure.agripurebackend.entities.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IQueryRepository extends JpaRepository<Query, Long> {
    List<Query> findByUser_UserName(String username);
}

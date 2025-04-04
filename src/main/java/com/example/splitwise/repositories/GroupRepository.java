package com.example.splitwise.repositories;

import com.example.splitwise.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
    Optional<Group> findById(Integer id);
}

package com.example.effectivemobiletest.repository;

import com.example.effectivemobiletest.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    Page<Task> findAll(Pageable pageable);

    Page<Task> findAllByAuthorId(UUID authorId, Pageable pageable);

    Page<Task> findAllByExecutorId(UUID executorId, Pageable pageable);
}

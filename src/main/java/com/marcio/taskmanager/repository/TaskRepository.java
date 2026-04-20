package com.marcio.taskmanager.repository;

import com.marcio.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    // So de herdar JpaRepository voce ja tem:
    //   save(task)         — salva ou atualiza
    //   findAll()          — lista todos
    //   findById(id)       — busca por ID
    //   deleteById(id)     — deleta por ID
}

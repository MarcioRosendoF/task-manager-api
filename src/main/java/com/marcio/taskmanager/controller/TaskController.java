package com.marcio.taskmanager.controller;

import com.marcio.taskmanager.dto.Task;
import com.marcio.taskmanager.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // POST /tasks — recebe uma tarefa no corpo do pedido e salva
    @PostMapping
    public Task create(@RequestBody Task task) {
        return taskService.create(task);
    }

    // GET /tasks — retorna todas as tarefas
    @GetMapping
    public List<Task> list() {
        return taskService.list();
    }

    // GET /tasks/1 — retorna a tarefa com aquele ID
    @GetMapping("/{id}")
    public Task findById(@PathVariable Long id) {
        return taskService.findById(id);
    }

    // DELETE /tasks/1 — deleta a tarefa com aquele ID
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        taskService.delete(id);
    }
}

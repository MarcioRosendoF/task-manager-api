package com.marcio.taskmanager.controller;

import com.marcio.taskmanager.dto.TaskRequestDTO;
import com.marcio.taskmanager.dto.TaskResponseDTO;
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
    public TaskResponseDTO create(@RequestBody TaskRequestDTO taskDTO) {
        return taskService.create(taskDTO);
    }

    // GET /tasks — retorna todas as tarefas
    @GetMapping
    public List<TaskResponseDTO> list() {
        return taskService.list();
    }

    // GET /tasks/1 — retorna a tarefa com aquele ID
    @GetMapping("/{id}")
    public TaskResponseDTO findById(@PathVariable Long id) {
        return taskService.findById(id);
    }

    // DELETE /tasks/1 — deleta a tarefa com aquele ID
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        taskService.delete(id);
    }
}

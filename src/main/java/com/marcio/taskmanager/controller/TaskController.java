package com.marcio.taskmanager.controller;

import com.marcio.taskmanager.dto.TaskRequestDTO;
import com.marcio.taskmanager.dto.TaskResponseDTO;
import com.marcio.taskmanager.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
    public ResponseEntity<TaskResponseDTO> create(@RequestBody TaskRequestDTO taskDTO) {
        TaskResponseDTO result = taskService.create(taskDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.id())
                .toUri();
        return ResponseEntity.created(uri).body(result);
    }

    // GET /tasks — retorna todas as tarefas
    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> list() {
        List<TaskResponseDTO> list = taskService.list();
        return ResponseEntity.ok(list);
    }

    // GET /tasks/1 — retorna a tarefa com aquele ID
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> findById(@PathVariable Long id) {
        TaskResponseDTO result = taskService.findById(id);
        return ResponseEntity.ok(result);
    }

    // PUT /tasks/1 — atualiza a tarefa com aquele ID
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> update(@PathVariable Long id, @RequestBody TaskRequestDTO taskDTO) {
        TaskResponseDTO result = taskService.update(id, taskDTO);
        return ResponseEntity.ok(result);
    }

    // DELETE /tasks/1 — deleta a tarefa com aquele ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        taskService.delete(id);
    }
}

package com.marcio.taskmanager.controller;

import com.marcio.taskmanager.dto.TaskRequestDTO;
import com.marcio.taskmanager.dto.TaskResponseDTO;
import com.marcio.taskmanager.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Tarefas", description = "Endpoints para gerenciamento de tarefas")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @Operation(summary = "Cria uma nova tarefa", description = "Recebe os dados da tarefa e a salva no banco de dados.")
    public ResponseEntity<TaskResponseDTO> create(@RequestBody @Valid TaskRequestDTO taskDTO) {
        TaskResponseDTO result = taskService.create(taskDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.id())
                .toUri();
        return ResponseEntity.created(uri).body(result);
    }

    @GetMapping
    @Operation(summary = "Lista todas as tarefas", description = "Retorna uma lista contendo todas as tarefas cadastradas.")
    public ResponseEntity<List<TaskResponseDTO>> list() {
        List<TaskResponseDTO> list = taskService.list();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma tarefa por ID", description = "Retorna os detalhes de uma tarefa específica baseada no ID fornecido.")
    public ResponseEntity<TaskResponseDTO> findById(@PathVariable Long id) {
        TaskResponseDTO result = taskService.findById(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma tarefa", description = "Altera os dados de uma tarefa existente com base no ID.")
    public ResponseEntity<TaskResponseDTO> update(@PathVariable Long id, @RequestBody @Valid TaskRequestDTO taskDTO) {
        TaskResponseDTO result = taskService.update(id, taskDTO);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Exclui uma tarefa", description = "Remove permanentemente uma tarefa do banco de dados.")
    public void delete(@PathVariable Long id) {
        taskService.delete(id);
    }
}

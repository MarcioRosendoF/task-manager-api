package com.marcio.taskmanager.service;

import com.marcio.taskmanager.dto.TaskRequestDTO;
import com.marcio.taskmanager.dto.TaskResponseDTO;
import com.marcio.taskmanager.exception.ResourceNotFoundException;
import com.marcio.taskmanager.model.Task;
import com.marcio.taskmanager.repository.TaskRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public TaskResponseDTO create(TaskRequestDTO taskDTO) {
        Task task = new Task();
        task.setTitle(taskDTO.title());
        task.setCompleted(taskDTO.completed());
        
        Task savedTask = repository.save(task);
        return toResponseDTO(savedTask);
    }

    public List<TaskResponseDTO> list() {
        return repository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public TaskResponseDTO findById(@NonNull Long id) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        return toResponseDTO(task);
    }

    public void delete(@NonNull Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Task not found with id: " + id);
        }
        repository.deleteById(id);
    }

    public TaskResponseDTO update(@NonNull Long id, TaskRequestDTO taskDTO) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        
        task.setTitle(taskDTO.title());
        task.setCompleted(taskDTO.completed());
        
        Task updatedTask = repository.save(task);
        return toResponseDTO(updatedTask);
    }

    private TaskResponseDTO toResponseDTO(Task task) {
        return new TaskResponseDTO(task.getId(), task.getTitle(), task.isCompleted());
    }
}

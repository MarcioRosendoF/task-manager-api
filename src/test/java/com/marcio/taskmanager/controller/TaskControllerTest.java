package com.marcio.taskmanager.controller;

import com.marcio.taskmanager.dto.TaskRequestDTO;
import com.marcio.taskmanager.dto.TaskResponseDTO;
import com.marcio.taskmanager.service.TaskService;
import com.marcio.taskmanager.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskService taskService;

    @Test
    public void create_ShouldReturnCreated() throws Exception {
        TaskRequestDTO request = new TaskRequestDTO("New Task", false);
        TaskResponseDTO response = new TaskResponseDTO(1L, "New Task", false);

        when(taskService.create(any(TaskRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/tasks/1"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("New Task"));
    }

    @Test
    public void findById_ShouldReturnNotFound_WhenIdDoesNotExist() throws Exception {
        Long id = 1L;
        when(taskService.findById(id)).thenThrow(new ResourceNotFoundException("Task not found with id: " + id));

        mockMvc.perform(get("/tasks/" + id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Resource not found"))
                .andExpect(jsonPath("$.message").value("Task not found with id: " + id))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.path").value("/tasks/" + id));
    }

    @Test
    public void update_ShouldReturnOk() throws Exception {
        Long id = 1L;
        TaskRequestDTO request = new TaskRequestDTO("Updated Task", true);
        TaskResponseDTO response = new TaskResponseDTO(id, "Updated Task", true);

        when(taskService.update(eq(id), any(TaskRequestDTO.class))).thenReturn(response);

        mockMvc.perform(put("/tasks/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Task"))
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    public void delete_ShouldReturnNoContent() throws Exception {
        Long id = 1L;
        doNothing().when(taskService).delete(id);

        mockMvc.perform(delete("/tasks/" + id))
                .andExpect(status().isNoContent());
    }
}

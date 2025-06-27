package taskmanagementsystem.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import taskmanagementsystem.dto.task.TaskRequest;
import taskmanagementsystem.dto.task.TaskResponse;
import taskmanagementsystem.model.task.Priority;
import taskmanagementsystem.model.task.Status;
import taskmanagementsystem.service.TaskService;


import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;




@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    private TaskRequest taskRequest;
    private TaskResponse taskResponse;

    @BeforeEach
    void setUp() {
        // Настройка тестовых данных с использованием Builder'а и новых полей
        taskRequest = TaskRequest.builder()
                .title("API Task")
                .description("Task Description via API")
                .status(Status.TO_DO)
                .priority(Priority.HIGH)
                .author("Test Author")
                .assignee("Test Assignee")
                .comments(List.of("First comment", "Second comment"))
                .build();

        taskResponse = TaskResponse.builder()
                .title("API Task")
                .description("Task Description via API")
                .status(Status.TO_DO)
                .priority(Priority.HIGH)
                .author("Test Author")
                .assignee("Test Assignee")
                .comments(List.of("First comment", "Second comment"))
                .build();
    }

    @Test
    @WithMockUser
    void create_shouldReturnCreatedTask() throws Exception {
        // Given: Настраиваем мок сервиса
        when(taskService.createTask(any(TaskRequest.class))).thenReturn(taskResponse);

        // When: Выполняем POST-запрос
        ResultActions resultActions = mockMvc.perform(post("/api/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskRequest)));

        // Then: Проверяем результат, включая новые поля
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title", is("API Task")))
                .andExpect(jsonPath("$.status", is(Status.TO_DO.toString()))) // Enum преобразуется в строку
                .andExpect(jsonPath("$.priority", is(Priority.HIGH.toString())))
                .andExpect(jsonPath("$.author", is("Test Author")))
                .andExpect(jsonPath("$.comments[0]", is("First comment")));
    }

    @Test
    @WithMockUser
    void update_shouldReturnUpdatedTask() throws Exception {
        // Given
        Long taskId = 1L;
        when(taskService.updateTask(eq(taskId), any(TaskRequest.class), any(Authentication.class)))
                .thenReturn(taskResponse);

        // When
        ResultActions resultActions = mockMvc.perform(put("/api/v1/tasks/{id}", taskId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskRequest)));

        // Then: Проверяем, что ответ соответствует новому DTO
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(taskResponse.title())))
                .andExpect(jsonPath("$.description", is(taskResponse.description())))
                .andExpect(jsonPath("$.assignee", is(taskResponse.assignee())));
    }

    @Test
    @WithMockUser
    void getById_shouldReturnTask() throws Exception {
        // Given
        Long taskId = 1L;
        when(taskService.getTaskById(taskId)).thenReturn(taskResponse);

        // When
        ResultActions resultActions = mockMvc.perform(get("/api/v1/tasks/{id}", taskId));

        // Then: Проверяем поля в ответе (поле id отсутствует)
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title", is(taskResponse.title())))
                .andExpect(jsonPath("$.author", is(taskResponse.author())));
    }

    @Test
    @WithMockUser
    void getAllTask_shouldReturnPagedTasks() throws Exception {
        // Given
        int page = 1;
        int size = 10;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").ascending());
        Page<TaskResponse> taskPage = new PageImpl<>(List.of(taskResponse), pageable, 1);

        when(taskService.getAll(any(Pageable.class), any(Authentication.class))).thenReturn(taskPage);

        // When
        ResultActions resultActions = mockMvc.perform(get("/api/v1/tasks")
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size)));

        // Then: Проверяем поле в элементе списка (id заменен на title)
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title", is(taskResponse.title())))
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.totalElements", is(1)));
    }

    @Test
    @WithMockUser
    void deleteById_shouldReturnNoContent() throws Exception {
        // Given
        Long taskId = 1L;
        doNothing().when(taskService).delete(taskId);

        // When
        ResultActions resultActions = mockMvc.perform(delete("/api/v1/tasks/{id}", taskId));

        // Then
        resultActions.andExpect(status().isNoContent());
    }
}
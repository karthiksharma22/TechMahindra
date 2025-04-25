package com.app.empdept;

import com.app.empdept.controller.DepartmentController;
import com.app.empdept.model.Department;
import com.app.empdept.repository.DepartmentRepository;
import com.app.empdept.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(DepartmentController.class)
public class DepartmentTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    private Department department;

    @BeforeEach
    public void setUp() {
        department = new Department();
        department.setId(1L);
        department.setName("Engineering");
    }

    @Test
    public void testDepartmentRepositorySaveAndFind() {
        departmentRepository.save(department);
        Department foundDepartment = departmentRepository.findById(department.getId()).orElse(null);
        assertNotNull(foundDepartment);
        assertEquals("Engineering", foundDepartment.getName());
    }

    @Test
    public void testSaveDepartment() {
        when(departmentRepository.save(department)).thenReturn(department);
        Department savedDepartment = departmentService.saveDepartment(department);
        assertNotNull(savedDepartment);
        assertEquals("Engineering", savedDepartment.getName());
        verify(departmentRepository, times(1)).save(department);
    }

    @Test
    public void testGetAllDepartments() {
        when(departmentRepository.findAll()).thenReturn(List.of(department));
        List<Department> departments = departmentService.getAllDepartments();
        assertNotNull(departments);
        assertFalse(departments.isEmpty());
        assertEquals("Engineering", departments.get(0).getName());
    }

    @Test
    public void testGetDepartmentById() {
        when(departmentRepository.findById(1L)).thenReturn(java.util.Optional.of(department));
        Department foundDepartment = departmentService.getDepartmentById(1L).orElse(null);
        assertNotNull(foundDepartment);
        assertEquals("Engineering", foundDepartment.getName());
    }

    @Test
    public void testDeleteDepartment() {
        departmentService.deleteDepartment(1L);
        verify(departmentRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testCreateDepartmentAPI() throws Exception {
        when(departmentRepository.save(department)).thenReturn(department);
        mockMvc.perform(post("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Engineering\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Engineering"));
    }

    @Test
    public void testGetDepartmentByIdAPI() throws Exception {
        when(departmentRepository.findById(1L)).thenReturn(java.util.Optional.of(department));
        mockMvc.perform(get("/api/departments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Engineering"));
    }

    @Test
    public void testDeleteDepartmentAPI() throws Exception {
        mockMvc.perform(delete("/api/departments/1"))
                .andExpect(status().isNoContent());
        verify(departmentRepository, times(1)).deleteById(1L);
    }
}

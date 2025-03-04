package com.wenzhi.leetcode_service.controller;

import com.wenzhi.leetcode_service.dao.JobTaskDao;
import com.wenzhi.leetcode_service.entity.JobTask;
import com.wenzhi.leetcode_service.entity.TriggerType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 方法二：在测试中禁用 Spring Security
 * */
@WebMvcTest(JobController.class)
@SpringBootTest(properties = "spring.security.enabled=false")
public class JobController2Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobTaskDao jobTaskMapper;

    @MockBean
    private com.wenzhi.leetcode_service.task.EnhancedScheduler scheduler;

    private JobTask testTask;

    @BeforeEach
    public void setUp() {
        testTask = new JobTask();
        testTask.setId(1L);
        testTask.setTaskName("Test Task");
        testTask.setTaskClass("com.example.tasks.TestTask");
        testTask.setMethodName("execute");
        testTask.setTriggerType(TriggerType.CRON);
        testTask.setCronExpression("0 0/5 * * * ?");
        testTask.setEnabled(true);
        testTask.setVersion(0);
    }

    @Test
    public void testListJobs() throws Exception {
        List<JobTask> tasks = Collections.singletonList(testTask);
        when(jobTaskMapper.selectEnabledTasks()).thenReturn(tasks);

        mockMvc.perform(get("/jobs"))
                .andExpect(status().isOk());
    }

    @Test
    public void testRefreshJob() throws Exception {
        when(jobTaskMapper.selectById(1L)).thenReturn(testTask);

        mockMvc.perform(post("/jobs/1/refresh"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateJob() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(testTask);

        when(jobTaskMapper.updateWithLock(testTask)).thenReturn(1);

        mockMvc.perform(put("/jobs/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }
}
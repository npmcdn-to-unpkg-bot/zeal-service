package com.zeal.test;

import com.zeal.service.AlbumService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 7/21/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/spring/applicationContext.xml")
public class ActivitiTest {


    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    AlbumService albumService;


    @Test
    @Transactional
    public void test() {

        albumService.unPublish(45, 1);
        Map<String, Object> params = new HashMap<>();
        params.put("albumId", 45L);
        ProcessInstance instance = runtimeService.startProcessInstanceByKey("album-unpublish", params);
        List<Task> user1Tasks = taskService.createTaskQuery().taskAssignee("1").list();
        List<Task> user4Tasks = taskService.createTaskQuery().taskAssignee("4").list();
        System.out.println(user1Tasks.size());
        System.out.println(user4Tasks.size());
    }

    @Test
    public void task() {
        List<Task> user1Tasks = taskService.createTaskQuery().taskAssignee("1").list();
        if (!user1Tasks.isEmpty()) {
            for (Task task : user1Tasks) {
                System.out.println(task.getDescription());
                taskService.complete(task.getId());
            }
        }
    }

    @Test
    public void processes(){
       List<ProcessDefinition> deployments =  repositoryService.createProcessDefinitionQuery().list();
        System.out.println(deployments.size());
    }

}

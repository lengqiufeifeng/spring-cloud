package logan.exemple.activiti.consumer.controller;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author logan
 * @Title: ConsumerController
 * @ProjectName spring-cloud
 * @Description: TODO
 * @date 2018/10/1516:09
 */
@RestController
@RequestMapping("/activiti")
public class ConsumerController {
    Logger logger= LoggerFactory.getLogger(ConsumerController.class);
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    @Autowired
    private RepositoryService repositoryService;

    @RequestMapping(value="/create",method= RequestMethod.GET)
    public boolean startActivity(String processDefinitionKey,String initiator){
        logger.info("method startActivityDemo begin....");

        logger.info( "调用流程存储服务，查询部署数量："
                + repositoryService.createDeploymentQuery().count());
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("apply",initiator);
        map.put("approve","lisi");
        //流程启动
        ExecutionEntity pi1 = (ExecutionEntity) runtimeService.startProcessInstanceByKey(processDefinitionKey,map);
        logger.info("=====",pi1.getId());
        List<Task> tq=taskService.createTaskQuery().list();

        process(initiator);//当前任务办理人
        process("lisi");

        logger.info("method startActivityDemo end....");
        return false;
    }
    private void process(String assignee){
        List<Task> tasks = taskService//与任务相关的Service
                .createTaskQuery()//创建一个任务查询对象
                .taskAssignee(assignee)
                .list();
        if(tasks !=null && tasks.size()>0){
            for(Task task:tasks){
                logger.info("任务ID:"+task.getId());
                logger.info("任务的办理人:"+task.getAssignee());
                logger.info("任务名称:"+task.getName());
                logger.info("任务的创建时间:"+task.getCreateTime());
//                taskService.claim(task.getId(),assignee);
                Map<String,Object> pass=new HashMap<>();
                pass.put("pass",true);
                taskService.complete(task.getId(),pass);
                logger.info("流程实例ID:"+task.getProcessInstanceId());

                logger.info("#####################################");
            }
        }
    }
}

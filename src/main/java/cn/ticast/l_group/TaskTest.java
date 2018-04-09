package cn.ticast.l_group;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskTest {

    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    /**
     * 部署流程定义（从inputStream）
     */
    @Test
    public void deploymentProcessDefinition_inputStream(){

        Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的Sevice
                .createDeployment()//创建一个部署对象
                .name("任务")//添加部署的名称
                .addClasspathResource("diagrams/l_group/task.bpmn")//
                .addClasspathResource("diagrams/l_group/task.png")
                .deploy();//完成部署
        System.out.println("部署ID："+deployment.getId());//
        System.out.println("部署名称："+deployment.getName());//
    }

    /**
     * 启动流程实例
     */
    @Test
    public void setProcessEngine(){
        //流程定义的key
        String processDefinitionKey = "task";
        /**启动流程实例的同时，设置流程变量用来指定任务的办理人，对应task.bpmn文件中的#{userIDs}*/
        Map<String,Object> variables = new HashMap<>();
        variables.put("userID", "大大,中中,小小");
        ProcessInstance pi = processEngine.getRuntimeService()//与正在执行的流程实例和执行对象相关的Service
                .startProcessInstanceByKey(processDefinitionKey,variables);//使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
        System.out.println("流程实例ID："+pi.getId());//流程实例ID   101
        System.out.println("流程定义ID："+pi.getProcessDefinitionId());//流程定义ID  helloworld:1:4
    }

    /**
     * 查询当前人的个人任务
     */
    @Test
    public void findMyPersonalTask(){
        String assignee = "小A";
        List<Task> list = processEngine.getTaskService()//与正在执行的任务管理相关的Service
                .createTaskQuery()//创建任务查询对象
                /**查询条件（where部分）*/
                .taskAssignee(assignee)//指定个人任务查询，指定办理人
                /**排序*/
                .orderByTaskCreateTime().asc()//使用创建时间的升序排列
                /**返回结果集*/
                .list();
        if (list!=null && list.size()>0){
            for (Task task:list) {
                System.out.println("任务ID:"+task.getId());
                System.out.println("任务名称:"+task.getName());
                System.out.println("任务的创建时间:"+task.getCreateTime());
                System.out.println("任务的办理人:"+task.getAssignee());
                System.out.println("流程实例ID:"+task.getProcessInstanceId());
                System.out.println("执行对象ID:"+task.getExecutionId());
                System.out.println("流程定义ID:"+task.getProcessDefinitionId());
                System.out.println("#######################################");
            }
        }
    }

    /**
     * 查询当前人的组任务
     */
    @Test
    public void findMyGroupTask(){
        String candidateUser = "大H";
        List<Task> list = processEngine.getTaskService()//与正在执行的任务管理相关的Service
                .createTaskQuery()//创建任务查询对象
                /**查询条件（where部分）*/
                .taskCandidateGroup(candidateUser)//组任务的办理人查询
                /**排序*/
                .orderByTaskCreateTime().asc()//使用创建时间的升序排列
                /**返回结果集*/
                .list();
        if (list!=null && list.size()>0){
            for (Task task:list) {
                System.out.println("任务ID:"+task.getId());
                System.out.println("任务名称:"+task.getName());
                System.out.println("任务的创建时间:"+task.getCreateTime());
                System.out.println("任务的办理人:"+task.getAssignee());
                System.out.println("流程实例ID:"+task.getProcessInstanceId());
                System.out.println("执行对象ID:"+task.getExecutionId());
                System.out.println("流程定义ID:"+task.getProcessDefinitionId());
                System.out.println("#######################################");
            }
        }
    }

    /**
     * 完成我的任务
     */
    @Test
    public void completePersonalTask(){
        //任务ID
        String taskId = "10805";
        processEngine.getTaskService()//与正在执行的任务管理相关的Service
                .complete(taskId);
        System.out.println("完成任务：任务ID: "+taskId);
    }

    /**查询正在执行的任务办理人表*/
    @Test
    public void findRunPersonTask() {
        //任务ID
        String taskId = "10204";
        List<IdentityLink> list = processEngine.getTaskService()//
                .getIdentityLinksForTask(taskId);

        if (list!=null && list.size()>0) {
            for (IdentityLink identityLink : list) {
                System.out.println(identityLink.getTaskId()+"   "+identityLink.getType()+"   "+identityLink.getProcessInstanceId()+"   "+identityLink.getUserId());
            }
        }
    }

    /**查询历史任务的办理人表*/
    @Test
    public void findHistoryPersonTask() {
        //流程实例ID
        String processInstanceId = "10201";
        List<HistoricIdentityLink> list = processEngine.getHistoryService()//
                .getHistoricIdentityLinksForProcessInstance(processInstanceId);

        list.stream().forEach((e) -> System.out.println(e.getTaskId()+"   "+e.getType()+"   "+e.getProcessInstanceId()+"   "+e.getUserId()));
    }

    /**拾取任务，将组任务分给个人任务，指定任务的办理人字段*/
    @Test
    public void claim() {
        //将组任务分配给个人任务
        //任务ID
        String taskId = "10805";
        //分配的个人任务（可以使组任务的成员，也可以是非组任务的成员）
        String userId = "大大";
        processEngine.getTaskService()//
                    .claim(taskId, userId);
    }

    /**将个人任务回退到组任务,前提，之前一定是个组任务*/
    @Test
    public void setAssigee() {
        //任务ID
        String taskId = "10204";
        processEngine.getTaskService()//
                .setAssignee(taskId,null);
    }

    /**向组任务中添加成员*/
    @Test
    public void addGroupUser() {
        //任务ID
        String taskId = "10204";
        //成员办理人
        String userId = "大H";
        processEngine.getTaskService()//
                    .addCandidateUser(taskId, userId);
    }

    /**从组任务中删除成员*/
    @Test
    public void deleteGroupUser() {
        //任务ID
        String taskId = "10204";
        //成员办理人
        String userId = "小B";
        processEngine.getTaskService()//
                .delegateTask(taskId, userId);
    }
}

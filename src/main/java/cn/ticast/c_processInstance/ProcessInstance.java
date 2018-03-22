package cn.ticast.c_processInstance;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

public class ProcessInstance {

    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    /**
     * 部署流程定义（从zip）
     */
    @Test
    public void deploymentProcessDefinition_zip(){
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("diagrams/helloworld.zip");
        ZipInputStream zipInputStream = new ZipInputStream(in);
        Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的Sevice
                .createDeployment()//创建一个部署对象
                .name("流程定义")//添加部署的名称
                .addZipInputStream(zipInputStream)//指定zip格式的文件完成部署
                .deploy();//完成部署
        System.out.println("部署ID："+deployment.getId());//1
        System.out.println("部署名称："+deployment.getName());//
    }

    /**
     * 启动流程实例
     */
    @Test
    public void setProcessEngine(){
        //流程定义的key
        String processDefinitionKey = "helloworld";
        org.activiti.engine.runtime.ProcessInstance pi = processEngine.getRuntimeService()//与正在执行的流程实例和执行对象相关的Service
                .startProcessInstanceByKey(processDefinitionKey);//使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
        System.out.println("流程实例ID："+pi.getId());//流程实例ID   101
        System.out.println("流程定义ID："+pi.getProcessDefinitionId());//流程定义ID  helloworld:1:4
    }


}

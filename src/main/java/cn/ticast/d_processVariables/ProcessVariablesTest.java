package cn.ticast.d_processVariables;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

public class ProcessVariablesTest {

    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    /**
     * 部署流程定义（从zip）
     */
    @Test
    public void deploymentProcessDefinition_inputStream(){
        InputStream inputStreambpmn = this.getClass().getResourceAsStream("/diagrams/processVariables.bpmn");
        InputStream inputStreampng = this.getClass().getResourceAsStream("/diagrams/processVariables.png");
        Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的Sevice
                .createDeployment()//创建一个部署对象
                .name("流程定义")//添加部署的名称
                .addInputStream("processVariables.bpmn", inputStreambpmn)//使用资源文件的名称（要求：与资源文件的名称要一致），和输入流完成部署
                .addInputStream("processVariables.png", inputStreambpmn)//使用资源文件的名称（要求：与资源文件的名称要一致），和输入流完成部署
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
        String processDefinitionKey = "processVariables";
        org.activiti.engine.runtime.ProcessInstance pi = processEngine.getRuntimeService()//与正在执行的流程实例和执行对象相关的Service
                .startProcessInstanceByKey(processDefinitionKey);//使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
        System.out.println("流程实例ID："+pi.getId());//流程实例ID
        System.out.println("流程定义ID："+pi.getProcessDefinitionId());//流程定义ID
    }


    /**设置流程变量*/

    /**获取流程变量*/
}

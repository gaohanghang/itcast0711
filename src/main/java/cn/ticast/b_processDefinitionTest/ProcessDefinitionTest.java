package cn.ticast.b_processDefinitionTest;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.FileUtils;
import org.junit.Test;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;
import java.util.List;
import java.util.zip.ZipInputStream;

public class ProcessDefinitionTest {

    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    /**
     * 部署流程定义（从classpath）
     */
    @Test
    public void deploymentProcessDefinition_classpath(){
        Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的Sevice
                .createDeployment()//创建一个部署对象
                .name("流程定义")//添加部署的名称
                .addClasspathResource("diagrams/helloworld.bpmn")//从classpath的资源加载，一次只能加载一个文件
                .addClasspathResource("diagrams/helloworld.png")//从classpath的资源中加载，一次只能加载一个文件
                .deploy();//完成部署
        System.out.println("部署ID："+deployment.getId());//1
        System.out.println("部署名称："+deployment.getName());//
    }

    /**
     * 部署流程定义（从classpath）
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
     * 查询流程定义
     */
    @Test
    public void findProcessDefinition() {
        List<ProcessDefinition> list = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
                .createProcessDefinitionQuery()//创建一个流程定义的查询
                /**指定查询条件，where条件*/
//                        .deploymentId(deploymentId)//使用部署对象ID查询
//                        .processDefinitionId(processDefinitionId)//使用流程定义ID查询
//                        .processDefinitionKey(processDefinitionKey)//使用流程定义的key查询
//                        .processDefinitionNameLike(processDefinitionNameLike)//使用流程定义的名称模糊查询

                /**排序*/
                .orderByProcessDefinitionVersion().asc()//按照版本的升序排列
//                        .orderByProcessDefinitionName().desc()//按照流程定义的名称降序排列

                /**返回的结果集*/
                .list();//返回一个集合列表，封装流程定义
//                        .singleResult();//返回唯一结果集
//                        .count();//返回唯一结果集
//                        .listPage(firstResult, maxResults);//分页查询

            if (list!=null && list.size()>0) {
                for (ProcessDefinition pd : list) {
                    System.out.println("流程定义ID:"+pd.getId());//流程定义的key+版本+随机数
                    System.out.println("流程定义的名称:"+pd.getName());//对应helloworld.bpmn文件中的name属性值
                    System.out.println("流程定义的Key:"+pd.getKey());//对应helloworld.bpmn文件中的id属性值
                    System.out.println("流程定义的版本:"+pd.getVersion());//当流程定义的key值相同的情况下，版本升级，默认1
                    System.out.println("资源名称bpmn文件:"+pd.getResourceName());
                    System.out.println("资源名称png文件:"+pd.getDiagramResourceName());
                    System.out.println("部署对象ID: "+pd.getDeploymentId());
                    System.out.println("##################################");
                }
            }

    }

    /**
     * 删除流程定义
     */
    @Test
    public void deleteProcessDefinition(){
        //使用部署ID，完成删除
        String deploymentId = "701";
        /**
         * 不带级联的删除
         *      只能删除没有启动的流程，如果流程启动，就会抛出异常
         */
//        processEngine.getRepositoryService()//
//                        .deleteDeployment(deploymentId);

        /**
         * 级联删除
         *  不管流程是否启动，都可以删除
         */
        processEngine.getRepositoryService()//
                        .deleteDeployment(deploymentId, true);
        System.out.println("删除成功！");
    }

    /**
     * 查看流程图
     * @throws IOException
     */
    @Test
    public void viewPic() throws IOException {
        /**将生成的图片放到文件夹下*/
        String deploymentId = "901";

        //获取图片资源的名称
        List<String> list = processEngine.getRepositoryService()//
                        .getDeploymentResourceNames(deploymentId);
        //定义图片资源的名称
        String resourceName = "";
        if (list!=null && list.size()>0) {
            for (String name : list) {
                if (name.indexOf(".png")>=0) {
                    resourceName = name;
                }
            }
        }

        //获取图片的输入流
        InputStream in = processEngine.getRepositoryService()//
                        .getResourceAsStream(deploymentId, resourceName);

        //将图片生成到D盘的目录下
        File file = new File("E:/"+resourceName);
        //将输入流的图片写到D盘下
        FileUtils.copyInputStreamToFile(in, file);
    }
}

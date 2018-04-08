package cn.ticast.e_historyQuery;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.junit.Test;

import java.util.List;

public class HistoryQueryTest {

    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    /**
     * 查询历史的流程实例
     */
    @Test
    public void findHistoryProcessInstance(){
        String processInstanceId  = "3001";
        HistoricProcessInstance hpi = processEngine.getHistoryService()//与历史数据（历史表）相关的Service
                        .createHistoricProcessInstanceQuery()//创建历史流程实例查询
                        .processInstanceId(processInstanceId)//使用流程实例ID查询
                        .orderByProcessInstanceStartTime().asc()//使用流程实例ID查询
                        .singleResult();
        System.out.println(hpi.getId()+"    "+hpi.getProcessDefinitionId()+"   "+hpi.getStartTime()+"   "+hpi.getEndTime()+"   "+hpi.getDurationInMillis());
    }

    /**
     * 查询历史活动
     */
    @Test
    public void findHistoryActiviti() {
        String processInstanceId = "3001";
        List<HistoricActivityInstance> list = processEngine.getHistoryService()//
                .createHistoricActivityInstanceQuery()//创建历史活动实例的查询
                .processInstanceId(processInstanceId)//
                .orderByHistoricActivityInstanceStartTime().asc()//
                .list();

        if (list!=null && list.size()>0) {
            for (HistoricActivityInstance hai: list) {
                System.out.println(hai.getId()+"   "+hai.getProcessInstanceId()+"  "+hai.getActivityType()+"  "+hai.getStartTime()+"  "+hai.getEndTime()+"  "+hai.getDurationInMillis());
                System.out.println("###################");
            }
        }
    }

    /**
     * 查询历史任务
     */
    @Test
    public void findHistoryTask() {
        String processInstanceId = "3001";
        List<HistoricTaskInstance> list = processEngine.getHistoryService()//
                .createHistoricTaskInstanceQuery()//创建历史活动实例的查询
                .processInstanceId(processInstanceId)//
                .orderByHistoricTaskInstanceStartTime().asc()
                .list();

        if (list!=null && list.size()>0) {
            for (HistoricTaskInstance hai: list) {
                System.out.println(hai.getId()+"   "+hai.getProcessInstanceId()+"  "+hai.getStartTime()+"  "+hai.getEndTime()+"  "+hai.getDurationInMillis());
                System.out.println("###################");
            }
        }
    }

    /**查询流程变量的历史表*/
    @Test
    public void findHistoryProcessVariables() {
        String processInstanceId = "3001";
        List<HistoricVariableInstance> list = processEngine.getHistoryService()//
                .createHistoricVariableInstanceQuery()//创建一个历史的流程变量
                .processInstanceId(processInstanceId)//
                .list();
        if (list!=null && list.size()>0){
            for (HistoricVariableInstance hvi:
                    list) {
                System.out.println(hvi.getId()+"   "+hvi.getProcessInstanceId()+"   "+hvi.getVariableName()+"   "+hvi.getVariableTypeName()+"   "+hvi.getValue());
                System.out.println("##############################");
            }
        }
    }


}

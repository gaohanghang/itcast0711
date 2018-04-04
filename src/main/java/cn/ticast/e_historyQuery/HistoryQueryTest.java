package cn.ticast.e_historyQuery;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.junit.Test;

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


}

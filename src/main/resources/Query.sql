#部署对象和流程定义相关的表
SELECT * FROM act_re_deployment		#部署对象表

SELECT * FROM act_re_procdef		  #流程定义表

SELECT * FROM act_ge_bytearray		#资源文件表

SELECT * FROM act_ge_property			#主键生成策略
#####################################################################
#流程实例, 执行对象, 任务
SELECT * FROM act_ru_execution  #正在执行的执行对象表

SELECT * FROM act_hi_procinst  #流程实例的历史表

SELECT * FROM act_ru_task     #正在执行的任务表（只有节点是UserTask的时候，该表中存在数据）

SELECT * FROM act_hi_taskinst   #任务历史表（只有节点是UserTask的时候，该表中存在数据）

SELECT * FROM act_hi_actinst  #所有活动节点的历史表

#####################################################################
#流程变量
#在流程执行或者任务执行的过程中，用于设置和获取变量，使用流程变量在流程传递过程中传递业务参数
SELECT * FROM act_ru_variable #正在执行的流程变量表

SELECT * FROM act_hi_varinst #历史的流程变量表

##########################################
SELECT * FROM act_ru_identitylink  #任务表(个人任务，组任务)

SELECT* FROM act_hi_identitylink #历史任务办理人表（个人任务，组任务）
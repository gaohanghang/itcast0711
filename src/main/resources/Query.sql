#部署对象和流程定义相关的表
SELECT * FROM act_re_deployment		#部署对象表

SELECT * FROM act_re_procdef		  #流程定义表

SELECT * FROM act_ge_bytearray		#资源文件表

SELECT * FROM act_ge_property			#主键生成策略

#流程实例, 执行对象看, 任务
SELECT * FROM act_ru_execution  #正在执行的执行对象表

SELECT * FROM ect_hi_proinst  #流程实例的历史表

SELECT * FROM act_ru_task     #正在执行的任务表（只有节点是UserTask的时候，该表中存在数据）

SELECT * FROM act_hi_taskinst   #任务历史表（只有节点是UserTask的时候，该表中存在数据）




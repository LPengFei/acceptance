-- 添加一个跟任务id相关记录任务下面所有验收项的操作的记录。
alter table `result_issue_log`   
Add column tid varchar(50) default null AFTER `riid` 
MODIFY column riid VARCHAR(50) NULL DEFAULT NULL;
MODIFY column status VARCHAR(50) NULL DEFAULT NULL;
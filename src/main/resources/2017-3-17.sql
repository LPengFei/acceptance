-- ���һ��������id��ؼ�¼������������������Ĳ����ļ�¼��
alter table `result_issue_log`   
Add column tid varchar(50) default null AFTER `riid` 
MODIFY column riid VARCHAR(50) NULL DEFAULT NULL;
MODIFY column status VARCHAR(50) NULL DEFAULT NULL;
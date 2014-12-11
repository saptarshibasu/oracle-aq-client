DECLARE
   subscriber sys.aq$_agent;

BEGIN
   dbms_aqadm.create_queue_table (Queue_table => 'poc.employee_qtab', Multiple_consumers => FALSE, Queue_payload_type => 'SYS.AQ$_JMS_TEXT_MESSAGE');

   DBMS_AQADM.CREATE_QUEUE (queue_name         => 'poc.employee_queue',queue_table        => 'poc.Employee_qtab', max_retries => 3, retry_delay => 1);

   DBMS_AQADM.START_QUEUE (queue_name => 'poc.employee_queue');

   DBMS_AQADM.START_QUEUE(queue_name  =>  'poc.employee_queue');
END;


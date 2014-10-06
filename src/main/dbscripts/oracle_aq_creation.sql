DECLARE
   subscriber sys.aq$_agent;

   /* Add subscribers RED and GREEN to the suscriber list: */
BEGIN
   dbms_aqadm.create_queue_table (Queue_table => 'poc.Employee_qtab', Multiple_consumers => TRUE, Queue_payload_type => 'SYS.AQ$_JMS_TEXT_MESSAGE');

   DBMS_AQADM.CREATE_QUEUE (queue_name         => 'poc.employee_queue',queue_table        => 'poc.Employee_qtab');

   DBMS_AQADM.START_QUEUE (queue_name => 'poc.employee_queue');

   subscriber := sys.aq$_agent('GREEN', NULL, NULL);
   DBMS_AQADM.ADD_SUBSCRIBER(queue_name => 'poc.employee_queue',
   subscriber => subscriber); 

   DBMS_AQADM.START_QUEUE(queue_name  =>  'poc.employee_queue');
END;


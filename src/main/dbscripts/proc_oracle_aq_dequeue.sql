CREATE OR REPLACE PROCEDURE emp_dequeue_proc as
   dequeue_options     dbms_aq.dequeue_options_t;
   message_properties  dbms_aq.message_properties_t;
   message_handle      RAW(16);
   message             SYS.AQ$_JMS_TEXT_MESSAGE;
   no_messages              exception; 
   pragma exception_init    (no_messages, -25228); 

BEGIN
   dequeue_options.consumer_name := 'GREEN';
   dequeue_options.wait := 1;
   DBMS_AQ.DEQUEUE(queue_name => 'poc.employee_queue',
           dequeue_options    => dequeue_options,
           message_properties => message_properties,
           payload            => message,
           msgid              => message_handle);

   DBMS_OUTPUT.PUT_LINE ('MSG_TYPE='||message.get_string_property('MSG_TYPE'));
   DBMS_OUTPUT.PUT_LINE ('MSG_NAME='||message.get_string_property('EMP_NAME'));
   DBMS_OUTPUT.PUT_LINE ('MSG_AGE='||message.get_int_property('EMP_AGE'));
EXCEPTION
   WHEN no_messages THEN
        DBMS_OUTPUT.PUT_LINE ('No more messages');
END;
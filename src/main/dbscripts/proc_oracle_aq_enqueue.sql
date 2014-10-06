create or replace procedure poc.emp_enqueue_proc
(
       p_name VARCHAR2,
       p_age INTEGER,
       p_type VARCHAR2
)
as
   id 		       pls_integer;
   enqueue_options     DBMS_AQ.enqueue_options_t;
   message_properties  DBMS_AQ.message_properties_t;
   message_handle      RAW(16);
   message             SYS.AQ$_JMS_TEXT_MESSAGE;
BEGIN
   message := sys.aq$_jms_text_message.construct;
   message.set_string_property('MSG_TYPE', p_type);
   message.set_string_property('EMP_NAME', p_name);
   message.set_int_property('EMP_AGE', p_age);
   message.set_text('EMP_NAME='||p_name||', EMP_AGE='||p_age);
   
   DBMS_AQ.ENQUEUE(queue_name => 'poc.employee_queue',           
         enqueue_options      => enqueue_options,       
         message_properties   => message_properties,     
         payload              => message,               
         msgid                => message_handle);

end;


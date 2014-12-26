Spring managed LMAX Disruptor Example project
==================
This project uses [disruptor-spring-manager](../disruptor-spring-manager) to create disruptor spring beans and perform message transactions. 
The project uses an embedded tomcat server to kick start the application using maven. Integration with IBM Websphere MQ is required to run this project. Ofcourse you can make minor modifications to get this running against ActiveMQ etc.      

The example uses a disruptor bean to process billing records.

Software pre-requisite
--------
1. JDK 6+
2. Maven 3+
3. Git      
4. IBM Websphere MQ server       


Setting up your environment
----
1. Create a local queue manager. Name it as you wish.   
2. Create a queue. Name it as your wish.     
3. Start queue manager    
4. Update [tomcat context](src/main/webapp/META-INF/context.xml) with the queue connection info and queue name.  


Configuring the disruptor
----------

The spring configuration is based on the Consumer Dependency diamond graph that looks like this:

	                                       |                                       |                                                    |
	                                       |                                       |                                                    |
	                                       |      journalBillingEventProcessor     |      billingBusinessEventProcessor                 |
	                                       |     /                                 |     /                                              |
	                                       |    /                                  |    /                                               |
	billingEventPublisher -> Ring Buffer ->|   -                                   |   -  corporateBillingBusinessEventProcessor        | -billingOutboundFormattingEventProcessor
	                                       |    \                                  |    \                                               |
	                                       |     \                                 |     \                                              |
	                                       |      billingValidationEventProcessor  |      customerSpecificBillingBusinessEventProcessor |
	                                       |                                       |                                                    |
	                                       |                                       |                                                    |


Spring configuration:    

	<bean id="billingDisruptor" class="org.anair.disruptor.DisruptorConfig"
		init-method="init" destroy-method="controlledShutdown">

		<property name="threadName" value="billingThread" />
		<property name="eventFactory">
			<bean
				class="org.anair.billing.disruptor.eventfactory.BillingEvent" />
		</property>
		<property name="eventHandlerChain">
			<array>
				<bean class="org.anair.disruptor.EventHandlerChain" scope="prototype">
					<constructor-arg name="currentEventHandlers">
						<array value-type="com.lmax.disruptor.EventHandler">
							<ref bean="journalBillingEventProcessor" />
							<ref bean="billingValidationEventProcessor" />
						</array>
					</constructor-arg>
					<constructor-arg name="nextEventHandlers">
						<array value-type="com.lmax.disruptor.EventHandler">
							<ref bean="billingBusinessEventProcessor" />
							<ref bean="corporateBillingBusinessEventProcessor" />
							<ref bean="customerSpecificBillingBusinessEventProcessor" />
						</array>
					</constructor-arg>
				</bean>
				
				<bean class="org.anair.disruptor.EventHandlerChain" scope="prototype">
					<constructor-arg name="currentEventHandlers">
						<array value-type="com.lmax.disruptor.EventHandler">
							<ref bean="billingBusinessEventProcessor" />
							<ref bean="corporateBillingBusinessEventProcessor" />
							<ref bean="customerSpecificBillingBusinessEventProcessor" />
						</array>
					</constructor-arg>
					<constructor-arg name="nextEventHandlers">
						<array value-type="com.lmax.disruptor.EventHandler">
							<ref bean="billingOutboundFormattingEventProcessor" />
						</array>
					</constructor-arg>
				</bean>
			</array>
		</property>
	</bean>


Components
----
1. [BillingRecord](src/main/java/org/anair/billing/model/BillingRecord.java) represents a Billing data model.        
2. [BillingEvent](src/main/java/org/anair/billing/disruptor/eventfactory/BillingEvent.java) is used to preload the ringbuffer with BillingRecord objects so that they don't get GCed.          
3. [BillingServiceImpl](src/main/java/org/anair/billing/service/BillingServiceImpl.java) performs billing business logic.    
4. [BillingEventPublisher](src/main/java/org/anair/billing/disruptor/publisher/BillingEventPublisher.java) prepares to publish billing records to the ring buffer       
5. [BillingEventTranslator](src/main/java/org/anair/billing/disruptor/eventtranslator/BillingEventTranslator.java) actually puts the data in ring buffer   
6. [Event Processors/consumers](src/main/java/org/anair/billing/disruptor/eventprocessor) consume off the ring buffer and can perform parallel operations on the data    
4. [BillingMessageListener](src/main/java/org/anair/billing/message/listener/BillingMessageListener.java) receives the MQ message and calls BillingEventPublisher.       
5. Check out [spring configuration](src/main/webapp/WEB-INF) for all spring configuration                      

Run it
----
1.Start tomcat server and the application    
	
	mvn tomcat7:run
You will see the following log messages that prints the disruptor configuration and dependency graph:

	15:58:17.269 localhost-startStop-1 INFO [DisruptorConfig] Created and configured LMAX disruptor {Thread Name: billingThread | Ringbuffer slot size: 1024 | Producer type: SINGLE | Wait strategy: BLOCKING}
	15:58:17.279 localhost-startStop-1 INFO [DisruptorConfig]
	{JournalBillingEventProcessor | BillingValidationEventProcessor} -> {BillingBusinessEventProcessor | CorporateBillingBusinessEventProcessor | CustomerSpecificBillingBusinessEventProcessor}
	{BillingBusinessEventProcessor | CorporateBillingBusinessEventProcessor | CustomerSpecificBillingBusinessEventProcessor} -> {BillingOutboundFormattingEventProcessor}
	15:58:17.289 localhost-startStop-1 INFO [JmxDisruptor] disruptor-spring:name=billingDisruptor,type=disruptor MBean defined for Disruptors.
	
2.Drop a message to the input queue. The message should be an integer that relays the number of billing messages to be generated and processed.
If you put in message of "20", here is the log output:

	16:11:37.189 billingListenerContainer-1 INFO [BillingEventTranslator] Published Id [0] to sequence: 0
	16:11:37.189 billingListenerContainer-1 INFO [BillingEventTranslator] Published Id [10] to sequence: 10
	16:11:37.199 billingThread INFO [BillingValidationEventProcessor] Sequence: 0. Id [0]
	16:11:37.269 billingThread INFO [BillingValidationEventProcessor] Sequence: 10. Id [10]
	16:11:37.199 billingThread INFO [JournalBillingEventProcessor] Sequence: 0. Id [0]
	16:11:37.289 billingThread INFO [BillingBusinessEventProcessor] Sequence: 0. Id [0]
	16:11:37.289 billingThread INFO [CustomerSpecificBillingBusinessEventProcessor] Sequence: 0. Id [0]
	16:11:37.299 billingThread INFO [CorporateBillingBusinessEventProcessor] Sequence: 0. Id [0]
	16:11:37.299 billingThread INFO [JournalBillingEventProcessor] Sequence: 10. Id [10]
	16:11:37.309 billingThread INFO [CustomerSpecificBillingBusinessEventProcessor] Sequence: 10. Id [10]
	16:11:37.309 billingThread INFO [BillingBusinessEventProcessor] Sequence: 10. Id [10]
	16:11:37.309 billingThread INFO [BillingOutboundFormattingEventProcessor] Sequence: 0. Id [0]
	16:11:37.309 billingThread INFO [CorporateBillingBusinessEventProcessor] Sequence: 10. Id [10]
	16:11:37.310 billingThread INFO [BillingOutboundFormattingEventProcessor] Sequence: 10. Id [10]


JMX
---
1.On application context startup, all Disruptor beans will be automatically identified and registered as MBeans         
2.View Disruptor MBeans through JConsole/Visual VM that looks like: ![MBean](disruptor-billing-mbean-browser.PNG)

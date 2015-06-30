package com.prodyna.showcase.pizza.messaging;

import com.prodyna.showcase.pizza.business.BusinessObject;
import com.prodyna.showcase.pizza.business.BusinessObjectConverter;
import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.slf4j.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(name = "OrderQueueReader", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/queue/PizzaOrder"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class OrderQueueReader implements MessageListener {

    @Inject
    private Logger log;

//    @Inject
//    private BusinessProcess businessProcess;

    @Inject
    private BusinessObjectConverter converter;

    @Inject
    private OrderQueueWriter orderWriter;

    @Override
    public void onMessage(Message message) {
        log.info("Received message " + message);
        if (message instanceof MapMessage) {
            try {
                MapMessage mapMessage = (MapMessage) message;
                BusinessObject bo = converter.convert( mapMessage );
                log.info("Received order " + bo);
                // businessProcess.startProcessById()
                orderWriter.writeOrder(bo, true);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("Unsupported message type: " + message.getClass().getName());
        }
    }

}

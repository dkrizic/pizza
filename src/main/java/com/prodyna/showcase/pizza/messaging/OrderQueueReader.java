package com.prodyna.showcase.pizza.messaging;

import com.prodyna.showcase.pizza.business.Order;
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

    @Inject
    private OrderQueueWriter orderWriter;

    @Override
    public void onMessage(Message message) {
        log.info("Received message " + message);
        if (message instanceof MapMessage) {
            try {
                MapMessage mapMessage = (MapMessage) message;
                Order order = new Order();
                order.setCount(mapMessage.getInt(MessageKeys.COUNT));
                order.setNumber(mapMessage.getInt(MessageKeys.NUMBER));
                order.setDescription(mapMessage.getString(MessageKeys.DESCRIPTION));
                log.info("Received order " + order);
                orderWriter.writeOrder(order, true);
            } catch (JMSException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("Unsupported message type: " + message.getClass().getName());
        }
    }

}

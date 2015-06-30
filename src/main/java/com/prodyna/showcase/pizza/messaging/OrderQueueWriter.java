package com.prodyna.showcase.pizza.messaging;

import com.prodyna.showcase.pizza.business.Order;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Queue;

public class OrderQueueWriter {

    @Inject
    private JMSContext context;

    @Resource(lookup = "java:/jms/queue/PizzaOrderAccepted")
    private Queue orderAccept;

    @Resource(lookup = "java:/jms/queue/PizzaOrderRejected")
    private Queue orderReject;

    public void writeOrder(Order order, boolean accept) throws JMSException {
        MapMessage mm = context.createMapMessage();
        mm.setInt(MessageKeys.COUNT, order.getCount());
        mm.setInt(MessageKeys.NUMBER, order.getNumber());
        mm.setString(MessageKeys.DESCRIPTION, order.getDescription());
        context.createProducer().send(accept ? orderAccept : orderReject, mm);
    }
}

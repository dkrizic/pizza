package com.prodyna.showcase.pizza.messaging;

import com.prodyna.showcase.pizza.business.Order;
import com.prodyna.showcase.pizza.common.OrderAccepted;
import com.prodyna.showcase.pizza.common.OrderRejected;

import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Queue;

public class OrderQueueWriter {

    @Inject
    private JMSContext context;

    @Inject
    @OrderAccepted
    private Queue orderAccept;

    @Inject
    @OrderRejected
    private Queue orderReject;

    public void writeOrder(Order order, boolean accept) throws JMSException {
        MapMessage mm = context.createMapMessage();
        mm.setInt(MessageKeys.COUNT, order.getCount());
        mm.setInt(MessageKeys.NUMBER, order.getNumber());
        mm.setString(MessageKeys.DESCRIPTION, order.getDescription());
        context.createProducer().send(accept ? orderAccept : orderReject, mm);
    }

}

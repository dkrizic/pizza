package com.prodyna.showcase.pizza.messaging;

import com.prodyna.showcase.pizza.business.BusinessObject;
import com.prodyna.showcase.pizza.business.BusinessObjectConverter;
import com.prodyna.showcase.pizza.common.OrderAccepted;
import com.prodyna.showcase.pizza.common.OrderRejected;

import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.MapMessage;
import javax.jms.Queue;

public class OrderQueueWriter {

    @Inject
    private JMSContext context;

    @Inject
    private BusinessObjectConverter converter;

    @Inject
    @OrderAccepted
    private Queue orderAccept;

    @Inject
    @OrderRejected
    private Queue orderReject;

    public void writeOrder(BusinessObject bo, boolean accept) throws Exception {
        MapMessage mm = converter.convert(bo);
        context.createProducer().send(accept ? orderAccept : orderReject, mm);
    }

}

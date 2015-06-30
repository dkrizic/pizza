package com.prodyna.showcase.pizza.common;

import javax.annotation.Resource;
import javax.enterprise.inject.Produces;
import javax.jms.Queue;

/**
 * Created by dkrizic on 30.06.15.
 */
public class QueueProducer {

    @Resource(lookup = "java:/jms/queue/PizzaOrder")
    private Queue order;

    @Resource(lookup = "java:/jms/queue/PizzaOrderAccepted")
    private Queue orderAccepted;

    @Resource(lookup = "java:/jms/queue/PizzaOrderRejected")
    private Queue orderRejected;

    @Produces
    @Order
    public Queue orderQueue() {
        return order;
    }

    @Produces
    @OrderAccepted
    public Queue orderAcceptedQueue() {
        return orderAccepted;
    }

    @Produces
    @OrderRejected
    public Queue orderRejectedQueue() {
        return orderRejected;
    }

}

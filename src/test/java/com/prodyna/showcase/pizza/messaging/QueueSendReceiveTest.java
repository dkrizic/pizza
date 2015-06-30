package com.prodyna.showcase.pizza.messaging;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.*;

/**
 * Created by dkrizic on 30.06.15.
 */
@RunWith(Arquillian.class)
public class QueueSendReceiveTest {

    private static final String COUNT = "count";
    private static final String NUMBER = "number";
    private static final String DESCRIPTION = "description";

    @Inject
    private Logger log;

    @Inject
    private JMSContext context;

    @Resource(lookup = "java:/jms/queue/PizzaOrder")
    private Queue order;

    @Resource(lookup = "java:/jms/queue/PizzaOrderAccepted")
    private Queue orderAccept;

    @Resource(lookup = "java:/jms/queue/PizzaOrderRejected")
    private Queue orderReject;

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "arquillian.war");
        war.addPackages(true, "com.prodyna.showcase.pizza");
        war.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        return war;
    }

    @Test
    @InSequence(1)
    public void ensureQueueEmpty() throws JMSException {
        final JMSConsumer consumer = context.createConsumer(orderAccept);
        while( true ) {
            Message m = consumer.receiveNoWait();
            if( m == null ) {
                break;
            }
            log.info("Deleted " + m);
        }
    }

    @Test
    @InSequence(2)
    public void testOrder() throws Exception {
        MapMessage mm = context.createMapMessage();
        mm.setInt(COUNT, 1);
        mm.setInt(NUMBER, 42);
        mm.setString(DESCRIPTION, "Speciale");
        context.createProducer().send(order, mm);
    }

    @Test
    @InSequence(3)
    public void checkAcceptQueue() throws Exception {
        Thread.sleep( 2000 );
        final JMSConsumer consumer = context.createConsumer(orderAccept);
        MapMessage mm = (MapMessage) consumer.receive();
        Assert.assertEquals("Speciale", mm.getString(DESCRIPTION) );
    }

}

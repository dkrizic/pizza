package com.prodyna.showcase.pizza.business;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSContext;
import javax.jms.MapMessage;
import java.util.Enumeration;

@Named
public class BusinessObjectConverter {

    @Inject
    private JMSContext context;

    public BusinessObject convert(MapMessage mm) throws Exception {
        Enumeration keys = mm.getMapNames();
        BusinessObject bo = new BusinessObject();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            bo.put(key, mm.getObject(key));
        }
        return bo;
    }

    public MapMessage convert(BusinessObject bo) throws Exception {
        MapMessage mm = context.createMapMessage();
        for (String key : bo.keySet()) {
            mm.setObject(key, bo.get(key));
        }
        return mm;
    }

}

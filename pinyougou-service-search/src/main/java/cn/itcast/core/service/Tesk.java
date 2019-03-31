package cn.itcast.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class Tesk {
        @Autowired
        private JmsTemplate jmsTemplate;

}

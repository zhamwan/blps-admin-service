package com.example.blpsadminservice.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.TextMessage;

@Service
public class JmsService {

    @Autowired
    ConnectionFactory connectionFactory;


    public void rpcWithSpringJmsTemplate() throws Exception {
        Connection clientConnection = connectionFactory.createConnection();
        clientConnection.start();
        JmsTemplate tpl = new JmsTemplate(connectionFactory);
        Message respone = tpl.receive("demoqueue");
    }
}

package com.netcracker.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.model.entity.Message;
import com.netcracker.service.ConsumerService;
import com.netcracker.service.StorageService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import io.quarkus.runtime.StartupEvent;
import lombok.RequiredArgsConstructor;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ConsumerServiceImpl implements ConsumerService {

    private static final String QUEUE_NAME = "message-queue";
    private final StorageService storageService;
    private final ConnectionFactory factory;
    private final ObjectMapper mapper;
    private final Logger logger;

    public void init(@Observes StartupEvent event) {
        DeliverCallback callback = (tag, delivery) -> {
            String strMessage = new String(delivery.getBody(), StandardCharsets.UTF_8);
            Message message = mapper.readValue(strMessage, Message.class);
            storageService.store(message);
        };

        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            channel.basicConsume(QUEUE_NAME, true, callback, tag -> {
            });
        } catch (IOException e) {
            logger.errorf("IOException occurred. Exception ", e);
        } catch (TimeoutException e) {
            logger.errorf("TimeoutException occurred. Exception ", e);
        }
    }
}

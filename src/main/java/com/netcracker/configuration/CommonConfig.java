package com.netcracker.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.ConnectionFactory;
import org.modelmapper.ModelMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class CommonConfig {

    @Produces
    public ModelMapper mapper() {
        return new ModelMapper();
    }

    @Produces
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Produces
    public ConnectionFactory factory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        return factory;
    }
}

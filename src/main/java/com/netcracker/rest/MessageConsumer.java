package com.netcracker.rest;

import com.netcracker.model.dto.MessageDto;
import com.netcracker.model.entity.Message;
import com.netcracker.service.StorageService;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/consume")
public class MessageConsumer {

    private final StorageService storageService;
    private final ModelMapper mapper;

    @Inject
    public MessageConsumer(StorageService storageService, ModelMapper mapper) {
        this.storageService = storageService;
        this.mapper = mapper;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String status() {
        return "Active";
    }

    @GET
    @Path("/get-all")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<MessageDto> getAll() {
        return storageService.getAll().stream()
                .sorted((m1, m2) -> m2.getTimestamp().compareTo(m1.getTimestamp()))
                .map(message -> mapper.map(message, MessageDto.class))
                .collect(Collectors.toSet());
    }
}
package com.netcracker.service.impl;

import com.netcracker.model.entity.Message;
import com.netcracker.service.StorageService;
import io.vertx.core.impl.ConcurrentHashSet;

import javax.enterprise.context.ApplicationScoped;
import java.util.Collections;
import java.util.Set;

@ApplicationScoped
public class StorageServiceImpl implements StorageService {

    private static final Set<Message> storage = new ConcurrentHashSet<>();

    @Override
    public void store(Message message) {
        storage.add(message);
    }

    @Override
    public Set<Message> getAll() {
        return Collections.unmodifiableSet(storage);
    }
}

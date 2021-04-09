package com.netcracker.service;

import com.netcracker.model.entity.Message;

import java.util.Set;

public interface StorageService {

    void store(Message message);

    Set<Message> getAll();
}

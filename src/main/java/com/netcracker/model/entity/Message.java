package com.netcracker.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Message {
    private Date timestamp;
    private String message;
}

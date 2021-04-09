package com.netcracker.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MessageDto {
    private Date timestamp;
    private String message;
}

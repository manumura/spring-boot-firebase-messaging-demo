package com.manu.firebasemessagingdemo.dto;

import lombok.Data;

@Data
public class DirectNotification extends AppNotification {

    private String token;
}

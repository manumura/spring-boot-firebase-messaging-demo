package com.manu.firebasemessagingdemo.controller;

import com.manu.firebasemessagingdemo.dto.DirectNotification;
import com.manu.firebasemessagingdemo.service.FirebaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FirebaseController {

    private final FirebaseService firebaseService;

    @PostMapping("/notification")
    public void sendTargetedNotification(@RequestBody DirectNotification notification) {
        firebaseService.sendNotificationToTarget(notification);
    }
}

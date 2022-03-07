package com.manu.firebasemessagingdemo.service;

import com.manu.firebasemessagingdemo.dto.DirectNotification;

public interface FirebaseService {

    void sendNotificationToTarget(DirectNotification notification);
}

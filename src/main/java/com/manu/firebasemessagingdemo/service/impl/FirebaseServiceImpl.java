package com.manu.firebasemessagingdemo.service.impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import com.manu.firebasemessagingdemo.dto.DirectNotification;
import com.manu.firebasemessagingdemo.service.FirebaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class FirebaseServiceImpl implements FirebaseService {

    @Value("${firebase.config.file}")
    private String firebaseConfigPath;

    private static final String ICON = "https://cdn-icons-png.flaticon.com/32/148/148836.png";

    @PostConstruct
    public void init() {
        try {
            ClassPathResource serviceAccount = new ClassPathResource(firebaseConfigPath);

            // Get our credentials to authorize this Spring Boot application.
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))
                    .build();

            // If our app firebase application was not initialized, do so.
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("Firebase application has been initialized");
            }

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void sendNotificationToTarget(DirectNotification notification) {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("title", notification.getTitle());
        dataMap.put("body", notification.getBody());
        dataMap.put("icon", ICON);
        dataMap.put("link", notification.getLink());

        Message message = Message.builder()
                .setWebpushConfig(
                        WebpushConfig.builder()
//                                .setNotification(
//                                        WebpushNotification.builder()
//                                                .setTitle(notification.getTitle())
//                                                .setBody(notification.getBody())
//                                                .setIcon(ICON)
//                                                .build())
                                .setFcmOptions(
                                        WebpushFcmOptions.builder()
                                                .setLink(notification.getLink())
                                                .build())
                                .putAllData(dataMap)
                                .build()
                )
                .setToken(notification.getToken())
                .build();

        FirebaseMessaging.getInstance().sendAsync(message);
    }
}

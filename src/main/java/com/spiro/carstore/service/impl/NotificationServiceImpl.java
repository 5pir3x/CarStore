package com.spiro.carstore.service.impl;

import com.spiro.carstore.service.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Override
    public void sendMail(String email, String subject, String messageBody) {
        System.out.print( "To: " + email + "\nSubject: " + subject + "\n" + messageBody + "\n");
    }
}

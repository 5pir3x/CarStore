package com.spiro.carstore.service;

public interface NotificationService {
    public void sendMail(String email, String subject, String messageBody);
}

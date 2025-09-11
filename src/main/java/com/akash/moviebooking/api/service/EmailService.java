package com.akash.moviebooking.api.service;


public interface EmailService {
    void sendBookingConfirmation(String toEmail, String subject, String body);
    void sendHtmlBookingConfirmation(String to, String subject, String htmlBody);
}

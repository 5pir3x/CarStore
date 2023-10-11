package com.spiro.carstore.service;


import com.spiro.carstore.service.impl.NotificationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
public class NotificationServiceImplTest {

    private final NotificationServiceImpl notificationService = new NotificationServiceImpl();

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Redirect System.out to outContent
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testSendMail() {
        String email = "test@example.com";
        String subject = "Test Subject";
        String messageBody = "Test Message Body";

        notificationService.sendMail(email, subject, messageBody);
        String s1 = outContent.toString();


        String expectedOutput = "To: " + email + "\nSubject: " + subject + "\n" + messageBody + "\n";

        assertEquals(expectedOutput, s1);
    }

    @Test
    public void testSendMailWithEmptySubjectAndBody() {
        String email = "test@example.com";
        String subject = "";
        String messageBody = "";

        notificationService.sendMail(email, subject, messageBody);

        String expectedOutput = "To: " + email + "\nSubject: \n\n";

        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testSendMailWithNullParameters() {
        notificationService.sendMail(null, null, null);

        String expectedOutput = "To: null\nSubject: null\nnull" + "\n";

        assertEquals(expectedOutput, outContent.toString());
    }
}


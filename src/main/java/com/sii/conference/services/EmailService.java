package com.sii.conference.services;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${email.file-path}")
    private String filePath;

    public Boolean CreateFile()
    {
        Path fileName = Path.of(filePath);
        try {
            Files.createFile(fileName);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public Boolean WriteToFile(String email, String content)
    {
        Path fileName = Path.of(filePath);
        LocalDateTime timeNow = LocalDateTime.now();
        String text = "{" +
                "date: " + timeNow + ", " +
                "to: " + email + ", " +
                "content: " + content + "}";
        try {
            Files.writeString(fileName, text);
            return true;
        } catch (IOException e) {
            return CreateFile();
        }
    }
}

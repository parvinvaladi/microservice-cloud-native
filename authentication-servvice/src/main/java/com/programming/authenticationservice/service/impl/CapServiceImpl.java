package com.programming.authenticationservice.service.impl;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.programming.authenticationservice.dto.ResponseMessageDto;
import com.programming.authenticationservice.service.CapService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Service
@Slf4j
public class CapServiceImpl implements CapService {

    private final DefaultKaptcha defaultKaptcha;

    public CapServiceImpl(DefaultKaptcha defaultKaptcha) {
        this.defaultKaptcha = defaultKaptcha;
    }

    @Override
    public ResponseEntity<ResponseMessageDto> get(HttpServletRequest request) {
        String text = defaultKaptcha.createText();
        request.getSession().setAttribute("text-kapcha",text);
        request.getSession().setAttribute("time-kapcha",new Date());
        BufferedImage image = defaultKaptcha.createImage(text);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image,"png",outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(ResponseMessageDto.builder()
                .status(HttpStatus.OK)
                .message(HttpStatus.OK.name())
                .data(outputStream.toByteArray())
                .build());
    }

    @Override
    public ResponseEntity<ResponseMessageDto> evaluate(String text,HttpServletRequest request) {
        String realText = (String) request.getSession().getAttribute("text-kapcha");
        Date createdTime = (Date) request.getSession().getAttribute("time-kapcha");
        if (realText == null)
            return ResponseEntity.ok(ResponseMessageDto.builder().message("invalid").build());
        log.info(String.valueOf(new Date().getTime() - createdTime.getTime()));
        if (createdTime == null || (new Date().getTime() - createdTime.getTime()) / (1000 * 60) > 5)
            return ResponseEntity.ok(ResponseMessageDto.builder().message("expired").build());

        return ResponseEntity.ok(ResponseMessageDto.builder()
                .message(HttpStatus.OK.name())
                        .status(HttpStatus.OK)
                .data("successfull").build());
    }
}


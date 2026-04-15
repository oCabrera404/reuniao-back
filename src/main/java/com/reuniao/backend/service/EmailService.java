package com.reuniao.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void enviarConvite(
            String destino,
            String titulo,
            Long participacaoId
    ) {

        String aceitar =
                "http://localhost:8080/convites/aceitar/" + participacaoId;

        String recusar =
                "http://localhost:8080/convites/recusar/" + participacaoId;

        String mensagem = """
            Você foi convidado para a reunião: %s

            Aceitar:
            %s

            Recusar:
            %s
            """.formatted(titulo, aceitar, recusar);

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(destino);
        email.setSubject("Convite para reunião");
        email.setText(mensagem);

        mailSender.send(email);
    }
}

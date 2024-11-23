package com.example.title.controllers;

import com.example.title.models.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class RegisterController {
    @GetMapping("/register")
    public String registerShow(Model model){
        return "register";
    }
    @PostMapping("/register/reg")
    public String regIn(@RequestParam String login,
                        @RequestParam String password,
                        @Autowired RestTemplate restTemplate){
        LoginRequest loginRequest = new LoginRequest(login, password);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // Важно указать тип контента

        HttpEntity<LoginRequest> requestEntity = new HttpEntity<>(loginRequest, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://localhost:8081/api-register/reg", // Замените на адрес вашего API
                    HttpMethod.POST,
                    requestEntity,
                    String.class // Или другой тип, если API возвращает что-то другое
            );

            // Обработка ответа от API
            if (response.getStatusCode().is2xxSuccessful()) {
                return "success";
            } else {
                return "Ошибка регистрации: " + response.getStatusCode();
            }
        } catch (Exception e) {
            return "Ошибка: " + e.getMessage();
        }
    }
}

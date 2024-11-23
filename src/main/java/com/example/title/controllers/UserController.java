package com.example.title.controllers;

import com.example.title.models.FioModel;
import com.example.title.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class UserController {
    @GetMapping("/user")
    public String page(){
        return "user";
    }
    @PostMapping("/user-get")
    public String getFio(Model model, @Autowired RestTemplate restTemplate,
                         @RequestParam String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // Важно указать тип контента
        headers.setBearerAuth(token);
        HttpEntity<List<UserModel>> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<List<UserModel>> response = restTemplate.exchange(
                    "http://localhost:8081/api-user", // Замените на адрес вашего API
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<List<UserModel>>() {} // Важно!
            );

            // Обработка ответа от API
            if (response.getStatusCode().is2xxSuccessful()) {
                List<UserModel> users = response.getBody();
                model.addAttribute("users", users);
                return "user";
            } else {
                return "Ошибка регистрации: " + response.getStatusCode();
            }
        } catch (Exception e) {
            return "Ошибка: " + e.getMessage();
        }
    }
    @PostMapping("/user-create")
    public String fioCreate(@RequestParam String username, @RequestParam String password, @RequestParam long role_id,
                            @RequestParam String token, @Autowired RestTemplate restTemplate){
        UserModel userModel = new UserModel(0, username, password, role_id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<UserModel> requestEntity = new HttpEntity<>(userModel, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://localhost:8081/api-user",
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
            return "redirect:/user";
        } catch (Exception e) {
            return "error";
        }
    }

    @PostMapping("/user-update")
    public String updateFio(@RequestParam long ID, @RequestParam String username, @RequestParam String password, @RequestParam long role_id,
                            @RequestParam String token, @Autowired RestTemplate restTemplate,
                            Model model) {
        UserModel userModel = new UserModel(0, username, password, role_id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<UserModel> requestEntity = new HttpEntity<>(userModel, headers);
        System.out.println(requestEntity);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://localhost:8081/api-user/" + ID,
                    HttpMethod.PUT,
                    requestEntity,
                    String.class
            );
            return "redirect:/user";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }
    @PostMapping("/user-delete")
    public String deleteAuthor(@RequestParam long ID, @Autowired RestTemplate restTemplate, @RequestParam String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<FioModel> requestEntity = new HttpEntity<>(headers);
        System.out.println(requestEntity);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://localhost:8081/api-user/" + ID,
                    HttpMethod.DELETE,
                    requestEntity,
                    String.class
            );
            return "redirect:/user";
        } catch (Exception e) {
            return "error";
        }
    }
}

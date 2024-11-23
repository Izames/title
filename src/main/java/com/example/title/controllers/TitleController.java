package com.example.title.controllers;

import com.example.title.models.FioModel;
import com.example.title.models.TitleModel;
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
public class TitleController {
    @GetMapping("/title")
    public String page(){
        return "title";
    }
    @PostMapping("/title-get")
    public String getTitle(Model model, @Autowired RestTemplate restTemplate,
                         @RequestParam String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // Важно указать тип контента
        headers.setBearerAuth(token);
        HttpEntity<List<FioModel>> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<List<TitleModel>> response = restTemplate.exchange(
                    "http://localhost:8081/api-title", // Замените на адрес вашего API
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<List<TitleModel>>() {} // Важно!
            );

            // Обработка ответа от API
            if (response.getStatusCode().is2xxSuccessful()) {
                List<TitleModel> titles = response.getBody();
                model.addAttribute("titles", titles);
                return "title";
            } else {
                return "Ошибка регистрации: " + response.getStatusCode();
            }
        } catch (Exception e) {
            return "Ошибка: " + e.getMessage();
        }
    }
    @PostMapping("/title-create")
    public String titleCreate(@RequestParam String name,
                            @RequestParam String token, @Autowired RestTemplate restTemplate){
        TitleModel titleModel = new TitleModel(0, name);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<TitleModel> requestEntity = new HttpEntity<>(titleModel, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://localhost:8081/api-title",
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
            return "redirect:/title";
        } catch (Exception e) {
            return "error";
        }
    }

    @PostMapping("/title-update")
    public String updateTitle(@RequestParam long ID, @RequestParam String name,
                            @RequestParam String token, @Autowired RestTemplate restTemplate,
                            Model model) {
        TitleModel titleModel = new TitleModel(ID, name);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<TitleModel> requestEntity = new HttpEntity<>(titleModel, headers);
        System.out.println(requestEntity);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://localhost:8081/api-title/" + ID,
                    HttpMethod.PUT,
                    requestEntity,
                    String.class
            );
            return "redirect:/title";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }
    @PostMapping("/title-delete")
    public String deleteTitle(@RequestParam long ID, @Autowired RestTemplate restTemplate, @RequestParam String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<TitleModel> requestEntity = new HttpEntity<>(headers);
        System.out.println(requestEntity);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://localhost:8081/api-title/" + ID,
                    HttpMethod.DELETE,
                    requestEntity,
                    String.class
            );
            return "redirect:/title";
        } catch (Exception e) {
            return "error";
        }
    }
}

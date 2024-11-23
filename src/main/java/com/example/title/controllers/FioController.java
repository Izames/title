package com.example.title.controllers;

import com.example.title.models.AuthorModel;
import com.example.title.models.AuthorTitleFio;
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

import java.util.ArrayList;
import java.util.List;

@Controller
public class FioController {
    @GetMapping("/fio")
    public String page(){
        return "fio";
    }
    @PostMapping("/fio-get")
    public String getFio(Model model, @Autowired RestTemplate restTemplate,
                            @RequestParam String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // Важно указать тип контента
        headers.setBearerAuth(token);
        HttpEntity<List<FioModel>> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<List<FioModel>> response = restTemplate.exchange(
                    "http://localhost:8081/api-fio", // Замените на адрес вашего API
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<List<FioModel>>() {} // Важно!
            );

            // Обработка ответа от API
            if (response.getStatusCode().is2xxSuccessful()) {
                List<FioModel> fios = response.getBody();
                model.addAttribute("fios", fios);
                return "fio";
            } else {
                return "Ошибка регистрации: " + response.getStatusCode();
            }
        } catch (Exception e) {
            return "Ошибка: " + e.getMessage();
        }
    }
    @PostMapping("/fio-create")
    public String fioCreate(@RequestParam String name, @RequestParam String surname, @RequestParam String lastname,
                               @RequestParam String token, @Autowired RestTemplate restTemplate){
        FioModel fioModel = new FioModel(0, name, surname, lastname);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<FioModel> requestEntity = new HttpEntity<>(fioModel, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://localhost:8081/api-fio",
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
            return "redirect:/fio";
        } catch (Exception e) {
            return "error";
        }
    }

    @PostMapping("/fio-update")
    public String updateFio(@RequestParam long ID, @RequestParam String name, @RequestParam String surname, @RequestParam String lastname,
                               @RequestParam String token, @Autowired RestTemplate restTemplate,
                               Model model) {
        FioModel fioModel = new FioModel(ID, name, surname, lastname);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<FioModel> requestEntity = new HttpEntity<>(fioModel, headers);
        System.out.println(requestEntity);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://localhost:8081/api-fio/" + ID,
                    HttpMethod.PUT,
                    requestEntity,
                    String.class
            );
            return "redirect:/fio";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }
    @PostMapping("/fio-delete")
    public String deleteAuthor(@RequestParam long ID, @Autowired RestTemplate restTemplate, @RequestParam String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<FioModel> requestEntity = new HttpEntity<>(headers);
        System.out.println(requestEntity);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://localhost:8081/api-fio/" + ID,
                    HttpMethod.DELETE,
                    requestEntity,
                    String.class
            );
            return "redirect:/fio";
        } catch (Exception e) {
            return "error";
        }
    }
}

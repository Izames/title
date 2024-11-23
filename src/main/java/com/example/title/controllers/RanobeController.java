package com.example.title.controllers;

import com.example.title.models.*;
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
public class RanobeController {
    @GetMapping("/ranobe")
    public String getPage() {
        return "ranobe";
    }

    @PostMapping("/ranobe-get")
    public String getRanobe(Model model, @Autowired RestTemplate restTemplate,
                            @RequestParam String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // Важно указать тип контента
        headers.setBearerAuth(token);
        HttpEntity<List<RanobeTitleModel>> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<List<RanobeTitleModel>> response = restTemplate.exchange(
                    "http://localhost:8081/api-ranobe", // Замените на адрес вашего API
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<List<RanobeTitleModel>>() {
                    } // Важно!
            );

            // Обработка ответа от API
            if (response.getStatusCode().is2xxSuccessful()) {
                List<RanobeTitleModel> ranobeTitleModels = response.getBody();
                List<RanobeModel> ranobes = new ArrayList<>();
                List<TitleModel> titles = new ArrayList<>();
                for (int i = 0; i < ranobeTitleModels.size(); i++) {
                    RanobeModel ranobe = ranobeTitleModels.get(i).getRanobe();
                    TitleModel title = ranobeTitleModels.get(i).getTitle();
                    ranobe.setTitleName(title.getName());
                    ranobes.add(ranobe);
                    titles.add(title);
                }
                model.addAttribute("ranobes", ranobes);
                model.addAttribute("titles", titles);
                return "ranobe";
            } else {
                return "Ошибка регистрации: " + response.getStatusCode();
            }
        } catch (Exception e) {
            return "Ошибка: " + e.getMessage();
        }
    }

    @PostMapping("/ranobe-create")
    public String createRanobe(@RequestParam String name, @RequestParam int chapters,
                              @RequestParam long titleID, @Autowired RestTemplate restTemplate, @RequestParam String token,
                              Model model) {
        RanobeModel ranobeModel = new RanobeModel(0, name, chapters, titleID, null);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<RanobeModel> requestEntity = new HttpEntity<>(ranobeModel, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://localhost:8081/api-ranobe",
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
            return "redirect:/ranobe";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/ranobe-update")
    public String updateRanobe(@RequestParam long ID, @RequestParam String name, @RequestParam int chapters,
                              @RequestParam long titleID, @Autowired RestTemplate restTemplate, @RequestParam String token,
                              Model model) {
        RanobeModel ranobeModel = new RanobeModel(ID, name, chapters, titleID, null);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<RanobeModel> requestEntity = new HttpEntity<>(ranobeModel, headers);
        System.out.println(requestEntity);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://localhost:8081/api-ranobe/" + ID,
                    HttpMethod.PUT,
                    requestEntity,
                    String.class
            );
            return "redirect:/ranobe";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/ranobe-delete")
    public String deleteRanobe(@RequestParam long ID, @Autowired RestTemplate restTemplate, @RequestParam String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<RanobeModel> requestEntity = new HttpEntity<>(headers);
        System.out.println(requestEntity);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://localhost:8081/api-ranobe/" + ID,
                    HttpMethod.DELETE,
                    requestEntity,
                    String.class
            );
            return "redirect:/ranobe";
        } catch (Exception e) {
            return "error";
        }
    }
}

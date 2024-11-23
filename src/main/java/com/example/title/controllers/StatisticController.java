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
public class StatisticController {
    @GetMapping("/statistic")
    public String getPage() {
        return "statistic";
    }

    @PostMapping("/statistic-get")
    public String getStatistic(Model model, @Autowired RestTemplate restTemplate,
                            @RequestParam String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // Важно указать тип контента
        headers.setBearerAuth(token);
        HttpEntity<List<StatisticTitleModel>> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<List<StatisticTitleModel>> response = restTemplate.exchange(
                    "http://localhost:8081/api-statistic", // Замените на адрес вашего API
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<List<StatisticTitleModel>>() {
                    } // Важно!
            );

            // Обработка ответа от API
            if (response.getStatusCode().is2xxSuccessful()) {
                List<StatisticTitleModel> statisticTitleModels = response.getBody();
                List<StatisiticModel> statistics = new ArrayList<>();
                List<TitleModel> titles = new ArrayList<>();
                for (int i = 0; i < statisticTitleModels.size(); i++) {
                    StatisiticModel statistic = statisticTitleModels.get(i).getStatisticDTO();
                    TitleModel title = statisticTitleModels.get(i).getTitleDTO();
                    statistic.setTitleName(title.getName());
                    statistics.add(statistic);
                    titles.add(title);
                }
                model.addAttribute("statistics", statistics);
                model.addAttribute("titles", titles);
                return "statistic";
            } else {
                return "Ошибка регистрации: " + response.getStatusCode();
            }
        } catch (Exception e) {
            return "Ошибка: " + e.getMessage();
        }
    }

    @PostMapping("/statistic-create")
    public String createStatistic(@RequestParam float rating, @RequestParam String ReleaseDate,
                               @RequestParam long title_id, @Autowired RestTemplate restTemplate, @RequestParam String token,
                               Model model) {
        StatisiticModel statisticModel = new StatisiticModel(0, rating, ReleaseDate, title_id, null);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<StatisiticModel> requestEntity = new HttpEntity<>(statisticModel, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://localhost:8081/api-statistic",
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
            return "redirect:/statistic";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/statistic-update")
    public String updateStatistic(@RequestParam long ID, @RequestParam float rating, @RequestParam String ReleaseDate,
                                  @RequestParam long title_id, @Autowired RestTemplate restTemplate, @RequestParam String token,
                               Model model) {
        StatisiticModel statisticModel = new StatisiticModel(ID, rating, ReleaseDate, title_id, null);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<StatisiticModel> requestEntity = new HttpEntity<>(statisticModel, headers);
        System.out.println(requestEntity);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://localhost:8081/api-statistic/" + ID,
                    HttpMethod.PUT,
                    requestEntity,
                    String.class
            );
            return "redirect:/statistic";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/statistic-delete")
    public String deleteStatistic(@RequestParam long ID, @Autowired RestTemplate restTemplate, @RequestParam String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<StatisiticModel> requestEntity = new HttpEntity<>(headers);
        System.out.println(requestEntity);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://localhost:8081/api-statistic/" + ID,
                    HttpMethod.DELETE,
                    requestEntity,
                    String.class
            );
            return "redirect:/statistic";
        } catch (Exception e) {
            return "error";
        }
    }
}

package com.example.title.controllers;

import com.example.title.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AnimeController {
    @GetMapping("/anime")
    public String getPage(){
        return "anime";
    }
    @PostMapping("/anime-get")
    public String getAnime(Model model, @Autowired RestTemplate restTemplate,
                           @RequestParam String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // Важно указать тип контента
        headers.setBearerAuth(token);
        HttpEntity<List<AnimeTitleModel>> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<List<AnimeTitleModel>> response = restTemplate.exchange(
                    "http://localhost:8081/api-anime", // Замените на адрес вашего API
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<List<AnimeTitleModel>>() {} // Важно!
            );

            // Обработка ответа от API
            if (response.getStatusCode().is2xxSuccessful()) {
                List<AnimeTitleModel> animeTitleModels = response.getBody();
                List<AnimeModel> animes = new ArrayList<>();
                List<TitleModel> titles = new ArrayList<>();
                for (int i = 0; i<animeTitleModels.size(); i++){
                    AnimeModel anime = animeTitleModels.get(i).getAnimes();
                    TitleModel title = animeTitleModels.get(i).getTitles();
                    anime.setTitleName(title.getName());
                    animes.add(anime);
                    titles.add(title);
                }
                model.addAttribute("animes", animes);
                model.addAttribute("titles", titles);
                return "anime";
            } else {
                return "Ошибка регистрации: " + response.getStatusCode();
            }
        } catch (Exception e) {
            return "Ошибка: " + e.getMessage();
        }
    }
    @PostMapping("/anime-create")
    public String createAnime(@RequestParam String name, @RequestParam int episodes, @RequestParam int seasons,
                              @RequestParam long titleID, @Autowired RestTemplate restTemplate, @RequestParam String token,
                              Model model) {
        System.out.println("зашел в метод");
        AnimeModel animeModel = new AnimeModel(0, name, seasons, episodes, titleID, null);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<AnimeModel> requestEntity = new HttpEntity<>(animeModel, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://localhost:8081/api-anime",
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
            return "redirect:/anime";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }
    @PostMapping("/anime-update")
    public String updateAnime(@RequestParam long ID, @RequestParam String name, @RequestParam int episodes, @RequestParam int seasons,
                              @RequestParam long titleID, @Autowired RestTemplate restTemplate, @RequestParam String token,
                              Model model) {
        AnimeModel animeModel = new AnimeModel(ID, name, seasons, episodes, titleID, null);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<AnimeModel> requestEntity = new HttpEntity<>(animeModel, headers);
        System.out.println(requestEntity);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://localhost:8081/api-anime/" + ID,
                    HttpMethod.PUT,
                    requestEntity,
                    String.class
            );
            return "redirect:/anime";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }
    @PostMapping("/anime-delete")
    public String deleteAnime(@RequestParam long ID, @Autowired RestTemplate restTemplate, @RequestParam String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<AnimeModel> requestEntity = new HttpEntity<>(headers);
        System.out.println(requestEntity);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://localhost:8081/api-anime/" + ID,
                    HttpMethod.DELETE,
                    requestEntity,
                    String.class
            );
            return "redirect:/anime";
        } catch (Exception e) {
            return "error";
        }
    }
}

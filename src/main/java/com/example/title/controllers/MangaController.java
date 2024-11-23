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
public class MangaController {
    @GetMapping("/manga")
    public String getPage(){
        return "manga";
    }
    @PostMapping("/manga-get")
    public String getManga(Model model, @Autowired RestTemplate restTemplate,
                           @RequestParam String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // Важно указать тип контента
        headers.setBearerAuth(token);
        HttpEntity<List<MangaTitleModel>> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<List<MangaTitleModel>> response = restTemplate.exchange(
                    "http://localhost:8081/api-manga", // Замените на адрес вашего API
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<List<MangaTitleModel>>() {} // Важно!
            );

            // Обработка ответа от API
            if (response.getStatusCode().is2xxSuccessful()) {
                List<MangaTitleModel> mangaTitleModels = response.getBody();
                List<MangaModel> mangas = new ArrayList<>();
                List<TitleModel> titles = new ArrayList<>();
                for (int i = 0; i<mangaTitleModels.size(); i++){
                    MangaModel manga = mangaTitleModels.get(i).getManga();
                    TitleModel title = mangaTitleModels.get(i).getTitle();
                    manga.setTitleName(title.getName());
                    mangas.add(manga);
                    titles.add(title);
                }
                model.addAttribute("mangas", mangas);
                model.addAttribute("titles", titles);
                return "manga";
            } else {
                return "Ошибка регистрации: " + response.getStatusCode();
            }
        } catch (Exception e) {
            return "Ошибка: " + e.getMessage();
        }
    }
    @PostMapping("/manga-create")
    public String createManga(@RequestParam String name, @RequestParam int chapters,
                              @RequestParam long titleID, @Autowired RestTemplate restTemplate, @RequestParam String token,
                              Model model) {
        MangaModel mangaModel = new MangaModel(0, name, chapters, titleID, null);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<MangaModel> requestEntity = new HttpEntity<>(mangaModel, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://localhost:8081/api-manga",
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
            return "redirect:/manga";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }
    @PostMapping("/manga-update")
    public String updateManga(@RequestParam long ID, @RequestParam String name, @RequestParam int chapters,
                              @RequestParam long titleID, @Autowired RestTemplate restTemplate, @RequestParam String token,
                              Model model) {
        MangaModel mangaModel = new MangaModel(ID, name, chapters, titleID, null);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<MangaModel> requestEntity = new HttpEntity<>(mangaModel, headers);
        System.out.println(requestEntity);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://localhost:8081/api-manga/" + ID,
                    HttpMethod.PUT,
                    requestEntity,
                    String.class
            );
            return "redirect:/manga";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }
    @PostMapping("/manga-delete")
    public String deleteManga(@RequestParam long ID, @Autowired RestTemplate restTemplate, @RequestParam String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<AnimeModel> requestEntity = new HttpEntity<>(headers);
        System.out.println(requestEntity);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://localhost:8081/api-manga/" + ID,
                    HttpMethod.DELETE,
                    requestEntity,
                    String.class
            );
            return "redirect:/manga";
        } catch (Exception e) {
            return "error";
        }
    }
}

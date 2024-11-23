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
public class AuthorController {
    @GetMapping("/author")
    public String page(){
        return "author";
    }
    @PostMapping("/author-get")
    public String getAuthor(Model model, @Autowired RestTemplate restTemplate,
                           @RequestParam String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // Важно указать тип контента
        headers.setBearerAuth(token);
        HttpEntity<List<AuthorTitleFio>> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<List<AuthorTitleFio>> response = restTemplate.exchange(
                    "http://localhost:8081/api-author", // Замените на адрес вашего API
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<List<AuthorTitleFio>>() {} // Важно!
            );

            // Обработка ответа от API
            if (response.getStatusCode().is2xxSuccessful()) {
                List<AuthorTitleFio> authorTitleFios = response.getBody();
                List<AuthorModel> authors = new ArrayList<>();
                List<FioModel> fios = new ArrayList<>();
                List<TitleModel> titles = new ArrayList<>();
                for (int i = 0; i<authorTitleFios.size(); i++){
                    AuthorModel author = authorTitleFios.get(i).getAuthor();
                    TitleModel title = authorTitleFios.get(i).getTitle();
                    FioModel fio = authorTitleFios.get(i).getFio();
                    author.setTitleName(title.getName());
                    author.setFio(fio.getSurname() + ' ' + fio.getName() + ' ' + fio.getLastname());
                    authors.add(author);
                    titles.add(title);
                    fios.add(fio);
                }
                model.addAttribute("authors", authors);
                model.addAttribute("titles", titles);
                model.addAttribute("fios", fios);
                return "author";
            } else {
                return "Ошибка регистрации: " + response.getStatusCode();
            }
        } catch (Exception e) {
            return "Ошибка: " + e.getMessage();
        }
    }
    @PostMapping("/author-create")
    public String authorCreate(@RequestParam int age, @RequestParam String nickname, @RequestParam long titleID,
                               @RequestParam long fioId, @RequestParam String token, @Autowired RestTemplate restTemplate){
        AuthorModel authorModel = new AuthorModel(0, age, nickname, titleID, fioId, null, null);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<AuthorModel> requestEntity = new HttpEntity<>(authorModel, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://localhost:8081/api-author",
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
            return "redirect:/anime";
        } catch (Exception e) {
            return "error";
        }
    }

    @PostMapping("/author-update")
    public String updateAuthor(@RequestParam long ID, @RequestParam int age, @RequestParam String nickname, @RequestParam long titleID,
                               @RequestParam long fioId, @Autowired RestTemplate restTemplate, @RequestParam String token,
                              Model model) {
        AuthorModel authorModel = new AuthorModel(ID, age, nickname, titleID, fioId, null, null);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<AuthorModel> requestEntity = new HttpEntity<>(authorModel, headers);
        System.out.println(requestEntity);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://localhost:8081/api-author/" + ID,
                    HttpMethod.PUT,
                    requestEntity,
                    String.class
            );
            return "redirect:/author";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }
    @PostMapping("/author-delete")
    public String deleteAuthor(@RequestParam long ID, @Autowired RestTemplate restTemplate, @RequestParam String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<AuthorModel> requestEntity = new HttpEntity<>(headers);
        System.out.println(requestEntity);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://localhost:8081/api-anime/" + ID,
                    HttpMethod.DELETE,
                    requestEntity,
                    String.class
            );
            return "redirect:/author";
        } catch (Exception e) {
            return "error";
        }
    }
}

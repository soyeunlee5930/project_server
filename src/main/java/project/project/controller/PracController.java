package project.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.project.model.Categories;
import project.project.model.Prac;
import project.project.model.Users;
import project.project.service.PracService;

import java.time.LocalDate;

@RestController
@RequestMapping("/admins")
@CrossOrigin(origins = "http://localhost:3000")
public class PracController {

    @Autowired
    private PracService pracService;

    @GetMapping("/prac/insert")
    public ResponseEntity<String> insertUser() {
        Prac prac = new Prac();

        prac.setNameTest("test1111");

        pracService.insertPrac(prac);

        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }
}

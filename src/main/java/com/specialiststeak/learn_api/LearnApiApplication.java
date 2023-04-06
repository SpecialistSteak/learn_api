package com.specialiststeak.learn_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.specialiststeak.learn_api.database.UserDatabase;
import com.specialiststeak.learn_api.objects.ExampleObject;
import com.specialiststeak.learn_api.objects.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;
import java.util.List;

import static com.specialiststeak.learn_api.Init.start;
import static com.specialiststeak.learn_api.utils.Compression.compressByteArray;

@SpringBootApplication
@Controller
@EnableWebMvc
public class LearnApiApplication {

    private static final ExampleObject v = new ExampleObject(
            1,
            "Example",
            "This is an example object",
            List.of("example", "object")
    );

    public static void main(String[] args) {
        start();
        SpringApplication.run(LearnApiApplication.class, args);
    }

    @GetMapping("/")
    public String index() {
        return "Index";
    }

    @GetMapping("/introduction")
    public String introduction() {
        return "Introduction";
    }

    @GetMapping({"/next-steps/", "/next-steps"})
    public String nextSteps() {
        return "NextSteps";
    }

    @GetMapping({"/api/login", "/api/login/"})
    @ResponseBody
    public ResponseEntity<String> login(@RequestParam final String accessToken) {
        final String ACCESS_TOKEN = "1234567890";
        String expectedAccessToken = "Bearer " + ACCESS_TOKEN;
        if (accessToken.equals(expectedAccessToken)) {
            return ResponseEntity.ok("Logged in");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect access token");
        }
    }

    @GetMapping({"/api/header_auth", "/api/header_auth/"})
    @ResponseBody
    public ResponseEntity<String> verify(@RequestHeader("Authorization") String authorizationHeader) throws IOException, InterruptedException {
        if (authorizationHeader.equals("Bearer 1234567890")) {
            return ResponseEntity.ok("Logged in");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect access token");
        }
    }

    @GetMapping({"/api/exampleoutput", "/api/exampleoutput/"})
    @ResponseBody
    public ResponseEntity<ExampleObject> exampleOutput() {
        return ResponseEntity.ok(v);
    }

    @GetMapping({"/api/compressedexampleoutput", "/api/compressedexampleoutput/"})
    @ResponseBody
    public ResponseEntity<byte[]> compressedExampleOutput() throws IOException {
        byte[] compressedData = compressByteArray(new ObjectMapper().writeValueAsBytes(List.of(
                v, v
        )));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setContentLength(compressedData.length);
        headers.set("Content-Encoding", "gzip");

        return new ResponseEntity<>(compressedData, headers, HttpStatus.OK);
    }

    @GetMapping({"/api/getusers", "/api/getusers/"})
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(UserDatabase.getAllUsers());
    }

    @GetMapping({"/api/getuser/{id}", "/api/getuser/{id}/"})
    public ResponseEntity<User> getUser(@PathVariable int id) {
        return UserDatabase.getUser(id) != null ?
                ResponseEntity.ok(UserDatabase.getUser(id)) : ResponseEntity.notFound().build();
    }

    @PostMapping({"/api/adduser", "/api/adduser/"})
    public ResponseEntity<String> addUser(@RequestBody User user) {
        if (UserDatabase.addUser(user)) {
            return ResponseEntity.ok().body("Success!");
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping({"/api/updateuser/{id}", "/api/updateuser/{id}/"})
    public ResponseEntity<Boolean> updateUser(@RequestParam int id, @RequestBody User user) {
        if (UserDatabase.getUser(id) != null) {
            return ResponseEntity.ok().body(UserDatabase.set(id, user));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping({"/api/removeuser/{id}", "/api/removeuser/{id}/"})
    public ResponseEntity<Boolean> removeUser(@PathVariable int id) {
        return ResponseEntity.ok().body(UserDatabase.remove(id));
    }
}

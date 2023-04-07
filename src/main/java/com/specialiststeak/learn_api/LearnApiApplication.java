package com.specialiststeak.learn_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.specialiststeak.learn_api.database.UserDatabase;
import com.specialiststeak.learn_api.objects.ExampleObject;
import com.specialiststeak.learn_api.objects.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;
import java.util.List;

import static com.specialiststeak.learn_api.Init.start;
import static com.specialiststeak.learn_api.utils.Compression.compressByteArray;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @GetMapping({"/api/introduction2", "/api/introduction2/"})
    public String introduction2() {
        return "Introduction2";
    }

    @GetMapping({"/next-steps/", "/next-steps"})
    public String nextSteps() {
        return "NextSteps";
    }

    private static String name = "{no name yet}";

    @GetMapping({"/api/get/", "/api/get"})
    @ResponseBody
    public ResponseEntity<String> get() {
        @SuppressWarnings("java:S1192")
        String bodycontent = "Hello " + name +
                "! Now try sending a POST request to /api/post/{newname} to change the name. " +
                "If you'd like a hint, check the headers of this response with `System.out.println(response.headers());`";
        if(!name.equals("{no name yet}")) {
            bodycontent = "Hello " + name + "!" + " Now send a PUT request to /api/PUT/ to change the name. We need to add `?name=newname` to the end of the URL to tell it that the request parameter is `name` and the value is `newname`.";
        }
        return ResponseEntity.ok()
                .header("Hint", "Add your name to the end of the URL and make the POST request (replace `{newname}` with your name).")
                .body(bodycontent);
    }

    @GetMapping({"/oauth/token", "/oauth/token/"})
    @ResponseBody
    public ResponseEntity<String> token() {
        return ResponseEntity.ok()
                .header("Token", "token")
                .header("Secret", "secret")
                .body("The headers contain your token and secret. Send a GET to /oauth/authorize/ with the token and secret in the request parameters ( ?token=token&&secret=secret ).");
    }

    @GetMapping({"/oauth/authorize", "/oauth/authorize/"})
    @ResponseBody
    public ResponseEntity auth(@RequestParam("token") String token, @RequestParam("secret") String secret) {
        if (token.equals("token") && secret.equals("secret")) {
            return ResponseEntity.ok("Authorized");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect token or secret");
        }
    }

    @PostMapping({"/api/post/{newname}", "/api/post/{newname}/"})
    @ResponseBody
    public ResponseEntity<String> put(@PathVariable String newname) {
        name = newname;
        return ResponseEntity.ok("Hello " + newname + "! " + "Now, try sending a GET request to /api/get/ and check again.");
    }

    @PutMapping({"/api/put/", "/api/put"})
    @ResponseBody
    public ResponseEntity<String> post(@RequestParam String name) {
        return ResponseEntity.ok("Hello " + name + "! " + "Send a DELETE request to /api/delete/ to delete all instances of a character from your name. Set the body to one character.");
    }

    @DeleteMapping({"/api/delete/", "/api/delete/"})
    @ResponseBody
    public ResponseEntity<String> delete(@RequestBody String charb) {
        char charA;
        try {
            charA = charb.charAt(0);
        } catch (Exception e){
            return ResponseEntity.ok("The body is empty! Try again. If you're confused, try using .body(\"a\") to set the body to \"a\".");
        }
        String oldName = name;
        name = name.replaceAll(String.valueOf(charA), "");
        if(oldName.equals(name)){
            return ResponseEntity.ok("You don't have any " + charA + "'s in your name! Try again.");
        }
        return ResponseEntity.ok("Deleted all " + charA + "'s from your name! Your new name is: "  + name + ". Congrats, you've completed the tutorial!");
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

    @GetMapping({"/api/endpoint1", "/api/endpoint1/"})
    @ResponseBody
    public ResponseEntity<String> endpoint1() {
        return ResponseEntity.ok("Hello World!");
    }

    @PostMapping({"/api/endpoint2", "/api/endpoint2/"})
    @ResponseBody
    public ResponseEntity<String> endpoint2(@RequestBody String name) {
        return ResponseEntity.ok("Hello " + name + "!");
    }

    @PutMapping({"/api/endpoint3", "/api/endpoint3/"})
    @ResponseBody
    public ResponseEntity<String> endpoint3(@RequestParam String name) {
        return ResponseEntity.ok("Hello " + name + "!");
    }

    @DeleteMapping({"/api/endpoint4", "/api/endpoint4/"})
    @ResponseBody
    public ResponseEntity<String> endpoint4(@RequestParam String name) {
        return ResponseEntity.ok("Hello " + name + "!");
    }

    @GetMapping({"/api/endpoint5", "/api/endpoint5/"})
    @ResponseBody
    public ResponseEntity<String> endpoint5(@RequestParam String name) {
        return ResponseEntity.ok("Hello " + name + "!");
    }

    @GetMapping({"/api/endpoint6", "/api/endpoint6/"})
    @ResponseBody
    public ResponseEntity<String> endpoint6(@PathVariable String name) {
        return ResponseEntity.ok("Hello " + name + "!");
    }

    @GetMapping({"/api/endpoint7", "/api/endpoint7/"})
    @ResponseBody
    public ResponseEntity<String> endpoint7(@RequestHeader String name) {
        return ResponseEntity.ok("Hello " + name + "!");
    }

    @GetMapping({"/api/endpoint8", "/api/endpoint8/"})
    @ResponseBody
    public ResponseEntity<String> endpoint8(@RequestHeader String name) {
        return ResponseEntity.ok("Hello " + name + "!");
    }

    @GetMapping({"/api/endpoint9", "/api/endpoint9/"})
    @ResponseBody
    public ResponseEntity<String> endpoint9(@RequestHeader String name) {
        return ResponseEntity.ok("Hello " + name + "!");
    }

    @GetMapping({"/api/endpoint10", "/api/endpoint10/"})
    @ResponseBody
    public ResponseEntity<String> endpoint10(@RequestHeader String name) {
        return ResponseEntity.ok("Hello " + name + "!");
    }

    @GetMapping({"/api/endpoint11", "/api/endpoint11/"})
    @ResponseBody
    public ResponseEntity<String> endpoint11(@RequestHeader String name) {
        return ResponseEntity.ok("Hello " + name + "!");
    }

    @GetMapping({"/api/endpoint12", "/api/endpoint12/"})
    @ResponseBody
    public ResponseEntity<String> endpoint12(@RequestHeader String name) {
        return ResponseEntity.ok("Hello " + name + "!");
    }

}

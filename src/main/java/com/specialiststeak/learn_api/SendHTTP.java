package com.specialiststeak.learn_api;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

public class SendHTTP {

    private static String decompressGzip(byte[] compressedData) {
        try (GZIPInputStream gis = new GZIPInputStream(
                new ByteArrayInputStream(compressedData))) {
            return new String(gis.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return null;
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest build = HttpRequest.newBuilder()
                .uri(URI.create("https://peoplegeneratorapi.live/api/person/100"))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .header("Accept-Encoding", "gzip")
                .build();
        var response = httpClient.send(build, HttpResponse.BodyHandlers.ofInputStream());
        String body = new String(new GZIPInputStream(response.body()).readAllBytes());
        System.out.println(body);
//        HttpResponse<byte[]> httpResponse = httpClient.send(build, HttpResponse.BodyHandlers.ofByteArray());
//        System.out.println(decompressGzip(httpResponse.body()));
//
//
//        URI[] uris = {
//                new URI("http://localhost:8080/"),                   // Homepage
//                new URI("http://localhost:8080/api/exampleoutput"),  // Example output
//                new URI("http://localhost:8080/api/getusers"),       // List of all users
//                new URI("http://localhost:8080/api/getuser/124"),    // Get user with id 124
//                new URI("http://localhost:8080/api/adduser"),        // Add user
//                new URI("http://localhost:8080/api/updateuser/124"), // Update user with id 124 to a new user
//                new URI("http://localhost:8080/api/removeuser/124")  // Remove user with id 124
//        };
//        var user = new User( // This is the user that we will use to send to the server
//                "Jenny",
//                143,
//                "Email@email.com",
//                "pword",
//                "example.png"
//        );
//        var client = HttpClient.newHttpClient(); // This is the client that will send the request, you only need one
//
//        var request = HttpRequest // This is the request that we will send to the server
//                .newBuilder()
//                .uri(uris[1])
//                .header("accept", "application/json") // This means that we want the server to send us JSON back
//                .GET()
//                .build();
//        var request2  = HttpRequest
//                .newBuilder()
//                .uri(uris[2])
//                .header("accept", "application/json")
//                .GET()
//                .build();
//        var request3 = HttpRequest
//                .newBuilder()
//                .uri(uris[3])
//                .header("accept", "application/json")
//                .GET()
//                .build();
//        var request4 = HttpRequest
//                .newBuilder()
//                .uri(uris[4])
//                .version(HttpClient.Version.HTTP_2)
//                .timeout(Duration.ofMinutes(1))
//                .header("Content-Type", "application/json")
//                .header("Authorization", "Bearer fake")
//                .POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(user)))
//                .build();
//        var request5 = HttpRequest
//                .newBuilder()
//                .uri(URI.create(uris[5] + "?id=124")) // this is how you add "query parameters".
//                // when you read the docs for an API, they should tell you what to include and how to include them
//                .version(HttpClient.Version.HTTP_2)
//                .timeout(Duration.ofMinutes(1))
//                .header("Content-Type", "application/json")
//                .PUT(HttpRequest.BodyPublishers.ofString(new Gson().toJson(user)))
//                .build();
//        var request6 = HttpRequest
//                .newBuilder()
//                .uri(uris[6])
//                .version(HttpClient.Version.HTTP_2)
//                .timeout(Duration.ofMinutes(1))
//                .header("Content-Type", "application/json")
//                .DELETE()
//                .build();
//
//        var response = client.send(request, HttpResponse.BodyHandlers.ofString()); // This is how we send and receive the response
//        var response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
//        var response3 = client.send(request3, HttpResponse.BodyHandlers.ofString());
//        var response4 = client.send(request4, HttpResponse.BodyHandlers.ofString());
//        var response5 = client.send(request5, HttpResponse.BodyHandlers.ofString());
//        var response6 = client.send(request6, HttpResponse.BodyHandlers.ofString());
//
//        System.out.println(response.body()); // This is how we print the response to the console
//        System.out.println(response2.body());
//        System.out.println(response3.body());
//        System.out.println(response4.body());
//        System.out.println(response5.body());
//        System.out.println(response6.body());

        /*
         * If you're reading this, these requests are similar to the type you might make for Google Docs using their API.
         * For instance, the following is a request to get the contents of a Google Doc using the Google Docs API:
         *
         *         HttpClient client = HttpClient.newHttpClient();
         *
         *         HttpRequest request = HttpRequest.newBuilder()
         *                 .uri(URI.create("https://docs.googleapis.com/v1/documents/YOUR_DOCUMENT_ID"))
         *                 .header("Authorization", "Bearer YOUR_ACCESS_TOKEN")
         *                 .header("Content-Type", "application/json")
         *                 .GET()
         *                 .build();
         *
         *         HttpResponse<String> response = client.send(request,
         *                 HttpResponse.BodyHandlers.ofString());
         *
         *         System.out.println(response.body());
         */
    }
}

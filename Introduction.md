# Learn APIs - A Hands on Experience
## Table of Contents
1. [Getting Started](#getting-started)
	a. [Requirements](#requirements)
2. [What is an API?](#what-is-an-api?)
	a. [How exactly does an API work?](#how-exactly-does-an-api-work?)
3. [Using an API](#using-an-api)
	a. [Booting the Server](#booting-the-server)
	b. [Making Your First Request](#making-your-first-request)
4. [HTTP Requests](#http-requests)
	a. [What is an HTTP request? What does it look like?](#what-is-an-http-request?-what-does-it-look-like?)
	b. [Endpoints](#endpoints)
	c. [Making Requests with Java](#making-requests-with-java)
5. [Part 2](#part-2)
### Getting Started
Welcome! This tutorial aims to provide a hands-on experience for learning from the basics of APIs to how to communicate with more complicated APIs. In this tutorial, we will cover what APIs are, how they work, and how to use them in your projects. 
#### Requirements
- Any IDE of your choice
- An API client of your choice, such as Postman or Insomnia (I'll be using Insomnia in this tutorial)
- Any flavor of JDK 19
- This API JarFile for this tutorial: [JarFile](https://github.com/specialiststeak/)
### What is an API?
Put simply, an API (or Application Programming Interface) is a way for you to communicate with some form of service, web service, or database. They contain a set of predefined options which allow you to tell them to execute different tasks for you. For instance, an API might let an app turn on your phone's flashlight by providing an interface to do so (even though I can't access the hardware), or let me do something like send location data to Google Maps. While there are "offline" APIs (like `java.util`) there are also online ones which allow your program to interact with other programs. Today we'll be looking into web-based APIs and how they work. Generally, however, APIs have 4 different ways in which they work: 
-   **GET**: This method is used to retrieve data from a server. Some examples might look like:
	- Getting a list of users from a database
	- Getting the contents of a Google Doc
	- Getting some form of randomly generated data
-   **POST**: This method is used to add new data to a server. Some examples might look like:
	- Adding a new user to a database
	- Adding some new content to a Google Doc
	- Uploading a video through an API rather than through a graphical user interface
-   **PUT**: This method is used to update existing data on a server. Some examples might look like:
	- Changing information about a specific user in a database (like changing a username)
	- Capitalizing all letters of someone's name if their idea matches a pattern (you can get pretty creative with these if they allow regular expressions)
	- Changing some of the contents of a Google Doc
-   **DELETE**: This method is used to delete data from a server. Some examples might look like: 
	- Deleting a user from a database
	- Deleting a Google Doc
	- Sending a request to have your data deleted from a website
#### How exactly does an API work?
APIs are similar to websites in that their URIs (URLs) do something when you go to them and give you something back as a response. These URIs (such as `https://google.com`) are called endpoints, and by going to them and sometimes providing them additional data, it will tell the server to do something and serve you something back. This is called sending a request to an API, where you send one of the parameters above specifying your type of request (GET, POST, etc.), some content (if the API requires it), and any other things the API requires. 

When you send a request to an API endpoint, it sends back a response with an HTTP status code (such as 200 OK, 404 NOT FOUND, etc.) which tells you the status of the request (if it went through, etc.) and sometimes includes other files alongside it such as JSON files (represent objects) or any other media. In simple terms, it allows for servers to transfer data between each other or provide operations for an application. Here's a diagram from Smashing Magazine which might help you understand it better: 
![API Example](https://cloud.netlifyusercontent.com/assets/344dbf88-fdf9-42bb-adb4-46f01eedd629/ab677d09-69b5-47db-a6e9-dcff07da51f7/platforms-api-diagram.png)
APIs can be used in programs, which means that they not only provide you with convenience in automating tasks or the ability to communicate with databases but are also an important part of any online service. For example, Discord might send a POST (add new data) request with your message to their database whenever you message your friends or send a GET (get data from a database) request upon startup to load your list of friends.

Not only do APIs allow for communication between servers internally, but also act as the common language of the digital world, due to communicating in standardized ways, regardless of platform. This means that a mobile app, a website, and a computer program could all communicate with the same API, like seen in the diagram below (credit to jlord):
![apidiagram](https://s3.amazonaws.com/jlord/apidiagram.png)

### Using an API
Now we're going to look into how to actually communicate with APIs, starting first from a client which makes it simpler to use them, then moving to Java so that we can use them in code.
#### Booting the Server
1. Make sure that you have the JarFile downloaded and put wherever you'd like it to be.
2. Run the file using: `java -jar learn_api.jar`
3. The API will now be up and running on port 8080 (as a locally hosted server). This means that the base URL of the API will be at `http://localhost:8080`, which is just the URL that things you're running locally usually run on. If you go to `http://localhost:8080` in your browser, you should see a welcome message telling you everything's working fine.
#### Making Your First Request
To get started, we're going to look into making a request with Insomnia to see what a response might look like in JSON format. To start:
1. Open up Insomnia
2. In the menu, select the plus button to make a new request
3. Paste in: `http://localhost:8080/api/exampleoutput`, and set the request type to `GET` if it isn't already set to it by default. This means that you're telling the API that you want to GET whatever it has at the endpoint for you.
4. Send it! You should see the following as the APIs response, which in this case is an object containing some data like a name, id, description, and an array of tags: ![exampleoutput](https://i.imgur.com/LyuIBR1.png)
As we can see, this is just an object formatted into a text file in a JSON format. Each of the things like "id" represent fields of the object, and "tags" in this case also represents an array. If it was just a String object, the JSON might look like:
```
{
	"example": "example string"
}
```
<hr>
Now let's try some other requests. 

I'll cover these more in later sections, but now we're going to try seeing how we can actually communicate with the API. Change the link in Insomnia from `http://localhost:8080/api/exampleoutput` to `http://localhost:8080/api/getusers` and press send (it should be a **GET** request). You should see a list of 10 users to the right, all in JSON format. Remember this step, we'll come back to it. 
![GET request to endpoint](https://i.imgur.com/nLSPcpZ.png)


Next up, where it says **GET**, change it to **POST** (meaning we're giving it some new data), and change `http://localhost:8080/api/getusers` to `http://localhost:8080/api/adduser`. Then, click *body* and select JSON. From here, paste in the following (understanding this represents a `user` object:
```
{
	"name": "jen",
	"id": 192,
	"email": "jenma@ameil.com",
	"password": "pwods",
		"profilePicture": "C:\\Users\\Example\\Documents\\Code\\learn_api\\cdn.discordapp.com\\attachments\\867..."
}
```
Then you can go ahead, and press send. You should see some form of confirmation message saying success. We can check the actual changes by sending the initial request we sent to see a list of users, where we can see that `jen` was successfully added to the list. We added data to the database in this example. 
![adding jen](https://i.imgur.com/cXtffgH.png)
![jen was added](https://i.imgur.com/L9sClqN.png)

Moving on, let's say we want to delete jen from the database. We can go in and make a **DELETE** request to `http://localhost:8080/api/removeuser/{id}`, which in this case would be `http://localhost:8080/api/removeuser/192`. The API knows that since we specified the number in the URL, to remove that user. We can send that and repeat the initial step to see that jen is no longer in the database. 

![deleting jen](https://i.imgur.com/bduSpKG.png)
In this case, it returned a true signifying that jen was deleted.

Now, let's look into what this would actually look like in code. 

### HTTP Requests
#### What is an HTTP request? What does it look like?
When you want to tell the API to do something, you make an HTTP request to it to tell it what you want. HTTP requests follow the following format: 
- Request line
	- This request line is the first part of the request. It contains information about your request such as: 
		- The HTTP method (GET, POST, etc.)
		- The URI (Uniform Resource Identifier) which is where you're sending the request, such as `http://localhost:8080/api/exampleoutput`
		- The HTTP version
- Headers
	- These contain metadata about your request, like date, time, IP address, compression data, and other information like this.
- Message body
	- This is the content of your request. For example, when you send a JSON file, assuming you need to give them some information you would generally put it in the 'body'.

From here, the API will take the data from the request, do whatever computing necessary, and respond to it with a JSON containing response information.
#### Endpoints
The rest of the basic endpoints I've made are as follows (paste them in after `http://localhost:8080`):
-   `/api/getusers` or `/api/getusers/`: This endpoint returns a list of all users in the database when an HTTP GET request is made to  `/api/getusers`  or  `/api/getusers/`.
-   `/api/getuser/{id}`  or  `/api/getuser/{id}/`: This endpoint returns a single user with the specified ID when an HTTP GET request is made to  `/api/getuser/{id}`  or  `/api/getuser/{id}/`.
-   `/api/adduser`  or  `/api/adduser/`: This endpoint adds a new user to the database when an HTTP POST request is made to  `/api/adduser`  or  `/api/adduser/`.
-   `/api/updateuser/{id}`  or  `/api/updateuser/{id}/`: This endpoint updates an existing user with the specified ID when an HTTP PUT request is made to  `/api/updateuser/{id}`  or  `/api/updateuser/{id}/`.
-   `/api/removeuser/{id}`  or  `/api/removeuser/{id}/`: This endpoint removes an existing user with the specified ID when an HTTP DELETE request is made to  `/api/removeuser/{id}`  or  `/api/removeuser/{id}/`.
#### Making Requests with Java
Firstly, we need to create an HTTP client. We can do so with:
`var client = HttpClient.newHttpClient();`
If you're unfamiliar with the `var` keyword, it infers the type of the variable and sets it dynamically. Next up, we need to create the URI that we want to send the request to:
`var uri = new URI("http://localhost:8080/api/adduser");`
We'll also declare a "User" object for convenience:
```
var user = new User(  
  "Jenny",  
  143,  
  "Email@email.com",  
  "pword",  
  "example.png"  
);
```
Afterwards, we can actually write up the request. 
```
var request = HttpRequest  
  .newBuilder()                               
  // starts to build the request object 
  .uri(uri)                                   
  // telling it to send the request to the URI we declared earlier  
  .version(HttpClient.Version.HTTP_2)         
  // telling it our HTTP client version
  .timeout(Duration.ofMinutes(1))             
  // setting a timeout of 1 minute, so that if there's no response within one minute it will cancel the request
  .header("Content-Type", "application/json") 
  //here we're telling it that this should be a JSON request (some APIs might support requests in other file formats like YAML)
  .header("Authorization", "Bearer fake")     
  // and here is a "custom" header. Some APIs might want you to put in a value here for an API token (think something like a password). In this case we're just giving it a fake one since this API doesn't need any verification tokens.
  .POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(user))).build();
  // finally, we declare this as a POST request, with a body (content we're giving it) of a String, with a JSON file representing the object. Gson converts the object to a JSON so we can send it.
  // finish it all up with .build(); to build the request
```
Now that we've finished drafting up the request, we can get to sending it. 
`var response = client.send(request, HttpResponse.BodyHandlers.ofString());`
This will send the request to the API, in this case telling it we want to give it the User object previously declared so that it can add it to its database. To see its response to our request, we can print the `body` of the response as follows:
`System.out.println(response.body());`
Which will, in this case, should say `Success!`, or would contain the data we requested.
If this is still a bit confusing, here are a bunch of examples with the endpoints above. Please give them a shot and see how it works out! Here's the program:
```
// as you try this out, I encourage you to keep sending the request to get all users so you can see how the API is letting you change the database
package com.specialiststeak.learn_api;  
  
import com.google.gson.Gson;  
import com.specialiststeak.learn_api.objects.User;  
  
import java.io.IOException;  
import java.net.URI;  
import java.net.URISyntaxException;  
import java.net.http.HttpClient;  
import java.net.http.HttpRequest;  
import java.net.http.HttpResponse;  
import java.time.Duration;  
  
public class SendHTTP {  
  public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {  
  URI[] uris = {  
  new URI("http://localhost:8080/"), // Homepage  
  new URI("http://localhost:8080/api/exampleoutput"), // Example output  
  new URI("http://localhost:8080/api/getusers"), // List of all users  
  new URI("http://localhost:8080/api/getuser/124"), // Get user with id 124  
  new URI("http://localhost:8080/api/adduser"), // Add user  
  new URI("http://localhost:8080/api/updateuser/124"), // Update user with id 124 to a new user  
  new URI("http://localhost:8080/api/removeuser/124") // Remove user with id 124  
  };  
  var user = new User( // This is the user that we will use to send to the server  
  "Jenny",  
  143,  
  "Email@email.com",  
  "pword",  
  "example.png"  
  );  
  var client = HttpClient.newHttpClient(); // This is the client that will send the request, you only need one  
  
  var request = HttpRequest // This is the request that we will send to the server  
  .newBuilder()  
  .uri(uris[1])  
  .header("accept", "application/json") // This means that we want the server to send us JSON back  
  .GET()  
  .build();  
  var request2 = HttpRequest  
  .newBuilder()  
  .uri(uris[2])  
  .header("accept", "application/json")  
  .GET()  
  .build();  
  var request3 = HttpRequest  
  .newBuilder()  
  .uri(uris[3])  
  .header("accept", "application/json")  
  .GET()  
  .build();  
  var request4 = HttpRequest  
  .newBuilder()  
  .uri(uris[4])  
  .version(HttpClient.Version.HTTP_2)  
  .timeout(Duration.ofMinutes(1))  
  .header("Content-Type", "application/json")  
  .header("Authorization", "Bearer fake")  
  .POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(user)))  
  .build();  
  var request5 = HttpRequest  
  .newBuilder()  
  .uri(URI.create(uris[5] + "?id=124")) // this is how you add "query parameters".  
 // when you read the docs for an API, they should tell you what to include and how to include them  .version(HttpClient.Version.HTTP_2)  
  .timeout(Duration.ofMinutes(1))  
  .header("Content-Type", "application/json")  
  .PUT(HttpRequest.BodyPublishers.ofString(new Gson().toJson(user)))  
  .build();  
  var request6 = HttpRequest  
  .newBuilder()  
  .uri(uris[6])  
  .version(HttpClient.Version.HTTP_2)  
  .timeout(Duration.ofMinutes(1))  
  .header("Content-Type", "application/json")  
  .DELETE()  
  .build();  
  
  var response = client.send(request, HttpResponse.BodyHandlers.ofString()); // This is how we send and receive the response  
  var response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());  
  var response3 = client.send(request3, HttpResponse.BodyHandlers.ofString());  
  var response4 = client.send(request4, HttpResponse.BodyHandlers.ofString());  
  var response5 = client.send(request5, HttpResponse.BodyHandlers.ofString());  
  var response6 = client.send(request6, HttpResponse.BodyHandlers.ofString());  
  
  System.out.println(response.body()); // This is how we print the response to the console  
  System.out.println(response2.body());  
  System.out.println(response3.body());  
  System.out.println(response4.body());  
  System.out.println(response5.body());  
  System.out.println(response6.body());  
  }  
}
```
### Part 2
If you'd like to play around with APIs a bit more, send a GET request to `http://localhost:8080/next-steps/` for further content. 

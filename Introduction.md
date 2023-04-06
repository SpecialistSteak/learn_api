# Learn APIs - A Hands on Experience
## Table of Contents
1. [Getting Started](#getting-started)<br>
   a. [Requirements](#requirements)<br>
2. [What is an API?](#what-is-an-api?)<br>
   a. [How exactly does an API work?](#how-exactly-does-an-api-work?)<br>
3. [Using an API](#using-an-api)<br>
   a. [Booting the Server](#booting-the-server)<br>
   b. [Making Your First Request](#making-your-first-request)<br>
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
Now let's try some other requests. Try sending a **GET** request to `http://localhost:8080/api/introduction2` to see the next steps of the guide (do it with Insomnia, or just open the link in your browser).
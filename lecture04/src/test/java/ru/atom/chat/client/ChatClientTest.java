package ru.atom.chat.client;

import okhttp3.Response;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.atom.chat.client.ChatClient;
import ru.atom.chat.server.ChatApplication;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ChatApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ChatClientTest {
    private static String MY_NAME_IN_CHAT = "I_AM_STUPID";
    private static String MY_MESSAGE_TO_CHAT = "SOMEONE_KILL_ME";

    @Test
    public void login() throws IOException {
        Response response = ChatClient.login(MY_NAME_IN_CHAT);
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println();
        Assert.assertTrue(response.code() == 200 || body.equals("Already logged in:("));
    }

    @Test
    public void viewChat() throws IOException {
        Response response = ChatClient.viewChat();
        System.out.println("[" + response + "]");
        System.out.println(response.body().string());
        Assert.assertEquals(200, response.code());
    }


    @Test
    public void viewOnline() throws IOException {
        Response response1 = ChatClient.login(MY_NAME_IN_CHAT);
        Response response = ChatClient.viewOnline();
        System.out.println("[" + response + "]");

        String responseBody = response.body().string();
        System.out.println(responseBody);

        Assert.assertTrue(response.code() == 200 && responseBody.equals(MY_NAME_IN_CHAT));
    }

    @Test
    public void say() throws IOException {
        Response response = ChatClient.say(MY_NAME_IN_CHAT, MY_MESSAGE_TO_CHAT);
        System.out.println("[" + response + "]");
        System.out.println(response.body().string());
        Assert.assertEquals(200, response.code());
    }

    @Test
    public void logout() throws IOException {
        Response response1 = ChatClient.login(MY_NAME_IN_CHAT);
        System.out.println("[" + response1 + "]");
        Response response = ChatClient.logout(MY_NAME_IN_CHAT);
        System.out.println("[" + response + "]");
        System.out.println(response.body().string());
        Assert.assertEquals(200, response.code());
    }

    @Test
    public void deleteHistory() throws IOException {
        Response response = ChatClient.deleteChatHistory();
        System.out.println("[" + response + "]");

        String body = response.body().string();
        System.out.println(body);
        Assert.assertTrue(response.code() == 200 && body.equals("History deleted!"));
    }

    @Test
    public void getRandomLogin() throws IOException {
        Response response = ChatClient.getRandomLogin();
        System.out.println("[" + response + "]");

        String[] suggestions = {"newUser", "Hello123", "myFirstLogin", "test123"};

        String body = response.body().string();

        boolean foundInSuggestions = false;

        for (int i = 0; i < suggestions.length; i++) {
           if (body.equals(suggestions[i])) {
               foundInSuggestions = true;
               break;
           }
        }

        System.out.println(body);
        Assert.assertTrue(response.code() == 200 && foundInSuggestions);
    }
}

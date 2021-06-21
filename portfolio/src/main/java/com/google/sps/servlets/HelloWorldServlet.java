package com.google.sps.servlets;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.*;

@WebServlet("/hello")
public class HelloWorldServlet extends HttpServlet {

    @Override
    public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        ArrayList<String> randFacts = new ArrayList<String>();
        randFacts.add(
                "I was born somewhere between Laos and Thailand while my parents were en route to a refugee camp.");
        randFacts.add("I recently moved to Fresno, CA from Visalia, CA and I'm living with 2 dogs, Max and Mister.");
        randFacts.add("I love dogs (and i'm also allergic to them).");
        randFacts.add("My favorite food is shrimp. I will eat any dish if it's included.");
        String json = convertToJsonUsingGson(randFacts);

        response.setContentType("application/json;");
        response.getWriter().println(json);
    }

    private String convertToJson(ArrayList<String> randFacts) {
        String json = "{";
        json += "\"fact1\": ";
        json += "\"" + randFacts.get(0) + "\"";
        json += ",  ";
        json += "\"fact2\": ";
        json += "\"" + randFacts.get(1) + "\"";
        json += ",  ";
        json += "\"fact3\": ";
        json += "\"" + randFacts.get(2) + "\"";
        json += ",  ";
        json += "\"fact4\": ";
        json += "\"" + randFacts.get(3) + "\"";
        json += "}";
        return json;
    }

    private String convertToJsonUsingGson(ArrayList<String> randFacts) {
        final Gson gson = new Gson();
        return gson.toJson(randFacts);
        
    }
}

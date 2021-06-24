package com.google.sps.servlets;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.KeyFactory;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@WebServlet("/form-handler")
public class FormHandlerServlet<Contact> extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String nameValue = request.getParameter("contact-name");
        String emailValue = request.getParameter("contact-email");
        String messageValue = request.getParameter("contact-message");

        String timestamp = LocalTime.now(ZoneId.of("America/Los_Angeles")).truncatedTo(ChronoUnit.SECONDS).toString();

        // clean inputs
        if (nameValue != null) {
            nameValue = Jsoup.clean(nameValue, Whitelist.none());
        }
        if (emailValue != null) {
            emailValue = Jsoup.clean(emailValue, Whitelist.none());
        }
        if (messageValue != null) {
            messageValue = Jsoup.clean(messageValue, Whitelist.none());
        }

        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
        KeyFactory keyFactory = datastore.newKeyFactory().setKind("Contact");
        FullEntity contactEntity = Entity.newBuilder(keyFactory.newKey())
            .set("contact-name", nameValue)
            .set("contact-email", emailValue)
            .set("contact-message", messageValue)
            .set("timestamp", timestamp)
            .build();
        datastore.put(contactEntity);

        // Print value in server logs.
        System.out.println("You submitted: " + nameValue + "\nEmail: " + emailValue + "\nMessage: " + messageValue);

        // Write value to the response for user.
        response.getWriter().println("You submitted: " + messageValue);
        response.sendRedirect("contact.html");
    }
}
package com.google.sps.servlets;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery.OrderBy;
import com.google.gson.Gson;
import com.google.sps.data.Contact;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/list-contacts")
public class ContactListServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // creates default instance of datastore along with services
        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

        // creates and build a query that specifies filters
        Query<Entity> query = Query.newEntityQueryBuilder().setKind("Contact").setOrderBy(OrderBy.desc("timestamp"))
                .build();

        // returns results of the query
        QueryResults<Entity> queryResults = datastore.run(query);

        // iterate queryResult elements, set fields, and create newContact to add to contactsList
        List<Contact> contactsList = new ArrayList<>();
        while (queryResults.hasNext()) {
            Entity entity = queryResults.next();
            long id = entity.getKey().getId();
            String name = entity.getString("contact-name");
            String email = entity.getString("contact-email");
            String message = entity.getString("contact-message");
            String timestamp = entity.getString("timestamp").toString();

            Contact newContact = new Contact(id, name, email, message, timestamp);
            contactsList.add(newContact);
        }

        Gson gson = new Gson();

        response.setContentType("application/json;");
        response.getWriter().println(gson.toJson(contactsList));
    }
}

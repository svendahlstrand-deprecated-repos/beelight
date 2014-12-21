package net.dahlstrand.supportbee;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public enum InboxStatus {
  EMPTY,
  TICKETS,
  CUSTOMER_TICKETS,
  NO_CONTACT;

  public static InboxStatus current() {
    Unirest.setDefaultHeader("Accept", "application/json");
    Unirest.setDefaultHeader("Content-Type", "application/json");

    Properties properties = getProperties();

    try {
      JSONArray tickets = Unirest.get("https://" + properties.getProperty("customerName") + ".supportbee.com/tickets")
        .queryString("auth_token", properties.getProperty("authToken"))
        .queryString("replies", "false")
        .asJson().getBody().getObject().getJSONArray("tickets");

      if (tickets.length() > 0) {
        for (int i = 0; i < tickets.length(); i++) {
          JSONArray labels = tickets.getJSONObject(i).getJSONArray("labels");

          for (int j = 0; j < labels.length(); j++) {
            if (labels.getJSONObject(j).getString("name").equals(properties.getProperty("importantLabel"))) {
              return CUSTOMER_TICKETS;
            }
          }
        }
        return TICKETS;
      }
      else {
        return EMPTY;
      }
    } catch (UnirestException e) {
      return NO_CONTACT;
    }
  }

  private static Properties getProperties() {
    Properties properties = new Properties();

    try {
      FileInputStream file = new FileInputStream("config/application.properties");
      properties.load(file);
      file.close();
    } catch (IOException e) {}

    return properties;
  }
}

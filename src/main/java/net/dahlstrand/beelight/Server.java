package net.dahlstrand.beelight;

import com.mashape.unirest.http.exceptions.UnirestException;
import net.dahlstrand.cleware.NoTrafficLightFoundException;
import net.dahlstrand.cleware.TrafficLight;
import net.dahlstrand.supportbee.InboxStatus;

import static net.dahlstrand.cleware.Color.*;

public class Server {
  public static void main(String[] args) throws InterruptedException, NoTrafficLightFoundException, UnirestException {
    TrafficLight lamp = TrafficLight.getInstance();

    switch (InboxStatus.current()) {
      case EMPTY:
        lamp.on(GREEN);
        break;
      case TICKETS:
        lamp.on(YELLOW);
        break;
      case CUSTOMER_TICKETS:
        lamp.on(RED);
        break;
      case NO_CONTACT:
        lamp.blink(RED);
        break;
    }
  }
}

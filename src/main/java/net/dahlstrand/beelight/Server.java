package net.dahlstrand.beelight;

import net.dahlstrand.cleware.NoTrafficLightFoundException;
import net.dahlstrand.cleware.TrafficLight;

import static net.dahlstrand.cleware.Color.*;

public class Server {
  public static void main(String[] args) throws InterruptedException, NoTrafficLightFoundException {
    TrafficLight lamp = TrafficLight.getInstance();

    lamp.on(RED);
    Thread.sleep(1000);
    lamp.on(GREEN);
    Thread.sleep(1000);

    lamp.off();
  }
}

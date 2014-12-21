package net.dahlstrand.cleware;

import java.io.IOException;

public class NoTrafficLightFoundException extends IOException {
  public NoTrafficLightFoundException(Throwable throwable) {
    super("Make sure the traffic light is plugged in.", throwable);
  }
}

package net.dahlstrand.cleware;

import com.codeminders.hidapi.ClassPathLibraryLoader;
import com.codeminders.hidapi.HIDDevice;
import com.codeminders.hidapi.HIDManager;

import java.io.IOException;

public class TrafficLight {
  private static final int VENDOR_ID = 3408;
  private static final int PRODUCT_ID = 8;
  private static final int ZERO = 0x0;

  private static TrafficLight instance;
  private HIDDevice device;

  private TrafficLight(HIDDevice device) {
    this.device = device;
  }

  public void off(Color color) throws NoTrafficLightFoundException {
    write(color, State.OFF);
  }

  public void off() throws NoTrafficLightFoundException {
    for (Color color : Color.values()) {
      off(color);
    }
  }

  public void on(Color color) throws NoTrafficLightFoundException {
    off();
    write(color, State.ON);
  }

  public void blink(Color color) throws NoTrafficLightFoundException {
    try {
      for (int i = 0; i < 2; i++) {
        on(color);
        Thread.sleep(500);
        off();
        Thread.sleep(500);
      }
    } catch (InterruptedException e) {
    }
  }

  public static TrafficLight getInstance() throws NoTrafficLightFoundException {
    if (instance == null) {
      ClassPathLibraryLoader.loadNativeHIDLibrary();
      HIDManager manager = null;

      try {
        manager = HIDManager.getInstance();
        instance = new TrafficLight(manager.openById(VENDOR_ID, PRODUCT_ID, null));
      } catch (IOException e) {
        throw (new NoTrafficLightFoundException(e));
      } finally {
        if (manager != null) {
          manager.release();
        }
      }
    }

    return instance;
  }

  private void write(Color color, State state) throws NoTrafficLightFoundException {
    try {
      device.write(new byte[]{ZERO, ZERO, color.getByteValue(), state.getByteValue()});
    } catch (IOException e) {
      throw (new NoTrafficLightFoundException(e));
    }
  }
}

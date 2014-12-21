package net.dahlstrand.cleware;

public enum State {
  OFF (0x0),
  ON (0x1);

  private final byte byteValue;

  State(int byteValue) {
    this.byteValue = (byte) byteValue;
  }

  public byte getByteValue() {
    return byteValue;
  }
}

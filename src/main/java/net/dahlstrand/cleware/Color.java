package net.dahlstrand.cleware;

public enum Color {
  RED (0x10),
  YELLOW (0x11),
  GREEN (0x12);

  private final byte byteValue;

  Color (int byteValue) {
    this.byteValue = (byte) byteValue;
  }

  public byte getByteValue() {
    return byteValue;
  }
}

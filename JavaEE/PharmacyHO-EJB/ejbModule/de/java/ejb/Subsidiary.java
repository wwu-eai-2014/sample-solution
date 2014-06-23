package de.java.ejb;

public enum Subsidiary {
  JAVA("JaVa", "http://localhost:8080/Pharmacy-Web/api"),
  C_SHARPE("C.Sharpe", "http://localhost:1053");

  private final String representation;
  private final String baseUri;

  private Subsidiary(String representation, String baseUri) {
    this.representation = representation;
    this.baseUri = baseUri;
  }

  @Override
  public String toString() {
    return representation;
  }

  public String getBaseUri() {
    return baseUri;
  }
}

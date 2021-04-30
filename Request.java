/*
  a request simply has a kind
  and other special attributes
  sometimes needed

  Is a simple wrapper class, allow
  public instance variables

*/

public class Request {

  public String kind;
  public double amount;
  public int elapsed;  // number of frames elapsed for multi-frame request

  public Request( String knd ) {
    kind = knd;
    elapsed = 0;
  }

  public Request( String knd, double amt ) {
    kind = knd;
    amount = amt;
    elapsed = 0;
  }

}

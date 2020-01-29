package livestreamerrunner;

import java.io.Serializable;

class Kedvenc implements Serializable {
    private final String StreamName, StreamLeírás;
    
    public Kedvenc(String StreamName, String StreamLeírás) {
    this.StreamName = StreamName;
    this.StreamLeírás = StreamLeírás;
  }

  public String getStreamName() {
    return StreamName;
  }

  public String getStreamLeírás() {
    return StreamLeírás;
  }
  
  @Override
    public String toString() {
        return getStreamName()+", "+getStreamLeírás();
    }
}

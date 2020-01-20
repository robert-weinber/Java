/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package livestreamerrunner;

import java.io.Serializable;

/**
 *
 * @author Wolfram
 */
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

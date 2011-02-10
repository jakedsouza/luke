package org.getopt.luke.decoders;

import org.apache.lucene.document.Fieldable;
import org.getopt.luke.Util;

public class BinaryDecoder implements Decoder {

  @Override
  public String decodeTerm(String fieldName, Object value) throws Exception {
    byte[] data;
    if (value instanceof byte[]) {
      data = (byte[])value;
    } else {
      data = value.toString().getBytes();
    }
    return Util.bytesToHex(data, 0, data.length, false);
  }

  @Override
  public String decodeStored(String fieldName, Fieldable value) throws Exception {
    if (value.isBinary()) {
      byte[] bytes = new byte[value.getBinaryLength()];
      System.arraycopy(value.getBinaryValue(), value.getBinaryOffset(), bytes, 0, value.getBinaryLength());
      return decodeTerm(fieldName, bytes);
    } else {
      return decodeTerm(fieldName, value);
    }
  }
  
  public String toString() {
    return "binary";
  }
}

package com.github.ambry.shared;

import com.github.ambry.utils.Utils;

import java.io.DataInputStream;
import java.io.IOException;


/**
 * A Response to the Put Request
 */
public class PutResponse extends Response {

  public PutResponse(int correlationId, String clientId, ServerErrorCode error) {
    super(RequestOrResponseType.PutResponse, Request_Response_Version, correlationId, clientId, error);
  }

  public static PutResponse readFrom(DataInputStream stream)
      throws IOException {
    RequestOrResponseType type = RequestOrResponseType.values()[stream.readShort()];
    if (type != RequestOrResponseType.PutResponse) {
      throw new IllegalArgumentException("The type of request response is not compatible: " + type);
    }
    Short versionId = stream.readShort();
    int correlationId = stream.readInt();
    String clientId = Utils.readIntString(stream);
    ServerErrorCode error = ServerErrorCode.values()[stream.readShort()];
    // ignore version for now
    return new PutResponse(correlationId, clientId, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("PutResponse[");
    sb.append("ServerErrorCode=").append(getError());
    sb.append("]");
    return sb.toString();
  }
}

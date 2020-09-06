package com.xux.elib;

import java.io.IOException;
import java.io.InputStream;

public interface HashHelper {
    String hexEncode(byte[] bytes);

    byte[] hexDecode(String str);

    byte[] sha256ToBytes(byte[] data);

    byte[] sha256ToBytes(String str);

    byte[] sha256ToBytes(InputStream inputStream) throws IOException;

    String sha256ToString(byte[] data);

    String sha256ToString(String str);

    String sha256ToString(InputStream inputStream) throws IOException;
}

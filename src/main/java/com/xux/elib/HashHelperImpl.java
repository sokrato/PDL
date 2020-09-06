package com.xux.elib;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class HashHelperImpl implements HashHelper {

    private static final char[] hexCode = "0123456789abcdef".toCharArray();

    @Override
    public String hexEncode(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(hexCode[(b >> 4) & 0xF]);
            sb.append(hexCode[b & 0xF]);
        }
        return sb.toString();
    }

    @Override
    public byte[] hexDecode(String str) {
        throw new RuntimeException("TODO");
    }

    private HashCode sha256(byte[] data) {
        return Hashing.sha256()
                .newHasher()
                .putBytes(data)
                .hash();
    }

    @Override
    public byte[] sha256ToBytes(byte[] data) {
        return sha256(data).asBytes();
    }

    @Override
    public byte[] sha256ToBytes(String str) {
        return sha256ToBytes(str.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public byte[] sha256ToBytes(InputStream inputStream) throws IOException {
        return sha256Stream(inputStream).asBytes();
    }

    @Override
    public String sha256ToString(byte[] data) {
        return sha256(data).toString();
    }

    @Override
    public String sha256ToString(String str) {
        return sha256ToString(str.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String sha256ToString(InputStream inputStream) throws IOException {
        return sha256Stream(inputStream).toString();
    }

    public HashCode sha256Stream(InputStream inputStream) throws IOException {
        Hasher hasher = Hashing.sha256().newHasher();
        byte[] buf = new byte[1024];
        int nread = 0;
        while ((nread = inputStream.read(buf)) > 0) {
            hasher.putBytes(buf, 0, nread);
        }
        return hasher.hash();
    }
}

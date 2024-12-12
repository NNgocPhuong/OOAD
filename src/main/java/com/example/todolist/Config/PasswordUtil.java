package com.example.todolist.Config;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {
    // Hàm mã hóa mật khẩu bằng SHA-256
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error occurred while hashing password", e);
        }
    }

    // Chuyển byte[] thành chuỗi hex
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    // Hàm kiểm tra mật khẩu
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        String hashedPlainPassword = hashPassword(plainPassword);
        return hashedPlainPassword.equals(hashedPassword);
    }
}

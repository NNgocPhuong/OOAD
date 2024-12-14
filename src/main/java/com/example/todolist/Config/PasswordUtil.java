package com.example.todolist.Config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // Hàm mã hóa mật khẩu bằng SHA-256
    // public static String hashPassword(String password) {
    //     try {
    //         MessageDigest digest = MessageDigest.getInstance("SHA-256");
    //         byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
    //         return bytesToHex(encodedHash);
    //     } catch (NoSuchAlgorithmException e) {
    //         throw new RuntimeException("Error occurred while hashing password", e);
    //     }
    // }

    // Chuyển byte[] thành chuỗi hex
    // private static String bytesToHex(byte[] hash) {
    //     StringBuilder hexString = new StringBuilder();
    //     for (byte b : hash) {
    //         String hex = Integer.toHexString(0xff & b);
    //         if (hex.length() == 1) hexString.append('0');
    //         hexString.append(hex);
    //     }
    //     return hexString.toString();
    // }

    // Hàm kiểm tra mật khẩu
    // public static boolean verifyPassword(String plainPassword, String hashedPassword) {
    //     String hashedPlainPassword = hashPassword(plainPassword);
    //     return hashedPlainPassword.equals(hashedPassword);
    // }

    public static String hashPassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }
    public static boolean verifyPassword(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}

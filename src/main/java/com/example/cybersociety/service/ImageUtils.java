package com.example.cybersociety.service;

import java.util.Base64;

public class ImageUtils {

    public static String convertImageToBase64(byte[] imageBytes) {
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    public static byte[] convertBase64ToImage(String base64String) {
        return Base64.getDecoder().decode(base64String);
    }
}

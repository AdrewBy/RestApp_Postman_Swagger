package org.ustsinau.chapter2_4.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class GetFileNameUtil {

    public static String getFileName(InputStream inputStream) {
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении потока: ", e);
        }

        String resultString = result.toString();
        String fileName = resultString.substring(0, resultString.lastIndexOf('"'));
        fileName = new StringBuffer(fileName).reverse().toString();
        fileName = fileName.substring(0, fileName.indexOf('"'));
        fileName = new StringBuffer(fileName).reverse().toString();
        return fileName;
    }
}
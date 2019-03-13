package com.task.delivery.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    /**
     * Reads all data from file
     *
     * @param filePath  absolute path to file
     * @param separator symbol used to separate single line values
     * @param skipFirst true if to skip first line, false otherwise
     * @return all non empty file data
     */
    public static List<String[]> readFile(String filePath, String separator, boolean skipFirst) {
        List<String[]> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            if (skipFirst) {
                br.readLine();
            }
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                String[] values = line.split(separator);
                // we only read data that has more than one column
                if (values.length > 1) {
                    lines.add(values);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}

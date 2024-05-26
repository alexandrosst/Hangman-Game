package com.alexandrosst;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashSet;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FileHandler {
    private static final String DIRECTORY_PATH = System.getProperty("user.dir") + "/medialab";

    public String chooseFile() {
        Stage stage = new Stage();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Dictionary");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        File initialDirectory = new File(DIRECTORY_PATH);
        fileChooser.setInitialDirectory(initialDirectory);

        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null)
            return selectedFile.getName();
        else
            return null;
    }

    public FileHandler() {
        try {
            Files.createDirectories(Paths.get(DIRECTORY_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean fileExists(String filename) {
        Path filePath = Paths.get(DIRECTORY_PATH, "/" + filename);
        return Files.exists(filePath);
    }

    public void readFile(String fileName, ArrayList<String> allDictionaryWords) throws IOException {
        Path inputPath = Paths.get(DIRECTORY_PATH, "/" + fileName);
        BufferedReader reader = new BufferedReader(new FileReader(inputPath.toFile()));

        String line;
        while ((line = reader.readLine()) != null) {
            allDictionaryWords.add(line);
        }
        reader.close();
    }

    public void writeFile(String fileName, HashSet<String> content) {
        try {
            Path outputPath = Paths.get(DIRECTORY_PATH, "/" + fileName + ".txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath.toFile()));
            for (var word : content) {
                writer.write(word);
                writer.write(System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
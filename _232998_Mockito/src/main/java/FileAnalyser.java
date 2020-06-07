import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileAnalyser {

    public int countLettersInFile(String filePath) throws IOException {
        File file = new File(filePath);
        int letters = 0;
        try (BufferedReader br = Files.newBufferedReader(Paths.get(String.valueOf(file)))) {
            String line;
            while ((line = br.readLine()) != null) {
                char[] ch = line.toCharArray();
                for (int i = 0; i < line.length(); i++) {
                    if (Character.isLetter(ch[i])) {
                        letters++;
                    }
                }
            }
        } catch (IOException e) {
            throw new FileNotFoundException(
                   "Nie można odnaleźć pliku: "+ filePath);
        }
        return letters;
    }
}


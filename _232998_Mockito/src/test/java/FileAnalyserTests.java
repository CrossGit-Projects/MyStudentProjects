import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileAnalyserTests {

    @Test
    void testEmptyPath() throws URISyntaxException {
        wrongPath("", "Nie można odnaleźć pliku: ");
    }

    @Test
    void testPathNotExist() throws URISyntaxException {
        wrongPath("notExistFile.txt", "Nie można odnaleźć pliku: notExistFile.txt");
    }

    @Test
    void testFileOnlyLetters() throws IOException, URISyntaxException {
        checkMethod("FileAnalyserTests/OnlyLetters.txt", 12);
    }

    @Test
    void testFileLettersAndNumbers() throws IOException, URISyntaxException {
        checkMethod("FileAnalyserTests/LettersAndNumbers.txt", 16);
    }

    @Test
    void testLettersNumbersSpecialSigns() throws IOException, URISyntaxException {
        checkMethod("FileAnalyserTests/LettersNumbersSpecialSigns.txt", 15);
    }

    @Test
    void testWhiteSpace() throws IOException, URISyntaxException {
        checkMethod("FileAnalyserTests/WhiteSpace.txt", 12);
    }

    private void checkMethod(String fileName, Integer expected) throws IOException, URISyntaxException {

        FileAnalyser call = new FileAnalyser();
        String platformIndependentPath = getPathToFile(fileName);
        assertEquals(call.countLettersInFile(platformIndependentPath), expected);
    }

    private void wrongPath(String wrongPath, String exceptionMessage) throws URISyntaxException {
        FileAnalyser call = new FileAnalyser();
        FileNotFoundException thrown = assertThrows(FileNotFoundException.class, () -> {
            call.countLettersInFile(wrongPath);
        });
        assertEquals(thrown.getMessage(), exceptionMessage);
    }

    private String getPathToFile(String fileName) throws URISyntaxException {
        return Paths.get(this.getClass().getResource(fileName).getPath()).toString();
    }
}
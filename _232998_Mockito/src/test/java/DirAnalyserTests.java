import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class DirAnalyserTests {
    private FileAnalyser fileAnalyser;
    private DirAnalyser dirAnalyser;

    public DirAnalyserTests() {
        fileAnalyser = mock(FileAnalyser.class);
        dirAnalyser = new DirAnalyser(fileAnalyser);
    }

    @Test
    void checkContentDirForTxt() throws IOException, URISyntaxException {

        String path = getPathToFile("DirectoryTest1");
        createWhen(path, "/test1.txt", 3);
        createWhen(path, "/test2.txt", 5);
        createWhen(path, "/test3.txt", 8);

        verifyTest(path, ".txt", 3, 16);

    }

    @Test
    void checkContentDirForXml() throws IOException, URISyntaxException {

        String path = getPathToFile("DirectoryTest1");
        createWhen(path, "/test4.xml", 2);
        createWhen(path, "/test5.xml", 4);
        createWhen(path, "/test6.xml", 5);
        createWhen(path, "/test7.xml", 6);

        verifyTest(path, ".xml", 4, 17);

    }

    @Test
    void checkEmptyDir() throws IOException, URISyntaxException {
        String path = getPathToFile("DirectoryTest2");
        verifyTest(path, ".txt", 0, 0);
    }

    private String getPathToFile(String fileName) throws URISyntaxException {
        return Paths.get(this.getClass().getResource(fileName).getPath()).toString();
    }

    private void verifyTest(String path, String extension, Integer countTimes, Integer expected) throws IOException {
        assertEquals(expected, dirAnalyser.calculateLettersInDirectory(path, extension));
        verify(fileAnalyser, times(countTimes)).countLettersInFile(anyString());
    }

    private void createWhen(String path, String nameFile, Integer retCount) throws IOException {
        when(fileAnalyser.countLettersInFile(path + nameFile)).thenReturn(retCount);
    }

}
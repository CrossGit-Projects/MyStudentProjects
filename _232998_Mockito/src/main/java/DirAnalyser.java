import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class DirAnalyser {
    private FileAnalyser fileAnalyser;

    public DirAnalyser(FileAnalyser fileAnalyser) {
        this.fileAnalyser = fileAnalyser;
    }

    public int calculateLettersInDirectory(String pathDirectory, String extensionFile) throws IOException {
        int countLetters = 0;
        File folder = new File(pathDirectory);
        FilenameFilter fileFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (name.endsWith(extensionFile)) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        File[] files = folder.listFiles(fileFilter);
        for (File file : files) {
            countLetters += fileAnalyser.countLettersInFile(file.getPath());
        }
        return countLetters;
    }
}

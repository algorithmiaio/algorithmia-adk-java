package algorithms;

import com.algorithmia.development.AbstractAlgorithm;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileHandleAbstractAlgorithm extends AbstractAlgorithm<String, FileOutputStream> {
    public FileOutputStream apply(String localFile) {
        try {
            File file = new File(localFile);
            file.createNewFile();
            file.delete();
            return new FileOutputStream(file);
        } catch (IOException e) {
            throw new RuntimeException("we detected an error making/deleting the file " + localFile);
        }
    }
}

import com.algorithmia.Algorithmia;
import com.algorithmia.data.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.Assume;
import org.junit.Assert;

public class DataDirectoryTest {


    @Test
    public void dataDirParent() throws Exception {
        DataDirectory dir = new DataDirectory(null, "data://.my/javaclienttest");
        DataDirectory parent = new DataDirectory(null, "data://.my");
        Assert.assertEquals(parent.path, dir.getParent().path);
    }

    @Test
    public void dataDirName() throws Exception {
        DataDirectory dir = new DataDirectory(null, "data://.my/javaclienttest");
        String expected = "javaclienttest";
        Assert.assertEquals(expected, dir.getName());
    }

    @Test
    public void dataDirCreate() throws Exception {
        final String key = System.getenv("ALGORITHMIA_API_KEY");
        Assume.assumeTrue(key != null);

        DataDirectory dir = Algorithmia.client(key).dir("data://.my/javaDataDirCreate");

        // Make sure test starts in clean state
        if(dir.exists()) {
            dir.delete(true);
        }

        dir.create();
        Assert.assertEquals(true, dir.exists());
        dir.delete(false);
        Assert.assertEquals(false, dir.exists());
    }

    @Test
    public void dataDirList() throws Exception {
        final String key = System.getenv("ALGORITHMIA_API_KEY");
        Assume.assumeTrue(key != null);

        DataDirectory dir = Algorithmia.client(key).dir("data://.my/javaDataDirList");

        if(dir.exists()) {
            dir.delete(true);
        }

        dir.create();
        dir.file("foo").put("bar");
        dir.file("foo2").put("bar2");
        DataFileIterator iter = dir.getFileIter();

        Set<String> filesFound = new HashSet<String>();
        int numFiles = 0;

        while (iter.hasNext()) {
            numFiles++;
            Assert.assertTrue(filesFound.add(iter.next().toString()));
        }

        Assert.assertEquals(2, numFiles);
        Assert.assertTrue(filesFound.contains("data://.my/javaDataDirList/foo"));
        Assert.assertTrue(filesFound.contains("data://.my/javaDataDirList/foo2"));
    }

}

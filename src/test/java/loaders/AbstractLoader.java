package loaders;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;
import org.javaync.io.AsyncFiles;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.*;

public class AbstractLoader {
    JsonParser parser = new JsonParser();
    String FIFOPIPE = "/tmp/algoout";


    JsonObject getOutput() throws Exception {
        Path in = Paths.get(FIFOPIPE);
        byte[] bytesCf = IOUtils.toByteArray(new FileInputStream(new File(FIFOPIPE)));
        String rawData = new String(bytesCf);
        return parser.parse(rawData).getAsJsonObject();
    }


    void prepareInput(JsonObject input){
        String stringified = input.toString();
        InputStream fakeIn = new ByteArrayInputStream(stringified.getBytes());
        System.setIn(fakeIn);
    }

}

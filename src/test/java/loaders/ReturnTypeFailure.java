package loaders;

import algorithms.FileHandleAbstractAlgorithm;
import com.algorithmia.development.Handler;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReturnTypeFailure {

    private FileHandleAbstractAlgorithm algo = new FileHandleAbstractAlgorithm();
    private JsonObject request = GenerateInput();
    public JsonObject expectedResponse = GenerateOutput();
    private JsonParser parser = new JsonParser();
    private String FIFOPIPE = "/tmp/algoout";


    public JsonObject GenerateInput() {
        JsonObject object = new JsonObject();
        object.addProperty("content_type", "text");
        object.addProperty("data", "/tmp/somefile.txt");
        return object;
    }

    public JsonObject GenerateOutput() {
        JsonObject expectedResponse = new JsonObject();
        expectedResponse.addProperty("message", "your output type was not successfully serializable.");
        return expectedResponse;
    }

    public JsonObject run() throws Exception {

        Handler handler = new Handler<>(algo);
        InputStream fakeIn = new ByteArrayInputStream(request.toString().getBytes());

        System.setIn(fakeIn);
        handler.serve();

        byte[] fifoBytes = Files.readAllBytes(Paths.get(FIFOPIPE));
        String rawData = new String(fifoBytes);
        JsonObject actualResponse = parser.parse(rawData).getAsJsonObject();
        return actualResponse;
    }
}

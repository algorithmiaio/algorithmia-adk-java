package loaders;

import algorithms.FileHandleAbstractAlgorithm;
import com.algorithmia.development.Handler;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

public class ReturnTypeFailure extends AbstractLoader{

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
        prepareInput(request);
        FileInputStream inputStream = new FileInputStream(FIFOPIPE);
        handler.serve();
        byte[] fifoBytes = IOUtils.toByteArray(inputStream);
        String rawData = new String(fifoBytes);
        JsonObject actualResponse = parser.parse(rawData).getAsJsonObject();
        return actualResponse;
    }
}

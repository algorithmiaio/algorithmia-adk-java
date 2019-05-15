package loaders;

import algorithms.BinaryAlgorithm;
import com.algorithmia.algorithm.Handler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Binary {

    private BinaryAlgorithm algo = new BinaryAlgorithm();
    private Gson gson = new Gson();
    private JsonParser parser = new JsonParser();
    private String FIFOPIPE = "/tmp/algoout";
    private JsonObject request = GenerateInput();
    public JsonObject expectedResponse = GenerateOutput();

    public JsonObject GenerateInput() {
        byte[] inputObj = ("This is a test").getBytes();
        JsonObject object = new JsonObject();
        object.addProperty("content_type", "binary");
        object.add("data", gson.toJsonTree(Base64.encodeBase64String(inputObj)));
        return object;
    }

    public JsonObject GenerateOutput() {
        String outputObj = "This is a test";
        JsonObject expectedResponse = new JsonObject();
        JsonObject metadata = new JsonObject();
        metadata.addProperty("content_type", "text");
        expectedResponse.add("metadata", metadata);
        expectedResponse.addProperty("result", outputObj);
        return expectedResponse;
    }


    public JsonObject run() throws Exception {
        Handler handler = new Handler<>(algo.getClass(), algo::foo);
        InputStream fakeIn = new ByteArrayInputStream(request.toString().getBytes());
        System.setIn(fakeIn);
        handler.serve();

        byte[] fifoBytes = Files.readAllBytes(Paths.get(FIFOPIPE));
        String rawData = new String(fifoBytes);
        JsonObject actualResponse = parser.parse(rawData).getAsJsonObject();
        return actualResponse;
    }
}

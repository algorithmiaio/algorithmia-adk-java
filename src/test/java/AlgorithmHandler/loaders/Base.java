package AlgorithmHandler.loaders;

import algorithms.BasicAlgorithm;
import com.algorithmia.development.Handler;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class  Base {
    private BasicAlgorithm algo = new BasicAlgorithm();
    private JsonObject request = GenerateInput();
    public JsonObject expectedResponse = GenerateOutput();
    private JsonParser parser = new JsonParser();
    private String FIFOPIPE = "/tmp/algoout";

    private JsonObject GenerateInput() {
        String inputObj = "james";
        JsonObject object = new JsonObject();
        object.addProperty("content_type", "text");
        object.addProperty("data", inputObj);

        return object;
    }

    private JsonObject GenerateOutput() {
        JsonObject expectedResponse = new JsonObject();
        JsonObject metadata = new JsonObject();
        metadata.addProperty("content_type", "text");
        expectedResponse.add("metadata", metadata);
        expectedResponse.addProperty("result", "Hello james");
        return expectedResponse;
    }

    public JsonObject run() throws Exception {

        Handler handler = new Handler<>(algo.getClass(), algo::Foo);
        String stringified = request.toString();
        InputStream fakeIn = new ByteArrayInputStream(stringified.getBytes());
        System.setIn(fakeIn);
        handler.serve();

        byte[] fifoBytes = Files.readAllBytes(Paths.get(FIFOPIPE));
        String rawData = new String(fifoBytes);
        JsonElement actualResponse = parser.parse(rawData);
        return actualResponse.getAsJsonObject();
    }
}

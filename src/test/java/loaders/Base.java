package loaders;

import algorithms.BasicAbstractAlgorithm;
import com.algorithmia.development.Handler;
import com.google.gson.JsonObject;

import java.util.concurrent.CompletableFuture;

public class  Base extends AbstractLoader{
    private BasicAbstractAlgorithm algo = new BasicAbstractAlgorithm();
    private JsonObject request = GenerateInput();
    public JsonObject expectedResponse = GenerateOutput();

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
        prepareInput(request);
        Handler handler = new Handler<>(algo);
        handler.serve();
        return getOutput();
    }
}
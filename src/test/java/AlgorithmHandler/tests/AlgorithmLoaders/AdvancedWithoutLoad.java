package AlgorithmHandler.tests.AlgorithmLoaders;

import AlgorithmHandler.algorithms.LoadingAlgorithm;
import com.algorithmia.algorithm.Handler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AdvancedWithoutLoad {
    private LoadingAlgorithm algo = new LoadingAlgorithm();
    private Gson gson = new Gson();
    private JsonObject request = GenerateInput();
    public JsonObject expectedResponse = GenerateOutput();
    private JsonParser parser = new JsonParser();
    private String FIFOPIPE = "/tmp/algoout";


    public JsonObject GenerateInput() {
        LoadingAlgorithm.AlgoInput inputObj = algo.new AlgoInput("james", 25);
        JsonObject object = new JsonObject();
        object.addProperty("content_type", "json");
        object.add("data", gson.toJsonTree(inputObj));
        return object;
    }

    public JsonObject GenerateOutput() {
        JsonObject expectedResponse = new JsonObject();
        expectedResponse.addProperty("message", "If using an load function with state, a load function must be defined as well.");
        return expectedResponse;
    }

    public JsonObject run() throws Exception {

        Handler handler = new Handler<>(algo.getClass(), algo::Apply);
        String stringified = request.toString();
        InputStream fakeIn = new ByteArrayInputStream(stringified.getBytes());

        System.setIn(fakeIn);
        handler.serve();

        byte[] fifoBytes = Files.readAllBytes(Paths.get(FIFOPIPE));
        String rawData = new String(fifoBytes);
        JsonObject actualResponse = parser.parse(rawData).getAsJsonObject();
        return actualResponse;
    }
}

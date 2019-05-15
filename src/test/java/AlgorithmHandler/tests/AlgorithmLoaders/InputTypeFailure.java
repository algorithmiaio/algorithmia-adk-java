package AlgorithmHandler.tests.AlgorithmLoaders;

import AlgorithmHandler.algorithms.LoadingAlgorithm;
import AlgorithmHandler.algorithms.MatrixAlgorithm;
import com.algorithmia.algorithm.Handler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class InputTypeFailure {
    private LoadingAlgorithm algo = new LoadingAlgorithm();
    private Gson gson = new Gson();
    private JsonObject request = GenerateInput();
    public JsonObject expectedResponse = GenerateOutput();
    private JsonParser parser = new JsonParser();
    private String FIFOPIPE = "/tmp/algoout";

    public JsonObject GenerateInput() {
        MatrixAlgorithm tmp = new MatrixAlgorithm();
        MatrixAlgorithm.AlgoInput inputObj = tmp.new AlgoInput(new Float[]{0.25f, 0.15f}, new Float[]{0.12f, -0.15f});
        JsonObject object = new JsonObject();
        object.addProperty("content_type", "json");
        object.add("data", gson.toJsonTree(inputObj));
        return object;
    }

    public JsonObject GenerateOutput() {
        JsonObject expectedResponse = new JsonObject();
        expectedResponse.addProperty("message", "Missing required field in JSON input: name");
        return expectedResponse;
    }


    public JsonObject run() throws Exception {

        Handler handler = new Handler<>(algo.getClass(), algo::Apply, algo::DownloadModel);
        InputStream fakeIn = new ByteArrayInputStream(request.toString().getBytes());

        System.setIn(fakeIn);
        handler.serve();

        byte[] fifoBytes = Files.readAllBytes(Paths.get(FIFOPIPE));
        String rawData = new String(fifoBytes);
        JsonObject actualResponse = parser.parse(rawData).getAsJsonObject();
        return actualResponse;

    }
}

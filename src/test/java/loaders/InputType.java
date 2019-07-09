package loaders;

import algorithms.BasicAbstractAlgorithm;
import com.algorithmia.development.Handler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;
import java.io.FileInputStream;

public class InputType extends AbstractLoader{
    private BasicAbstractAlgorithm algo = new BasicAbstractAlgorithm();
    private Gson gson = new Gson();
    private JsonObject request = GenerateInput();
    public JsonObject expectedResponse = GenerateOutput();
    private JsonParser parser = new JsonParser();
    private String FIFOPIPE = "/tmp/algoout";


    public JsonObject GenerateInput() {
        Float[] inputObj = new Float[]{0.25f, 0.15f};
        JsonObject object = new JsonObject();
        object.addProperty("content_type", "json");
        object.add("data", gson.toJsonTree(inputObj));

        return object;
    }

    public JsonObject GenerateOutput() {
        JsonObject expectedResponse = new JsonObject();
        expectedResponse.addProperty("message", "unable to parse input into type java.lang.String , with input [0.25,0.15]");
        expectedResponse.addProperty("error_type", "class java.lang.RuntimeException");
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

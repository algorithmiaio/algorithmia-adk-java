package loaders;

import algorithms.PrimitiveTypeAbstractAlgorithm;
import com.algorithmia.development.Handler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

public class PrimitiveType extends AbstractLoader{
    private PrimitiveTypeAbstractAlgorithm algo = new PrimitiveTypeAbstractAlgorithm();
    private JsonObject request = GenerateInput();
    public JsonObject expectedResponse = GenerateOutput();

    public JsonObject GenerateInput() {
        Float inputObj = 32.5f;
        JsonObject object = new JsonObject();
        object.addProperty("content_type", "json");
        object.add("data", gson.toJsonTree(inputObj));
        return object;
    }

    public JsonObject GenerateOutput() {
        JsonObject expectedResponse = new JsonObject();
        JsonObject metadata = new JsonObject();
        metadata.addProperty("content_type", "text");
        expectedResponse.add("metadata", metadata);
        expectedResponse.addProperty("result", "Hello, the number is 32.5");
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

package loaders;

import algorithms.ThrowsExceptionAbstractAlgorithm;
import com.algorithmia.development.Handler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;


import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

public class ExceptionExample extends AbstractLoader{
    private ThrowsExceptionAbstractAlgorithm algo = new ThrowsExceptionAbstractAlgorithm();
    private Gson gson = new Gson();
    private JsonObject request = GenerateInput();
    public JsonObject expectedResponse = GenerateOutput();


    public JsonObject GenerateInput() {
        String inputObj = "hello world";
        JsonObject object = new JsonObject();
        object.addProperty("content_type", "text");
        object.add("data", gson.toJsonTree(inputObj));
        return object;
    }

    public JsonObject GenerateOutput() {
        JsonObject expectedResponse = new JsonObject();
        expectedResponse.addProperty("message", "This is an exception.");
        return expectedResponse;
    }

    public JsonObject run() throws Exception {
        prepareInput(request);
        Handler handler = new Handler<>(algo);
        FileInputStream inputStream = new FileInputStream(FIFOPIPE);
        handler.serve();
        byte[] fifoBytes = IOUtils.toByteArray(inputStream);
        String rawData = new String(fifoBytes);
        JsonObject actualResponse = parser.parse(rawData).getAsJsonObject();
        return actualResponse;
    }
}

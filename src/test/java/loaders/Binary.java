package loaders;

import algorithms.BinaryAbstractAlgorithm;
import com.algorithmia.development.ADK;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.ReaderInputStream;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

public class Binary extends AbstractLoader{

    private BinaryAbstractAlgorithm algorithm = new BinaryAbstractAlgorithm();
    private Gson gson = new Gson();
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
        byte[] outputObj = ("This is a test").getBytes();
        JsonObject expectedResponse = new JsonObject();
        JsonObject metadata = new JsonObject();
        metadata.addProperty("content_type", "binary");
        expectedResponse.add("metadata", metadata);
        expectedResponse.add("result", gson.toJsonTree(Base64.encodeBase64String(outputObj)));
        return expectedResponse;
    }


    public JsonObject run() throws Exception {
        prepareInput(request);
        ADK algo = new ADK<>(algorithm);
        FileInputStream inputStream = new FileInputStream(FIFOPIPE);
        algo.init();
        byte[] fifoBytes = IOUtils.toByteArray(inputStream);
        String rawData = new String(fifoBytes);
        return parser.parse(rawData).getAsJsonObject();
    }
}

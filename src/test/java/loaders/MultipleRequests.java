package loaders;

import algorithms.BasicAlgorithm;
import com.algorithmia.development.Handler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MultipleRequests {
    private BasicAlgorithm algo = new BasicAlgorithm();
    private Gson gson = new Gson();
    private JsonParser parser = new JsonParser();
    private String FIFOPIPE = "/tmp/algoout";
    private JsonObject request = GenerateInput();
    private JsonObject response = GenerateOutput();
    public String expectedResponse;



    JsonObject GenerateInput(){
            String inputObj = "james";
            JsonObject object = new JsonObject();
            object.addProperty("content_type", "text");
            object.addProperty("data", inputObj);

            return object;
    }

    JsonObject GenerateOutput(){
        JsonObject expectedResponse = new JsonObject();
        JsonObject metadata = new JsonObject();
        metadata.addProperty("content_type", "text");
        expectedResponse.add("metadata", metadata);
        expectedResponse.addProperty("result", "Hello james");
        return expectedResponse;
    }

    String DuplicateRequests(String request, int times){
        String output = request;
        for(int i=0; i< times; i++){
            output = output.concat("\n").concat(request);
        }
        return output.concat("\n");
    }
    public String run() throws Exception {

        Handler handler = new Handler<>(algo);
        String stringified = request.toString();
        String duplicatedInput = DuplicateRequests(stringified, 5);
        this.expectedResponse = DuplicateRequests(this.response.toString(), 5);
        InputStream fakeIn = new ByteArrayInputStream(duplicatedInput.getBytes());
        System.setIn(fakeIn);
        handler.serve();

        byte[] fifoBytes = Files.readAllBytes(Paths.get(FIFOPIPE));
        String rawData = new String(fifoBytes);
        return rawData;
    }
}

package loaders;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;

public class AbstractLoader {
    JsonParser parser = new JsonParser();
    String FIFOPIPE = "/tmp/algoout";
    Gson gson = new Gson();


    void prepareInput(JsonObject input){
        String stringified = input.toString();
        InputStream fakeIn = new ByteArrayInputStream(stringified.getBytes());
        System.setIn(fakeIn);
    }

}

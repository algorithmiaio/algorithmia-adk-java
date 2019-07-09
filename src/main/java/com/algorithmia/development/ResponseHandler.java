package com.algorithmia.development;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

final class ResponseHandler {

    private String FIFOPATH = "/tmp/algoout";



    private void write(String serializedData){
        try(PrintStream stream = new PrintStream(new FileOutputStream(this.FIFOPATH, true))){
            stream.println(serializedData);
            stream.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    <OUTPUT> void writeToPipe(OUTPUT outputObject) {
        Response response = new Response(outputObject);
        String serialized = response.getJsonOutput();
        write(serialized);
    }

    <ERRORTYPE extends RuntimeException> void writeErrorToPipe(ERRORTYPE e) {
        SerializableException<ERRORTYPE> exception = new SerializableException<>(e);
        String serialized = exception.getJsonOutput();
        write(serialized);
    }
}

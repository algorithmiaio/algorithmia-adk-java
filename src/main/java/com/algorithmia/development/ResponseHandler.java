package com.algorithmia.development;

import java.io.*;

final class ResponseHandler {

    private String FIFOPATH = "/tmp/algoout";



    PrintStream preparePipe(){
            try {
                FileOutputStream stream  = new FileOutputStream(this.FIFOPATH, true);
                return new PrintStream(stream, true);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
    }

    <OUTPUT> void writeToPipe(OUTPUT outputObject) {
        Response response = new Response(outputObject);
        String serialized = response.getJsonOutput();
        PrintStream output = preparePipe();
        output.println(serialized);
        output.flush();
    }

    <ERRORTYPE extends RuntimeException> void writeErrorToPipe(ERRORTYPE e) {
        SerializableException<ERRORTYPE> exception = new SerializableException<>(e);
        String serialized = exception.getJsonOutput();
        PrintStream output = preparePipe();
        output.println(serialized);
        output.flush();
    }
}

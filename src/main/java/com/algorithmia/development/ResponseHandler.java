package com.algorithmia.development;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

final class ResponseHandler {
    private String FIFOPATH;

    public ResponseHandler(String fifoPath) {
        this.FIFOPATH = fifoPath;
    }

    private void _writeRemote(String serializedData) {
        try{
        PrintStream stream = new PrintStream(new FileOutputStream(this.FIFOPATH, true));
        stream.println(serializedData);
        stream.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void _writeLocal(String serializedData) {
        PrintStream stream = new PrintStream(System.out);
        stream.println(serializedData);
        stream.flush();
    }

    <OUTPUT> void writeToPipe(OUTPUT outputObject, Boolean isLocal) {
        Response response = new Response(outputObject);
        String serialized = response.getJsonOutput();
        if (isLocal) {
            _writeLocal(serialized);
        } else {
            _writeRemote(serialized);
        }
    }

    <ERRORTYPE extends RuntimeException> void writeErrorToPipe(ERRORTYPE e, Boolean isLocal) {
        SerializableException<ERRORTYPE> exception = new SerializableException<>(e);
        String serialized = exception.getJsonOutput();
        if (isLocal) {
            _writeLocal(serialized);
        } else {
            _writeRemote(serialized);
        }
    }
}

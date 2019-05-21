package com.algorithmia.development;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.stream.Stream;


public class Handler<INPUT, OUTPUT> {

    private AlgorithmInterface<INPUT, OUTPUT> implementation;
    private RequestHandler<INPUT> in;
    private ResponseHandler out = new ResponseHandler();


    public Handler(AlgorithmInterface<INPUT, OUTPUT> implementation) {
        this.implementation = implementation;
        Class<INPUT> inputClass = getInputClass(implementation);
        this.in = new RequestHandler<>(inputClass);
    }

    private void load() {
            implementation.load();
            System.out.println("PIPE_INIT_COMPLETE");
            System.out.flush();
        }


    private void execute() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Stream<String> buffer = reader.lines();
        load();
        buffer.forEach((line) -> {
            INPUT input = in.processRequest(line);
            OUTPUT output = implementation.apply(input);
            out.writeToPipe(output);
        });
    }

    private Class<INPUT> getInputClass(AlgorithmInterface<INPUT, OUTPUT> algoClass) {
        Method[] methods = algoClass.getClass().getMethods();
        for (Method method : methods) {
            if (method.getName().equals(this.implementation.getClass().getMethods()[0].getName())
                    && method.getReturnType() != Object.class){
                Class<?>[] parameters = method.getParameterTypes();
                return (Class<INPUT>) parameters[0];
            }
        }
        throw new RuntimeException("Unable to find the 'public' method reference called " + "'apply'" + " in the provided class.");
    }

    public void serve() {
        try {
            execute();
        } catch (RuntimeException e) {
            out.writeErrorToPipe(e);
        }
    }

}

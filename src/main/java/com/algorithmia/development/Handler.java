package com.algorithmia.development;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.stream.Stream;
import org.springframework.core.annotation.AnnotationUtils;


public class Handler<INPUT, OUTPUT> {

    private AbstractAlgorithm<INPUT, OUTPUT> implementation;
    private RequestHandler<INPUT> in;
    private String FIFO_PATH = "/tmp/algoout";
    private ResponseHandler out = new ResponseHandler(FIFO_PATH);


    public Handler(AbstractAlgorithm<INPUT, OUTPUT> implementation) {
        this.implementation = implementation;
        Class<INPUT> inputClass = getInputClass(implementation);
        this.in = new RequestHandler<>(inputClass);
    }

    private void load() {
            implementation.load();
            System.out.println("PIPE_INIT_COMPLETE");
            System.out.flush();
        }
    private Class<INPUT> getInputClass(AbstractAlgorithm<INPUT, OUTPUT> algoClass) {
        Method[] methods = algoClass.getClass().getMethods();
        Class<INPUT> bestGuess = null;
        for (Method method : methods) {
            if (AnnotationUtils.findAnnotation(method, FindApply.class) != null) {
                bestGuess = (Class<INPUT>) method.getParameterTypes()[0];
                if (method.getReturnType() != Object.class) {
                    return bestGuess;
                }
            }
        }
        if (bestGuess != null) {
            return bestGuess;
        } else {
            throw new RuntimeException("Unable to find the 'public' method reference called " + "'apply'" + " in the provided class.");
        }
    }


    public void serve() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            Stream<String> buffer = reader.lines();
            try {
                load();
            } catch (RuntimeException e) {
                out.writeErrorToPipe(e, false);
            }

            buffer.forEach((line) -> {
                try {
                    INPUT input = in.processRequest(line);
                    OUTPUT output = implementation.apply(input);
                    out.writeToPipe(output, false);
                } catch (RuntimeException e) {
                    out.writeErrorToPipe(e, false);
                }
            });
        } catch (IOException ex){
            throw new RuntimeException(ex);
        }
    }

}

package com.algorithmia.development;

import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.stream.Stream;
import org.springframework.core.annotation.AnnotationUtils;


public class ADK<INPUT, OUTPUT> {

    private AbstractAlgorithm<INPUT, OUTPUT> implementation;
    private RequestHandler<INPUT> in;
    private String FIFO_PATH = "/tmp/algoout";
    private ResponseHandler out = new ResponseHandler(FIFO_PATH);
    private RuntimeException loadingException;
    private Boolean is_local;

    public ADK(AbstractAlgorithm<INPUT, OUTPUT> implementation) {
        this.implementation = implementation;
        if (new File(FIFO_PATH).exists()) {
            this.is_local = false;
        } else {
            this.is_local = true;
        };
        Class<INPUT> inputClass = getInputClass(implementation);
        this.in = new RequestHandler<>(inputClass);
    }

    private void load() {
        try {
            implementation.load();
            System.out.println("PIPE_INIT_COMPLETE");
            System.out.flush();
        } catch (RuntimeException e) {
            this.loadingException = e;
        }
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


    public void init() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            Stream<String> buffer = reader.lines();
            load();
            buffer.forEach((line) -> {
                if (this.loadingException != null) {
                    out.writeErrorToPipe(this.loadingException, false);
                } else{
                    try {
                        INPUT input = in.processRequest(line);
                        OUTPUT output = implementation.apply(input);
                        out.writeToPipe(output, false);
                    } catch (RuntimeException e) {
                        out.writeErrorToPipe(e, false);
                    }
                }
            });
        } catch (IOException ex){
            throw new RuntimeException(ex);
        }
    }

    public void init(INPUT payload) {
            load();
        if (is_local) {
            try {
                OUTPUT output = implementation.apply(payload);
                out.writeToPipe(output, true);
            } catch (RuntimeException e) {
                out.writeErrorToPipe(e, true);
            }
        } else {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                Stream<String> buffer = reader.lines();

                buffer.forEach((line) -> {
                    if (this.loadingException != null) {
                        out.writeErrorToPipe(this.loadingException, false);
                    } else{
                        try {
                            INPUT input = in.processRequest(line);
                            OUTPUT output = implementation.apply(input);
                            out.writeToPipe(output, false);
                        } catch (RuntimeException e) {
                            out.writeErrorToPipe(e, false);
                        }
                    }
                });
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

}

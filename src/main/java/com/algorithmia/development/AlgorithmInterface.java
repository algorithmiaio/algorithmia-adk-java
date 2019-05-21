package com.algorithmia.development;

public interface AlgorithmInterface<INPUT, OUTPUT> {
    OUTPUT apply(INPUT input);
    default void load(){return;}
}

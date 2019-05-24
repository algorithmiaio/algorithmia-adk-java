package com.algorithmia.development;

public abstract class AbstractAlgorithm<INPUT, OUTPUT>{
    @FindApply
    public abstract OUTPUT apply(INPUT input);
    public void load(){}
}


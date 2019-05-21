package structures;

import com.algorithmia.development.Required;

public class LoadingInput {
    @Required
    public String name;
    @Required
    public Integer age;

    public LoadingInput(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
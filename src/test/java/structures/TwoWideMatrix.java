package structures;

import com.algorithmia.development.Required;

import java.util.Arrays;

public class TwoWideMatrix {
    @Required
    public Float[] top;
    @Required
    public Float[] bottom;
    @Required
    int width;

    public Boolean dimCheck() {
        return top.length == bottom.length;
    }

    TwoWideMatrix(int size) {
        top = new Float[size];
        bottom = new Float[size];
        Arrays.fill(top, 0f);
        Arrays.fill(bottom, 0f);
        width = size;
    }

    void setTop(Float[] arr) {
        top = arr;
        dimCheck();
    }

    void setBottom(Float[] arr) {
        bottom = arr;
        dimCheck();
    }

    public int getWidth() {
        return width;
    }
}
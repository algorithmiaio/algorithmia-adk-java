package AlgorithmHandler;
import AlgorithmHandler.tests.AlgorithmLoaders.Base;
import AlgorithmHandler.tests.AlgorithmLoaders.Binary;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.*;

import java.io.File;
import java.io.IOException;

@FixMethodOrder()
public class algorithmTests {
    protected String FIFOPIPE = "/tmp/algoout";

    protected JsonParser parser = new JsonParser();

    @Before
    public void IntializePipe() throws IOException, InterruptedException {
        Process p = Runtime.getRuntime().exec("touch  " + FIFOPIPE);
        p.waitFor();
    }

    @After
    public void TeardownPipe() {
        File pipe = new File(FIFOPIPE);
        System.setIn(System.in);
        pipe.delete();
    }


    @Test
    public void BaseTest() throws Exception {
        Base base = new Base();
        JsonObject result = base.run();
        Assert.assertEquals(result, base.expectedResponse);

    }


    @Test
    public void BinaryTest() throws Exception{
        Binary binary = new Binary();
        JsonObject result = binary.run();
        Assert.assertEquals(result, binary.expectedResponse);
    }


}

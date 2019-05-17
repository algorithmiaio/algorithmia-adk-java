package AlgorithmHandler;
import AlgorithmHandler.loaders.*;
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

    @Test
    public void AdvancedAlgorithmWithoutLoadingFailureTest() throws Exception{
        AdvancedWithoutLoad algo = new AdvancedWithoutLoad();
        JsonObject result = algo.run();
        Assert.assertEquals(result.get("message"), algo.expectedResponse.get("message"));
    }

    @Test
    public void algorithmFailureTest() throws Exception{
        ExceptionExample algo = new ExceptionExample();
        JsonObject result = algo.run();
        Assert.assertEquals(result.get("message"), algo.expectedResponse.get("message"));
    }

    @Test
    public void ComplexTypeTest() throws Exception{
        ComplexType algo = new ComplexType();
        JsonObject result = algo.run();
        Assert.assertEquals(result, algo.expectedResponse);
    }

    @Test
    public void InputTypeFailureTest() throws Exception{
        InputTypeFailure algo = new InputTypeFailure();
        JsonObject result = algo.run();
        Assert.assertEquals(result.get("message"), algo.expectedResponse.get("message"));
    }

    @Test
    public void ReturnTypeFailureTest() throws Exception{
        ReturnTypeFailure algo = new ReturnTypeFailure();
        JsonObject result = algo.run();
        Assert.assertEquals(result.get("message"), algo.expectedResponse.get("message"));
    }

    @Test
    public void MultipleRequestsTest() throws Exception{
        MultipleRequests algo = new MultipleRequests();
        String result = algo.run();
        Assert.assertEquals(result, algo.expectedResponse);
    }


}

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

public class GraphTest {
    DotGraph testGraph;
    DotGraph faultGraph;
    String expFile;
    String result;

    @Before
    public void setup() throws IOException {
        System.out.println("in setup");
        //Arrange
        testGraph = new DotGraph();
        faultGraph = new DotGraph();
    }

    @Test
    public void testOutputDotGraph() throws IOException {
        System.out.println("in outputDotGraph");
        testGraph.parseGraph("test/test.dot");

        //Act
        testGraph.outputDOTGraph("test/output.dot");
        expFile = Files.readString(Paths.get("test/output.dot"));


        //Assert
        Assert.assertNotNull(expFile);

    }


    @Test
    public void testChangedOutputDotGraph() throws IOException {
        System.out.println("in outputDotGraph");
        testGraph.parseGraph("test/MissingVertexTest.dot");

        //Act
        testGraph.outputDOTGraph("test/output.dot");
        expFile = Files.readString(Paths.get("test/output.dot"));


        //Assert
        Assert.assertNotNull(expFile);

    }

    @Test
    public void testOutputGraphics() {
        System.out.println("in outputGraphics");
        testGraph.parseGraph("test/test.dot");

        //Act
        result = testGraph.outputGraphics("test/output1", "png");

        //Assert
        Assert.assertEquals("Graphic Successfully Made", result);
    }

    @Test
    public void testChangedOutputGraphics() {
        System.out.println("in outputGraphics");
        testGraph.parseGraph("test/MissingVertexTest.dot");

        //Act
        result = testGraph.outputGraphics("test/output2", "png");

        //Assert
        Assert.assertEquals("Graphic Successfully Made", result);

    }


    @After
    public void teardown(){
        System.out.println("teardown");
    }

}

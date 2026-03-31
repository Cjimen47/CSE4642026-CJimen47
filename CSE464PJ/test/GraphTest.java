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
        testGraph.parseGraph("CSE464PJ/test/test.dot");

        //Act
        testGraph.outputDOTGraph("CSE464PJ/test/output.dot");
        expFile = Files.readString(Paths.get("CSE464PJ/test/output.dot"));


        //Assert
        Assert.assertNotNull(expFile);

    }



    @After
    public void teardown(){
        System.out.println("teardown");
    }

}

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

    @Before
    public void setup() throws IOException {
        System.out.println("in setup");
        //Arrange
        testGraph = new DotGraph();
        testGraph.parseGraph("test/test.dot");

        faultGraph = new DotGraph();
        testGraph.parseGraph("test/test.dot");
    }

    @Test
    public void testAddEdge(){
        System.out.println("in AddEdge");

        //Act
        String result = testGraph.addEdge("a", "c");

        //Assert
        Assert.assertEquals("Source a and Destination c have been successfully added as an edge.", result);
    }

    @Test
    public void testAddDuplicateEdge(){
        System.out.println("in AddEdge");

        //Act
        String result = testGraph.addEdge("a", "b");

        //Assert
        Assert.assertEquals("Source a and Destination b already exists as an edge.", result);
    }

    @Test
    public void testAddInvalidEdge(){
        System.out.println("in AddEdge");

        //Act
        String result = testGraph.addEdge("w", "b");

        //Assert
        Assert.assertEquals("Source a and Destination b already exists as an edge.", result);
    }

    @After
    public void teardown(){
        System.out.println("teardown");
    }

}

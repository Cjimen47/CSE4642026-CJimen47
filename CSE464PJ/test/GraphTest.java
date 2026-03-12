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
    DotGraph expected;


    @Before
    public void setup() throws IOException {
        System.out.println("in setup");
        //Arrange
        testGraph = new DotGraph();
        testGraph.parseGraph("test/test.dot");

        expected = new DotGraph();
        expected.parseGraph("test/test.dot");

    }

    @Test
    public void testAddNode(){
        System.out.println("in addNode");

        //Act
        testGraph.addNode("w");


        //Assert
        Assert.assertNotEquals(expected.dotGraph.vertexSet().toString(), testGraph.dotGraph.vertexSet().toString());
    }

    @Test
    public void testAddDuplicateNode(){
        System.out.println("in addNode");

        //Act
        testGraph.addNode("a");
        testGraph.addNode("a");
        testGraph.addNode("a");
        testGraph.addNode("a");
        testGraph.addNode("a");


        //Assert
        Assert.assertEquals(expected.dotGraph.vertexSet().toString(), testGraph.dotGraph.vertexSet().toString());
    }

    @Test
    public void testAddNodes(){
        System.out.println("in addNode");

        //Arrange
        String [] nodes = {"i","j","k","l"};
        expected.addNode("i");
        expected.addNode("j");
        expected.addNode("k");
        expected.addNode("l");

        //Act
        testGraph.addNodes(nodes);

        //Assert
        Assert.assertEquals(expected.dotGraph.vertexSet().toString(), testGraph.dotGraph.vertexSet().toString());
    }

    @Test
    public void testAddDuplicateNodes(){
        System.out.println("in addNode");

        //Arrange
        String [] nodes = {"i","i","i","i","i","i","i","j","k","l"};
        expected.addNode("i");
        expected.addNode("j");
        expected.addNode("k");
        expected.addNode("l");

        //Act
        testGraph.addNodes(nodes);

        //Assert
        Assert.assertEquals(expected.dotGraph.vertexSet().toString(), testGraph.dotGraph.vertexSet().toString());
    }

    @Test
    public void testAddNoNodes(){
        System.out.println("in addNode");

        //Arrange
        String [] nodes = {};

        //Act
        testGraph.addNodes(nodes);

        //Assert
        Assert.assertEquals(expected.dotGraph.vertexSet().toString(), testGraph.dotGraph.vertexSet().toString());
    }

    @After
    public void teardown(){
        System.out.println("teardown");
    }

}

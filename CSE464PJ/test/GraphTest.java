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
    Graph<String, DefaultEdge> expected;
    String xString;
    String expFile;

    @Before
    public void setup() throws IOException {
        System.out.println("in setup");
        //Arrange
        testGraph = new DotGraph();
        faultGraph = new DotGraph();

        expected = new DefaultDirectedGraph<>(DefaultEdge.class);
        expected.addVertex("a");
        expected.addVertex("b");
        expected.addVertex("c");
        expected.addVertex("d");
        expected.addVertex("e");
        expected.addVertex("f");
        expected.addVertex("g");
        expected.addVertex("h");

        expected.addEdge("a","b");
        expected.addEdge("b","c");
        expected.addEdge("c","d");
        expected.addEdge("d","a");
        expected.addEdge("a","e");
        expected.addEdge("e","f");
        expected.addEdge("e","g");
        expected.addEdge("f","h");
        expected.addEdge("g","h");

        xString ="Number of Nodes: 8\n" +
                "Label of Nodes: [a, b, c, d, e, f, g, h]\n" +
                "Number of Edges: 9\n" +
                "Nodes and Edge Direction of Edges: {\n" +
                "a->b;\n" +
                "b->c;\n" +
                "c->d;\n" +
                "d->a;\n" +
                "a->e;\n" +
                "e->f;\n" +
                "e->g;\n" +
                "f->h;\n" +
                "g->h;\n" +
                "}";

        expFile = Files.readString(Paths.get("test/expected.txt"));
    }

    @Test
    public void testParseGraph(){
        System.out.println("in parseGraph");

        //Act
        testGraph.parseGraph("test/test.dot");

        //Assert
        Assert.assertEquals(expected.vertexSet().toString(), testGraph.dotGraph.vertexSet().toString());
        Assert.assertEquals(expected.edgeSet().toString(), testGraph.dotGraph.edgeSet().toString());
    }

    @Test
    public void testInvalidEdges(){
        System.out.println("in InvalidEdges");

        //Act
        testGraph.parseGraph("test/test.dot");
        faultGraph.parseGraph("test/MissingVertexTest.dot");

        System.out.println(testGraph.toString());
        System.out.println(faultGraph.toString());


        //Assert
        Assert.assertEquals(testGraph.dotGraph.edgeSet().toString(), faultGraph.dotGraph.edgeSet().toString());
    }

    @Test
    public void testDuplicateEdges(){
        System.out.println("in DuplicateEdges");

        //Act
        testGraph.parseGraph("test/test.dot");
        faultGraph.parseGraph("test/DuplicateEdges.dot");

        //Assert
        Assert.assertEquals(testGraph.dotGraph.edgeSet().toString(), faultGraph.dotGraph.edgeSet().toString());
    }


    @Test
    public void testToString(){
        System.out.println("in toString");

        //Act
        testGraph.parseGraph("test/test.dot");

        //Assert
        Assert.assertEquals(xString, testGraph.toString());
    }

    @Test
    public void testOutputGraph() throws IOException {
        System.out.println("in outputGraph");

        //Arrange
        testGraph.parseGraph("test/test.dot");

        //Act
        testGraph.outputGraph("test/test.txt");
        String output = Files.readString(Paths.get("test/test.txt"));

        //Assert
        Assert.assertEquals(expFile, output);
    }

    @After
    public void teardown(){
        System.out.println("teardown");
    }

}

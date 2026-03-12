import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

public class GraphTest {
    DotGraph testGraph;
    Graph<String, DefaultEdge> expected;
    String xString;

    @Before
    public void setup(){
        System.out.println("in setup");
        //Arrange
        testGraph = new DotGraph();

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

        xString ="Number of Nodes: 8 \n Label of Nodes: ";

    }

    @Test
    public void testParseGraph(){
        System.out.println("in parseGraph");

        //Act
        testGraph.parseGraph("/Users/coraljimenez-gudino/IdeaProjects/CSE4642026-CJimen47/CSE464PJ/test/test.dot");

        //Assert
        Assert.assertEquals(expected.vertexSet().toString(), testGraph.dotGraph.vertexSet().toString());
        Assert.assertEquals(expected.edgeSet().toString(), testGraph.dotGraph.edgeSet().toString());
    }

}

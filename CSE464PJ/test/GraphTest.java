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
        testGraph.parseGraph("CSE464PJ/test/test.dot");
    }

    @Test
    public void bsf() throws IOException {
        System.out.println("in bsf");
        //Arrange
        Path path = testGraph.GraphSearch("a","d");
    }

    @Test
    public void testRemoveNode() throws Exception {
        System.out.println("in removeNode");

        //Act
        try {
            testGraph.removeNode("d");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        //Assert
        Assert.assertEquals("Number of Nodes: 7\n" +
                "Label of Nodes: [a, b, c, e, f, g, h]\n" +
                "Number of Edges: 7\n" +
                "Nodes and Edge Direction of Edges: {\n" +
                "a->b;\n" +
                "b->c;\n" +
                "a->e;\n" +
                "e->f;\n" +
                "e->g;\n" +
                "f->h;\n" +
                "g->h;\n" +
                "}", testGraph.toString());
    }

    @Test
    public void testRemoveNodes() throws Exception {
        System.out.println("in removeNodes");
        String[] nodes = {"f", "h", "g"};

        //Act
        try {
            testGraph.removeNodes(nodes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //Assert
        Assert.assertEquals("Number of Nodes: 5\n" +
                "Label of Nodes: [a, b, c, d, e]\n" +
                "Number of Edges: 5\n" +
                "Nodes and Edge Direction of Edges: {\n" +
                "a->b;\n" +
                "b->c;\n" +
                "c->d;\n" +
                "d->a;\n" +
                "a->e;\n" +
                "}", testGraph.toString());

    }

    @Test
    public void testRemoveEdge() throws Exception {
        System.out.println("in removeEdge");

        try {
            testGraph.removeEdge("c","d");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //Assert
        Assert.assertEquals("Number of Nodes: 8\n" +
                "Label of Nodes: [a, b, c, d, e, f, g, h]\n" +
                "Number of Edges: 8\n" +
                "Nodes and Edge Direction of Edges: {\n" +
                "a->b;\n" +
                "b->c;\n" +
                "d->a;\n" +
                "a->e;\n" +
                "e->f;\n" +
                "e->g;\n" +
                "f->h;\n" +
                "g->h;\n" +
                "}", testGraph.toString());
    }

    @Test
    public void mixedSuccessfulRemove() throws Exception {
        System.out.println("in mixedSuccessfulRemove");

        //Act
            testGraph.removeNode("d");
            testGraph.removeEdge("f","h");
            testGraph.removeNode("c");
            testGraph.removeEdge("g","h");


        //Assert
        Assert.assertEquals("Number of Nodes: 6\n" +
                "Label of Nodes: [a, b, e, f, g, h]\n" +
                "Number of Edges: 4\n" +
                "Nodes and Edge Direction of Edges: {\n" +
                "a->b;\n" +
                "a->e;\n" +
                "e->f;\n" +
                "e->g;\n" +
                "}", testGraph.toString());
    }

    @Test
    public void nodeUnsuccessfulRemove() throws Exception {
        System.out.println("in nodeUnsuccessfulRemove");


        //Act
            testGraph.removeNode("c");
            testGraph.removeNode("q");
            testGraph.removeNode("d");
            testGraph.removeNode("c");


            //Assert
            Assert.assertEquals("Number of Nodes: 6\n" +
                    "Label of Nodes: [a, b, e, f, g, h]\n" +
                    "Number of Edges: 6\n" +
                    "Nodes and Edge Direction of Edges: {\n" +
                    "a->b;\n" +
                    "a->e;\n" +
                    "e->f;\n" +
                    "e->g;\n" +
                    "f->h;\n" +
                    "g->h;\n" +
                    "}", testGraph.toString());



    }
    @Test
    public void nodesUnsuccessfulRemove() throws Exception {
        System.out.println("in nodesUnsuccessfulRemove");
        String [] nodes = {"c", "q", "d", "c"};

        //Act
        testGraph.removeNodes(nodes);

        //Assert
        Assert.assertEquals("Number of Nodes: 6\n" +
                "Label of Nodes: [a, b, e, f, g, h]\n" +
                "Number of Edges: 6\n" +
                "Nodes and Edge Direction of Edges: {\n" +
                "a->b;\n" +
                "a->e;\n" +
                "e->f;\n" +
                "e->g;\n" +
                "f->h;\n" +
                "g->h;\n" +
                "}", testGraph.toString());



    }

    @Test
    public void edgeUnsuccessfulRemove() throws Exception {
        System.out.println("in edgeUnsuccessfulRemove");

        //Act
        try {
            testGraph.removeEdge("b", "c");
            testGraph.removeEdge("a", "c");
            testGraph.removeEdge("c", "c");
            testGraph.removeEdge("g", "h");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            //Assert
            Assert.assertEquals("Number of Nodes: 8\n" +
                    "Label of Nodes: [a, b, c, d, e, f, g, h]\n" +
                    "Number of Edges: 7\n" +
                    "Nodes and Edge Direction of Edges: {\n" +
                    "a->b;\n" +
                    "c->d;\n" +
                    "d->a;\n" +
                    "a->e;\n" +
                    "e->f;\n" +
                    "e->g;\n" +
                    "f->h;\n" +
                    "}", testGraph.toString());

        }

    }

    @After
    public void teardown(){
        System.out.println("teardown\n");
    }

}

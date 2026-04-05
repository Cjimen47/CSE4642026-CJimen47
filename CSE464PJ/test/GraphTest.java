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
    public void rootDfs() throws IOException {
        System.out.println("in rootDfs");
        //Arrange
        Path path = testGraph.GraphSearch("a","h");

        Assert.assertEquals("a->b->c->d->e->f->h",path.toString());

    }

    @Test
    public void leafDfs() throws IOException {
        System.out.println("in leafDfs");
        //Arrange
        Path path = testGraph.GraphSearch("d","h");


        Assert.assertEquals("d->a->b->c->e->f->h",path.toString());

    }

    @Test
    public void noPathDfs() throws IOException {
        System.out.println("in noPathDfs");
        //Arrange
        Path path = testGraph.GraphSearch("h","a");

        Assert.assertEquals("h",path.toString());
    }

    @Test
    public void nonexistentNodeDfs() throws IOException {
        System.out.println("in nonexistentNodeDfs");
        //Arrange
        Path path = testGraph.GraphSearch("b","q");

        Assert.assertEquals("",path.toString());
    }

    @Test
    public void sameNodeDfs() throws IOException {
        System.out.println("in sameNodedfs");
        //Arrange
        Path path = testGraph.GraphSearch("b","b");

        Assert.assertEquals("b",path.toString());
    }


    @After
    public void teardown(){
        System.out.println("teardown\n");
    }

}

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
    public void rootBsf() throws IOException {
        System.out.println("in rootBsf");
        //Arrange
        Path path = testGraph.GraphSearch("a","h");

        Assert.assertEquals("a->b->e->c->f->g->d->h",path.toString());

    }

    @Test
    public void leafBsf() throws IOException {
        System.out.println("in leafBsf");
        //Arrange
        Path path = testGraph.GraphSearch("d","h");

        Assert.assertEquals("d->a->b->e->c->f->g->h",path.toString());

    }

    @Test
    public void noPathBsf() throws IOException {
        System.out.println("in noPathBsf");
        //Arrange
        Path path = testGraph.GraphSearch("h","a");

        Assert.assertEquals("h",path.toString());
    }

    @Test
    public void nonexistentNodeBsf() throws IOException {
        System.out.println("in nonexistentNodeBsf");
        //Arrange
        Path path = testGraph.GraphSearch("b","q");

        Assert.assertEquals("",path.toString());
    }

    @Test
    public void sameNodeBsf() throws IOException {
        System.out.println("in sameNodeBsf");
        //Arrange
        Path path = testGraph.GraphSearch("b","b");

        Assert.assertEquals("b",path.toString());
    }


    @After
    public void teardown(){
        System.out.println("teardown\n");
    }

}

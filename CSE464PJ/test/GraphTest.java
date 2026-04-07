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
    public void rootGraphSearch() throws IOException {
        System.out.println("in rootGraphSearch");
        //Arrange
        Path bfsPath = testGraph.GraphSearch("a","h", DotGraph.Algorithm.BFS);
        System.out.println("This is the node path before: " + testGraph.nodePath.toString());
        Assert.assertEquals("a->b->e->c->f->g->d->h",bfsPath.toString());

        testGraph.nodePath.nodePath.clear();

        System.out.println("This is the node path after: " + testGraph.nodePath.toString());
        Path dfsPath = testGraph.GraphSearch("a","h", DotGraph.Algorithm.DFS);
        Assert.assertEquals("a->b->c->d->e->f->h",dfsPath.toString());

    }

    @Test
    public void leafGraphSearch() throws IOException {
        System.out.println("in leafGraphSearch");

        //Arrange
        Path bfsPath = testGraph.GraphSearch("d","h", DotGraph.Algorithm.BFS);
        Assert.assertEquals("d->a->b->e->c->f->g->h",bfsPath.toString());

        testGraph.nodePath.nodePath.clear();

        Path dfsPath = testGraph.GraphSearch("d","h", DotGraph.Algorithm.DFS);
        Assert.assertEquals("d->a->b->c->e->f->h",dfsPath.toString());

    }

    @Test
    public void noPathDfs() throws IOException {
        System.out.println("in noPathDfs");
        //Arrange
        Path path = testGraph.GraphSearch("h","a", DotGraph.Algorithm.DFS);

        Assert.assertNull(path);
    }

    @Test
    public void nonexistentNodeDfs() throws IOException {
        System.out.println("in nonexistentNodeDfs");
        //Arrange
        Path path = testGraph.GraphSearch("b","q", DotGraph.Algorithm.DFS);

        Assert.assertNull(path);
    }

    @Test
    public void sameNodeDfs() throws IOException {
        System.out.println("in sameNodedfs");
        //Arrange
        Path path = testGraph.GraphSearch("b","b", DotGraph.Algorithm.DFS);

        Assert.assertNull(path);
    }


    @After
    public void teardown(){
        System.out.println("teardown\n");
    }

}

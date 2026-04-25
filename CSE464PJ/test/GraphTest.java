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
        DotGraph.BFS bfs = testGraph.new BFS("a","h");
        Path result = bfs.Search();
        Assert.assertEquals("a->b->e->c->f->g->d->h",result.toString());

        //System.out.println("Performing 2nd test: ");
        testGraph.nodePath.nodePath.clear();


        DotGraph.GraphsSearch dfs = testGraph.new DFS("a", "h");
        result = dfs.Search();
        Assert.assertEquals("a->b->c->d->e->f->h",result.toString());

    }


    @Test
    public void leafGraphSearch() throws IOException {
        System.out.println("in leafGraphSearch");

        //Arrange
        DotGraph.BFS bfs = testGraph.new BFS("d","h");
        Path result = bfs.Search();
        Assert.assertEquals("d->a->b->e->c->f->g->h",result.toString());

        //System.out.println("Performing 2nd test: ");
        testGraph.nodePath.nodePath.clear();


        DotGraph.GraphsSearch dfs = testGraph.new DFS("d", "h");
        result = dfs.Search();
        Assert.assertEquals("d->a->b->c->e->f->h",result.toString());

    }


    @Test
    public void noPathDfs() throws IOException {
        System.out.println("in noPathDfs");
        DotGraph.BFS bfs = testGraph.new BFS("h","a");
        Path result = bfs.Search();
        Assert.assertNull(result);

        //System.out.println("Performing 2nd test: ");

        DotGraph.GraphsSearch dfs = testGraph.new DFS("h", "a");
        result = dfs.Search();
        Assert.assertNull(result);

    }

    @Test
    public void nonexistentNodeDfs() throws IOException {
        System.out.println("in nonexistentNodeDfs");

        //Arrange
        DotGraph.BFS bfs = testGraph.new BFS("b","q");
        Path result = bfs.Search();
        Assert.assertNull(result);

        //System.out.println("Performing 2nd test: ");

        DotGraph.GraphsSearch dfs = testGraph.new DFS("b", "q");
        result = dfs.Search();
        Assert.assertNull(result);
    }

    @Test
    public void sameNodeDfs() throws IOException {
        System.out.println("in sameNodedfs");
        //Arrange
        DotGraph.BFS bfs = testGraph.new BFS("b","b");
        Path result = bfs.Search();
        Assert.assertNull(result);

        //System.out.println("Performing 2nd test: ");

        DotGraph.GraphsSearch dfs = testGraph.new DFS("b", "b");
        result = dfs.Search();
        Assert.assertNull(result);
    }


    @After
    public void teardown(){
        System.out.println("teardown\n");
    }

}

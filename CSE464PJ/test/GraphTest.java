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
        //DotGraph.GraphSearch bfs = testGraph.new BFS("a","h");
        DotGraph.SearchContext bfsContext = testGraph.new SearchContext(testGraph.new BFS("a","h"));
        Path result = bfsContext.performSearch();
        Assert.assertEquals("a->b->e->c->f->g->d->h",result.toString());

        //System.out.println("Performing 2nd test: ");
        testGraph.nodePath.nodePath.clear();


        DotGraph.SearchContext dfsContext = testGraph.new SearchContext(testGraph.new DFS("a","h"));
        result = dfsContext.performSearch();
        Assert.assertEquals("a->b->c->d->e->f->h",result.toString());

        DotGraph.SearchContext rndContext = testGraph.new SearchContext(testGraph.new RND("a","h"));
        result = rndContext.performSearch();

    }


    @Test
    public void leafGraphSearch() throws IOException {
        System.out.println("in leafGraphSearch");

        //Arrange
        DotGraph.SearchContext bfsContext = testGraph.new SearchContext(testGraph.new BFS("d","h"));
        Path result = bfsContext.performSearch();
        Assert.assertEquals("d->a->b->e->c->f->g->h",result.toString());

        //System.out.println("Performing 2nd test: ");
        testGraph.nodePath.nodePath.clear();


        DotGraph.SearchContext dfsContext = testGraph.new SearchContext(testGraph.new DFS("d","h"));
        result = dfsContext.performSearch();
        Assert.assertEquals("d->a->b->c->e->f->h",result.toString());

    }


    @Test
    public void noPathDfs() throws IOException {
        System.out.println("in noPathDfs");
        DotGraph.SearchContext bfsContext = testGraph.new SearchContext(testGraph.new BFS("h","a"));
        Path result = bfsContext.performSearch();
        Assert.assertNull(result);

        //System.out.println("Performing 2nd test: ");

        DotGraph.SearchContext dfsContext = testGraph.new SearchContext(testGraph.new DFS("h","a"));
        result = dfsContext.performSearch();
        Assert.assertNull(result);

    }

    @Test
    public void nonexistentNodeDfs() throws IOException {
        System.out.println("in nonexistentNodeDfs");

        //Arrange
        DotGraph.SearchContext bfsContext = testGraph.new SearchContext(testGraph.new BFS("b","q"));
        Path result = bfsContext.performSearch();
        Assert.assertNull(result);

        //System.out.println("Performing 2nd test: ");

        DotGraph.SearchContext dfsContext = testGraph.new SearchContext(testGraph.new DFS("b","q"));
        result = dfsContext.performSearch();
        Assert.assertNull(result);
    }

    @Test
    public void sameNodeDfs() throws IOException {
        System.out.println("in sameNodedfs");
        //Arrange
        DotGraph.SearchContext bfsContext = testGraph.new SearchContext(testGraph.new BFS("b","b"));
        Path result = bfsContext.performSearch();
        Assert.assertNull(result);

        //System.out.println("Performing 2nd test: ");

        DotGraph.SearchContext dfsContext = testGraph.new SearchContext(testGraph.new DFS("b","q"));
        result = dfsContext.performSearch();
        Assert.assertNull(result);
    }


    @After
    public void teardown(){
        System.out.println("teardown\n");
    }

}

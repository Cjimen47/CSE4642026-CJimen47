import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.SingleSourcePaths;
import org.jgrapht.alg.interfaces.StrongConnectivityAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.Graph;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;
import org.jgrapht.traverse.DepthFirstIterator;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DotGraph {
    Graph<String, DefaultEdge> dotGraph;
    Path nodePath;

    enum Algorithm{
        BFS,
        DFS
    }

    public DotGraph() {
        dotGraph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        nodePath = new Path();
    }

    //******* Feature 1 *******///
    public void parseGraph(String filepath){
        //Takes a DOT graph file and creates a directed graph
        File file = new File(filepath);

        try{
            Scanner sc = new Scanner(file);
            //Fetch title of graph
            String [] fstLine = sc.nextLine().split(" ");
            //Move onto nodes of graph

            while(sc.hasNextLine()){
                Pattern pattern = Pattern.compile("(\\w+) -> (\\w+);");
                Matcher matcher = pattern.matcher(sc.nextLine());
                boolean matchFound = matcher.find();

                if(matchFound) {
                    //Add the vertex
                    dotGraph.addVertex(matcher.group(1));
                    dotGraph.addVertex(matcher.group(2));
                    //Add the edge
                    dotGraph.addEdge(matcher.group(1), matcher.group(2));
                }
            }
            sc.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public String toString(){
        StringBuilder output = new StringBuilder("Number of Nodes: " + dotGraph.vertexSet().size() + "\n" +
                "Label of Nodes: " + dotGraph.vertexSet().toString() + "\n" +
                "Number of Edges: " + dotGraph.edgeSet().size() + "\n" +
                "Nodes and Edge Direction of Edges: {\n");

        for (DefaultEdge curEdge : dotGraph.edgeSet()) {
            output.append(dotGraph.getEdgeSource(curEdge)).append("->").append(dotGraph.getEdgeTarget(curEdge)).append(";\n");
        }
        output.append("}");

        return output.toString();
    }

    public void outputGraph(String filepath){
        try{
            FileWriter myWriter = new FileWriter(filepath);
            myWriter.write(this.toString());
            myWriter.close();
        } catch (IOException e){
            System.out.println("Error occurred while writing to file.");
            e.printStackTrace();
        }
    }

    //******* Feature 2 *******///
    public String addNode(String label){
        //Add a node and check for duplicate labels
        if(dotGraph.addVertex(label)){
            return "Node " + label + "successfully added.";
        }
            return "Node " + label + "already exists";
    }

    public String addNodes(String[] label){
        //Add a node and check for duplicate labels
        StringBuilder result = new StringBuilder();
        for (String s : label) {

            if (dotGraph.addVertex(s)) {
                result.append("Node ").append(s).append("successfully added.");
            } else {
                result.append("Node ").append(s).append("already exists. ");
            }
        }

        return result.toString();
    }

    //******* Feature 3 *******///
    public String addEdge(String srcLabel, String dstLabel){
        //Add an edge and check for duplicate labels
        if(dotGraph.addEdge(srcLabel, dstLabel) != null){
            return "Source " + srcLabel + " and Destination " + dstLabel + " have been successfully added as an edge.";
        }
            return "Source " + srcLabel + " and Destination " + dstLabel + " already exists as an edge.";

    }

    //******* Feature 4 *******///
    public String outputDOTGraph(String path){
        String output = null;
        StringWriter writer = new StringWriter();

        //Output imported graph into a DOT file
        DOTExporter<String, DefaultEdge> exporter = new DOTExporter<>();
        exporter.setVertexAttributeProvider((v) -> {
            Map<String, Attribute> map = new LinkedHashMap<>();
            map.put("label", DefaultAttribute.createAttribute(v.toString()));
            return map;
        });

        try{
            exporter.exportGraph(dotGraph, writer);
            output = writer.toString();

            FileWriter myWriter = new FileWriter(path);
            myWriter.write(output);
            myWriter.close();
        } catch (IOException e){
            System.out.println("An error occurred while writing to file.");
            e.printStackTrace();
        }
        System.out.print("Graph has been output into a file");

        return output;
    }

    public String outputGraphics(String path, String format){
        //Output imported graph into a graphics
        GraphViz gv = new GraphViz();
        gv.add(outputDOTGraph(path));
        gv.decreaseDpi();
        gv.decreaseDpi();
        File out = new File(path + "." + format);
        gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), format ), out );

        return "Graphic Successfully Made";
    }

    //******* Remove Edges and Nodes *******///
    public void removeNode(String label) throws Exception {
        try{
            if(dotGraph.removeVertex(label)){
                System.out.println("Node " + label + " Successfully removed.");
            }
            else{
                throw new Exception(label + " node doesn't exist");
            }
        }
        catch (Exception e){
            System.out.println(label + " node doesn't exist");

        }

    }

    public void removeNodes(String[] label) throws Exception{

        for (String s : label) {

            try {
                if (dotGraph.removeVertex(s)) {
                    System.out.print("Node " + s + " successfully removed.\n" );
                } else {
                    throw new Exception("Node " + s + " doesn't exist");
                }
            }
            catch(Exception e){
                System.out.println("Node " + s + " doesn't exist");
            }
        }

        //System.out.println(result.toString());
    }

    public void removeEdge(String srcLabel, String dstLabel) throws Exception {
        try {
            if (dotGraph.removeEdge(srcLabel, dstLabel) != null) {
                System.out.println("Edge " + srcLabel + " -> " + dstLabel + " has been successfully removed");
            } else {
                throw new Exception("Edge doesn't exist");

            }
        }
        catch(Exception e){
            System.out.println("Edge " + srcLabel + " -> " + dstLabel + " does not exists.");

        }
    }

    //******* Graph Search *******///

    public interface SearchStrategy{
        Path Search();
    }

    class SearchContext {
        private SearchStrategy strategy;

        public SearchContext(SearchStrategy strategy){
            this.strategy = strategy;
        }

        public void setSearchContext (SearchStrategy strategy){
            this.strategy = strategy;
        }

        public Path performSearch(){
            return strategy.Search();
        }

    }


    abstract class GraphSearch implements SearchStrategy{
        String src;
        String dst;
        List<String> nodes;
        ArrayList<Boolean> explored;
        String v;

        GraphSearch(String src, String dst){
            this.src = src;
            this.dst = dst;
        }

        public final Path Search(){
            //System.out.println("Beginning Graph Traversal");

            Setup();
            InitializeTraversal();

            if(Verify()){
                while(!statusEmpty()){
                    RetrieveNode();
                    TraverseNode();
                }
            }

            return nodePath;
        }

        abstract void InitializeTraversal();
        abstract boolean statusEmpty();
        abstract void RetrieveNode();
        abstract void TraverseNode();
        //abstract void Traverse();

        //Set up the objects that will be needed for both graph traversals
        void Setup(){
            //System.out.println("We enter set up");
            nodes = Arrays.asList(Arrays.copyOf(dotGraph.vertexSet().toArray(), dotGraph.vertexSet().toArray().length, String[].class));
            explored = new ArrayList<>();

            for(int i = 0; i < nodes.size(); i++){
                explored.add(false);
            }

            //System.out.println("This is nodes: " + nodes);
            //System.out.println("This is explored: " + explored);
        }



        //Verify that the src and dst exist
        boolean Verify(){
            final boolean hasSource = nodes.contains(src);
            final boolean hasDestination = nodes.contains(dst);
            final boolean sameNode = src.equals(dst);
            final boolean noPath = dotGraph.outgoingEdgesOf(src).isEmpty();

            if(!hasSource || !hasDestination || sameNode || noPath){
                //System.out.println("Path cannot be found due to invalid src/dst");
                nodePath = null;
                return false;
            }

            return true;
        }


    }

    class BFS extends GraphSearch{
        Queue<String> Q = new LinkedList<>();

        BFS(String src, String dst) {
            super(src, dst);
        }

        @Override
        void InitializeTraversal(){
            //Label the root as explored
            explored.set(nodes.indexOf(src), true);
            //Add Root to queue
            Q.add(src);
            nodePath.updatePath(src);


        }

        @Override
        boolean statusEmpty(){
            //System.out.println(Q.isEmpty());
            return Q.isEmpty();
        }

        @Override
        void RetrieveNode(){
            v = Q.remove();
            //System.out.println("This is the node we are traversing with: " + v);

        }

        @Override
        void TraverseNode(){

            for (DefaultEdge edge : dotGraph.outgoingEdgesOf(v)) {
                if (explored.get(nodes.indexOf(dotGraph.getEdgeTarget(edge))) != false) {
                    explored.set(nodes.indexOf(dotGraph.getEdgeTarget(edge)), true);
                }

                nodePath.updatePath(dotGraph.getEdgeTarget(edge));

                if (dotGraph.getEdgeTarget(edge).equals(dst)) {

                    Q.clear();

                    System.out.println("Visit Node History: " + nodePath.toString());
                    System.out.println("Found target node: " + dotGraph.getEdgeTarget(edge));

                    return;
                }

                Q.add(dotGraph.getEdgeTarget(edge));
                System.out.println("Visit Node History: " + nodePath.toString());
            }

            //System.out.println("This is the queue we are traversing with: " + Q.toString());


        }

    }

    class DFS extends GraphSearch{
        Stack<String> S = new Stack<>();

        DFS(String src, String dst) {
            super(src, dst);
        }

        //Prepare to begin to traverse graph
        @Override
        void InitializeTraversal(){
            v = src;
            S.push(v);
        }

        @Override
        boolean statusEmpty(){
            //System.out.println("Is the stack empty? " + S.empty());
            return S.empty();
        }

        @Override
        void RetrieveNode(){
            v = S.pop();
        }

        @Override
        void TraverseNode(){

            if(explored.get(nodes.indexOf(v)) != true){
                explored.set(nodes.indexOf(v), true);
                nodePath.updatePath(v);
                System.out.println("Visit Node History: " + nodePath.toString());

                //System.out.println("v is currently " + v + " and its nodePath is " + nodePath.toString());
                //System.out.println("This is the explored array " + explored.toString());

                if(v.equals(dst)){
                    //System.out.println("Path Found");
                    S.clear();
                    System.out.println("Found target node: " + v);

                    return;
                }
            }
            else{
                return;
            }
            //System.out.println("v is currently " + v + " and its outgoing edges are " + dotGraph.outgoingEdgesOf(v).toString());
            ArrayList<String> edges = new ArrayList<>();
            //collect edges
            for(DefaultEdge edge : dotGraph.outgoingEdgesOf(v)){
                edges.add(dotGraph.getEdgeTarget(edge));
            }

            //push edges onto stack
            for(int i = edges.size(); i > 0; i--){
                S.push(edges.get(i-1));
            }

        }


    }

    class RND extends GraphSearch{
        List<String> nextEdges = new ArrayList<>();
        String nextNode;
        Random randNum = new Random();
        int attempt = 0;
        boolean con;
        boolean found;

        RND(String src, String dst) {
            super(src, dst);
        }

        @Override
        void InitializeTraversal(){
            //Make the first node to be traversed be the source

            //System.out.println("This is the first initial reset");

            ResetPath();

        }

        @Override
        boolean statusEmpty(){
            return found;

        }

        @Override
        void RetrieveNode(){
            v = nextNode;
            //System.out.println("This is the node we're traversing with: " + v);

        }

        @Override
        void TraverseNode(){
            //Add node to path
            nodePath.updatePath(v);

            //Check whether path has been found
            if(v.equals(dst)){
                found = true;
                System.out.println("This is the path we ended with: " + nodePath.toString());

            }
            else{
                //Else decide if we continue
                con = randNum.nextBoolean();

                if(con && !dotGraph.outgoingEdgesOf(v).isEmpty()){
                    //Continue down this path
                    //Collect the outgoing edges of the current node and assign a random node
                    collectEdges();
                   }
                else{
                    //Finish traversing down this path
                    System.out.println("Attempt " + attempt + " This is the path we found: " + nodePath.toString());
                    //System.out.println("/**********************************************/");
                    ResetPath();

                }

            }

        }

        void ResetPath(){
            //System.out.println("We've reset the path");
            v = src; //Start at beginning

            //Reset node path
            nodePath.visitHistory.clear(); //Clear nodePath

            //Collect the outgoing edges of the current node and assign a random node
            collectEdges();

            //Intitalize new node path
            nodePath.updatePath(v);

            attempt++;

        }

        void collectEdges(){
            for(DefaultEdge edge : dotGraph.outgoingEdgesOf(v)){
                //System.out.println("This is the edge we're looking at " + edge.toString());
                nextEdges.add(dotGraph.getEdgeTarget(edge));
            }

            nextNode = nextEdges.get(randNum.nextInt(nextEdges.size()));
            nextEdges.clear();

        }


    }

}

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
    String title;
    Path nodePath;

    public DotGraph() {
        dotGraph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        title = "null";
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
            title = fstLine[0];
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
        else{
            return "Node " + label + "already exists";
        }

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
        else{
            return "Source " + srcLabel + " and Destination " + dstLabel + " already exists as an edge.";
        }
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

    //******* Breadth First Search *******///
    public Path GraphSearch(String src, String dst){
        //Create a queue
        Queue<String> Q = new LinkedList<>();

        List<String> nodes = Arrays.asList(Arrays.copyOf(dotGraph.vertexSet().toArray(), dotGraph.vertexSet().toArray().length, String[].class));
        ArrayList<Boolean> explored = new ArrayList<>();

        for(int i = 0; i < nodes.size(); i++){
            explored.add(false);
        }


        String v;

        //Label the root as explored
        explored.set(nodes.indexOf(src), true);
        //Add Root to queue
        Q.add(src);

        /*while(!Q.isEmpty()){
            v = Q.remove();

            if(v.equals(dst)){
                return nodePath;
            }

            Object[] children = dotGraph.outgoingEdgesOf(v).toArray();




        }*/




        //Create an array of booleans that aligns with the Vertex set

        //Add src to the root

        //create a while loop that only goes while Q is not empty

        //Create a source v that holds the dequeued result

        //If v is a match, then return the nodePath

        //Else
        //Get the set of edges touching v

        //Check every edge

        //If the edge has not been labeled as explored, label it

        //Add the parent to the node path
        //Queue the visited edge

        return nodePath;


    }






}

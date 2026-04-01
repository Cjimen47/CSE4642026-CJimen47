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

    public DotGraph() {
        dotGraph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        title = "null";
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
            System.out.println("An error occurred while writing to file.");
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
    public String removeNode(String label){
        if(dotGraph.removeVertex(label)){
            return "Node " + label + "successfully removed.";
        }
        else{
            return "Node " + label + "doesn't exists";
        }
    }

    public String removeNodes(String[] label){
        StringBuilder result = new StringBuilder();

        for (String s : label) {

            if (dotGraph.removeVertex(s)) {
                result.append("Node ").append(s).append("successfully removed.");
            } else {
                result.append("Node ").append(s).append("doesn't exists. ");
            }
        }

        return result.toString();
    }

    public String removeEdge(String srcLabel, String dstLabel){
        if (dotGraph.removeEdge(srcLabel, dstLabel) != null){
            return "Edge successfully removed";
        }
        else{
            return "Edge does not exist in graph.";
        }
    }










}

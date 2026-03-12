import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.SingleSourcePaths;
import org.jgrapht.alg.interfaces.StrongConnectivityAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.Graph;
import org.jgrapht.traverse.DepthFirstIterator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
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
    public void addNode(String label){
        //Add a node and check for duplicate labels
    }

    public void addNodes(String label){
        //Add a node and check for duplicate labels
    }

    //******* Feature 3 *******///
    public String addEdge(String srcLabel, String dstLabel){
        //Add an edge and check for duplicate labels
        if(dotGraph.addEdge(srcLabel, dstLabel) != null){
            return "Source " + srcLabel + "and Destination " + dstLabel + " have been successfully added as an edge.";
        }
        else{
            return "Source " + srcLabel + "and Destination " + dstLabel + " already exists as an edge.";
        }
    }

    //******* Feature 4 *******///








}

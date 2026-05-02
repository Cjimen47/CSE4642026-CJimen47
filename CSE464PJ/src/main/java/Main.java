//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        DotGraph userGraph = new DotGraph();
        Scanner scn = new Scanner(System.in);
        String[] nodes;
        Path result;

        System.out.println("Welcome to the Graph Manager");
        System.out.println("Please enter the path of the file containing the graph");

        String path = scn.nextLine();
        userGraph.parseGraph(path);
        System.out.println("Here is the graph you inputed: " );
        System.out.println(userGraph.toString());

        System.out.println(
                "Enter a number to do a certain search: \n"+
                        "1. Breadth-First Search \n"+
                        "2. Depth-First Search \n"+
                        "3. Random Search \n"

        );


        while(scn.hasNextLine()){
            switch (scn.nextLine()){
                case "1":
                    System.out.println("Please enter the source and destination node you'd like to do a bfs search (separate by comma)");
                    nodes = scn.nextLine().split(",");
                    DotGraph.SearchContext bfsContext = userGraph.new SearchContext(userGraph.new BFS(nodes[0],nodes[1]));
                    result = bfsContext.performSearch();
                    userGraph.nodePath.visitHistory.clear();
                    break;

                case "2":
                    System.out.println("Please enter the source and destination node you'd like to do a dfs search (separate by comma)");
                    nodes = scn.nextLine().split(",");
                    DotGraph.SearchContext dfsContext = userGraph.new SearchContext(userGraph.new DFS(nodes[0],nodes[1]));
                    result = dfsContext.performSearch();
                    userGraph.nodePath.visitHistory.clear();
                    break;
                case "3":
                    System.out.println("Please enter the source and destination node you'd like to do a random search (separate by comma)");
                    nodes = scn.nextLine().split(",");
                    DotGraph.SearchContext rndContext = userGraph.new SearchContext(userGraph.new RND(nodes[0],nodes[1]));
                    result = rndContext.performSearch();
                    userGraph.nodePath.visitHistory.clear();
                    break;
                case "4":
                    System.out.println("You've quit the system, thank you");
                    break;
            }

            System.out.println(
                    "Enter a number to do a certain search: \n"+
                            "1. Breadth-First Search \n"+
                            "2. Depth-First Search \n"+
                            "3. Random Search \n"

            );

        }


        /*

        System.out.println("To find a breadth first search path, please enter a source and destination node (separate by comma): " );
        String[] nodes = scn.nextLine().split(",");
        userGraph.GraphSearch(nodes[0], nodes[1], DotGraph.Algorithm.BFS);
        System.out.println("Here is the path found: " );
        System.out.println(userGraph.nodePath.toString());

        userGraph.nodePath.nodePath.clear();

        System.out.println("To find a depth first search path, please enter a source and destination node (separate by comma): " );
        nodes = scn.nextLine().split(",");
        userGraph.GraphSearch(nodes[0], nodes[1], DotGraph.Algorithm.DFS);
        System.out.println("Here is the path found: " );
        System.out.println(userGraph.nodePath.toString());

        System.out.println("Please enter the node you wish to remove: ");
        String node = scn.nextLine();
        try {
            userGraph.removeNode(node);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Here is the resulting graph: ");
        System.out.println(userGraph.toString());

        System.out.println("Please enter the nodes you wish to remove (separate by comma): ");
        nodes = scn.nextLine().split(",");
        try {
            userGraph.removeNodes(nodes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Here is the resulting graph: ");
        System.out.println(userGraph.toString());

        System.out.println("Please enter the source and destination of the edge you wish to remove (separate by comma): ");
        nodes = scn.nextLine().split(",");
        try {
            userGraph.removeEdge(nodes[0],nodes[1]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Here is the resulting graph: ");
        System.out.println(userGraph.toString());

         */


    }
}
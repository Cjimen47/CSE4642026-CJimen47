//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        DotGraph userGraph = new DotGraph();
        Scanner scn = new Scanner(System.in);

        System.out.println("Welcome to the Graph Manager");
        System.out.println("Please enter the path of the file containing the graph");

        String path = scn.nextLine();
        userGraph.parseGraph(path);
        System.out.println("Here is the graph you inputed: " );
        System.out.println(userGraph.toString());




    }
}
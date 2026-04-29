import java.util.ArrayList;

public class Path {
    ArrayList<String> visitHistory;

    public Path(){
        visitHistory = new ArrayList<>();
    }


    public void updatePath (String node){
        if(!visitHistory.contains(node)){
            visitHistory.add(node);

        }
    }

    public String toString(){
        StringBuilder output = new StringBuilder();

        for(int i = 0; i < visitHistory.size(); i++ ){
            if(i == visitHistory.size() - 1){
                output.append(visitHistory.get(i));
            }
            else{
                output.append(visitHistory.get(i)).append("->");

            }

        }

        return output.toString();
    }




}

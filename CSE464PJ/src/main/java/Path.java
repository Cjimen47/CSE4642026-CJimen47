import java.util.ArrayList;

public class Path {
    ArrayList<String> nodePath;

    public Path(){
        nodePath = new ArrayList<>();
    }


    public void updatePath (String node){
        if(!nodePath.contains(node)){
            nodePath.add(node);

        }
    }

    public String toString(){
        StringBuilder output = new StringBuilder();

        for(int i = 0; i < nodePath.size(); i++ ){
            if(i == nodePath.size() - 1){
                output.append(nodePath.get(i));
            }
            else{
                output.append(nodePath.get(i)).append("->");

            }

        }

        return output.toString();
    }




}

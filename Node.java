public class Node {
    
    public String parent;

    public int rank;

    public Node(String p){
        this.parent = p;
        this.rank = 0;
    }

    public void setParent(String p){
        this.parent = p;
    }

    public void setRank(int r){
        this.rank = r;
    }
}
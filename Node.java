public class Node {
    
    public int parent;

    public int rank;

    public Node(int p){
        this.parent = p;
        this.rank = 0;
    }

    public void setParent(int p){
        this.parent = p;
    }

    public void setRank(int r){
        this.rank = r;
    }
}
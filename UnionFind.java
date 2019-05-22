import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class UnionFind{

	// map to record string -> node
	public HashMap<Integer, Node> nodes;

	public UnionFind(){
		nodes = new HashMap<Integer, Node>();
	}

	public void union(int p, int q){
		int rootP = find(p);
		int rootQ = find(q);

		// if the two roots are the same, already in a component
		if (rootP == rootQ) return;

		Node nodep = nodes.get(rootP);
		Node nodeq = nodes.get(rootQ);

		// merge two components together
		if (nodep.rank < nodeq.rank){
			nodep.setParent(rootQ);
		} else if (nodep.rank > nodeq.rank) {
			nodeq.setParent(rootP);
		} else {
			nodep.setParent(rootQ);
			nodeq.setRank(nodeq.rank + 1);
		} 
	}

	//recursively do path compression and find the root of the component
  	public int find(int p){
		Node node = nodes.get(p);
		if (node.parent != p){
			node.setParent(find(node.parent));
		}
		return node.parent;
	}

	public void addEdge(int p, int q){
		if (!nodes.containsKey(p)){
			nodes.put(p, new Node(p));
		}
		if (!nodes.containsKey(q)){
			nodes.put(q, new Node(q));
		}
		union(p, q);
	}

	public String outputComponents() {
        Set<Entry<Integer, Node>> nodeSet = this.nodes.entrySet();
        nodeSet.forEach((node) -> {
            this.find(node.getKey());
        });

        StringBuilder sb = new StringBuilder();
        return nodeSet.parallelStream()
			.map(node -> node.getKey() + " " +  node.getValue().parent + "\n").collect(Collectors.joining());
    }
}
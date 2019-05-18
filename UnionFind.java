import java.util.HashMap;
import java.util.ArrayList;

public class UnionFind{

	// map to record string -> node
	public static HashMap<String, Node> nodes;

    public UnionFind(){
			nodes = new HashMap<String, Node>();
    }

	public void union(String p, String q){
		String rootP = find(p);
		String rootQ = find(q);

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

  public String find(String p){
		Node node = nodes.get(p);
		if (node.parent != p){
			node.setParent(find(node.parent));
		}
		return node.parent;
	}

	public void addEdge(String input){
		String[] pair = input.split(" ", 2);
		if (!nodes.containsKey(pair[0])){
			nodes.put(pair[0], new Node(pair[0]));
		}
		if (!nodes.containsKey(pair[1])){
			nodes.put(pair[1], new Node(pair[1]));
		}
		union(pair[0], pair[1]);
	}

	public String outputComponents(){
		StringBuilder sb = new StringBuilder();
		for (String key : nodes.keySet()){
			// Need to run find again for all components
			sb.append(key + " " + this.find(key) + "\n");
		}
		return sb.toString();
	}
}
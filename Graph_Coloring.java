import java.util.HashMap;
import java.util.Scanner;

/** Create following graph and test whether it is 3 colorable 
(3)---(2) 
 |   / | 
 |  /  | 
 | /   | 
(0)---(1) 

4
5

0 1
1 2
0 2
2 3
3 0

3
**/

public class Graph_Coloring {

	private static Node[] graph;
	private static boolean solution=false;

	private static class Node {
		int name;
		HashMap<Node, Boolean> adjVertices;

		Node(int name) {
			this.name = name;
			this.adjVertices = new HashMap<>();
		}
	}

	/** Main function **/
	public static void main(String[] args) {
		Scanner scnr = new Scanner(System.in);

		CreateGraph(scnr);
		System.out.println("\nEnter number of colors");
		int c = scnr.nextInt();
		
		int[] colours = new int[graph.length];
		
		solve(0, c, colours);
		
		if(!solution){
			System.out.println("No solution");
		}
	}

	private static void CreateGraph(Scanner scnr) {

		System.out.println("Enter number of vertices: ");
		int v = scnr.nextInt();

		System.out.println("\nEnter the Edges: ");
		int e = scnr.nextInt();

		graph = new Node[v];
		for (int i = 0; i < graph.length; i++) {
			graph[i] = new Node(i);
		}
		
		System.out.println("\nStart entering the vertex pairs: ");
		while (e-- > 0) {
			int from = scnr.nextInt();
			int to = scnr.nextInt();
			graph[from].adjVertices.put(graph[to], true);
			graph[to].adjVertices.put(graph[from], true);
		}
	}

	// Function to assign colors recursively
	public static void solve(int vertex, int numColours, int[] colours) {

		// Base Case
		if (vertex == graph.length) {
			System.out.println("\nSolution exists ");
			solution=true;
			display(colours);
			return;
		}

		// Try all colours
		for (int c = 1; c <= numColours; c++) {

			if (isPossible(vertex, c,colours)) {
				// Assign and proceed with next vertex.
				colours[vertex] = c;
				solve(vertex + 1, numColours, colours);
				// Wrong assignement, after backTrack.
				
				colours[vertex] = 0;
			}
		}
	}

	// Function to check if it is valid to alot that colour to vertex.
	public static boolean isPossible(int v, int c, int[] colours) {
		for (Node n : graph[v].adjVertices.keySet()) {
			if (colours[n.name]==c)
				return false;
		}
		return true;
	}

	// Display solution
	public static void display(int[] colours) {
		System.out.print("\nColors : ");
		for (int i = 0; i < colours.length; i++)
			System.out.print(colours[i] + " ");
		System.out.println();
	}
}
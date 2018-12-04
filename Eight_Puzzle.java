import java.util.*;

public class Eight_Puzzle {

	static class Node implements Comparable<Node> {
		int[][] parent;
		int[][] self;
		int f; // Heuristic function
		int g; // Distance from root

		public Node(int[][] parent, int[][] self) {
			this.parent = parent;
			this.self = self;
		}

		@Override
		public int compareTo(Node o) {
			// TODO Auto-generated method stub
			return this.f - o.f;
		}

		@Override
		public boolean equals(Object obj) {
			Node o = (Node) obj;
			return Arrays.deepEquals(this.self, o.self);
		}

		@Override
		public String toString() {
			String retval = "";
			for (int i = 0; i < this.self.length; ++i) {
				for (int j = 0; j < this.self[i].length; ++j) {
					if (this.self[i][j] == 0) {
						retval += "  ";
						continue;
					}
					retval += this.self[i][j] + " ";
				}
				retval += "\n";
			}
			return retval;
		}

	}
/**
8 1 3
4 0 2
7 6 5
*/
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner scnr = new Scanner(System.in);
		int[][] initial_state = new int[3][3];
		System.out.println("Enter the Initial State: ");
		for (int i = 0; i < initial_state.length; ++i) {
			for (int j = 0; j < initial_state[i].length; ++j) {
				initial_state[i][j] = scnr.nextInt();
			}
		}
		int ch = 0;
		System.out.println("Enter 0 for manhattan-distance solution and 1 for hamming-distance solution: ");
		ch = scnr.nextInt();

		if (ch == 0) {
			Eight_puzzle_manhattan(initial_state);
		}
		else{
			Eight_puzzle_hamming(initial_state);
		}
	}

	public static void Eight_puzzle_hamming(int[][] initial_state) {
		if (isGoal(initial_state)) {
			System.out.println("Done!");
			System.out.println("Total Steps = 0");
			System.out.println("Total States Enqued = 0");
			System.out.println("END");
			return;
		}

		// Intitializing the root node for the intial board.
		Node root = new Node(null, initial_state);
		int h = hamming_distance(initial_state);
		int g = 0;
		int f = g + h;
		root.f = f;
		root.g = g;
		
		int states_enqued=1;

		LinkedList<Node> open = new LinkedList<>();
		HashMap<int[][], Node> close = new HashMap<>();

		open.add(root);

		while (!open.isEmpty()) {
			Node parent = findMin(open);
			open.remove(parent);

			LinkedList<int[][]> children = generate_children(parent.self);

			for (int[][] child : children) {
				if (!Arrays.deepEquals(child, parent.parent)) {
					h = hamming_distance(child);
					g = parent.g + 1;
					f = h + g;

					if (isGoal(child)) {
						int steps = 0;
						close.put(parent.self, parent);
						System.out.println("Done!");
						LinkedList<Node> solution = new LinkedList<>();
						solution.addFirst(new Node(parent.self, child));
						while (parent != null) {
							steps++;
							solution.addFirst(parent);
							parent = close.get(parent.parent);
						}

						for (Node sol : solution) {
							System.out.print(sol + "------------\n");
						}
						System.out.println("Total Steps = " + steps);
						System.out.println("Total States Enqued = " + states_enqued);
						System.out.println("END");
						return;
					}

					Node existing_open = findNode(open, child);
					if (existing_open != null && existing_open.f > f) {
						existing_open.f = f;
						existing_open.g = g;
						existing_open.parent = parent.self;
					} else {
						Node n = new Node(parent.self, child);
						n.f = f;
						n.g = g;
						states_enqued++;
						open.add(n);
					}

					Node existing_close = close.get(child);
					if (existing_close != null && existing_close.f > f) {
						existing_close.f = f;
						existing_close.g = g;
						existing_close.parent = parent.self;
						Node n = close.remove(child);
						states_enqued++;
						open.add(n);
					}
				}
			}

			close.put(parent.self, parent);
		}
	}

	public static void Eight_puzzle_manhattan(int[][] initial_state) {
		if (isGoal(initial_state)) {
			System.out.println("Done!");
			System.out.println("Total Steps = 0");
			System.out.println("Total States Enqued = 0");
			System.out.println("END");
			return;
		}

		// Intitializing the root node for the intial board.
		Node root = new Node(null, initial_state);
		int h = manhattan_distance(initial_state);
		int g = 0;
		int f = g + h;
		root.f = f;
		root.g = g;
		
		int states_enqued=1;

		LinkedList<Node> open = new LinkedList<>();
		HashMap<int[][], Node> close = new HashMap<>();

		open.add(root);

		while (!open.isEmpty()) {
			Node parent = findMin(open);
			open.remove(parent);

			LinkedList<int[][]> children = generate_children(parent.self);

			for (int[][] child : children) {
				if (!Arrays.deepEquals(child, parent.parent)) {
					h = manhattan_distance(child);
					g = parent.g + 1;
					f = h + g;

					if (isGoal(child)) {
						int steps = 0;
						close.put(parent.self, parent);
						System.out.println("Done!");
						LinkedList<Node> solution = new LinkedList<>();
						solution.addFirst(new Node(parent.self, child));
						while (parent != null) {
							steps++;
							solution.addFirst(parent);
							parent = close.get(parent.parent);
						}

						for (Node sol : solution) {
							System.out.print(sol + "------------\n");
						}
						System.out.println("Total Steps = " + steps);
						System.out.println("Total States Enqued = " + states_enqued);
						System.out.println("END");
						return;
					}

					Node existing_open = findNode(open, child);
					if (existing_open != null && existing_open.f > f) {
						existing_open.f = f;
						existing_open.g = g;
						existing_open.parent = parent.self;
					} else {
						Node n = new Node(parent.self, child);
						n.f = f;
						n.g = g;
						states_enqued++;
						open.add(n);
					}

					Node existing_close = close.get(child);
					if (existing_close != null && existing_close.f > f) {
						existing_close.f = f;
						existing_close.g = g;
						existing_close.parent = parent.self;
						Node n = close.remove(child);
						states_enqued++;
						open.add(n);
					}
				}
			}

			close.put(parent.self, parent);
		}
	}


	public static boolean isGoal(int[][] state) {
		int count = 1;
		for (int i = 0; i < state.length; ++i) {
			for (int j = 0; j < state[i].length; ++j) {
				if (i == state.length - 1 && j == state[i].length - 1) {
					return true;
				}
				if (count != state[i][j]) {
					return false;
				}
				count++;
			}
		}

		return true;
	}

	public static Node findNode(LinkedList<Node> list, int[][] node) {
		for (Node n : list) {
			if (Arrays.deepEquals(n.self, node)) {
				return n;
			}
		}
		return null;
	}

	public static Node findMin(LinkedList<Node> list) {
		Node min = list.getFirst();
		for (Node n : list) {
			if (n.compareTo(min) < 0) {
				min = n;
			}
		}
		return min;
	}

	public static LinkedList<int[][]> generate_children(int[][] board) {
		LinkedList<int[][]> retval = new LinkedList<>();

		// Find the Empty location on the board:
		int row = 0, col = 0;
		outer: for (int i = 0; i < board.length; ++i) {
			for (int j = 0; j < board[i].length; ++j) {
				if (board[i][j] == 0) {
					row = i;
					col = j;
					break outer;
				}
			}
		}

		// Movement in North:
		if (row - 1 >= 0) {
			int[][] copy = deep_copy(board);
			copy[row][col] = copy[row - 1][col];
			copy[row - 1][col] = 0;
			retval.add(copy);
		}

		// Movement in South:
		if (row + 1 < board.length) {
			int[][] copy = deep_copy(board);
			copy[row][col] = copy[row + 1][col];
			copy[row + 1][col] = 0;
			retval.add(copy);
		}

		// Movement in West:
		if (col - 1 >= 0) {
			int[][] copy = deep_copy(board);
			copy[row][col] = copy[row][col - 1];
			copy[row][col - 1] = 0;
			retval.add(copy);
		}

		// Movement in East:
		if (col + 1 < board[0].length) {
			int[][] copy = deep_copy(board);
			copy[row][col] = copy[row][col + 1];
			copy[row][col + 1] = 0;
			retval.add(copy);
		}

		return retval;
	}

	public static int[][] deep_copy(int[][] arr) {
		int[][] retval = new int[arr.length][arr[0].length];

		for (int i = 0; i < arr.length; ++i) {
			for (int j = 0; j < arr[i].length; ++j) {
				retval[i][j] = arr[i][j];
			}
		}

		return retval;
	}

	public static int hamming_distance(int[][] board) {
		int retval = 0;
		if (board[0][0] != 1) {
			retval++;
		}
		if (board[0][1] != 2) {
			retval++;
		}
		if (board[0][2] != 3) {
			retval++;
		}
		if (board[1][0] != 4) {
			retval++;
		}
		if (board[1][1] != 5) {
			retval++;
		}
		if (board[1][2] != 6) {
			retval++;
		}
		if (board[2][0] != 7) {
			retval++;
		}
		if (board[2][1] != 8) {
			retval++;
		}
		return retval;
	}

	public static int manhattan_distance(int[][] board) {
		int retval = 0;
		for (int i = 0; i < board.length; ++i) {
			for (int j = 0; j < board[i].length; ++j) {
				if (board[i][j] == 1) {
					retval += (i + j);
				}
				if (board[i][j] == 2) {
					retval += Math.abs(i) + Math.abs(j - 1);
				}
				if (board[i][j] == 3) {
					retval += Math.abs(i) + Math.abs(j - 2);
				}
				if (board[i][j] == 4) {
					retval += Math.abs(i - 1) + Math.abs(j);
				}
				if (board[i][j] == 5) {
					retval += Math.abs(i - 1) + Math.abs(j - 1);
				}
				if (board[i][j] == 6) {
					retval += Math.abs(i - 1) + Math.abs(j - 2);
				}
				if (board[i][j] == 7) {
					retval += Math.abs(i - 2) + Math.abs(j);
				}
				if (board[i][j] == 8) {
					retval += Math.abs(i - 2) + Math.abs(j - 1);
				}
			}
		}
		return retval;
	}
}
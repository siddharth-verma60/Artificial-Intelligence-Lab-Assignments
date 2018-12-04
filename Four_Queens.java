
public class Four_Queens {

	// Type for defining position of queen(row,col)
	static class position {
		int row;
		int col;

		public position(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}

	public static void main(String[] args) {
		int N=16;
		position[] positions=new position[N];
		N_Queens_DFS(N,0,positions);
		displaySolution(N, positions);
	}

	/**
	 * Uninformed Depth First Search method to solve N- Queens. N = No. of rows
	 * and columns row = current row we are setting queen in positions = Array
	 * of type position. Contains N no.of position types.
	 **/
	public static boolean N_Queens_DFS(int N, int row, position[] positions) {

		// Base Case
		if (row == N)
			return true;

		for (int col = 0; col < N; col++) {
			// Tells if the current cell is safe to keep the queen to avoid
			// attacks
			boolean isSafe = true;

			int i = 0;
			while (i < row) {
				position queen = positions[i];
				// Checking the diagonals, columns and rows on the board if they
				// are safe from attack
				if (queen.row == row || queen.col == col || queen.row - queen.col == row - col
						|| queen.row + queen.col == row + col) {
					isSafe = false; // if current cell is found unsafe.
					break;
				}
				i += 1;
			}

			if (isSafe) {
				// Add the current safe cell
				positions[row] = new position(row, col);
				if (N_Queens_DFS(N, row + 1, positions)) // DFS Call
					return true;
			}
		}

		return false;
	}

	// Function to display the final solution
	public static void displaySolution(int N, position[] positions) {

		char[][] solutionBoard = new char[N][N];

		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < N; ++j) {
				solutionBoard[i][j] = '*';
			}
		}

		for (position pos : positions) {
			solutionBoard[pos.row][pos.col] = 'Q';
		}

		System.out.println("Solution:");
		System.out.println();

		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < N; ++j) {
				System.out.print(solutionBoard[i][j]);
			}
			System.out.println();
		}
	}

}

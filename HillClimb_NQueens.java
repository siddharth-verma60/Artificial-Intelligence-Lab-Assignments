import java.util.Scanner;

public class HillClimb_NQueens {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scnr=new Scanner(System.in);
		
		System.out.println("Provide the value of N: ");
		int N=scnr.nextInt();
		System.out.println("How many number of climbs do you require ? ");
		int numClimbs=scnr.nextInt();
		HillClimb(N, numClimbs);
	}

	public static void HillClimb(int N, int num_climbs) {
		char[][] board;
		boolean climb = true;
		int count = 0;

		while (climb) {
			board = new char[N][N];

			// Randomly Place the Queens:
			int[] queenPositions = InitializeBoard(board);
			System.out.println("#############################################");
			System.out.println("Climb Number: " + (++count));
			System.out.println("#############################################");
			System.out.println();
			System.out.println("Initial Board: ");
			printBoard(board);
			int heuristicValue = heuristicValue(board);
			System.out.println("Number of pairs of Queens attacking each other: " + heuristicValue);
			System.out.println();
			
			int minValue = heuristicValue;
			boolean Best = false;

			// Stores best queen positions by row (Array-index is column)
			int[] queenBestPositions = new int[N];

			// Going through each column
			for (int j = 0; j < board[0].length; ++j) {
				System.out.println("Traversing COLUMN " + j + ":");
				Best = false;

				// Traversing each row
				for (int i = 0; i < board.length; ++i) {

					if (i != queenPositions[j]) {
						placeQueen(board, queenPositions, i, j);
						printBoard(board);
						System.out.println();
						
						// Climbing Hill
						heuristicValue = heuristicValue(board);
						if (heuristicValue < minValue) {
							Best = true;
							minValue = heuristicValue;
							queenBestPositions[j] = i;
						}
						// Resetting to original queen position:
						queenReset(board, queenPositions, i, j);
					}
				}
				
				if (Best) {
					// If a lower score was found, place queen in this position.
					placeBestQueen(board, queenPositions, queenBestPositions[j], j);
					System.out.println("Best board found this iteration: ");
				} else {
					System.out.println("No better board-arrangement found.");
				}
				
				printBoard(board);
				System.out.println("Number of pairs of Queens attacking each other: " + minValue);
				System.out.println();
			}

			// Hill Climbing problem solved, reached local minima
			if (minValue == 0){
				climb = false;
				System.out.println("Problem solved in " + (count) + " climbs.");
			}
			
			
			else if(count>=num_climbs){
				climb=false;
				System.out.println("Problem not solved");
			}
			
		}
	}

	public static int[] InitializeBoard(char[][] board) {
		int[] queenPos = new int[board[0].length];
		for (int j = 0; j < board[0].length; ++j) {
			for (int i = 0; i < board.length; ++i) {
				board[i][j] = '*';
			}
			int index = (int) (Math.random() * board[0].length);
			board[index][j] = 'Q';
			queenPos[j] = index;
		}
		return queenPos;
	}

	public static void printBoard(char[][] board) {
		for (int i = 0; i < board.length; ++i) {
			for (int j = 0; j < board[i].length; ++j) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}

	public static int heuristicValue(char[][] board) {
		int pairs = 0;

		// Calculating pairs in each row:
		for (int i = 0; i < board.length; ++i) {
			int temp = 0;
			for (int j = 0; j < board[i].length; ++j) {
				if (board[i][j] == 'Q') {
					temp++;
				}
			}
			pairs += (temp * (temp - 1) / 2);
		}

		// Calculating pairs in diagonals from left to right:
		int row = board.length, col = 0;
		while (row > 0 && col < board[0].length) {
			int temp = 0;
			for (int i = 0, j = col; i < row && j < board[0].length; i++, j++) {
				if (i == 0 && j == board[i].length - 1) {
					break;
				}
				if (board[i][j] == 'Q') {
					temp++;
				}
			}
			pairs += (temp * (temp - 1) / 2);
			row--;
			col++;
		}
		row = 1;
		col = board[0].length - 1;
		while (row < board.length && col > 0) {
			int temp = 0;
			for (int i = row, j = 0; i < board.length && j < col; i++, j++) {
				if (i == board.length - 1 && j == 0) {
					break;
				}
				if (board[i][j] == 'Q') {
					temp++;
				}
			}
			pairs += (temp * (temp - 1) / 2);
			row++;
			col--;
		}

		// Calculating pairs in diagonals from right to left:
		row = board.length;
		col = board[0].length - 1;
		while (row > 0 && col > 0) {
			int temp = 0;
			for (int i = 0, j = col; i < row && j >= 0; ++i, --j) {
				if (i == 0 && j == 0) {
					break;
				}
				if (board[i][j] == 'Q') {
					temp++;
				}
			}
			pairs += (temp * (temp - 1) / 2);
			row--;
			col--;
		}

		row = 1;
		col = 1;
		while (row < board.length && col > 0) {
			int temp = 0;
			for (int i = row, j = board[0].length - 1; i < board.length && j >= col; ++i, --j) {
				if (i == board.length - 1 && j == board[0].length - 1) {
					break;
				}
				if (board[i][j] == 'Q') {
					temp++;
				}
			}
			pairs += (temp * (temp - 1) / 2);
			row++;
			col++;
		}
		return pairs;
	}

	public static void placeQueen(char[][] board, int[] queenPos, int row, int col) {
		board[queenPos[col]][col]='^';
		board[row][col]='Q';
	}
	
	public static void queenReset(char[][] board, int[] queenPos, int row, int col){
		board[queenPos[col]][col]='Q';
		board[row][col]='*';
	}
	
	public static void placeBestQueen(char[][] board, int[] queenPos, int row, int col){
		board[queenPos[col]][col]='*';
		board[row][col]='Q';
		queenPos[col]=row;
	}
}

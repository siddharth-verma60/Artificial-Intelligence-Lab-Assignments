import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Sudoku {

	private static class pair {
		int row;
		int col;

		public pair(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}

	public static void main(String[] args) {
		// Easy Puzzle
//		 int[][] puzzle = { { 0, 3, 0, 0, 8, 0, 0, 0, 6 }, { 5, 0, 0, 2, 9, 4,
//		 7, 1, 0 }, { 0, 0, 0, 3, 0, 0, 5, 0, 0 },
//		 { 0, 0, 5, 0, 1, 0, 8, 0, 4 }, { 4, 2, 0, 8, 0, 5, 0, 3, 9 }, { 1, 0,
//		 8, 0, 3, 0, 6, 0, 0 },
//		 { 0, 0, 3, 0, 0, 7, 0, 0, 0 }, { 0, 4, 1, 6, 5, 3, 0, 0, 2 }, { 2, 0,
//		 0, 0, 4, 0, 0, 6, 0 } };

		// Medium puzzle
		int[][] puzzle = { { 3, 0, 8, 2, 9, 6, 0, 0, 0 }, { 0, 4, 0, 0, 0, 8, 0, 0, 0 }, { 5, 0, 2, 1, 0, 0, 0, 8, 7 },
				{ 0, 1, 3, 0, 0, 0, 0, 0, 0 }, { 7, 8, 0, 0, 0, 0, 0, 3, 5 }, { 0, 0, 0, 0, 0, 0, 4, 1, 0 },
				{ 1, 2, 0, 0, 0, 7, 8, 0, 3 }, { 0, 0, 0, 8, 0, 0, 0, 2, 0 }, { 0, 0, 0, 5, 4, 2, 1, 0, 6 } };

		// Hard Puzzle
//		int[][] puzzle = { { 7, 0, 0, 0, 0, 0, 0, 0, 0 }, { 6, 0, 0, 4, 1, 0, 2, 5, 0 }, { 0, 1, 3, 0, 9, 5, 0, 0, 0 },
//				{ 8, 6, 0, 0, 0, 0, 0, 0, 0 }, { 3, 0, 1, 0, 0, 0, 4, 0, 5 }, { 0, 0, 0, 0, 0, 0, 0, 8, 6 },
//				{ 0, 0, 0, 8, 4, 0, 5, 3, 0 }, { 0, 4, 2, 0, 3, 6, 0, 0, 7 }, { 0, 0, 0, 0, 0, 0, 0, 0, 9 } };

		System.out.println("Solving the following puzzle: ");
		print_puzzle(puzzle);
		
		int[][] puzzle2=new int[9][9];
		for(int i=0;i<9;++i){
			for(int j=0;j<9;++j){
				puzzle2[i][j]=puzzle[i][j];
			}
		}
		
		long startTimebtfch = System.nanoTime();
		System.out.println("Using backtracking with forward checking and heurisitics.");
		boolean result2 = solve_btfch(puzzle);
		long endTimebtfch = System.nanoTime();

		if (result2) {
			System.out.println("Execution time: " + ((endTimebtfch - startTimebtfch)/1000000000) +" seconds" );
		} else {
			System.out.println("Failed to find solution from backtracking with heuristics");
		}

		long startTimebt = System.nanoTime();
		System.out.println("Using backtracking search.");
		boolean result1 = solve_backtrack(puzzle2);
		long endTimebt = System.nanoTime();

		if (result1) {
			System.out.println("Execution time: " + ((endTimebt - startTimebt)/1000000000) +" seconds" );
		} else {
			System.out.println("Failed to find solution from backtracking");
		}
	}

	// Recursive backtracking algorithm to solve puzzle:
	public static boolean solve_backtrack(int[][] puzzle) {

		// store all the possible values remaining for a square
		List<Integer> domain = new LinkedList<>();

		for (int i = 1; i <= 9; ++i) {
			domain.add(i);
		}

		// get a list of the empty squares (remaining variables)
		ArrayList<pair> empty_squares = get_empty_squares(puzzle);

		// if there are no remaining empty squares we're done
		if (empty_squares.size() == 0) {
			System.out.println("Woohoo, success! Check it out:");
			print_puzzle(puzzle);
			return true;
		}

		pair square = get_random_square(empty_squares);
		int row = square.row;
		int col = square.col;

		while (domain.size() != 0) {

			// get a random value out of the list of remaining possible values
			int value = domain.get((int) (Math.floor(Math.random() * domain.size())));
			domain.remove((Integer) value);

			// check the value against the constraints
			if (check_row(square, value, puzzle)) {
				if (check_col(square, value, puzzle)) {
					if (check_block(square, value, puzzle)) {
						puzzle[row][col] = value;
						if (solve_backtrack(puzzle))
							return true;
						else
							puzzle[row][col] = 0;
					}
				}
			}
		}
		return false;
	}

	// Solves the sudoku puzzle using forward checking and 3 heuristic:
	// minimum remaining values (MRV)
	public static boolean solve_btfch(int[][] puzzle) {

		// get a list of the empty squares (remaining variables)
		ArrayList<pair> empty_squares = get_empty_squares(puzzle);

		// if there are no remaining empty squares we're done
		if (empty_squares.size() == 0) {
			System.out.println("Woohoo, success! Check it out:");
			print_puzzle(puzzle);
			return true;
		}

		// find the most constrained square (one with least remaining values)
		List<Integer>[] remaining_values = get_remaining_values(puzzle);
		ArrayList<Integer> mrv_list = new ArrayList<>();

		for (pair square : empty_squares) {
			mrv_list.add(remaining_values[square.row + 9 * square.col].size());
		}

		// make a list of the squares with the minimum remaining values (mrv)
		ArrayList<pair> mrv_squares = new ArrayList<>();
		int minimum = find_min(mrv_list);
		for (int i = 0; i < mrv_list.size(); ++i) {
			int value = mrv_list.get(i);
			if (value == minimum) {
				mrv_squares.add(empty_squares.get(i));
			}
		}
		pair square = mrv_squares.get((int) (Math.random() * mrv_squares.size()));

		int row = square.row;
		int col = square.col;

		List<Integer> values = remaining_values[col + row * 9];

		while (values.size() != 0) {
			ArrayList<Integer> lcv_list = get_lcv(values, row, col, remaining_values);

			// take the least constraining value
			int min_lcv_list = find_min(lcv_list);
			int value = values.get(lcv_list.indexOf(min_lcv_list));
			values.remove((Integer) value);

			if (forward_check(remaining_values, value, row, col)) {
				puzzle[row][col] = value;
				if (solve_btfch(puzzle))
					return true;
				else
					puzzle[row][col] = 0;
			}
		}
		return false;
	}

	// checks to see if the value being removed is the only one left
	public static boolean forward_check(List<Integer>[] remaining_values, int value, int row, int col) {
		for (int i = 0; i < 9; ++i) {
			if (i == col)
				continue;

			List<Integer> x = remaining_values[row * 9 + i];

			if (x.size() == 1) {
				if (x.get(0) == value)
					return false;
			}
		}

		for (int i = 0; i < 9; ++i) {
			if (i == row)
				continue;

			List<Integer> x = remaining_values[col + 9 * i];
			if (x.size() == 1) {
				if (x.get(0) == value)
					return false;
			}
		}

		int block_row = row / 3;
		int block_col = col / 3;
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {

				if (block_row * 3 + i == row && block_col * 3 + j == col)
					continue;

				List<Integer> x = remaining_values[block_col * 3 + j + (block_row * 3 + i) * 9];
				if (x.size() == 1) {
					if (x.get(0) == value)
						return false;
				}
			}
		}
		return true;
	}

	// counts the number of times a value appears in constrained cells
	public static ArrayList<Integer> get_lcv(List<Integer> values, int row, int col, List<Integer>[] remaining_values) {
		ArrayList<Integer> lcv_list = new ArrayList<>();

		for (Integer value : values) {
			int count = 0;
			for (int i = 0; i < 9; ++i) {
				if (i == col)
					continue;
				List<Integer> x = remaining_values[row * 9 + i];
				if (x.contains(value))
					count += 1;
			}

			for (int i = 0; i < 9; ++i) {
				if (i == row)
					continue;
				List<Integer> x = remaining_values[row * 9 + i];
				if (x.contains(value)) {
					count += 1;
				}
			}

			int block_row = row / 3;
			int block_col = col / 3;

			for (int i = 0; i < 3; ++i) {
				for (int j = 0; j < 3; ++j) {
					if (block_row * 3 + i == row && block_col * 3 + j == col)
						continue;

					List<Integer> x = remaining_values[block_col * 3 + j + (block_row * 3 + i) * 9];
					if (x.contains(value))
						count += 1;
				}
			}
			lcv_list.add(count);
		}
		return lcv_list;
	}

	public static List<Integer>[] get_remaining_values(int[][] puzzle) {
		LinkedList<Integer>[] remaining_values = new LinkedList[81];

		// initialize all remaining values to the full domain
		for (int i = 0; i < 81; ++i) {
			LinkedList<Integer> newList = new LinkedList<>();
			for (int num = 1; num < 10; ++num) {
				newList.add(num);
			}
			remaining_values[i] = newList;
		}

		for (int row = 0; row < puzzle.length; ++row) {
			for (int col = 0; col < puzzle[row].length; ++col) {
				if (puzzle[row][col] != 0) {
					// remove the value from the constrained squares
					int value = puzzle[row][col];
					remaining_values = remove_values(row, col, value, remaining_values);
				}
			}
		}
		return remaining_values;
	}

	// Removes the specified value from constrained squares and returns the new
	// list
	public static LinkedList<Integer>[] remove_values(int row, int col, int value,
			LinkedList<Integer>[] remaining_values) {
		// use a value of zero to indicate that the square is assigned
		LinkedList<Integer> temp = new LinkedList<>();
		temp.add(0);
		remaining_values[col + row * 9] = temp;

		// Remove the specified value from each row, column, and block if it's
		// there
		for (int i = row * 9; i < row * 9 + 9; ++i) {
			LinkedList<Integer> x = remaining_values[i];
			x.remove((Integer) value);
		}

		for (int i = 0; i < 9; ++i)
			remaining_values[col + 9 * i].remove((Integer) value);

		int block_row = row / 3;
		int block_col = col / 3;
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				remaining_values[block_col * 3 + j + (block_row * 3 + i) * 9].remove((Integer)value);
			}
		}

		return remaining_values;
	}

	public static int find_min(ArrayList<Integer> list) {
		int min = Integer.MAX_VALUE;
		for (Integer n : list) {
			min = Math.min(min, n);
		}
		return min;
	}

	public static pair get_random_square(ArrayList<pair> empty_squares) {

		// randomly pick one of the empty squares to expand and return it
		return empty_squares.get((int) (Math.floor(Math.random() * empty_squares.size())));
	}

	// returns the list of empty squares indices for the puzzle
	public static ArrayList<pair> get_empty_squares(int[][] puzzle) {
		ArrayList<pair> empty_squares = new ArrayList<>();

		// scan the whole puzzle for empty cells
		for (int i = 0; i < puzzle.length; ++i) {
			for (int j = 0; j < puzzle[i].length; ++j) {
				if (puzzle[i][j] == 0) {
					empty_squares.add(new pair(i, j));
				}
			}
		}
		return empty_squares;
	}

	// checks the 9x9 block to which the square belongs
	public static boolean check_block(pair square, int value, int[][] puzzle) {
		int block_row = square.row / 3;
		int block_col = square.col / 3;

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				if (i == square.row && j == square.col)
					continue;
				if (puzzle[block_row * 3 + i][block_col * 3 + j] == value)
					return false;
			}
		}
		return true;
	}

	// checks the row of the specified square for the same value
	public static boolean check_row(pair square, int value, int[][] puzzle) {
		for (int i = 0; i < puzzle[0].length; ++i) {
			if (i == square.col) {
				continue;
			}
			if (puzzle[square.row][i] == value) {
				return false;
			}
		}
		return true;
	}

	// checks the column of the specified square for the same value
	public static boolean check_col(pair square, int value, int[][] puzzle) {
		for (int i = 0; i < puzzle.length; ++i) {
			if (i == square.row) {
				continue;
			}
			if (puzzle[i][square.col] == value) {
				return false;
			}
		}
		return true;
	}

	public static void print_puzzle(int[][] puzzle) {
		for (int i = 0; i < puzzle.length; ++i) {
			for (int j = 0; j < puzzle[i].length; ++j) {
				System.out.print(puzzle[i][j] + ",");
			}
			System.out.println();
		}
	}
}
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FarmerWolfGoatCabbage {

	public static void main(String[] args) {
		 //Order=[Man, Goat, Wolf, Cabbage], 0=>left_side, 1=>right_side
		int[] initial_state={0,0,0,0};
		List<int[]> solution = solution_path(initial_state);
		print_solution(solution);
	}

	public static boolean goal_reach(int[] state) {
		int[] arr = { 1, 1, 1, 1 };
		return Arrays.equals(state, arr);
	}

	public static void move(int[] state, int character) {
		if (state[character] == 0)
			state[character] = 1;
		else
			state[character] = 0;
	}

	public static boolean is_safe(int[] child) {
		// Is man with goat?
		if (child[0] == child[1])
			return true;
		// Is goat with wolf?
		else if (child[1] == child[2])
			return false;
		// Is goat with cabbage?
		else if (child[1] == child[3])
			return false;
		else
			return true;
	}

	public static List<int[]> check_safe_child(List<int[]> children, int[] child) {
		if (is_safe(child)){
			children.add(child);
		}
		return children;
	}

	public static List<int[]> expand_state(int[] state) {
		List<int[]> child_nodes = new LinkedList<>();
		int[] child = copy(state);

		// Man has the ability to move alone
		move(child, 0);
		
		child_nodes=check_safe_child(child_nodes, child);

		// For each entity: Goat, Wolf, Cabbage
		for (int i = 1; i < 4; ++i) {
			// If the entity is on the same side of the man, then move the
			// entity as well as man.
			if (state[i] == state[0]) {
				child = copy(state);
				move(child, 0);
				move(child, i);
				check_safe_child(child_nodes, child);
			}
		}
		return child_nodes;
	}

	public static int[] copy(int[] arr) {
		int[] retval = new int[arr.length];

		for (int i = 0; i < arr.length; ++i) {
			retval[i] = arr[i];
		}
		return retval;
	}

	public static List<int[]> solution_path(int[] initial_state) {
		List<int[]> solution = new LinkedList<>();
		solution.add(initial_state);

		int[] next = copy(initial_state);
		while (next != null && !goal_reach(next)) {
			List<int[]> next_level = expand_state(next);
			next = null;

			for (int[] child : next_level) {
				if (!(isPresent(child, solution))) {
					next = child;
					solution.add(next);
					break;
				}
			}
		}
		return solution;
	}

	public static boolean isPresent(int[] child, List<int[]> solution) {
		for (int[] ch : solution) {
			if (Arrays.equals(ch, child)) {
				return true;
			}
		}
		return false;
	}

	public static void print_solution(List<int[]> solution) {
		String[] mapping = { "left", "right" };

		System.out.println("Solution: ");
		for (int[] sol : solution) {
			String str = "[Man: " + mapping[sol[0]] + ", Goat: " + mapping[sol[1]] + ", Wolf: " + mapping[sol[2]]
					+ ", Cabbage: " + mapping[sol[3]] + "]";
			System.out.println(str);
		}

	}

}
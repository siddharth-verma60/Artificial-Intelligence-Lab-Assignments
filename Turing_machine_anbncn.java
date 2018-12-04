import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

//Example input: 

public class Turing_machine_anbncn {

	static HashMap<state, state_action> state_table;
	static char[] tape;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		create_state_table();
		start_turing_machine();

	}

	public static class state {
		String state_name;
		char input;

		public state(String state_name, char input) {
			this.state_name = state_name;
			this.input = input;
		}

		@Override
		public boolean equals(Object obj) {
			state s = (state) obj;
			boolean retval = s.state_name.equals(this.state_name) && this.input == s.input;
			return retval;
		}

		@Override
		public String toString() {
			StringBuilder retval = new StringBuilder();
			retval.append(this.state_name);
			retval.append(", ");
			retval.append(this.input);
			return retval.toString();
		}

		@Override
		public int hashCode() {
			String retval = state_name + input;
			return retval.hashCode();
		}
	}

	public static class state_action {
		char write;
		String direction;
		String next_state;

		public state_action(char write, String direction, String next_state) {
			this.write = write;
			this.direction = direction;
			this.next_state = next_state;
		}

		@Override
		public String toString() {
			return "[write=" + write + ", direction=" + direction + ", next_state=" + next_state + "]";
		}
	}

	public static void create_state_table() {
		state_table = new HashMap<>();

		state_table.put(new state("start", 'a'), new state_action('X', "right", "stateA"));
		state_table.put(new state("start", 'Y'), new state_action('Y', "right", "stateD"));

		state_table.put(new state("stateA", 'Y'), new state_action('Y', "right", "stateA"));
		state_table.put(new state("stateA", 'a'), new state_action('a', "right", "stateA"));
		state_table.put(new state("stateA", 'b'), new state_action('Y', "right", "stateB"));

		state_table.put(new state("stateB", 'Z'), new state_action('Z', "right", "stateB"));
		state_table.put(new state("stateB", 'b'), new state_action('b', "right", "stateB"));
		state_table.put(new state("stateB", 'c'), new state_action('Z', "left", "stateC"));

		state_table.put(new state("stateC", 'a'), new state_action('a', "left", "stateC"));
		state_table.put(new state("stateC", 'Y'), new state_action('Y', "left", "stateC"));
		state_table.put(new state("stateC", 'b'), new state_action('b', "left", "stateC"));
		state_table.put(new state("stateC", 'Z'), new state_action('Z', "left", "stateC"));
		state_table.put(new state("stateC", 'X'), new state_action('X', "right", "start"));

		state_table.put(new state("stateD", 'Y'), new state_action('Y', "right", "stateD"));
		state_table.put(new state("stateD", 'Z'), new state_action('Z', "right", "stateD"));
		state_table.put(new state("stateD", ' '), new state_action(' ', "left", "stop"));

		state_table.put(new state("stop", ' '), null);
	}

	public static void start_turing_machine() {
		Scanner scnr = new Scanner(System.in);
		System.out.println("Input String: ");
		String input = scnr.nextLine();
		tape = new char[200];
		Arrays.fill(tape, ' ');

		for (int i = 0; i < input.length(); ++i) {
			tape[i + 50] = input.charAt(i);
		}

		String current_state = "start";
		int head = 50;

		try {
			while (!current_state.equals("stop")) {
				char action;
				String next_state;

				state currState = new state(current_state, tape[head]);
				state_action stateAction = state_table.get(currState);

				System.out.println("Tip Head: " + (head - 50));
				System.out.println("State: " + currState + " => " + stateAction);

				action = stateAction.write;
				next_state = stateAction.next_state;

				tape[head] = action;
				if (stateAction.direction == "right") {
					head++;
				} else {
					head--;
				}
				current_state = next_state;
			}
		} catch (Exception ex) {
			System.out.println("Output: N");
			return;
		}

		System.out.println("Tip Head: " + (head - 50));
		System.out.println("State: stop");

		// Display:
		System.out.println();
		System.out.print("Output: ");

		if (current_state.equals("stop")) {
			System.out.println("Y");
		} else {
			System.out.println("N");
		}
	}
}
import java.util.*;

//Example input: 101

public class Turing_machine_increment {

	static HashMap<state, state_action> state_table;
	static char[] tape;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		create_state_table();
		start_turing_machine();
		
	}
	
	public static class state{
		String state_name;
		char input;
		
		public state(String state_name, char input) {
			this.state_name = state_name;
			this.input = input;
		}
		@Override
		public boolean equals(Object obj) {
			state s=(state)obj;
			boolean retval=s.state_name.equals(this.state_name) && this.input==s.input;
			return retval;
		}
		@Override
		public String toString() {
			StringBuilder retval=new StringBuilder();
			retval.append(this.state_name);
			retval.append(", ");
			retval.append(this.input);
			return retval.toString();
		}
		@Override
		public int hashCode() {
			String retval=state_name+input;
			return retval.hashCode();
		}
	}
	
	public static class state_action{
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
	
	public static void create_state_table(){
		state_table=new HashMap<>();
		
		state_table.put(new state("start", '0'), new state_action('0', "right", "start"));
		state_table.put(new state("start", '1'), new state_action('1', "right", "start"));
		state_table.put(new state("start", ' '), new state_action(' ', "left", "add"));
		state_table.put(new state("add", '0'), new state_action('1', "left", "done"));
		state_table.put(new state("add", '1'), new state_action('0', "left", "add"));
		state_table.put(new state("add", ' '), new state_action('1', "left", "done"));
		state_table.put(new state("done", '0'), new state_action('0', "left", "done"));
		state_table.put(new state("done", '1'), new state_action('1', "left", "done"));
		state_table.put(new state("done", ' '), new state_action(' ', "right", "stop"));
		state_table.put(new state("stop", ' '), null);
	}

	public static void start_turing_machine(){
		Scanner scnr=new Scanner(System.in);
		System.out.println("Input String: ");
		String input=scnr.nextLine();
		tape=new char[200];
		Arrays.fill(tape, ' ');
		
		for(int i=0;i<input.length();++i){
			tape[i+50]=input.charAt(i);
		}
		
		String current_state="start";
		int head=50;
		
		while(!current_state.equals("stop")){
			char action;
			String next_state;
			
			state currState= new state(current_state, tape[head]);
			state_action stateAction=state_table.get(currState);
			
			System.out.println("Tip Head: "+(head-50));
			System.out.println("State: "+currState+" => "+stateAction);
			
			action=stateAction.write;
			next_state=stateAction.next_state;
			
			tape[head]=action;
			if(stateAction.direction=="right"){
				head++;
			}else{
				head--;
			}
			current_state=next_state;
		}
		
		System.out.println("Tip Head: "+(head-50));
		System.out.println("State: stop");
		
		//Display:
		System.out.println();
		System.out.print("Output: ");
		for(int i=49; i<tape.length;++i){
			System.out.print(tape[i]);
		}
	}
}
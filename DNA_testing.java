import java.io.*;

public class DNA_testing {

	public static void main(String[] args) {

		String inputFile = "/Users/siddharthverma/Desktop/Eclipse/AI_Lab_Assignments/src/test4.txt";
		String DNA_string = readFile(inputFile).replaceAll("\\s", "");

		int max = 0;

		for (int i = 0; i < DNA_string.length(); ++i) {
			if (DNA_string.charAt(i) == 'C') {
				int count = 0;
				while ((i+2) < DNA_string.length() && DNA_string.substring(i, i + 3).equals("CAG")) {
					i += 3;
					count++;
					max = Math.max(count, max);
				}
			}
		}
		System.out.println();
		System.out.println("Maximum number of CAG repeats: " + max);

		String diagnosis = null;
		if (max <= 9) {
			diagnosis = "not human";
		} else if (max <= 35) {
			diagnosis = "normal";
		} else if (max <= 39) {
			diagnosis = "high risk";
		} else if (max <= 180) {
			diagnosis = "Huntington's disease";
		} else {
			diagnosis = "not human";
		}

		System.out.println("Diagnosis: " + diagnosis);
	}

	public static String readFile(String filename) {

		// This will reference one line at a time
		String line = null;
		StringBuilder retval = new StringBuilder();

		try {
			FileReader fileReader = new FileReader(filename);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

//			System.out.println("Input DNA sequence: ");
//			System.out.println();
			while ((line = bufferedReader.readLine()) != null) {
				retval.append(line);
//				System.out.println(line);
			}
			// Always close files.
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			System.out.println("Unable to open file '" + filename + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + filename + "'");
		}

		return retval.toString();
	}

}

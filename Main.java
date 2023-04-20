import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) {
        try {
            // Check if a file was passed as an argument
            if (args.length == 0) {
                throw new FileNotFoundException("No input file was provided");
            }

            String filename = args[0];
            File inputFile = new File(filename);

            // Check if the file has a valid extension
            if (!filename.toLowerCase().endsWith(".arxml")) {
                throw new IllegalArgumentException("Invalid file extension. The file must be a .arxml file.");
            }

            // Check if the file is not empty
            if (inputFile.length() == 0) {
                throw new EmptyFileException("The file is empty.");
            }

            // Read the file
            Scanner input = new Scanner(inputFile);
            ArrayList<String> lines = new ArrayList<>();

            while (input.hasNextLine()) {
                lines.add(input.nextLine());
            }

            input.close();

            // Sort the lines that contain the string "<SHORT-NAME>"
            ArrayList<Integer> shortNameIndexes = new ArrayList<>();

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);

                if (line.contains("<SHORT-NAME>")) {
                    shortNameIndexes.add(i);
                }
            }

            for (int i = 0; i < shortNameIndexes.size(); i++) {
                for (int j = i + 1; j < shortNameIndexes.size(); j++) {
                    String s1 = lines.get(shortNameIndexes.get(i));
                    String s2 = lines.get(shortNameIndexes.get(j));

                    int minLength = Math.min(s1.length(), s2.length());

                    for (int k = 0; k < minLength; k++) {
                        char c1 = s1.charAt(k);
                        char c2 = s2.charAt(k);

                        if (c1 == c2) {
                            continue;
                        } else if (c1 > c2) {
                            Collections.swap(lines, shortNameIndexes.get(i), shortNameIndexes.get(j));
                            int start1=shortNameIndexes.get(i);
                            int end1=shortNameIndexes.get(i);
                            int start2=shortNameIndexes.get(j);
                            int end2=shortNameIndexes.get(j);
                            while(!lines.get(start1).contains("CONTAINER"))
                            {
                                start1 --;
                                start2--;

                                Collections.swap(lines, start1, start2);

                            }
                            while(!lines.get(end1).contains("CONTAINER"))
                            {
                                end1 ++;
                                end2 ++;

                                Collections.swap(lines, end1, end2);

                            }


                            break;
                        } else {
                            break;
                        }
                    }
                }
            }

            // Write the sorted lines to a new file
            String outputFilename = filename.replace(".arxml", "_mod.arxml");
            File outputFile = new File(outputFilename);
            PrintWriter output = new PrintWriter(outputFile);

            for (String line : lines) {
                output.println(line);
            }

            output.close();

            System.out.println("File successfully processed.");
        } catch (FileNotFoundException | IllegalArgumentException | EmptyFileException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

class EmptyFileException extends Exception {
    public EmptyFileException(String message) {
        super(message);
    }
}

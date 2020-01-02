package editor;

import java.io.*;
import java.awt.*;
import java.util.*;
import java.nio.charset.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.FileAlreadyExistsException;
import java.lang.Number;
import java.lang.Integer;
import java.util.regex.*;

public class ProcessImage {
    // Note: The pixels are in row-major order. That is the first n values (where n = width) represent
    // the first row of the image, the 2nd n values represent the 2nd row, and so forth.
    private int[][] red_m;
    private int[][] green_m;
    private int[][] blue_m;

    private int maximumColorValue_m; // This value will always become 255
    private int width_m;
    private int height_m;

    private void createNewImage(Scanner scanner) {

        //int stuff = Integer.parseInt(getNext(scanner));
        //System.out.println(stuff);

        // for (int row = 0; row < height_m; row++) {
        //   for (int column = 0; column < width_m; column++) {
        //     //red_m[column][row] = Integer.parseInt(getNext(scanner));
        //     System.out.println("Red: " + this.red_m);
        //     //green_m[column][row] = Integer.parseInt(getNext(scanner));
        //     System.out.println("Green: " + this.green_m);
        //     //blue_m[column][row] = Integer.parseInt(getNext(scanner));
        //     System.out.println("Blue: " + this.blue_m);
        //   }
        // }
    }

    public void outputFile(String outputFileName) {
        try {
            // Path outputFilePath = Paths.get(outputFileName); // Creat an output file path
            // PrintWriter output = new PrintWriter(Files.newBufferedWriter(outputFilePath, StandardCharsets.US_ASCII));
            //   // Make it so we can write to this output file

            // // When we are printing we are going to follow the same process that we did to get the data
            // output.println("P6");
            // output.println(" ");
            // output.println(this.width_m);
            // output.println(" ");
            // output.println(this.height_m);
            // output.println(" ");
            // output.println(this.maximumColorValue_m);

            // // Now we are outputting the pixels
            // for(int row = 0; row < this.height_m; row++) {
            //   for(int column = 0; column< this.width_m; column++) {
            //     output.println(this.red_m[column][row]);
            //     output.println(this.green_m[column][row]);
            //     output.println(this.blue_m[column][row]);
            //   }
            // }

            // output.close();

        }
        catch(Exception e) {
            //System.out.println(e);
        }
    }

    private void processHeader(Scanner scanner)  {
        // Header ::= MagicNumber Separator Width Separator Height Separator MaxColorValue \s
    }

    private void processSeparator(Scanner scanner) {
        // Separator ::= \s+ Comment? \s* | Comment \s+
        // Remember: \s = "whitespace character": space, tab, newline, carriage return, vertical tab
        // Remember: ? = once or none
        // Remember: * = zero or more times
        // Remember: | = or
        // Remember: + = one or more
    }

    private void processComment(Scanner scanner) {
        //Comment ::= #[^\n]*\n
        // ^^ Means that comments start with # and will always end with an endline

    }

    private void processWidth(Scanner scanner) {
        // Width ::= \d+
    }

    private void processHeight(Scanner scanner) {
        // Height ::= \d+
    }

    private void processPixels(Scanner scanner) {
        // Pixels ::= (Pixel (Separator Pixel)* )?
    }

    private void processPixel(Scanner scanner) {
        // Pixel ::= RedColorValue Separator GreenColorValue Separator BlueColorValue
    }

    private void processRedColorValue(Scanner scanner) {
        // RedColorValue ::= Number
    }

    private void processGreenColorValue(Scanner scanner) {
        // GreenColorValue ::= Number
    }

    private void processBlueColorValue(Scanner scanner) {
        // BlueColorValue ::= Number
    }

    private void processMaxColorValue(Scanner scanner) {
        // MaxColorValue ::= 255
    }

    ProcessImage(String inputFileName) {
        try {
            // Path inputFilePath = Paths.get(inputFileName);
            // Scanner scanner = new Scanner(inputFilePath, StandardCharsets.US_ASCII.displayName());

            // //PPM_File ::= Header Pixels Separator*
            //   // Remember that * means zero or more times
            // processHeader(scanner);
            // processPixels(scanner);
            // // Handle potential Separator


            // scanner.next(); // Get past the MagicNumber 'P6' //

            // Set the private class variables
            // width_m = Integer.parseInt(getNext(scanner));
            // height_m = Integer.parseInt(getNext(scanner));
            // System.out.println("height" + height_m);
            // System.out.println("width" + width_m);
            //maximumColorValue_m = Integer.parseInt(getNext(scanner)); // maximumColorValue will always be 255
            // red_m = new int[width_m][height_m];
            // green_m = new int[width_m][height_m];
            // blue_m = new int[width_m][height_m];

            //createNewImage(scanner);
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
}

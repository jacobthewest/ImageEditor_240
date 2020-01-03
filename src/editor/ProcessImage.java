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

import editor.Pixel;

public class ProcessImage {
    // Note: The pixels are in row-major order. That is the first n values (where n = width) represent
    // the first row of the image, the 2nd n values represent the 2nd row, and so forth.
    private int[][] red_m;
    private int[][] green_m;
    private int[][] blue_m;
    private Pixel[][] importedImageAsArray;

    private int maxColorValue_m; // This value will always become 255
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

        // MagicNumber
        scanner.next(); // MagicNumber will be "P3"

        // Separator
        //processSeparator(scanner); We haven't needed to make this yet.

        // Width
        processWidth(scanner);

        // Separator
        //processSeparator(scanner); We haven't needed to make this yet.

        // Height
        processHeight(scanner);

        // Separator
        //processSeparator(scanner); We haven't needed to make this yet.

        // MaxColorValue
        processMaxColorValue(scanner);

        // \s
        scanner.next(); // Skip over the white space character
    }

    private void processSeparator(Scanner scanner) {
        // Separator ::= \s+ Comment? \s* | Comment \s+
        // Remember: \s = "whitespace character": space, tab, newline, carriage return, vertical tab
        // Remember: ? = once or none
        // Remember: * = zero or more times
        // Remember: | = or
        // Remember: + = one or more

        String currentCharacter = scanner.next();
        System.out.println(currentCharacter);
        System.out.println(scanner.next());
        System.out.println(scanner.next());
        System.out.println(scanner.next());
        System.out.println(scanner.next());

        if(Pattern.matches("\\s+", currentCharacter)) {
            System.out.println("Good, now we will go to the comment place.");
            processWhiteSpaceCharacter(scanner, currentCharacter); // Current Character is the whitespace character
            // currentCharacter is no longer a whitespace character, so we need to process the comment
            processComment(scanner);
        }
        else if (scanner.hasNext("#")){
            System.out.println("looks like this is just a comment\n Here is the char " + currentCharacter);
            processComment(scanner);
        }
        else {
            System.out.println("none of these worked. Here is the characterr " + currentCharacter);
        }
    }

    private void processWhiteSpaceCharacter(Scanner scanner, String currentCharacter) {
        // Will be \s+ or \s*
        // Remember: \s = whitespace character
        // Remember: + = one or more and * = zero or more times

        while(Pattern.matches("\\s+", currentCharacter)) {
            currentCharacter = scanner.next();
        }
    }

    private void processComment(Scanner scanner) {
        //Comment ::= #[^\n]*\n
        // ^^ Means that comments start with # and will always end with an endline

        boolean currentCharacterIsTheNewLine = false;

        while (!currentCharacterIsTheNewLine) {
            String newCharacter = scanner.next();
            if(Pattern.matches("\\n", newCharacter))
                currentCharacterIsTheNewLine = true;
        }

        // Now the current character is the new line and is ready for the next char
    }

    private void processWidth(Scanner scanner) {
        // Width ::= \d+
        this.width_m = Integer.parseInt(scanner.next());
    }

    private void processHeight(Scanner scanner) {
        // Height ::= \d+
        this.height_m = Integer.parseInt(scanner.next());
    }

    private void processPixels(Scanner scanner) {
        // Pixels ::= (Pixel (Separator Pixel)* )?

        importedImageAsArray = new Pixel[this.height_m][this.width_m];

        try {
            for (int row = 0; row < this.height_m; row++) {
                for (int col = 0; col < this.width_m; col++) {
                    processPixel(scanner, row, col);
                }
            }
        }
        catch(Exception e) {
            System.out.println("Failed to process all of the pixels");
        }
    }

    private void processPixel(Scanner scanner, int row, int col) {
        // Pixel ::= RedColorValue Separator GreenColorValue Separator BlueColorValue
        int tempRedInt = 0;
        int tempGreenInt = 0;
        int tempBlueInt = 0;

        try {
            tempRedInt = Integer.parseInt(scanner.next());
        }
        catch(NoSuchElementException ignored) {}
        try {
            tempGreenInt = Integer.parseInt(scanner.next());

        }
        catch(NoSuchElementException ignored) {}
        try {
            tempBlueInt = Integer.parseInt(scanner.next());

        }
        catch(NoSuchElementException ignored) {}

        System.out.println("Red: " + tempRedInt + " Green: " + tempGreenInt + " Blue: " + tempBlueInt);

        this.importedImageAsArray[row][col] = new Pixel(tempRedInt, tempGreenInt, tempBlueInt);

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
        this.maxColorValue_m = Integer.parseInt(scanner.next());
    }

    // Constructor
    ProcessImage(String inputFileName) {
        try {
            FileReader fileReader = new FileReader(inputFileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            Scanner scanner = new Scanner(bufferedReader);
            scanner.useDelimiter("(\\s+)(#[^\\n]*\\n)?(\\s*)|(#[^\\n]*\\n)(\\s*)");
                //^^ Used to ignore Separators

            //PPM_File ::= Header Pixels Separator*
               // Remember that * means zero or more times
            processHeader(scanner);
            processPixels(scanner);
            scanner.close();
            // Handle potential Separator


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
            System.out.println("^^That is the one");
        }
    }
}

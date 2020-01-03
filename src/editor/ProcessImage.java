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
import java.lang.StringBuilder;
import java.util.regex.*;

import editor.Pixel;

public class ProcessImage {
    private Pixel[][] importedImageAsArray_m;

    private int maxColorValue_m; // This value will always become 255
    private int width_m;
    private int height_m;

    public void writeToOutputFile(String outputFileName, ProcessImage importedImage, Pixel[][] modifiedPixelArray) {
        try {
            // Find the correct capacity that we need for our string builder.
            int capacity = 100 * importedImage.getHeight() * importedImage.getWidth(); // We use 100 just to be safe.

            StringBuilder output = new StringBuilder(capacity);

            String new_line = System.getProperty("line.separator");

            output.append("P3" + new_line);
            output.append(importedImage.getWidth());
            output.append(" ");
            output.append(importedImage.getHeight() + new_line);

            System.out.println("hEre is the max color: " + importedImage.getMaxColorValue());


            for(int row = 0; row < this.height_m; row++) {
                for(int col = 0; col < this.width_m; col++) {
                    output.append(modifiedPixelArray[row][col].getRed());
                    output.append(" ");
                    output.append(modifiedPixelArray[row][col].getGreen());
                    output.append(" ");
                    output.append(modifiedPixelArray[row][col].getBlue());
                    output.append(" ");
                }
            }
            output.append(" ");

            System.out.println(output);

            // Write to the file
            FileWriter fileWriter = new FileWriter(outputFileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            fileWriter.append(output); // This writes to the file
            fileWriter.close();
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

        this.importedImageAsArray_m = new Pixel[this.height_m][this.width_m];

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

        this.importedImageAsArray_m[row][col] = new Pixel(tempRedInt, tempGreenInt, tempBlueInt);

    }

    private void processMaxColorValue(Scanner scanner) {
        // MaxColorValue ::= 255
        this.maxColorValue_m = Integer.parseInt(scanner.next());
    }

    public int getWidth() {
        return this.width_m;
    }

    public int getHeight() {
        return this.height_m;
    }

    public int getMaxColorValue() {
        return  this.maxColorValue_m;
    }

    public Pixel[][] getPixelArray() {
        return this.importedImageAsArray_m;
    }

    public Pixel[][] invert (ProcessImage importedImage) {
        int num_cols = importedImage.getWidth();
        int num_rows = importedImage.getHeight();
        Pixel[][] modifyThesePixels = importedImage.getPixelArray();

        for(int row = 0; row < num_rows; row++) {
            for(int col = 0; col < num_cols; col++) {
                modifyThesePixels[row][col].invert();
            }
        }
        return modifyThesePixels;
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
            scanner.close(); // This handles the Separator*


        }
        catch(Exception e) {
            System.out.println(e);
            System.out.println("^^That is the one");
        }
    }
}

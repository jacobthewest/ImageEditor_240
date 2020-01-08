package editor;

import java.io.*;
import java.util.*;
import java.lang.Integer;
import java.lang.StringBuilder;

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
            output.append(importedImage.getMaxColorValue() + new_line);


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

            // Write to the file
            FileWriter fileWriter = new FileWriter(outputFileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            fileWriter.append(output); // This writes to the file
            fileWriter.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    private void processHeader(Scanner scanner)  {
        // Header ::= MagicNumber Separator Width Separator Height Separator MaxColorValue \s

        // MagicNumber
        scanner.next(); // MagicNumber will be "P3"

        // Width
        processWidth(scanner);

        // Height
        processHeight(scanner);

        // MaxColorValue
        processMaxColorValue(scanner);
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

    public Pixel[][] grayscale (ProcessImage importedImage) {
        int num_cols = importedImage.getWidth();
        int num_rows = importedImage.getHeight();
        Pixel[][] modifyThesePixels = importedImage.getPixelArray();

        for(int row = 0; row < num_rows; row++) {
            for(int col = 0; col < num_cols; col++) {
                modifyThesePixels[row][col].grayscale();
            }
        }
        return modifyThesePixels;
    }

    public Pixel[][] emboss (ProcessImage importedImage) {
        int num_cols = importedImage.getWidth();
        int num_rows = importedImage.getHeight();
        Pixel[][] modifyThesePixels = importedImage.getPixelArray();
        Pixel[][] originalPixels = importedImage.getPixelArray();

        for(int row = num_rows - 1; row > -1; row--) {
            for(int col = num_cols - 1; col > -1; col--) {
                if ((row > 0) && (col > 0)) {
                    if (
                        originalPixels[row-1][col].getRed() < 0 ||
                        originalPixels[row-1][col].getGreen() < 0 ||
                        originalPixels[row-1][col].getBlue() < 0 ||
                        originalPixels[row][col-1].getRed() < 0 ||
                        originalPixels[row][col-1].getGreen() < 0 ||
                        originalPixels[row][col-1].getBlue() < 0
                    ) {
                        originalPixels[row][col].set128();
                    }
                    else {
                        Pixel upperLeftPixel = originalPixels[row - 1][col - 1];
                        modifyThesePixels[row][col].emboss(upperLeftPixel);
                    }
                }
                else {
                    modifyThesePixels[row][col].set128();
                }
            }
        }
        return modifyThesePixels;
    }

    public Pixel[][] motionBlur(ProcessImage importedImage, int n) {
        int num_cols = importedImage.getWidth();
        int num_rows = importedImage.getHeight();
        int imageWidthInIndexPositions = num_cols - 1;
        Pixel[][] modifyThesePixels = importedImage.getPixelArray();
        Pixel[][] originalPixels = importedImage.getPixelArray();

        for(int row = 0; row < num_rows; row++) {
            for(int col = 0; col < num_cols; col++) {
               modifyThesePixels[row][col].motionBlur(originalPixels, row, col, imageWidthInIndexPositions, n);
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
            scanner.close();


        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
}

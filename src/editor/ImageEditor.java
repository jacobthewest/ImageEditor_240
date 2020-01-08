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

import editor.ProcessImage;

public class ImageEditor {
	public static void main(String args[]) {
        // Command example to run the program "java ImageEditor bike.ppm bike-inverted.ppm invert"
        try {
            String INPUT_FILE_NAME = args[0];
            String OUTPUT_FILE_NAME = args[1];

            ProcessImage importedImage = new ProcessImage(INPUT_FILE_NAME);

            // What action wants to be performed
            switch(args[2].toUpperCase()) {
                case "INVERT":
                    Pixel[][] invertedPixelArray = importedImage.invert(importedImage);
                    importedImage.writeToOutputFile(OUTPUT_FILE_NAME, importedImage, invertedPixelArray);
                    break;
                case "GRAYSCALE":
                    Pixel[][] grayscalePixelArray = importedImage.grayscale(importedImage);
                    importedImage.writeToOutputFile(OUTPUT_FILE_NAME, importedImage, grayscalePixelArray);
                    break;
                case "EMBOSS":
                    Pixel[][] embossPixelArray = importedImage.emboss(importedImage);
                    importedImage.writeToOutputFile(OUTPUT_FILE_NAME, importedImage, embossPixelArray);
                    break;
                case "MOTIONBLUR":
                    int n = Integer.parseInt(args[3]);
                    Pixel[][] motionBlurPixelArray = importedImage.motionBlur(importedImage, n);
                    importedImage.writeToOutputFile(OUTPUT_FILE_NAME, importedImage, motionBlurPixelArray);
                    break;
                default:
                    break;
            }
        }
        catch(Exception e) {
            System.out.println("USAGE: java ImageEditor in-file out-file (grayscale|invert|emboss|motionblur motion-blur-length)");
        }
	}
}

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

            ProcessImage newImage = new ProcessImage(INPUT_FILE_NAME);

            //newImage.writeToOutputFile(OUTPUT_FILE_NAME);
            // What action wants to be performed
            switch(args[2].toUpperCase()) {
                case "INVERT":
                    //ProcessImage inverted_image = new ProcessImage(INPUT_FILE_NAME);
                    break;
                case "GRAYSCALE":
                    break;
                case "EMBOSS":
                    break;
                case "MOTIONBLUR":
                    break;
                default:
                    break;
            }
        }
        catch(Exception e) {
            System.out.println("Jacob, ERROR: " + e);
            System.out.println("USAGE: java ImageEditor in-file out-file (grayscale|invert|emboss|motionblur motion-blur-length)");
        }
	}
}

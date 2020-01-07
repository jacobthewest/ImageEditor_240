package editor;

import java.awt.*;
import java.lang.Math.*;
import java.rmi.UnexpectedException;

public class Pixel {

    private int red_m;
    private int green_m;
    private int blue_m;

    public int getRed() {
        return this.red_m;
    }

    public int getGreen() {
        return this.green_m;
    }

    public int getBlue() {
        return this.blue_m;
    }

    public void invert() {
        int tempRed = this.red_m;
        int tempGreen = this.green_m;
        int tempBlue = this.blue_m;

        int invertedRed = 255 - tempRed;
        int invertedGreen = 255 - tempGreen;
        int invertedBlue = 255 - tempBlue;

        this.red_m = invertedRed;
        this.green_m = invertedGreen;
        this.blue_m = invertedBlue;
    }

    public void grayscale() {
        int tempGrayscale = (this.red_m + this.green_m + this.blue_m) / 3;
        this.red_m = tempGrayscale;
        this.green_m = tempGrayscale;
        this.blue_m = tempGrayscale;
    }

    public void emboss(Pixel upperLeftPixel) {
        // Work on the calculations with the upper left
        int redDiff = this.red_m - upperLeftPixel.getRed();
        int greenDiff = this.green_m - upperLeftPixel.getGreen();
        int blueDiff = this.blue_m - upperLeftPixel.getBlue();

        int absRedDiff = Math.abs(redDiff);
        int absGreenDiff = Math.abs(greenDiff);
        int absBlueDiff = Math.abs(blueDiff);

        // Find the largest difference (positive or negative). We will call this maxDifference.
        int maxDifference = 0;
        boolean useRedAbsDif = false;

        // Red is the largest dif
        if ((absRedDiff > absGreenDiff) && (absRedDiff > absBlueDiff)) {
            maxDifference = redDiff;
        }
        // Green is the largest dif
        else if ((absGreenDiff > absRedDiff) && (absGreenDiff > absBlueDiff)) {
            maxDifference = greenDiff;
        }
        // Blue is the largest dif
        else if ((absBlueDiff > absRedDiff) && (absBlueDiff > absGreenDiff)) {
            maxDifference = blueDiff;
        }
        // Blue and Green are equal and larger than Red
        else if ((absBlueDiff == absGreenDiff) && (absBlueDiff > absRedDiff) && (absGreenDiff > absRedDiff)) {
            // Favor Green
            maxDifference = greenDiff;
        }
        // Red and Green are equal and larger Blue
        else if ((absRedDiff == absGreenDiff) && (absRedDiff > absBlueDiff) && (absGreenDiff > absBlueDiff)) {
            // Favor Red
            maxDifference = redDiff;
        }
        // Red and Blue are equal and larger Green
        else if ((absRedDiff == absBlueDiff) && (absRedDiff > absGreenDiff) && (absBlueDiff > absGreenDiff)) {
            // Favor Red
            maxDifference = redDiff;
        }
        // All are equal
        else {
            //useRedAbsDif = true;
            maxDifference = redDiff;
        }

        // Find the largest difference (positive or negative). We will call this maxDifference. We
        // then add 128 to maxDifference. If there are multiple equal differences with differing signs
        // (e.g. -3 and 3), favor the red difference first, then green, then blue.
        // newPixelValues = 128 + maxDifference

        int newPixelValues = 128 + maxDifference;
//        if (useRedAbsDif) {
//            newPixelValues = absRedDiff;
//        }

        // If needed, we then scale v to be between 0 and 255 by doing the following:
            // If v < 0, then we set v to 0.
            // If v > 255, then we set v to 255.

        if (newPixelValues < 0) {
            newPixelValues = 0;
        }
        else if (newPixelValues > 255) {
            newPixelValues = 255;
        }

        // The pixel’s red, green, and blue values are all set to v.
        this.red_m = newPixelValues;
        this.green_m = newPixelValues;
        this.blue_m = newPixelValues;
    }

    public void set128() {
        int newPixelValues = 128;

        this.red_m = newPixelValues;
        this.green_m = newPixelValues;
        this.blue_m = newPixelValues;
    }

    public Pixel(int red, int green, int blue) {
        this.red_m = red;
        this.green_m = green;
        this.blue_m = blue;
    }
}

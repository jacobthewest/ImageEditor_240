package editor;

import java.awt.*;


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

    public void emboss() {

    }

    public Pixel(int red, int green, int blue) {
        this.red_m = red;
        this.green_m = green;
        this.blue_m = blue;
    }
}

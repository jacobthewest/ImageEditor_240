package editor;

import java.awt.*;

public class Pixel {

    private Color red_m;
    private Color green_m;
    private Color blue_m;

    public Color getRed() {
        return this.red_m;
    }

    public Color getGreen() {
        return this.green_m;
    }

    public Color getBlue() {
        return this.blue_m;
    }

    public void invert() {
        int tempRed = this.red_m.getRed();
        int tempGreen = this.green_m.getGreen();
        int tempBlue = this.blue_m.getBlue();

        int invertedRed = 255 - tempRed;
        int invertedGreen = 255 - tempGreen;
        int invertedBlue = 255 - tempBlue;

        this.red_m = new Color(invertedRed);
        this.green_m = new Color(invertedGreen);
        this.blue_m = new Color(invertedBlue);

    }


    public Pixel(int red, int green, int blue) {
        this.red_m = new Color(red);
        this.green_m = new Color(green);
        this.blue_m = new Color(blue);
    }
}

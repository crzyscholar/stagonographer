import java.awt.*;
import java.awt.image.BufferedImage;

public class Pixel {
    private int x;
    private int y;
    private Color color;

    public Pixel(int x, int y, Color color){
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    private static Pixel[] getPixelArray(BufferedImage imageToEncrypt){
        int height = imageToEncrypt.getHeight();
        int width = imageToEncrypt.getWidth();
        Pixel[] pixels = new Pixel[height*width];
        int count = 0;
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                Color colorToAdd = new Color(imageToEncrypt.getRGB(i,j));
                pixels[count] = new Pixel(i, j, colorToAdd);
                count++;
            }
        }
        return pixels;
    }
}

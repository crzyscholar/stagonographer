import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DecryptLSB {
    public static void Decrypt(){
        String directory = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
        String newImageFileString = directory + "\\export.png";
        //TODO add jpeg and pdf(optional) file types too
        File newImageFile = new File(newImageFileString);
        try{
            BufferedImage image = ImageIO.read(newImageFile);
            Pixel[] pixels = GetPixelArray(image);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private static Pixel[] GetPixelArray(BufferedImage imageToEncrypt){
        int height = imageToEncrypt.getHeight();
        int width = imageToEncrypt.getWidth();
        Pixel[] pixels = new Pixel[height*width];
        System.out.println(DecodeMessageFromPixels(pixels));
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

    private static String DecodeMessageFromPixels(Pixel[] pixels){
        boolean completed = false;
        int pixelIndex = 0;
        StringBuilder messageBuilder = new StringBuilder("");
        while(!completed){
            Pixel[] pixelsToRead = new Pixel[3];
            for(int i = 0; i < 3; i++){
                pixelsToRead[i] = pixels[pixelIndex];
                pixelIndex++;
            }
            messageBuilder.append(ConvertPixelsToCharacter(pixelsToRead));
            if(IsEndOfMessage(pixelsToRead[2])){
                completed = true;
            }
        }
        return messageBuilder.toString();
    }

    private static char ConvertPixelsToCharacter(Pixel[] pixelsToRead){
        ArrayList<String> binaryValues = new ArrayList<String>();
        for(int i = 0; i < pixelsToRead.length; i++){
            String[] currentBinary = TurnPixelIntegersToBinary(pixelsToRead[i]);
            binaryValues.add(currentBinary[0]);
            binaryValues.add(currentBinary[1]);
            binaryValues.add(currentBinary[2]);
        }
        return ConvertBinaryValuesToCharacter(binaryValues);
    }

    private static String[] TurnPixelIntegersToBinary(Pixel pixel){
        String[] values = new String[3];
        values[0] = Integer.toBinaryString(pixel.getColor().getRed());
        values[1] = Integer.toBinaryString(pixel.getColor().getGreen());
        values[2] = Integer.toBinaryString(pixel.getColor().getBlue());
        return values;
    }

    private static char ConvertBinaryValuesToCharacter(ArrayList<String> binaryValues){
        StringBuilder endBinary = new StringBuilder("");
        for(int i = 0; i < binaryValues.size() - 1; i++){
            endBinary.append(binaryValues.get(i).charAt(binaryValues.get(i).length() - 1));
        }
        String endBinaryString = endBinary.toString();
        String noZeros = RemovePaddedZeros(endBinaryString);
        int ascii = Integer.parseInt(noZeros, 2);
        return (char)ascii;
    }

    private static String RemovePaddedZeros(String endBinaryString){
        StringBuilder builder = new StringBuilder(endBinaryString);
        int paddedZeros = 0;
        for(int i = 0; i < builder.length(); i++){
            if(builder.charAt(i) == '0'){
                paddedZeros++;
            }
            else{
                break;
            }
        }
        for(int i = 0; i < paddedZeros; i++){
            builder.deleteCharAt(0);
        }
        return builder.toString();
    }

    private static boolean IsEndOfMessage(Pixel pixel){
        if(TurnPixelIntegersToBinary(pixel)[2].endsWith("1")){
            return false;
        }
        return true;
    }
}

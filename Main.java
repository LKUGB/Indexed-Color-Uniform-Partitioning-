
/**********************************************************************************
 * Author: Shiming Jin
 * Project Name: Indexed Color (Uniform Partitioning)
 * Date: 2/29
 * Command line order: width, height, input file (.raw), output file (.bmp), # of colors in the color table
 ************************************************************************************/

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws IOException {
        int w = Integer.parseInt(args[0]);
        int ht = Integer.parseInt(args[1]);
        int N = Integer.parseInt(args[4]);
        RGB[][] pixels = new RGB[ht][w];

        try {
            InputStream is = new FileInputStream(args[2]);
            // create data input stream
            DataInputStream input = new DataInputStream(is);
            for (int i = 0; i < ht; i++){
                for (int j = 0; j < w; j++) {
                    RGB pixel = new RGB();
                    pixel.setR(input.readUnsignedByte());
                    pixel.setG(input.readUnsignedByte());
                    pixel.setB(input.readUnsignedByte());
                    pixels[i][j] = pixel;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //get colorList
        RGB[] colorList = Main.getColorList(N);

        //use uniform partitioning to complete color quantization;
        int[][] indexes = UniformPartition.UniPar(pixels, colorList, N);

        //output bmp file
        ReadWriteBMP.makeBMPFile(indexes, w, ht, N, colorList, args[3]);
    }

    //get ColorList
    public static RGB[] getColorList(int N){
        int rSize = 2, gSize = 2, bSize = 2;
        int colorNum = (int) (Math.log10(N) / Math.log10(2));
        rSize = (int) Math.pow(rSize,colorNum / 3);
        gSize = (int) Math.pow(gSize,colorNum / 3);
        bSize = (int) Math.pow(bSize,colorNum / 3);
        if(colorNum % 3 == 1){
            rSize *= 2;
        }
        else if(colorNum % 3 == 2){
            rSize *= 2;
            gSize *= 2;
        }

        //populate array containing the averages
        int[] redAverages = new int[rSize];
        int[] greenAverages = new int[gSize];
        int[] blueAverages = new int[bSize];
        for(int i = 0; i < rSize; i++){
            redAverages[i] = ((256/rSize)/2) + ((256/rSize) * i);
        }
        for(int i = 0; i < gSize; i++){
            greenAverages[i] = ((256/gSize)/2) + ((256/gSize) * i);
        }
        for(int i = 0; i < bSize; i++){
            blueAverages[i] = ((256/bSize)/2) + ((256/bSize) * i);
        }

        RGB[] colorList = new RGB[N];
        //populate colorList
        int count = 0;
        for (int i = 0; i < rSize; i++) {
            for (int j = 0; j < gSize; j++) {
                for (int k = 0; k < bSize; k++) {
                    colorList[count] = new RGB();
                    colorList[count].setR(redAverages[i]);
                    colorList[count].setG(greenAverages[j]);
                    colorList[count].setB(blueAverages[k]);
                    count++;
                }
            }
        }
        return colorList;
    }
}

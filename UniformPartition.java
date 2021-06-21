public class UniformPartition {

    public static int[][] UniPar(RGB[][] pixels, RGB[] colorTable, int N){
        int height = pixels.length;
        int width = pixels[0].length;
        int rSize = 2, gSize = 2, bSize = 2;
        int colorNum = (int) (Math.log10(N) / Math.log10(2));
        rSize = (int) Math.pow(rSize,colorNum/3);
        gSize = (int) Math.pow(gSize,colorNum/3);
        bSize = (int) Math.pow(bSize,colorNum/3);

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

        //change the R,G,B values of the pixels with the average values
        for(int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                //set red average
                for (int k = 0; k < rSize; k++) {
                    if (pixels[i][j].getR() >= k * (256 / rSize) && pixels[i][j].getR() <= (k + 1) * (256 / rSize) - 1) {
                        pixels[i][j].setR(redAverages[k]);
                    }
                }
                //set green average
                for (int k = 0; k < gSize; k++) {
                    if (pixels[i][j].getG() >= k * (256 / gSize) && pixels[i][j].getG() <= (k + 1) * (256 / gSize) - 1) {
                        pixels[i][j].setG(greenAverages[k]);
                    }
                }
                //set blue average
                for (int k = 0; k < bSize; k++) {
                    if (pixels[i][j].getB() >= k * (256 / bSize) && pixels[i][j].getB() <= (k + 1) * (256 / bSize) - 1) {
                        pixels[i][j].setB(blueAverages[k]);
                    }
                }
            }
        }

        //traverse through the color table to find which color matches the transformed pixels
        //record the matched index in the color table and save it in the index array
        int[][] indexes = new int[height][width];
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                for(int k = 0; k < colorTable.length; k++) {
                    if (pixels[i][j].getR() == colorTable[k].getR()
                            && pixels[i][j].getG() == colorTable[k].getG()
                            && pixels[i][j].getB() == colorTable[k].getB()) {
                        indexes[i][j] = k;
                    }
                }
            }
        }
        return indexes;
    }
}

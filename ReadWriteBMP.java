import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ReadWriteBMP {
    //Header file provided by: Dr. Burg
    public static void makeBMPFile(int[][] indexes, int w, int ht, int N, RGB[] colorList, String outputFileName)throws IOException {
        DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(outputFileName));
        try (outputStream) {
            byte[] twoBytes = new byte[2];
            twoBytes[0] = 'B';
            twoBytes[1] = 'M';
            outputStream.write(twoBytes);

            int fileSize = w * ht;
            outputStream.writeInt(Integer.reverseBytes(fileSize));

            short zero = 0;
            outputStream.writeShort(Short.reverseBytes(zero));
            outputStream.writeShort(Short.reverseBytes(zero));

            int offsetToPixelData = 54 + N*4;
            outputStream.writeInt(Integer.reverseBytes(offsetToPixelData));

            int headerSize = 40;
            outputStream.writeInt(Integer.reverseBytes(headerSize));
            outputStream.writeInt(Integer.reverseBytes(w));
            outputStream.writeInt(Integer.reverseBytes(ht));

            short one = 1;
            outputStream.writeShort(Short.reverseBytes(one));

            short bitsPerChannel = 8;
            outputStream.writeShort(Short.reverseBytes(bitsPerChannel));

            int compressionType = 0;
            outputStream.writeInt(Integer.reverseBytes(compressionType));

            int compression = 0;
            outputStream.writeInt(Integer.reverseBytes(compression));

            outputStream.writeInt(Integer.reverseBytes(w));
            outputStream.writeInt(Integer.reverseBytes(ht));

            outputStream.writeInt(Integer.reverseBytes(N));

            outputStream.writeInt(Integer.reverseBytes(N));

            //print all colors in the colorList in bytes
            byte[] fourBytes = new byte[4];
            for (int i = 0; i < N; i++) {
                fourBytes[0] = (byte) colorList[i].getR();
                fourBytes[1] = (byte) colorList[i].getG();
                fourBytes[2] = (byte) colorList[i].getB();
                fourBytes[3] = (byte) 0;
                outputStream.write(fourBytes);
            }

            //convert integer index array to byte index array
            byte [] byteIndexes = new byte[w*ht];
            int k = 0;
            for (int i = ht - 1; i > 0; i--) {
                for (int j = 0; j < w; j++) {
                    byteIndexes[k] = (byte) indexes[i][j];
                    k++;
                }
            }
            outputStream.write(byteIndexes);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        outputStream.close();
    }
}

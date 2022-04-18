import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.lang.*;


public class BitsIO {
    static int counter = 0;
    private static byte currByte = 0;
    private static int bitsInCurrByte = 0;
    private static FileOutputStream outputStream;
    private static byte[] input;
    private static int inputBitIt = 0;

    public static void initOutputFile(String path) throws IOException {
        File outputFile = new File(path);
        outputFile.createNewFile();
        outputStream = new FileOutputStream(outputFile);
    }


    public static void output_bit(boolean bit) throws IOException {
        //counter++;
        //System.out.println("COUNTER: " + counter);
        currByte <<= 1;
        currByte += bit ? 1 : 0;
        System.out.println("BIT:"+(bit ? 1 : 0));
        bitsInCurrByte++;
        if(bitsInCurrByte == 8) {
            System.out.print("out byte: " + currByte);
            outputByte(currByte);
            currByte = 0;
            bitsInCurrByte = 0;
        }
    }
    public static void finishByte() throws IOException {
        currByte <<= (8 - bitsInCurrByte);
        outputByte(currByte);
    }

    public static void outputByte(byte b) throws IOException {
        outputStream.write(b);
    }

    public static byte[] getInput(String path) throws IOException {
        input = Files.readAllBytes(Paths.get(path));
        return input;
    }

    public static boolean getInputBit() {
        //counter++;
        //System.out.println("COUNTER: " + counter);
        byte b = input[inputBitIt/8];
        //System.out.println("byte: " + b);
        //System.out.println("it%8 = " + inputBitIt%8);
        //System.out.println("shifted byte = " + ((b >> (7 - inputBitIt%8)) & 1));
        boolean bit = (((b >> (7 - inputBitIt%8)) & 1) == 1);
        //System.out.println("BIT:"+(bit ? 1 : 0));
        inputBitIt++;
        return bit;
    }

    public static boolean isInput() {
        return input.length*8>inputBitIt;
    }
}

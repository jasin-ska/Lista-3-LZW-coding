package lzw;

import lzw.Codes.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.lang.*;


public class BitsIO {
    private static byte currByte = 0;
    private static int bitsInCurrByte = 0;
    private static FileOutputStream outputCodedStream;
    private static FileOutputStream outputDecodedStream;
    private static byte[] input;
    private static int inputBitIt = 0;
    private static Code code = new OmegaCode();

    public static void reset() {
        currByte = 0;
        bitsInCurrByte = 0;
        inputBitIt = 0;
    }

    public static void setCode(String codeName) {
        switch (codeName) {
            case "delta" -> code = new DeltaCode();
            case "gamma" -> code = new GammaCode();
            case "fib" -> {code = new FibonacciCode(); FibonacciCode.initFibo();}
        }
    }

    public static void initCodedOutputFile(String path) throws IOException {
        File outputFile = new File(path);
        outputFile.createNewFile();
        outputCodedStream = new FileOutputStream(outputFile);
    }

    public static void initDecodedOutputFile(String path) throws IOException {
        File outputFile = new File(path);
        outputFile.createNewFile();
        outputDecodedStream = new FileOutputStream(outputFile);
    }

    public static void outputSeq(List<Byte> seq) throws IOException {
        byte[] bytes = new byte[seq.size()];
        for(int i = 0; i < seq.size(); i++) bytes[i] = seq.get(i);
        outputDecodedStream.write(bytes);
    }


    public static void outputIdx(int idx) throws IOException {
        List<Boolean> bits = code.outputCoded(idx);
        for(boolean bit : bits) {
            output_bit(bit);
        }
    }



    public static void output_bit(boolean bit) throws IOException {
        currByte <<= 1;
        currByte += bit ? 1 : 0;
        bitsInCurrByte++;
        if(bitsInCurrByte == 8) {
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
        outputCodedStream.write(b);
    }

    public static byte[] getCodingInput(String path) throws IOException {
        input = Files.readAllBytes(Paths.get(path));
        return input;
    }

    public static ArrayList<Integer> getDecodingInput(String path) throws IOException {
        input = Files.readAllBytes(Paths.get(path));
        ArrayList<Boolean> bits = new ArrayList<>(8*input.length);
        for(int b = 0; b < input.length; b++) {
            for (int i = 0; i < 8; i++) {
                bits.add(((input[b] >> (7 - i)) & 1) == 1);
            }
        }
        return code.decodeInput(bits);
    }

    public static boolean getInputBit() {
        byte b = input[inputBitIt/8];
        boolean bit = (((b >> (7 - inputBitIt%8)) & 1) == 1);
        inputBitIt++;
        return bit;
    }

    public static boolean isInput() {
        return input.length*8>inputBitIt;
    }
}

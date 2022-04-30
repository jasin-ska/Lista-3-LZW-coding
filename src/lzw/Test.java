package lzw;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


public class Test {

    private static final String testDirectory = "./testy";

    private static boolean isDecodedEqual(String name, String ext, char code) throws IOException {
        String filePath = testDirectory + "/" + name + "/" + name + "." + ext;
        String decodedFilePath = testDirectory + "/" + name + "/" + name + "-decoded-" + code + "." + ext;

        byte[] fileBefore = Files.readAllBytes(Paths.get(filePath));
        byte[] fileDecoded = Files.readAllBytes(Paths.get(decodedFilePath));
        return Arrays.equals(fileBefore, fileDecoded);
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Usage: java Test [fileName] (coding)");
            return;
        }
        System.out.println("File: " + args[0]);

        String fileNameExt = args[0];
        String fileName = args[0].substring(0, fileNameExt.indexOf('.'));
        String ext = args[0].substring(fileNameExt.indexOf('.') + 1);

        if (args.length == 2) {
            if (!Objects.equals(args[1], "all")) {
                BitsIO.setCode(args[1]);
                testFile(fileName, ext, args[1].charAt(0));
            } else {
                allCodesTest(fileName, ext);
            }
        } else testFile(fileName, ext, 'o');
    }

    static void allCodesTest(String fileName, String ext) throws IOException {
        System.out.println("omega.....................");
        testFile(fileName, ext, 'o');
        System.out.println("delta.....................");
        BitsIO.setCode("delta");
        testFile(fileName, ext, 'd');
        System.out.println("gamma.....................");
        BitsIO.setCode("gamma");
        testFile(fileName, ext, 'g');
        System.out.println("fibonacci.................");
        BitsIO.setCode("fib");
        testFile(fileName, ext, 'f');
    }

    static void testFile(String name, String ext, char code) throws IOException {
        BitsIO.reset();
        String filePath = testDirectory + "/" + name + "/" + name + "." + ext;
        String codedFilePath = testDirectory + "/" + name + "/" + name + "-coded-" + code + "." + ext;
        String decodedFilePath = testDirectory + "/" + name + "/" + name + "-decoded-" + code + "." + ext;

        //encode
        System.out.print("Encoding: ");
        BitsIO.reset();
        BitsIO.initCodedOutputFile(codedFilePath);
        byte[] input = BitsIO.getCodingInput(filePath);
        long cStart = System.currentTimeMillis();
        Encoder.encode(input);
        BitsIO.finishByte();
        long cStop = System.currentTimeMillis();
        System.out.println("DONE");
        long encodingTime = cStop - cStart;

        BitsIO.reset();

        //decode
        System.out.print("Decoding: ");
        BitsIO.reset();
        BitsIO.initDecodedOutputFile(decodedFilePath);
        ArrayList<Integer> indexes = BitsIO.getDecodingInput(codedFilePath);
        long dStart = System.currentTimeMillis();
        Decoder.decode(indexes);
        long dStop = System.currentTimeMillis();
        System.out.println("DONE");
        long decodingTime = dStop - dStart;

        File fileBefore = new File(filePath);
        long sizeBefore = fileBefore.length();
        File fileCoded = new File(codedFilePath);
        long sizeCoded = fileCoded.length();
        File fileDecoded = new File(decodedFilePath);
        long sizeDecoded = fileDecoded.length();

        float entropyBefore = Entropy.calculateEntropy(filePath);
        float entropyCoded = Entropy.calculateEntropy(codedFilePath);
        float CompressionRate = (float) sizeBefore / sizeCoded;


        System.out.println("STATS ---------");
        System.out.println("\tCompression rate: " + CompressionRate);
        System.out.println("\tEntropy: " + entropyBefore + "  -->  " + entropyCoded);
        System.out.println("\tSize: " + sizeBefore + "  -->  " + sizeCoded);
        System.out.println("\t(size decoded: " + sizeDecoded + ")");
        System.out.println("TIME ------------");
        System.out.println("\tCompression: " + encodingTime / 1000f + " s");
        System.out.println("\tDecompression: " + decodingTime / 1000f + " s");

        System.out.println(isDecodedEqual(name, ext, code) ? "ORIGINAL = DECODED" : "DECODING ERROR");
    }


}

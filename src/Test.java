import Codes.Code;
import Codes.DeltaCode;
import Codes.FibonacciCode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) throws IOException {
        //BitsIO.setCode("fib");

        //encoding
        BitsIO.initCodedOutputFile("./test3-coded.bin");
        byte[] input = BitsIO.getCodingInput("./test3.bin");
        Encoder.encode(input);
        BitsIO.finishByte();

        //decoding
        BitsIO.initDecodedOutputFile("./test3-decoded.bin");
        ArrayList<Integer> indexes = BitsIO.getDecodingInput("./test3-coded.bin");
        System.out.print("Indexes: ");
        for(int i : indexes) System.out.print(i+", ");
        System.out.println();
        Decoder.decode(indexes);

        //byte[] decoded = Files.readAllBytes(Paths.get("./pan-tadeusz-decoded.txt"));
        //System.out.println("Decoded: " + Arrays.toString(decoded));

        /*File fileBefore = new File("./pan-tadeusz-small.txt");
        long sizeBefore = fileBefore.length();
        File fileDecoded = new File("./pan-tadeusz-small-decoded.txt");
        long sizeDecoded = fileDecoded.length();
        System.out.println("before: " + sizeBefore + ", after: " +sizeDecoded);*/

        //System.out.println((byte)'ę');
        //System.out.println((char)25);

        /*Code code = new FibonacciCode();
        ArrayList<Boolean> bits = (ArrayList<Boolean>) code.outputCoded(143);
        for(boolean b : bits) {
            System.out.print(b? 1:0);
        }
        System.out.println();

        ArrayList<Integer> indxs = code.decodeInput(bits);
        for(int i : indxs) {
            System.out.print(i);
        }
        System.out.println();*/


    }


    public static void testDecoding() throws IOException {
        ArrayList<Integer> indexes = new ArrayList<>();
        indexes.add(84);
        indexes.add(79);
        indexes.add(66);
        indexes.add(69);
        indexes.add(79);
        indexes.add(82);
        indexes.add(78);
        indexes.add(79);
        indexes.add(84);
        indexes.add(256);
        indexes.add(258);
        indexes.add(260);
        indexes.add(265);
        indexes.add(259);
        indexes.add(261);
        indexes.add(263);

        Decoder.decode(indexes);
        /*
        Dictionary dictionary = new DecodingDictionary();
        dictionary.initDictionary();
        int oldIdx = indexes.get(0);
        String seq = dictionary.getSequenceAt(oldIdx);
        String c = ""+seq.charAt(0);
        int idx;
        for (int i = 0; i < indexes.size() - 1; i++) {
            idx = indexes.get(i + 1);
            if (dictionary.getSequenceAt(idx) == null) {
                seq = dictionary.getSequenceAt(oldIdx);
                seq += c;
            }
            else {
                seq = dictionary.getSequenceAt(idx);
                System.out.println("Seq: " + seq);
            }

            c = "";
            c += seq.charAt(0);
            dictionary.addSequence(dictionary.getSequenceAt(oldIdx) + c);
            oldIdx = idx;
        }

         */
    }

    public static void testOmega() {


        byte[] input = new byte[2];
        input[0] = -68;
        input[1] = 72;
        List<Integer> indexes = new ArrayList<>();
        int n = 1;
        int value = 0;
        int it = 0;
        int bitsCounter = 0;
        for(byte b : input) {
            for(int i = 0; i < 8; i++) {
                //System.out.print(((b >> (7 - i)) & 1));
                value += ((b >> (7 - i)) & 1) * Math.pow(2, n - it);
                //System.out.println("value: " + value);
                it++;
                bitsCounter++;
                if(it == n + 1) {
                    n = value;
                    it = 0;
                    bitsCounter = 0;
                    value = 0;
                    if(((b >> (7 - (i+1))) & 1) == 0) {
                        indexes.add(n);
                        n = 1;
                    }
                }
            }
            //System.out.println();
        }

        for(int i : indexes) System.out.println(i);
    }
}

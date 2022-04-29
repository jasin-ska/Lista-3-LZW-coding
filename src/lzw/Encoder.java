package lzw;

import lzw.Dictionaries.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Encoder {
    public static void encode(byte[] input) throws IOException {
        Dictionary dictionary = new EncodingDictionary();
        dictionary.initDictionary();

        //System.out.print("INPUT: ");
        //System.out.println(Arrays.toString(input));
        //System.out.println();

        List<Byte> sequence = new ArrayList<>();
        sequence.add(input[0]);
        byte next = 0;
        for (int i = 0; i < input.length-1; i++) {
            //if(i>0)System.out.println("\n" + 100*i/input.length + "%");
            if (i != input.length - 1) {
                next = input[i + 1];
                //else next += dictionary.getSequenceAt(input[i+1]+256);
                //System.out.println("NEXT: " + next);
                //System.out.println("input[i+1]: " + input[i+1]);
                //System.out.println("dict od input[i+1] + 256: " + dictionary.getSequenceAt(input[i+1]+256));
            }
            List<Byte> combined = new ArrayList<>(sequence.size()+1);
            combined.addAll(sequence);
            combined.add(next);
            if (dictionary.getIndex(combined) != -1) {
                sequence.add(next);
                //System.out.println("In if, seq: " + sequence);
            }
            else {
                //System.out.println("seq: " + sequence);
                //System.out.println("In else, seq: " + sequence + ",    i = " + i + ", seq+next = " + (combined));
                BitsIO.outputIdx(dictionary.getIndex(sequence)); //ftem.out.println(", seq from dict: " + dictionary.getSequenceAt(dictionary.getIndex(sequence)));
                dictionary.addSequence(combined);
                sequence.clear();
                sequence.add(next);
            }
            next = 0;
        }
        BitsIO.outputIdx(dictionary.getIndex(sequence));
    }
}

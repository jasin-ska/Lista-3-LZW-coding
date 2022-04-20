package Codes;

import java.util.ArrayList;
import java.util.List;

public class OmegaCode implements Code{
    @Override
    public List<Boolean> outputCoded(int idx) { // dziala (dla przykladu z wykladu)
        System.out.println("idx: " + idx);
        List<Boolean> bits = new ArrayList<>();
        bits.add(0, false);
        int k, bitNr = 0;
        while(idx > 1) {
            k = idx;
            bitNr = 0;
            while(k >= 1) {
                bitNr++;
                bits.add(0, k%2==1);
                k/=2;
            }
            idx = bitNr - 1;
        }
        //System.out.print("output coded bits: ");
        for(boolean b : bits) System.out.print(b? 1:0);
        System.out.println();

        return bits;
    }

    @Override
    public ArrayList<Integer> decodeInput(ArrayList<Boolean> input) { // dziala tylko moze dac 0 na koniec ilestam
        ArrayList<Integer> indexes = new ArrayList<>();
        int n = 1;
        int value = 0;
        int it = 0;
        //int bitsCounter = 0;
        for (int b = 0; b < input.size(); b++) {

            //System.out.print(input.get(b));
            //value += ((input[b] >> (7 - i)) & 1) * Math.pow(2, n - it);
            value += (input.get(b)? 1 : 0) * Math.pow(2, n - it);
            //System.out.println("value: " + value);
            it++;
            //bitsCounter++;
            if (it == n + 1) {
                n = value;
                it = 0;
                //bitsCounter = 0;
                value = 0;
                boolean nextBitValue = true;
                boolean nextNextBitValue = true;
                /*if (i == 7 && b != input.length - 1) {
                    nextBitValue = ((input[b + 1] >> 7) & 1) == 1;
                    nextNextBitValue = ((input[b + 1] >> 6) & 1) == 1;
                } else {
                    nextBitValue = ((input[b] >> (7 - (i + 1))) & 1) == 1;
                    if (i + 1 == 7 && b != input.length - 1) nextNextBitValue = ((input[b + 1] >> 7) & 1) == 1;
                    else nextNextBitValue = ((input[b] >> (7 - (i + 2))) & 1) == 1;
                }*/
                nextBitValue = input.get(b+1) != null? input.get(b+1) : true;
                nextNextBitValue = input.get(b+2) != null? input.get(b+2) : true;

                if (!nextBitValue) {
                    indexes.add(n);
                    //System.out.println("ADDING " + n);
                    /*if (i == 7) {
                        b++;
                        i = 0;
                    } else i++;*/
                    b++;
                    n = 1;
                    if (!nextNextBitValue) return indexes;
                }
            }

        //System.out.println();
    }


        return indexes;
    }
}

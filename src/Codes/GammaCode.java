package Codes;

import java.util.ArrayList;
import java.util.List;

public class GammaCode implements Code{
    @Override
    public List<Boolean> outputCoded(int idx) {
        List<Boolean> bits = new ArrayList<>();
        int k, bitCounter = 0;
        while(idx >= 1) {
            k = idx & 1;
            bits.add(0, k == 1);
            idx /= 2;
            bitCounter++;
        }
        for(int i = 0; i < bitCounter - 1; i++) bits.add(0, false);
        return bits;
    }

    @Override
    public ArrayList<Integer> decodeInput(ArrayList<Boolean> input) {
        ArrayList <Integer> indexes = new ArrayList<>();
        int b = 0;
        while(b < input.size()) {
            int it = 0;
            int value = 0;
            int codeSize = 1;
            while (b < input.size() && !input.get(b)) {
                it++;
                b++;
                codeSize++;
            }
            //System.out.println("codeSize: " + codeSize);
            //System.out.println("it: " + it);

            while (b < input.size() && it >= 0) {
                value += (input.get(b) ? 1 : 0) * Math.pow(2, it);
                //System.out.println("dodaje do value " + ((input.get(b) ? 1 : 0) * Math.pow(2, it)));
                it--;
                b++;
            }
            if(b < input.size()) indexes.add(value);
            //b += 2*(codeSize-1);
        }
        return indexes;
    }
}

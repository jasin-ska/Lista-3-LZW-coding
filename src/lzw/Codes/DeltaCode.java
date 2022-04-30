package lzw.Codes;

import java.util.ArrayList;
import java.util.List;

public class DeltaCode implements Code {
    @Override
    public List<Boolean> outputCoded(int idx) {
        List<Boolean> bits = new ArrayList<>();
        int k, bitCounter = 0;
        while (idx > 1) {
            k = idx & 1;
            bits.add(0, k == 1);
            idx /= 2;
            bitCounter++;
        }
        bitCounter++;
        int bitCounter2 = 0;
        while (bitCounter >= 1) {
            k = bitCounter & 1;
            bits.add(0, k == 1);
            bitCounter /= 2;
            bitCounter2++;
        }
        for (int i = 0; i < bitCounter2 - 1; i++) bits.add(0, false);
        return bits;
    }

    @Override
    public ArrayList<Integer> decodeInput(ArrayList<Boolean> input) {
        ArrayList<Integer> indexes = new ArrayList<>();
        int b = 0;
        while (b < input.size()) {
            int it = 0;
            int valueN = 0;
            while (b < input.size() && !input.get(b)) {
                it++;
                b++;
            }
            while (b < input.size() && it >= 0) {
                valueN += (input.get(b) ? 1 : 0) * Math.pow(2, it);
                it--;
                b++;
            }

            int value = 0;
            it = 0;
            value += Math.pow(2, valueN - 1);
            while (b < input.size() && it < valueN - 1) {
                value += (input.get(b) ? 1 : 0) * Math.pow(2, valueN - 2 - it);
                it++;
                b++;
                if (b == input.size()) indexes.add(value);
            }

            if (b < input.size()) indexes.add(value);
        }
        return indexes;
    }
}

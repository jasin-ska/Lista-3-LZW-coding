package lzw.Codes;

import lzw.Test;

import java.util.ArrayList;
import java.util.List;

public class OmegaCode implements Code {
    @Override
    public List<Boolean> outputCoded(int idx) {
        List<Boolean> bits = new ArrayList<>();
        bits.add(0, false);
        int k = idx, bitNr = 0;
        while (idx > 1) {
            k = idx;
            bitNr = 0;
            while (k >= 1) {
                bitNr++;
                bits.add(0, k % 2 == 1);
                k /= 2;
            }
            idx = bitNr - 1;
        }
        return bits;
    }

    @Override
    public ArrayList<Integer> decodeInput(ArrayList<Boolean> input) { // dziala tylko moze dac 0 na koniec ilestam
        ArrayList<Integer> indexes = new ArrayList<>();
        int n = 1;
        int value = 0;
        int it = 0;
        for (int b = 0; b < input.size(); b++) {
            value += (input.get(b) ? 1 : 0) * Math.pow(2, n - it);
            it++;
            if (it == n + 1) {
                n = value;
                it = 0;
                value = 0;
                boolean nextBitValue = true;
                boolean nextNextBitValue = true;
                nextBitValue = input.get(b + 1) != null ? input.get(b + 1) : true;
                nextNextBitValue = input.get(b + 2) != null ? input.get(b + 2) : true;

                if (!nextBitValue) {
                    indexes.add(n);
                    b++;
                    n = 1;
                    if (!nextNextBitValue) {
                        return indexes;
                    }
                }
            }
        }

        return indexes;
    }
}

package lzw.Codes;

import java.util.ArrayList;
import java.util.List;

public class FibonacciCode implements Code{

    private static List<Integer> fibo = new ArrayList<>(46);

    public static void initFibo() {
        fibo.add(1);
        fibo.add(2);
        for(int i = 2; i < 46; i++) {
            fibo.add(fibo.get(i - 1) + fibo.get(i - 2));
        }
    }

    public static int maxFibo(int idx)
    {
        int i=2;

        while(fibo.get(i-1) <= idx) i++;

        return(i - 2);
    }

    @Override
    public List<Boolean> outputCoded(int idx) {
        int index = maxFibo(idx);
        List<Boolean> bits = new ArrayList<>();
        int i = index;
        while (idx > 0)
        {
            bits.add(0, true);
            idx -= fibo.get(i);
            i = i - 1;
            while (i >= 0 && fibo.get(i) > idx)
            {
                bits.add(0, false);
                i = i - 1;
            }
        }

        bits.add(true); //...11

        return bits;
    }

    @Override
    public ArrayList<Integer> decodeInput(ArrayList<Boolean> input) {
        ArrayList <Integer> indexes = new ArrayList<>();
        int idx = 0;
        int fiboIt = 0;
        for(int i = 0; i < input.size()-1; i++) {
            if(input.get(i)) {
                idx += fibo.get(fiboIt);
                if(input.get(i+1)) {
                    indexes.add(idx);
                    fiboIt = -1;
                    idx = 0;
                    i++;
                }
            }
            fiboIt++;
        }
        return indexes;
    }
}

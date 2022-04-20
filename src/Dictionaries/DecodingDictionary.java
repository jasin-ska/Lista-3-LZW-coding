package Dictionaries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DecodingDictionary implements Dictionary {
    private final List<List<Byte>> dictionary = new ArrayList<>(256);

    @Override
    public void initDictionary() {
        List<Byte> list;
        for (int i = 0; i <= 255; i++) {
            list = new ArrayList<>(1);
            list.add(0, (byte)i);
            dictionary.add(list);
        }
    }

    @Override
    public void addSequence(List<Byte> bytes) {
        dictionary.add(bytes);
    }

    @Override
    public int getIndex(List<Byte> sequence) { // O(n)
        return dictionary.indexOf(sequence);
    }

    @Override
    public List<Byte> getSequenceAt(int index) { // O(1) i to w decoding jest
        if(index < 0 || index >= dictionary.size()) return null;
        return dictionary.get(index);
    }
}

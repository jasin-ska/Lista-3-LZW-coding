package lzw;

import lzw.Dictionaries.DecodingDictionary;
import lzw.Dictionaries.Dictionary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Decoder {
    public static void decode(ArrayList<Integer> indexes) throws IOException {
        Dictionary dictionary = new DecodingDictionary();
        dictionary.initDictionary();
        int oldIdx = indexes.get(0);
        List<Byte> seq = dictionary.getSequenceAt(oldIdx);
        int idx;

        BitsIO.outputSeq(seq);
        for (int i = 0; i < indexes.size() - 1; i++) {
            idx = indexes.get(i + 1);

            if (dictionary.getSequenceAt(idx) == null) {
                List<Byte> combined = new ArrayList<>(dictionary.getSequenceAt(oldIdx).size() + 1);
                combined.addAll(dictionary.getSequenceAt(oldIdx));
                combined.add(seq.get(0));
                dictionary.addSequence(combined);
                BitsIO.outputSeq(combined);
            }
            else {
                seq = dictionary.getSequenceAt(idx);
                BitsIO.outputSeq(seq);
                List<Byte> combined = new ArrayList<>(dictionary.getSequenceAt(oldIdx).size() + 1);
                combined.addAll(dictionary.getSequenceAt(oldIdx));
                combined.add(seq.get(0));
                dictionary.addSequence(combined);
            }
            oldIdx = idx;
        }
    }
}

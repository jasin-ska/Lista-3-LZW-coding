import Dictionaries.DecodingDictionary;
import Dictionaries.Dictionary;
import Dictionaries.EncodingDictionary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Decoder {
    public static void decode(ArrayList<Integer> indexes) throws IOException {
        Dictionary dictionary = new DecodingDictionary();
        dictionary.initDictionary();
        int oldIdx = indexes.get(0);
        List<Byte> seq = dictionary.getSequenceAt(oldIdx);
        System.out.println("OldIdx: " + oldIdx);
        /*byte next = dictionary.getSequenceAt(indexes.get(1)).get(0);
        List<Byte> combined0 = new ArrayList<>(dictionary.getSequenceAt(oldIdx).size() + 1);
        combined0.addAll(dictionary.getSequenceAt(oldIdx));
        combined0.add(next);
        dictionary.addSequence(combined0);*/
        int idx;
        //System.out.println("Seq: " + seq);
        BitsIO.outputSeq(seq);
        for (int i = 0; i < indexes.size() - 1; i++) {
            idx = indexes.get(i + 1);
            System.out.println("decoding idx: " + idx);
            System.out.println("seq from dict: " + dictionary.getSequenceAt(idx));
            if (dictionary.getSequenceAt(idx) == null) {
                //seq = dictionary.getSequenceAt(oldIdx);
                List<Byte> combined = new ArrayList<>(dictionary.getSequenceAt(oldIdx).size() + 1);
                System.out.println("combined size: " + (seq.size() + 1));
                combined.addAll(dictionary.getSequenceAt(oldIdx));
                combined.add(seq.get(0));
                dictionary.addSequence(combined);
                System.out.println("Added seq: " +combined);
                BitsIO.outputSeq(combined);
            }
            else {
                seq = dictionary.getSequenceAt(idx);
                System.out.println("!!!Seq: " + seq);
                BitsIO.outputSeq(seq);
                System.out.println("Outputed seq: " + seq);
                List<Byte> combined = new ArrayList<>(dictionary.getSequenceAt(oldIdx).size() + 1);
                System.out.println("combined size: " + (seq.size() + 1));
                combined.addAll(dictionary.getSequenceAt(oldIdx));
                combined.add(seq.get(0));
                dictionary.addSequence(combined);
            }

            //next = seq.get(0);
            //System.out.println("SQ: " + seq);
            //System.out.println("next: " + next);
            //System.out.println("oldIdx: " + oldIdx);
            //List<Byte> combined = new ArrayList<>(seq.size() + 1);
            //System.out.println("combined size: " + (seq.size() + 1));
            //combined.addAll(dictionary.getSequenceAt(oldIdx));
            //combined.add(next);
            //dictionary.addSequence(combined);
            //System.out.println("Just added seq: " + combined);
            oldIdx = idx;
        }



    }
}

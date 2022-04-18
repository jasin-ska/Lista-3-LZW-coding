import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EncodingDictionary implements Dictionary{
    private final HashMap<String, Integer> dictionary = new HashMap<>(256);
    public int index = 256;

    @Override
    public void initDictionary() {
        for (int i = 0; i <= 255; i++) {
            dictionary.put(""+(char)i, i);
        }
    }

    @Override
    public void addSequence(String sequence) {
        dictionary.put(sequence, index);
        index++;
    }

    @Override
    public int getIndex(String sequence) {
        return (dictionary.get(sequence) != null)? dictionary.get(sequence) : -1;
    }

    @Override
    public String getSequenceAt(int index) {
        for(String s : dictionary.keySet())
            if(dictionary.get(s) == index)
                return s;
        return null;
    }
}

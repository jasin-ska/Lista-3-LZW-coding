package lzw.Codes;

import java.util.ArrayList;
import java.util.List;

public interface Code {
    List<Boolean> outputCoded(int idx);
    ArrayList<Integer> decodeInput(ArrayList<Boolean> input);
}

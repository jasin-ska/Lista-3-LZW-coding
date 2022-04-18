public class Encoder {
    public static void encode(byte[] input) {
        Dictionary dictionary = new EncodingDictionary();
        dictionary.initDictionary();

        String sequence = ""+(char)input[0];;
        String next = "";
        for (int i = 0; i < input.length; i++) {
            if (i != input.length - 1)
                next += (char)input[i + 1];
            if (dictionary.getIndex(sequence+next) != -1) {
                sequence += next;
            }
            else {
                System.out.println("seq: " + sequence);
                BitsIO.outputIdx(dictionary.getIndex(sequence));
                dictionary.addSequence(sequence + next);
                sequence = ""+next;
            }
            next = "";
        }
        BitsIO.outputIdx(dictionary.getIndex(sequence));
    }
}

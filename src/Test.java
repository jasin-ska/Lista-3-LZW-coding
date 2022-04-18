public class Test {
    public static void main(String[] args) {
        String wiki = "TOBEORNOTTOBEORTOBEORNOT";
        byte[] input = wiki.getBytes();

        Encoder.encode(input);
    }
}

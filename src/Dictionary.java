public interface Dictionary {
    void initDictionary();
    void addSequence(String sequence);
    int getIndex(String sequence);
    String getSequenceAt(int index);
}

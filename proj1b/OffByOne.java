public class OffByOne implements CharacterComparator {

    @Override
    public boolean equalChars(char a, char b) {
        int diff = a - b;
        return diff == 1 || diff == -1;
    }
}

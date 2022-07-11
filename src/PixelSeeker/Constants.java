package PixelSeeker;

public enum Constants {
    // 1 - Num, 2 - String, 3 - Array
    UNDETERMINED (0),
    NUMERICAL (1),
    STRING (2),
    ARRAY (3);
    private final int type;
    Constants(int type){
        this.type = type;
    }
}

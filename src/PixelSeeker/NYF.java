package PixelSeeker;

public class NYF {
    char status;
    NYF(int type012){
        switch (type012){
            case 0:
                status = 'N';
                break;
            case 1:
                status = 'Y';
                break;
            case 2:
                status = 'F';
        }
    }
}

package PixelSeeker;

public class Parcel {
    private Object object;
    private Class classObject;
    public Parcel(Object object, Class classObject) {
        this.object = object;
        this.classObject = classObject;
    }
    public Class getClassObject() {
        return classObject;
    }

    public Object getObject() {
        return object;
    }

    @Override
    public String toString() {
        return
                "object=" + object +
                ", classObject=" + classObject;
    }
}

package language;

public class ObjectArray {
    public static void main(String[] args) {
        String[] a = new String[2];
        Object[] b = a;
        b[0] = "aaa";
        b[1] = Integer.valueOf("42");
    }

}

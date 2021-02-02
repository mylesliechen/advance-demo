package cx.commom.sample.concurrent;

public class ClassLoaderSing {
    private static ClassLoaderSing instance = new ClassLoaderSing();
    private static int x = 0;
    private static int t;

    private ClassLoaderSing() {
        x++;
        t++;
    }

    public static ClassLoaderSing getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        ClassLoaderSing sing = ClassLoaderSing.getInstance();
        System.out.println(sing.x);
        System.out.println(sing.t);
    }
}

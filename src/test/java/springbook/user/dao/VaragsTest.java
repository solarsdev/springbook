package springbook.user.dao;

public class VaragsTest {
    public static void main(String[] args) {
        test();
    }

    private static void test(String... test) {
        System.out.println(test.length);
        for (int i = 0; i < test.length; i++) {
            System.out.println(test[i]);
        }
    }
}

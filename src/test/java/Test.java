public class Test {


    @org.junit.jupiter.api.Test
    public void test(){
        String a = "dsadas.png";
        System.out.println(a.indexOf("."));
        System.out.println(a.lastIndexOf("."));
        System.out.println(a.substring(a.lastIndexOf(".") + 1));
        System.out.println(a.substring(0, a.lastIndexOf(".")));
    }
}

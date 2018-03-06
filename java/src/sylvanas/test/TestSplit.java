package sylvanas.test;

/**
 * @Description:
 */
public class TestSplit {



    public static void main(String[] args) {
        String s = "kkk=vvvqqq=eee";
        String[] array = s.split(";");
        for (String ss: array){
            System.out.println(array.length);
        }
    }
}

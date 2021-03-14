import org.junit.jupiter.api.Test;

public class StringTestes {

    @Test
    void testeCharAt() {

        String str = "Cassio-Colorado-e-Bonito";

        char first = str.charAt(0);
        char lestPos = str.charAt(str.length() - 1);

        System.out.println(lestPos);
        System.out.println(first);
        System.out.println(str.length());
        System.out.println(str.charAt(str.length() / 2));

        for (int i = 0; i < str.length(); i++) {
            System.out.println("Posicão: " + i +" letra: " +str.charAt(i));
        }

    }

    @Test
    void testeComoPegarMetadeDeString() {
        String nome = "cascio";
        System.out.println(nome.length());
        System.out.println(nome.charAt(nome.length()/2));
        System.out.println(nome.charAt(0));
    }

    @Test
    void subString() {
        String nome = "Dayse Eliane";

        String sub1 = nome.substring(1, 3);

        String sub2 = nome.substring(5);

        System.out.println(sub1);
        System.out.println(sub2);
    }

    @Test
    void split() {
        String str = "La vem o disco voador";

        var split = str.split(" ");

        System.out.println(split[0]);

    }

    @Test
    void subSequence() {
        String str = "hahaha bem feito";

        var sub = str.subSequence(1, 2);

        System.out.println(sub);

        String teste = (String) sub;
    }

    @Test
    void indexOxAndLastIndexOf() {
        String str = "Vamo intera";

        var indexOf = str.indexOf("V");
        var indexOF2 = str.indexOf(" ");

        System.out.println(indexOf);
        System.out.println(indexOF2);

        var last = str.lastIndexOf("a");
        System.out.println(last);
        System.out.println(str.lastIndexOf("a",str.length()-2));


        var indexOf3 = str.indexOf("amo");
        System.out.println(indexOf3);

        var contem = str.contains("Vmo");
        System.out.println(contem);

        int i = 10;
        System.out.println(String.valueOf(i));
        var j = String.valueOf(i);

        String value = "10";
        var inter = Double.valueOf(value);
        System.out.println(inter);

        //var concat = value.concat("+");
        value.concat("Vamoo");
        System.out.println(value);

    }

    @Test
    void stringBuilder() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("A");
        System.out.println(stringBuilder);


        String teste = "cassio";
        System.out.println(teste.compareTo("llxze"));
        

    }
}

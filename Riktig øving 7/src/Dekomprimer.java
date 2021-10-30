import java.io.IOException;

public class Dekomprimer {

    public static void main(String[] args) throws IOException {
        String inNavn = "Resources/komprimert2.lz";
        String utNavn = "Resources/dekomprimert.txt";

        Huffmankoding huffmankoding = new Huffmankoding();
        LempelZiv lz = new LempelZiv("Resources/dekomprimert.lz", utNavn);

        huffmankoding.dekomprimer(inNavn, "Resources/dekomprimert.lz");
        lz.dekomprimer();
        System.out.println("Dekomprimering fullført");
    }
}

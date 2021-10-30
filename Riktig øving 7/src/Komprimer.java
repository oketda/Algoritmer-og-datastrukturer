import java.io.*;

public class Komprimer {

    public static void main(String[] args) throws IOException {
        String innNavn = "Resources/forelesning.txt";


        LempelZiv lempelZiv = new LempelZiv(innNavn, "Resources/komprimert.lz");
        //LempelZiv lempelZiv2 = new LempelZiv("Resources/dekomprimert.lz", "Resources/dekomprimert2.txt");
        Huffmankoding huffmankoding = new Huffmankoding();

        lempelZiv.komprimer();
        huffmankoding.komprimer( "Resources/komprimert.lz", "Resources/komprimert2.lz");
        System.out.println("Komprimering fullført");
        //huffmankoding.dekomprimer("Resources/komprimert2.lz", "Resources/dekomprimert.lz");
        //lempelZiv2.dekomprimer();

    }
}
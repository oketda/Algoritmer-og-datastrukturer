import java.util.ArrayList;

public class LempelZiv {

    private final int LENGDE = 3;
    private String innFil;
    private String utFil;

    public LempelZiv(String innFil, String utFil){
        this.innFil = innFil;
        this.utFil = utFil;
    }

    public byte[] komprimer(){
        byte[] lestBytes = FilHåndtering.lesBytesFraFil(innFil);
        byte[] komprimerteBytes = komprimerBytes(lestBytes);
        FilHåndtering.skrivBytestilFil(komprimerteBytes, utFil);
        return komprimerteBytes;
    }

    public void dekomprimer(){
        byte[] lestBytes = FilHåndtering.lesBytesFraFil(innFil);
        byte[] dekomprimerteBytes = dekomprimerBytes(lestBytes);
        FilHåndtering.skrivBytestilFil(dekomprimerteBytes, utFil);
    }

    public byte[] komprimerBytes(byte[] bytes){
        ArrayList<Byte> komprimert = new ArrayList<>();
        byte uendret = LENGDE;
        int index;
        for (index = LENGDE; index < bytes.length; index++) {
            boolean match = false;
            short avstand = 0;
            byte matchLengde = 0;
            for (short j = 3; j <= index && j < Short.MAX_VALUE; j++) {
                if (bytes[index - j] == bytes[index]) {
                    int funn;
                    for (funn = 1; funn < j && funn < Byte.MAX_VALUE && index + funn < bytes.length;) {
                        if (bytes[index + funn] == bytes[index - j + funn]) {
                            funn++;
                        } else {
                            break;
                        }
                    }
                    if (funn > LENGDE && funn > matchLengde) {
                        match = true;
                        matchLengde = (byte) funn;
                        avstand = j;
                        if (matchLengde == Byte.MAX_VALUE) {
                            break;
                        }
                    }
                }
            }
            if (match) {
                if (uendret != 0) {
                    komprimert.add(uendret);
                    for (int i = index - uendret; i < index; i++) {
                        komprimert.add(bytes[i]);
                    }
                    uendret = 0;
                }
                komprimert.add((byte) (-matchLengde));
                komprimert.add((byte) (avstand & 0xff));
                komprimert.add((byte) ((avstand >> 8) & 0xff));

                index += ((int) (matchLengde) - 1);
            } else {
                uendret++;
            }
            if (uendret == Byte.MAX_VALUE) {
                komprimert.add(uendret);
                for (int i = index - uendret + 1; i <= index; i++) {
                    komprimert.add(bytes[i]);
                }
            }
        }
        komprimert.add(uendret);
        for (int i = index-uendret; i < index; i++) {
            komprimert.add(bytes[i]);
        }
        return FilHåndtering.listeTilArray(komprimert);
    }

    public byte[] dekomprimerBytes(byte[] komprimerteBytes){
        ArrayList<Byte> dekomprimert = new ArrayList<>();
        int indeksUt = 0;
        for (int i = 0; i < komprimerteBytes.length; i++) {
            byte x = komprimerteBytes[i];
            if (x < 0){
                short avstand = (short)((komprimerteBytes[i+1] & 0xff) | ((komprimerteBytes[i+2] & 0xff) << 8));
                int start = indeksUt;
                for (int j = start-avstand; j < start-avstand-x; j++) {
                    dekomprimert.add(dekomprimert.get(j));
                    indeksUt++;
                }
                i += (LENGDE-1);
            } else{
                for (int j = i+1; j <= i+x && j < komprimerteBytes.length; j++) {
                    dekomprimert.add(komprimerteBytes[j]);
                    indeksUt++;
                }
                i += (int)x;
            }
        }
        return FilHåndtering.listeTilArray(dekomprimert);
    }
}

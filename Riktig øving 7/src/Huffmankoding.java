import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Huffmankoding {
    private static final int ALFABET_STØRRELSE = 256;

    private static ArrayList<HuffmanNode> lagNodeListe(int[] frekvens) {
        ArrayList<HuffmanNode> nodeListe = new ArrayList<>();
        for (int i = 0; i < frekvens.length; i++) {
            if(frekvens[i] != 0){
                nodeListe.add(new HuffmanNode((char) i, frekvens[i], null, null));
            }
        }
        return nodeListe;
    }

    public void komprimer(String inNavn, String utNavn) throws IOException {
        int frekvens[] = new int[ALFABET_STØRRELSE];
        DataInputStream f = new DataInputStream(new FileInputStream(inNavn));
        int antall = f.available();
        for (int i = 0; i < antall; ++i) {
            int c = f.read();
            frekvens[c]++;
        }
        f.close();
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>(ALFABET_STØRRELSE, new Sammenligner());
        pq.addAll(lagNodeListe(frekvens));
        HuffmanNode root = HuffmanNode.lagHuffmanTre(pq);
        root.lagBitstreng(root, "");
        FileInputStream in = new FileInputStream(inNavn);
        DataOutputStream out = new DataOutputStream(new FileOutputStream(utNavn));
        for (int t : frekvens) {
            out.writeInt(t);
        }
        int input;
        long skrivByte = 0L;
        int i = 0;
        int j = 0;
        ArrayList<Byte> bytes = new ArrayList<>();
        for (int k = 0; k < antall; ++k) {
            input = Math.abs(in.read());
            j = 0;
            String bitString = root.bitstring[input];
            while (j < bitString.length()) {
                if (bitString.charAt(j) == '0')skrivByte = (skrivByte<<1);
                else skrivByte = ((skrivByte<<1)|1);
                ++j;
                ++i;
                if (i == 8) {
                    bytes.add((byte)skrivByte);
                    i = 0;
                    skrivByte = 0L;
                }
            }
        }
        int sisteByte = i;
        while (i < 8 && i != 0) {
            skrivByte = (skrivByte<<1);
            ++i;
        }
        bytes.add((byte)skrivByte);
        out.writeInt(sisteByte);
        for (Byte s : bytes) {
            out.write(s);
        }
        in.close();
        out.close();
    }

    public void dekomprimer(String inNavn, String utNavn) throws IOException {
        DataInputStream in = new DataInputStream(new FileInputStream(inNavn));
        int [] frekvens = new int [ALFABET_STØRRELSE];
        for (int i = 0; i < frekvens.length; i++) {
            int frek = in.readInt();
            frekvens[i] = frek;
        }

        int sisteByte = in.readInt();
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>(ALFABET_STØRRELSE, new Sammenligner());
        pq.addAll(lagNodeListe(frekvens));
        HuffmanNode root = HuffmanNode.lagHuffmanTre(pq);
        DataOutputStream out = new DataOutputStream(new FileOutputStream(utNavn));
        byte ch;
        byte [] bytes = in.readAllBytes();
        in.close();
        int lengde = bytes.length;
        Bitstreng h = new Bitstreng(0, 0);
        if(sisteByte>0) lengde--;
        for (int i = 0; i <lengde; i++) {
            ch = bytes[i];
            Bitstreng b = new Bitstreng(8, ch);
            h = Bitstreng.linkSammen(h,b);
            h = skrivChar(root, h, out);
        }
        if(sisteByte>0){
            Bitstreng b = new Bitstreng(sisteByte, bytes[lengde]>>(8-sisteByte));
            h = Bitstreng.linkSammen(h, b);
            skrivChar(root, h, out);
        }
        in.close();
        out.close();
    }

    private static Bitstreng skrivChar(HuffmanNode root ,Bitstreng h, DataOutputStream dos) throws IOException {
        HuffmanNode tre = root;
        int c=0;
        for (long j = 1<< h.lengde-1; j!=0; j>>=1) {
            c++;
            if((h.biter & j) == 0)tre = tre.venstre;
            else tre = tre.høyre;
            if(tre.venstre == null){
                long cha = tre.c;
                dos.write((byte)cha);
                long temp =(long) ~(0 << (h.lengde-c));
                h.biter = (h.biter & temp);
                h.lengde = h.lengde-c;
                c = 0;
                tre = root;
            }
        }
        return h;
    }
}



class Sammenligner implements Comparator<HuffmanNode> {
    @Override
    public int compare(HuffmanNode x, HuffmanNode y) {
        return x.frekvens-y.frekvens;
    }
}

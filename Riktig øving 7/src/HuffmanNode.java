import java.util.PriorityQueue;

public class HuffmanNode{

    char c;
    int frekvens;
    String [] bitstring;

    HuffmanNode venstre, høyre;

    public HuffmanNode(char c, int i, HuffmanNode l, HuffmanNode r){
        this.c = c;
        this.frekvens = i;
        this.venstre = l;
        this.høyre = r;
        bitstring = new String[256];
    }

    public HuffmanNode(){
        bitstring = new String[256];
    }

    public static HuffmanNode lagHuffmanTre(PriorityQueue<HuffmanNode> pq){
        HuffmanNode root = new HuffmanNode();
        while(pq.size() > 1){
            HuffmanNode hv = pq.poll();
            HuffmanNode hh = pq.poll();
            HuffmanNode h = new HuffmanNode('\0',(hv.frekvens+hh.frekvens), hv, hh);
            pq.add(h);
            root = h;
        }
        return root;
    }

    public void lagBitstreng(HuffmanNode root, String s) {
        if (root.venstre != null && root.høyre != null) {
            lagBitstreng(root.venstre, s+"0");
            lagBitstreng(root.høyre, s+"1");

        }else bitstring[root.c] = s;

    }
}

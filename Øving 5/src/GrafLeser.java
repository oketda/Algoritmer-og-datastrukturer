import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

public class GrafLeser {

    private int noder;
    private LinkedList<Integer> kanter[];

    public GrafLeser(int noder){
        this.noder = noder;
        kanter = new LinkedList[noder];
        for (int i = 0; i < noder; i++) {
            kanter[i] = new LinkedList<>();
        }
    }

    void nyKant(int n1, int n2){
        kanter[n1].add(n2);
    }

    void DFSUtil(int n, boolean besøkt[]){
        besøkt[n] = true;
        System.out.print(n+" ");

        Iterator<Integer> i = kanter[n].listIterator();
        while(i.hasNext()){
            int neste = i.next();
            if (!besøkt[neste]){
                DFSUtil(neste, besøkt);
            }
        }
    }

    void DFS(int n){
        boolean besøkt[] = new boolean[noder];

        DFSUtil(n, besøkt);
    }

    void DFSUtilNoPrint(int n, boolean besøkt[]){
        besøkt[n] = true;

        Iterator<Integer> i = kanter[n].listIterator();
        while(i.hasNext()){
            int neste = i.next();
            if (!besøkt[neste]){
                DFSUtilNoPrint(neste, besøkt);
            }
        }
    }

    GrafLeser transponer(){
        GrafLeser g = new GrafLeser(noder);

        for (int n = 0; n < noder; n++) {
            Iterator<Integer> i = kanter[n].listIterator();
            while (i.hasNext()){
                g.kanter[i.next()].add(n);
            }
        }
        return g;
    }

    void ferdigTid(int n, boolean besøkt[], Stack stack){
        besøkt[n] = true;

        Iterator<Integer> i = kanter[n].listIterator();
        while (i.hasNext()){
            int neste = i.next();
            if (!besøkt[neste]){
                ferdigTid(neste, besøkt, stack);
            }
        }
        stack.push(new Integer(n));
    }

    void printSammenhengende(){
        Stack stack = new Stack();
        boolean besøkt[] = new boolean[noder];
        for (int i = 0; i < noder; i++) {
            besøkt[i] = false;
        }
        for (int i = 0; i < noder; i++) {
            if (besøkt[i] == false){
                ferdigTid(i, besøkt, stack);
            }
        }
        GrafLeser g = transponer();

        for (int i = 0; i < noder; i++) {
            besøkt[i] = false;
        }
        int antSammenhengende = 0;
        while(stack.empty() == false) {
            int n = (int) stack.pop();
            if (noder < 100) {
                if (besøkt[n] == false) {
                    g.DFSUtil(n, besøkt);
                    System.out.println();
                }
            }
            else {
                if (besøkt[n] == false) {
                    g.DFSUtilNoPrint(n, besøkt);
                    antSammenhengende++;
                }
            }
        }
        if (noder >= 100){
            System.out.println("Antall sterkt sammenhengende noder i denne grafen er: " + antSammenhengende);
        }

    }

    public static ArrayList<Integer> lesFIl(String fil) throws IOException {
        BufferedReader bfr = new BufferedReader(new FileReader(fil));
        ArrayList<Integer> txtItems = new ArrayList<>();
        String string;

        while ((string = bfr.readLine()) != null)
        {
            //string = string.trim();
            String[] deler = string.split(" ");
            for (int i = 0; i < deler.length; i++) {
                if ((string.length() != 0)) {
                    txtItems.add(Integer.parseInt(deler[i]));
                }
            }
        }
        return txtItems;
    }

    public static void main(String[] args) throws IOException {
        String fil = "resources/L7g6.txt";
        ArrayList<Integer> grafSifre = lesFIl(fil);
        GrafLeser graf = new GrafLeser(grafSifre.get(0));

        for (int i = 2; i < grafSifre.size(); i=i+2) {
            graf.nyKant(grafSifre.get(i), grafSifre.get(i+1));
        }

        //graf.DFS(grafSifre.get(2));
        System.out.println();
        graf.printSammenhengende();
    }
}

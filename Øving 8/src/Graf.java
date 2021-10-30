import java.io.*;
import java.util.Comparator;
import java.util.Date;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Graf {

    Node[] noder;
    PriorityQueue<Node> pq;

    public Graf(BufferedReader noder, BufferedReader kanter, BufferedReader typer) throws IOException {
        lesNoder(noder);
        lesKanter(kanter);
        lesTyper(typer);
    }

    public void lesNoder(BufferedReader noder) throws IOException {
        StringTokenizer st = new StringTokenizer(noder.readLine());
        int størrelse = Integer.parseInt(st.nextToken());
        this.noder = new Node[størrelse];
        for (int i = 0; i < størrelse; i++) {
            st = new StringTokenizer(noder.readLine());
            int index = Integer.parseInt(st.nextToken());
            double breddegrad = Double.parseDouble(st.nextToken())*(Math.PI/180);
            double lengdegrad = Double.parseDouble(st.nextToken())*(Math.PI/180);
            this.noder[index] = new Node(index, breddegrad, lengdegrad);
            this.noder[index].info = new Forige();
        }
    }

    public void lesKanter(BufferedReader kanter) throws IOException {
        StringTokenizer st = new StringTokenizer(kanter.readLine());
        int størrelse = Integer.parseInt(st.nextToken());
        for (int i = 0; i < størrelse; i++) {
            st = new StringTokenizer(kanter.readLine());
            int fra = Integer.parseInt(st.nextToken());
            int til = Integer.parseInt(st.nextToken());
            int vekt = Integer.parseInt(st.nextToken());
            int lengde = Integer.parseInt(st.nextToken());
            int fartsgrense = Integer.parseInt(st.nextToken());

            Kant k = new Kant(noder[fra].kant, noder[til], vekt, lengde, fartsgrense);
            noder[fra].kant = k;
        }
    }

    public void lesTyper(BufferedReader typer) throws IOException {
        StringTokenizer st = new StringTokenizer(typer.readLine());
        int størrelse = Integer.parseInt(st.nextToken());
        for (int i = 0; i < størrelse; i++) {
            st = new StringTokenizer(typer.readLine());
            int index = Integer.parseInt(st.nextToken());
            int type = Integer.parseInt(st.nextToken());
            String navn  = st.nextToken();
            while (st.hasMoreTokens()){
                navn += " " + st.nextToken();
            }
            noder[index].navn = navn;
            noder[index].type = type;
        }
    }

    public void lagPQ(){
        pq = new PriorityQueue<>(new SammenlignerDijkstra());
    }

    public void lagPQAstar(){
        pq = new PriorityQueue<>(new SammenlignerAStar());
    }

    public int avstandsFormel(Node n1, Node n2){
        double sinusBredde = Math.sin((n1.breddegrad - n2.lengdegrad) / 2.0);
        double sinusLengde = Math.sin((n1.breddegrad - n2.lengdegrad) / 2.0);
        double cosinusBreddeN1 = Math.cos(n1.breddegrad);
        double cosinusBreddeN2 = Math.cos(n2.breddegrad);

        return (int) (2*6371*1000
                * Math.asin(Math.sqrt(sinusBredde * sinusLengde + cosinusBreddeN1 * cosinusBreddeN2 * sinusLengde * sinusLengde)));
    }

    public void forkort(Node n, Kant k){
        Forige fn = n.info;
        Forige fnk = k.nesteNode.info;
        if (fnk.avstand > fn.avstand + k.vekt) {
            fnk.avstand = fn.avstand + k.vekt;
            fnk.forigeNode = n;
            pq.add(k.nesteNode);
        }
    }

    public void forkort(Node n, Kant k, Node mål){
        Forige fn = n.info;
        Forige fnk = k.nesteNode.info;
        if (fnk.avstand > fn.avstand + k.vekt){
            if (fnk.avstandTilMål == -1) {
                fnk.avstandTilMål = avstandsFormel(k.nesteNode, mål);
            }
            fnk.avstand = fn.avstand + k.vekt;
            fnk.forigeNode = n;
            fnk.fullAvstand = fnk.avstand + fnk.avstandTilMål;
            pq.add(k.nesteNode);
        }

    }

    public void dijkstra(Graf g, int start, int slutt){
        Date startTid = new Date();
        int antallNoder = gjørDijkstra(g.noder[start], g.noder[slutt]);
        Date sluttTid = new Date();
        Node n = g.noder[slutt];

        System.out.println("Antall noder hentet ut er: " + antallNoder);
        System.out.println("Dijkstra tok " + (sluttTid.getTime()-startTid.getTime()) + "ms");

        printTid(n);

        try{
            FileWriter os = new FileWriter("Resources/resultat.txt");

            while(n !=null){
                os.write(n.toString()+"\n");
                n = n.info.forigeNode;
            }
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int gjørDijkstra(Node start, Node slutt){
        start.info.avstand = 0;
        slutt.mål = true;
        lagPQ();
        pq.add(start);
        int antall = 0;
        while(!pq.isEmpty()){
            Node n = pq.poll();
            antall++;
            if (n.mål){
                return antall;
            }
            for (Kant k = n.kant; k != null; k = k.neste){
                forkort(n, k);
            }
        }
        return -1;
    }

    public void finnType(int s, int type){
        lagPQ();
        Node start = noder[s];
        Node[] nærmeste = new Node[10];

        start.info.avstand = 0;
        pq.add(start);
        int antall = 0;
        while (!pq.isEmpty()){
            Node n = pq.poll();
            if (n.type == type){
                nærmeste[antall] = n;
                antall++;
                n.brukt = true;
            }
            if (antall == 10){
                pq.clear();
            }
            for (Kant k = n.kant; k != null; k = k.neste){
                forkort(n, k);
            }
        }
        printNærmesteAvType(nærmeste, start);
    }

    public void printNærmesteAvType(Node[] nærmeste, Node start){
        System.out.println("De 10 nærmeste nodene av samme type fra " + start.navn + " er:");
        for (Node n : nærmeste){
            System.out.println(n.navn);
        }

        try{
            FileWriter os = new FileWriter("Resources/resultat.txt");
            for (Node n : nærmeste){
                os.write(n.toString()+"\n");
            }
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void astar(Graf g, int start, int slutt){
        Date startTid = new Date();
        int antallNoder = gjørAstar(noder[start], noder[slutt]);
        Date sluttTid = new Date();

        System.out.println("Antall noder hentet ut er: " + antallNoder);
        System.out.println("A* tok " + (sluttTid.getTime()-startTid.getTime()) + "ms");

        Node n = g.noder[slutt];

        printTid(n);

        try{
            FileWriter os = new FileWriter("Resources/resultat.txt");

            while(n !=null){
                os.write(n.toString()+"\n");
                n = n.info.forigeNode;
            }
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int gjørAstar(Node start, Node slutt){
        start.info.avstand = 0;
        start.info.avstandTilMål = avstandsFormel(start, slutt);
        start.info.fullAvstand = start.info.avstandTilMål;
        slutt.mål = true;
        lagPQAstar();
        pq.add(start);
        int antall = 0;

        while(!pq.isEmpty()){
            Node n = pq.poll();
            antall++;
            if (n.mål){
                return antall;
            }
            for (Kant k = n.kant; k != null; k = k.neste){
                forkort(n, k, slutt);
            }
        }

        return -1;
    }

    public void printTid(Node n){
        int timer = n.info.avstand/360000;
        int minutter = (n.info.avstand%360000)/6000;
        int sekunder = ((n.info.avstand%360000)%6000)/100;
        System.out.println("Kjøretid er: " + timer + " timer " + minutter + " minutter og " + sekunder + " sekunder");
    }

    public static void main(String[] args) {
        Graf g = null;
        try {
            BufferedReader noder = new BufferedReader(new FileReader("Resources/noder.txt"));
            BufferedReader kanter = new BufferedReader(new FileReader("Resources/kanter.txt"));
            BufferedReader typer = new BufferedReader(new FileReader("Resources/interessepkt.txt"));
            g = new Graf(noder, kanter, typer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Dijkstras metode for Trondeheim lufthavn Værnes til Helsinki.
        g.dijkstra(g, 6198111, 1221382);

        //Dijkstras metode for Kårvåg til Gjemnes
        //g.dijkstra(g, 6013683, 6225195);

        //A* metode for Trondeheim lufthavn Værnes til Helsinki.
        //g.astar(g, 6198111, 1221382);

        //A* metode for Kårvåg til Gjemnes
        //g.astar(g, 6013683, 6225195);

        //Finner 10 nærmeste bensinstasjoner fra Trondheim Lufthavn Værnes
        //g.finnType(6198111, 2);

        //Finner 10 nærmeste ladestasjoner fra Røros hotel
        //g.finnType(1117256, 4);

    }
}

class SammenlignerDijkstra implements Comparator<Node> {
    @Override
    public int compare(Node x, Node y) {
        return x.info.avstand-y.info.avstand;
    }
}

class SammenlignerAStar implements Comparator<Node> {
    @Override
    public int compare(Node x, Node y) {
        return x.info.fullAvstand-y.info.fullAvstand;
    }
}
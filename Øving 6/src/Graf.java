import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Graf {
    int N, K;
    Node []node;

    public void ny_graf(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        node = new Node[N];
        for (int i = 0; i < N; i++) {
            node[i] = new Node(i);
        }
        K = Integer.parseInt(st.nextToken());
        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            int fra = Integer.parseInt(st.nextToken());
            int til = Integer.parseInt(st.nextToken());
            int vekt = Integer.parseInt(st.nextToken());
            Vkant k = new Vkant(node[til], (Vkant)node[fra].kant1, vekt, til);
            node[fra].kant1 = k;
        }
    }

    public void initforgj(Node s){
        for (int i = N; i-- > 0 ;) {
            node[i].d = new Forgj();
        }
        ((Forgj)s.d).dist = 0;
    }


    void dijkstra(Node s){
        initforgj(s);
        Node []pri = node;
        PriorityQueue<Node> kø = new PriorityQueue<>(N, Comparator.comparingInt(a -> ((Forgj)a.d).dist));
        for (int i = 0; i < N; i++) {
            kø.add(pri[i]);
        }
        for (int i = N; i > 1; --i) {
            Node n = kø.poll();
            for (Vkant k = (Vkant)n.kant1; k!= null; k = (Vkant)k.neste){
                forkort(n,k, kø);
            }
        }
    }

    public void forkort(Node n, Vkant k, PriorityQueue<Node> pq){
        Forgj nd = (Forgj)n.d, md = (Forgj)k.til.d;
        if (md.dist > nd.dist + k.vekt){
            md.dist = nd.dist + k.vekt;
            md.forgj = n;
            pq.remove(k.til);
            pq.add(k.til);

        }
    }

    public static void main(String[] args) throws IOException {
        Graf obj = new Graf();

        //String fil = "Resources/vg1.txt";
        String fil = "Resources/vg2.txt";
        BufferedReader br = new BufferedReader(new FileReader(new File(fil)));

        obj.ny_graf(br);
        Node s = obj.node[7];
        obj.dijkstra(s);

        System.out.format("%-7s%-7s%-7s%n", "Node", "Fra", "Vekt");
        for (int i = 0; i < obj.N; i++) {
            boolean run = true;
                if (((Forgj)obj.node[i].d).dist != Forgj.uendelig){
                    String fra = (((Forgj) obj.node[i].d).forgj == null) ? "Start" : String.valueOf(((Forgj)obj.node[i].d).forgj.index);
                    System.out.format("%-7s%-7s%-7s%n", i, fra, ((Forgj) obj.node[i].d).dist);
                } else {
                    System.out.format("%-7s%-7s%-7s%n", i, "", "Ikke nådd");
                }
        }
    }
}

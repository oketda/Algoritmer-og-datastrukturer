public class Kant {
    Kant neste;
    Node til;

    public Kant(Node n, Kant nst){
        til = n;
        neste = nst;
    }
}

class Node{
    Kant kant1;
    Object d;
    int index;

    public Node(int index){
        this.index = index;
    }
}

class Forgj{
    int dist;
    Node forgj;
    static int uendelig = 1000000000;

    public int getDist() {
        return dist;
    }

    public Node getForgj() {
        return forgj;
    }

    public Forgj(){
        dist = uendelig;
    }
}

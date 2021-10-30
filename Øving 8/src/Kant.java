public class Kant {

    Kant neste;
    Node nesteNode;
    int vekt;
    int lengde;
    int fartsgrense;

    public Kant(Kant kant, Node node, int vekt, int lengde, int fartsgrense){
        this.neste = kant;
        this.nesteNode = node;
        this.vekt = vekt;
        this.lengde = lengde;
        this.fartsgrense = fartsgrense;
    }
}
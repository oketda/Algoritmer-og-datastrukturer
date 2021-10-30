public class Node {

    int index;
    double breddegrad;
    double lengdegrad;
    Kant kant;
    int type;
    Forige info;
    boolean mål;
    boolean brukt;
    String navn;

    public Node(int index, double breddegrad, double lengdegrad){
        this.index = index;
        this.breddegrad = breddegrad;
        this.lengdegrad = lengdegrad;
    }

    @Override
    public String toString() {
        return this.breddegrad*(Math.PI/180)+","+ this.lengdegrad*(Math.PI/180);
    }
}

class Forige {

    int avstand;
    int fullAvstand;
    int avstandTilMål;
    int inf = 99999999;
    Node forigeNode;

    public Forige(){
        avstand = inf;
        fullAvstand = inf;
        avstandTilMål = -1;

    }
}

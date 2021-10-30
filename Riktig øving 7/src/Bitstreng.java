public class Bitstreng {

    int lengde;
    long biter;

    public Bitstreng(){}


    public Bitstreng(int lengde, long biter){
        this.lengde = lengde;
        this.biter = biter;
    }

    public Bitstreng(int len, byte b){
        this.lengde = len;
        this.biter = konverterByte(b, len);
    }

    public long konverterByte(byte b, int len){
        long temp = 0;
        for(long i = 1<<len-1; i != 0; i >>= 1){
            if((b & i) == 0){
                temp = (temp << 1);
            }
            else temp = ((temp << 1) | 1);
        }
        return temp;
    }

    static Bitstreng linkSammen(Bitstreng s1, Bitstreng s2) {
        Bitstreng ny = new Bitstreng();
        ny.lengde = s1.lengde + s2.lengde;
        if (ny.lengde > 64) {
            System.out.println("For lang bitstreng, gÃ¥r ikke!");
            return null;
        }
        ny.biter = s2.biter | (s1.biter << s2.lengde);
        return ny;
    }
}

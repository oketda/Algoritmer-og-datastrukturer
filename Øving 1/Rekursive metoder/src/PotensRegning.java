import java.util.Date;

public class PotensRegning {

    private double p(double x, int n){
        if (n == 0){
            return 1;
        }
        return x * p(x, n-1);
    }

    private double p2(double x, int n){
        if (n == 0){
            return 1;
        }
        else if (n%2 == 0){
            return p2(x*x, n/2);
        }
        else {
            return x*p2(x*x,(n-1)/2);
        }
    }

    public static void main(String[] args) {
        PotensRegning obj = new PotensRegning();

        System.out.println("Metode fra oppgave 2.1-1:");
        System.out.println("2^10: " + obj.p(2,10));
        System.out.println("3^14: " + obj.p(3,14) + "\n");

        System.out.println("Metode fra oppgave 2.2-3:");
        System.out.println("2^10: " + obj.p2(2,10));
        System.out.println("3^14: " + obj.p2(3,14) + "\n");

        System.out.println("Math.pow():");
        System.out.println("2^10: " + Math.pow(2,10));
        System.out.println("3^14: " + Math.pow(3,14) + "\n");

        int eksponent = 5000;

        System.out.println("Hastighetstest med potensen 1.0001^" + eksponent + ":");

        Date start = new Date();
        int runder = 0;
        double tid;
        Date slutt;
        do {
            obj.p(1.0001, eksponent);
            slutt = new Date();
            ++runder;
        } while (slutt.getTime()-start.getTime() < 1000);
        tid = (double)
                (slutt.getTime()-start.getTime()) / runder;
        double avrundetTid = Math.round(tid * 10000000.0) / 10000000.0;
        System.out.println("Millisekund pr. runde metode fra oppgave 2.1-1: " + avrundetTid);

        Date start2 = new Date();
        int runder2 = 0;
        double tid2;
        Date slutt2;
        do {
            obj.p2(1.0001, eksponent);
            slutt2 = new Date();
            ++runder2;
        } while (slutt2.getTime()-start2.getTime() < 1000);
        tid2 = (double)
                (slutt2.getTime()-start2.getTime()) / runder2;
        double avrundetTid2 = Math.round(tid2 * 10000000.0) / 10000000.0;
        System.out.println("Millisekund pr. runde metode fra oppgave 2.2-3: " + avrundetTid2);

        Date start3 = new Date();
        int runder3 = 0;
        double tid3;
        Date slutt3;
        do {
            Math.pow(1.0001, eksponent);
            slutt3 = new Date();
            ++runder3;
        } while (slutt3.getTime()-start3.getTime() < 1000);
        tid3 = (double)
                (slutt3.getTime()-start3.getTime()) / runder3;
        double avrundetTid3 = Math.round(tid3 * 10000000.0) / 10000000.0;
        System.out.println("Millisekund pr. runde med bruk av Math.pow(): " + avrundetTid3);
    }
}

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class DoubleHash<K, V> {

    ArrayList<HashNode<K, V>> bucketArray;

    private int totalBuckets;
    private int size;
    public int crashes;

    public DoubleHash(){
        bucketArray = new ArrayList<>();
        //totalBuckets = 10739639;
        totalBuckets = 13499999;
        size = 0;

        for (int i = 0; i < totalBuckets; i++) {
            bucketArray.add(null);
        }
    }

    public int getSize(){
        return size;
    }

    public boolean isEmpty(){
        return getSize() == 0;
    }

    private int hash1(K key){
        Integer hashCode = (Integer) key;
        return (hashCode*7) % totalBuckets;
    }

    public int hash2(K key){
        Integer hashCode = (Integer) key;
        return hashCode%(totalBuckets-1)+1;
    }

    public V get(K key, V value)
    {
        int bucketIndex = hash1(key);
        HashNode<K, V> head = bucketArray.get( bucketIndex);

        // Search key in chain
        while (head != null)
        {
            Integer v = (Integer) head.value;
            if (v == (Integer) value) {
                return head.value;
            }
            else if (bucketIndex+hash2(key) > totalBuckets){
                bucketIndex = (bucketIndex+hash2(key))%totalBuckets;
            }
            else {
                bucketIndex = bucketIndex + hash2(key);
            }
            head = bucketArray.get(bucketIndex);
        }

        // If key not found
        return null;
    }

    public void add(K key, V value){
        int bucketIndex = hash1(key);
        HashNode<K, V> head = bucketArray.get(bucketIndex);

        while (head != null){
            bucketIndex = (bucketIndex + hash2(key))%totalBuckets;
            head = bucketArray.get(bucketIndex);
            crashes++;
        }

        size++;
        head = bucketArray.get(bucketIndex);
        HashNode<K, V> newNode = new HashNode<K, V>(key, value);
        bucketArray.set(bucketIndex, newNode);
    }

    public static void main(String[] args) {
        DoubleHash<Integer, Integer> doubleHash = new DoubleHash();
        HashMap<Integer, Integer> hashMap = new HashMap();
        ArrayList<Integer> randomNumbers = new ArrayList<>();
        Random rand = new Random();


        int test1 = 0;

        for (int i = 0; i < 10000000; i++) {
            int randomNumber = Math.abs(rand.nextInt(100000000)+1);
            randomNumbers.add(randomNumber);
            if (i == 100){
                test1 = randomNumber;
            }
        }

        Date start = new Date();
        for (int i = 0; i < randomNumbers.size(); i++) {
            doubleHash.add(randomNumbers.get(i), randomNumbers.get(i));
        }
        Date slutt = new Date();
        Long tid = slutt.getTime()-start.getTime();



        /*Date start2 = new Date();
        for (int i = 0; i < randomNumbers.size(); i++) {
            hashMap.put(randomNumbers.get(i), randomNumbers.get(i));
        }
        Date slutt2 = new Date();
        Long tid2 = slutt2.getTime()-start2.getTime();*/

        System.out.println("Tiden å legge alle elementene inn i min hashtabell var: " + tid + "ms\n");

        //System.out.println("Tiden å legge alle elementene inn i java sin Hashmap var: " + tid2 + "ms\n");

        /*int h1 = doubleHash.hash1(test1);

        System.out.println(test1);
        System.out.println(h1);
        System.out.println(doubleHash.bucketArray.get(h1).value.hashCode());
        System.out.println(doubleHash.get(test1,test1));*/


        double lastfaktor = (double)doubleHash.getSize()/doubleHash.totalBuckets;

        System.out.println("Antall krasjer er: " + doubleHash.crashes);
        System.out.println("Lastfaktor er: " + lastfaktor);
        System.out.println("Antall krasjer per tall er: " + (double)doubleHash.crashes/doubleHash.getSize());
}
}

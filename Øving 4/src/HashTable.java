import java.io.*;
import java.util.ArrayList;

public class HashTable<K, V> {

    ArrayList<HashNode<K, V>> bucketArray;

    private int totalBuckets;
    private int size;
    public int crashes;

    public HashTable(){
        bucketArray = new ArrayList<>();
        totalBuckets = 101;
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
        int hashCode =Integer.parseInt(key.toString());
        int h1 = hashCode % totalBuckets;
        return h1;
    }

    public V get(K key, String navn)
    {
        int bucketIndex =hash1(key);
        HashNode<K, V> head = bucketArray.get(bucketIndex);

        // Search key in chain
        while (head != null)
        {
            String s = head.value.toString();
            s = s.substring(0, s.indexOf(","));
            if (head.key.equals(key) && s.equals(navn)) {
                return head.value;
            }
            head = head.next;
        }

        // If key not found
        return null;
    }

    public void add(K key, V value){
        int bucketIndex = hash1(key);
        HashNode<K, V> head = bucketArray.get(bucketIndex);

        while (head != null){
            head = head.next;
            crashes++;
        }

        size++;
        head = bucketArray.get(bucketIndex);
        HashNode<K, V> newNode = new HashNode<K, V>(key, value);
        newNode.next = head;
        bucketArray.set(bucketIndex, newNode);
    }

    public int createKey(String s){
        char[] charArray = s.toCharArray();
        int sum = 0;

        for (int i = 0; i < charArray.length; i++) {
            sum += (Character.getNumericValue(charArray[i])*7)%totalBuckets;
        }
        return sum;
    }

    public static String[] readTxtFile(String file) throws IOException {
        BufferedReader bfr = new BufferedReader(new FileReader(file));
        ArrayList<String> txtItems = new ArrayList<>();
        String string;

        while ((string = bfr.readLine()) != null)
        {
            string = string.trim();
            if ((string.length()!=0))
            {
                txtItems.add(string);
            }
        }

        String[] navn = (String[])txtItems.toArray(new String[txtItems.size()]);

        return navn;
    }

    public static void main(String[] args) throws IOException {
        HashTable<Integer, String> hashTable = new HashTable<>();

        String navn[] = readTxtFile("resources/navn.txt");

        for (int i = 0; i < navn.length; i++) {
            hashTable.add(hashTable.createKey(navn[i].substring(0, navn[i].indexOf(","))), navn[i]);
        }

        String testNavn = "Herman Tolpinrud";

        double lastfaktor = (double)hashTable.getSize()/hashTable.totalBuckets;

        System.out.println(hashTable.get(hashTable.createKey(testNavn), testNavn));
        System.out.println("Antall krasjer er: " + hashTable.crashes);
        System.out.println("Lastfaktor er: " + lastfaktor);
        System.out.println("Krasjer per person er: " + (double)hashTable.crashes/hashTable.getSize());
    }
}

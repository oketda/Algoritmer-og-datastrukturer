public class Node {

    String value;
    int data;
    Node left;
    Node right;

    Node(String value) {
        this.value = value;
        right = null;
        left = null;
    }

    Node(int data){
        this.data = data;
    }
}

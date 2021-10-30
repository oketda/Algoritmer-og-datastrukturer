import java.util.Random;

public class DualLinkedList {

    Node head;

    public void add(int new_data){

        Node new_Node = new Node(new_data);

        new_Node.right = head;
        new_Node.left = null;

        if (head != null)
            head.left = new_Node;

        head = new_Node;
    }

    //Fills a linked list with random numbers
    public void generateLinkedList(DualLinkedList list, int size){
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            if (i == size-1){
                list.add(rand.nextInt(9)+1);
            }
            else{
                list.add(rand.nextInt(10));
            }
        }
    }

    // This function prints contents of linked list starting from the given node
    public void printlist(Node node)
    {
        Node last = null;
        while (node != null) {
            System.out.print(node.data);
            last = node;
            node = node.right;
        }
        System.out.println();
    }

    public void sum(Node node1, Node node2, DualLinkedList answer) {
        int tot = checkSize(node1, node2);
        boolean run = true;
        node1 = toEnd(node1);
        node2 = toEnd(node2);

        //number that keeps track if 2 added numbers are above 9
        int res = 0;

        if (tot > 0) {
            while (node2 != null) {
                int data = node1.data + node2.data + res;
                res = 0;
                if (data > 9) {
                    data = data % 10;
                    res = 1;
                }
                answer.add(data);
                node1 = node1.left;
                node2 = node2.left;
            }
            while(node1 != null){
                answer.add(node1.data+res);
                res = 0;
                node1 = node1.left;
            }
        }
        else if (tot < 0){
            while (node1 != null) {
                int data = node1.data + node2.data + res;
                res = 0;
                if (data > 9) {
                    data = data % 10;
                    res = 1;
                }
                answer.add(data);
                node1 = node1.left;
                node2 = node2.left;
            }
            while(node2 != null){
                answer.add(node2.data+res);
                res = 0;
                node2 = node2.left;
            }
        }
        else{
            while(node1 != null){
                int data = node1.data + node2.data + res;
                res = 0;
                if (data > 9){
                    data = data % 10;
                    res = 1;
                }
                answer.add(data);
                node1 = node1.left;
                node2 = node2.left;
            }
        }
        if (res > 0){
            answer.add(res);
        }
    }

    public void subtract(Node node1, Node node2, DualLinkedList answer){
        //Int to check wich number is the largest
        int tot = checkSize(node1, node2);
        node1 = toEnd(node1);
        node2 = toEnd(node2);

        //number that keeps track if 1 number subtracted by the other is below 0
        int res = 0;

        if (tot > 0) {
            while (node2 != null) {
                int data = node1.data - node2.data + res;
                res = 0;
                if (data < 0) {
                    data = data + 10;
                    res = -1;
                }
                answer.add(data);
                node1 = node1.left;
                node2 = node2.left;
            }
            while(node1 != null){
                answer.add(node1.data+res);
                res = 0;
                node1 = node1.left;
            }
        }
        else if (tot < 0){
            while (node1 != null) {
                int data = node2.data - node1.data + res;
                res = 0;
                if (data < 0) {
                    data = data + 10;
                    res = -1;
                }
                if (node2.left == null && data == 0){
                    answer.head.data = -answer.head.data;
                    node1 = node1.left;
                    node2 = node2.left;
                }
                else if (node2.left == null && data != 0){
                    answer.add(-data);
                    node1 = node1.left;
                    node2 = node2.left;
                }
                else {
                    answer.add(data);
                    node1 = node1.left;
                    node2 = node2.left;
                }
            }
            while(node2 != null){
                answer.add(node2.data + res);
                res = 0;
                node2 = node2.left;
            }
        }
        else{
            answer.add(0);
        }
    }

    public int checkSize(Node node1, Node node2){
        int tot = 0;
        Node start1 = node1;
        Node start2 = node2;
        boolean run = true;
        while (run){
            if (node1.right == null && node2.right != null){
                tot = -1;
                run = false;
            } else if (node1.right != null && node2.right == null){
                tot = 1;
                run = false;
            }
            else if (node1.right == null && node2.right == null){
                boolean run2 = true;
                while(run2){
                    if (start1.data > start2.data){
                        tot = 1;
                        run2 = false;
                    }
                    else if (start2.data > start1.data){
                        tot = -1;
                        run2 = false;
                    }
                    else{
                        if (start1.right == null && start2.right == null){
                            tot = 0;
                            run2 = false;
                        }
                        else {
                            start1 = start1.right;
                            start2 = start2.right;
                        }
                    }
                }
                run = false;
            }
            else {
                node1 = node1.right;
                node2 = node2.right;
            }
        }

        return tot;
    }

    public Node toEnd(Node node){
        boolean run = true;
        while (run){
            if (node.right != null){
                node = node.right;
            }
            else {
                run = false;
            }
        }
        return node;
    }


    public static void main(String[] args) {
        DualLinkedList dualLinkedList1 = new DualLinkedList();
        DualLinkedList dualLinkedList2 = new DualLinkedList();
        DualLinkedList answer1 = new DualLinkedList();
        DualLinkedList answer2 = new DualLinkedList();

        dualLinkedList1.generateLinkedList(dualLinkedList1, 20);
        dualLinkedList2.generateLinkedList(dualLinkedList2, 20);

        //Check to see if subtraction worked with equal numbers and with specific numbers I wanted to test
        /*dualLinkedList1.add(8);
        dualLinkedList1.add(9);
        dualLinkedList1.add(1);

        dualLinkedList2.add(1);
        dualLinkedList2.add(2);
        dualLinkedList2.add(2);*/


        dualLinkedList1.printlist(dualLinkedList1.head);
        dualLinkedList2.printlist(dualLinkedList2.head);

        answer1.sum(dualLinkedList1.head, dualLinkedList2.head, answer1);
        System.out.print("Number 1 + number 2 = ");
        answer1.printlist(answer1.head);

        answer2.subtract(dualLinkedList1.head, dualLinkedList2.head, answer2);
        System.out.print("Number 1 - number 2 = ");
        answer2.printlist(answer2.head);

    }
}

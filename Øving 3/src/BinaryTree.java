public class BinaryTree {

    Node root;
    final static int COUNT = 10;

    BinaryTree(){
        root = null;
    }

    void insert(String value){
        root = insertRecursive(root, value);
    }

    Node insertRecursive(Node root, String value){
        if (root == null)
        {
            root = new Node(value);
            return root;
        }
        int compared = value.compareTo(root.value);
        if (compared < 0) {
            root.left = insertRecursive(root.left, value);
        }
        else if (compared > 0) {
            root.right = insertRecursive(root.right, value);
        }

        return root;
    }

    void inorderRecursive(Node root){
        if (root != null){
            inorderRecursive(root.left);
            inorderRecursive(root.right);
        }
    }

    void treeInsert(String arr[]){
        for (int i = 0; i < arr.length; i++) {
            insert(arr[i]);
        }
    }

    static void print2DUtil(Node root, int space)
    {
        // Base case
        if (root == null)
            return;

        // Increase distance between levels
        space += COUNT;

        // Process right child first
        print2DUtil(root.right, space);

        // Print current node after space
        // count
        System.out.print("\n");
        for (int i = COUNT; i < space; i++)
            System.out.print(" ");
        System.out.print(root.value + "\n");

        // Process left child
        print2DUtil(root.left, space);
    }

    // Wrapper over print2DUtil()
    void print2D(Node root)
    {
        // Pass initial space count as 0
        print2DUtil(root, 0);
    }

    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        String arr[] = {"Hode", "Ben", "Kne", "Hals", "Tå", "Fot", "Albue", "Arm", "Lår", "Iris"};
        tree.treeInsert(arr);
        tree.inorderRecursive(tree.root);



       tree.print2D(tree.root);
    }
}

import java.util.Random;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

/**
 * A tree which can only be added to on creation and is always a full
 * tree. Only has values in the leaves, and take a size and maxVal for
 * initialization.
 */
public class FullTree {
    public Node root;
    public int[] leaves;
    public int size;

    public FullTree (int size, int maxVal) {
        this.size = size;
        this.createTree(maxVal);
    }

    /**
     * Creates a tree with this.size items in the leaves that contain
     * values no larger than maxVal
     */
    private void createTree (int maxVal) {
        // generate leaf values
        int[] leaves = new int[size];
        ArrayList<Node> currLevel = new ArrayList<Node>(this.size);
        Random rng = new Random();
        for (int i = 0; i < this.size; i++) {
            leaves[i] = rng.nextInt(maxVal + 1);
            currLevel.add(new Node(null, null, leaves[i]));
        }
        this.leaves = leaves;

        // construct binary tree
        ArrayList<Node> nextLevel = null;
        while (nextLevel == null || nextLevel.size() != 1) {
            int count = 0;
            nextLevel = new ArrayList<Node>(currLevel.size() / 2);
            while (count < currLevel.size() / 2) {
                Node left = currLevel.get(count * 2);
                Node right = currLevel.get((count * 2) + 1);
                nextLevel.add(new Node(left, right, -1));
                count++;
            }
            currLevel = nextLevel;
        }

        this.root = nextLevel.get(0);
    }

    /**
     * Exactly what it sounds like
     */
    public void printLevelOrder() {
        Queue<Node> nodes = new LinkedList<Node>();
        nodes.offer(this.root);
        while (!(nodes.peek() == null)) {
            Node curr = nodes.poll();
            System.out.print(curr.val + " ");
            nodes.offer(curr.left);
            nodes.offer(curr.right);
        }
        System.out.println();
    }
}
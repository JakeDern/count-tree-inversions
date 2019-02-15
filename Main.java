import java.util.Arrays;
import java.util.Scanner;
import java.lang.Math;

public class Main {
    public static int numOps = 0;

    // inner class that lets the algorithm return multiple values at each level
    static class InversionTuple {
        public int numInversions;
        public int[] items;
    
        public InversionTuple(int numInversions, int[] items) {
            this.numInversions = numInversions;
            this.items = items;
        }
    }
    public static void main(String[] args) {
        // fetch tree size
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter power of 2 for size of tree: ");
        int size = sc.nextInt();

        // verify tree size
        if ((int)(Math.ceil((Math.log(size) / Math.log(2)))) !=  
        (int)(Math.floor(((Math.log(size) / Math.log(2))))) ) {
            System.out.println("That was not a power of 2");
            System.exit(1);
        }

        // fetch max int val
        System.out.print("Enter max int value: ");
        int maxVal = sc.nextInt();

        // construct tree and calc inverisons
        FullTree tree = new FullTree(size, maxVal);
        InversionTuple t = TreeInversionCount(tree.root);
        System.out.println("\n*****Results****\n");
        System.out.println("Minimum inverions: " + t.numInversions);

        // compute for performed as a function of input size
        double expected = size * (Math.log(size) / Math.log(2));
        double multiplier = numOps / expected;
        String result = "" + Math.ceil(multiplier) + "*" + size + "log(" + size + ")";
        System.out.println("Work performed: " + numOps 
                            + " ~= " + result
                            + " = " + Math.ceil(multiplier) + "*nlog(n)");
        
        // print other stats if they're reasonably sized
        if (size < 100) {
            System.out.println("Original leaves: " + Arrays.toString(tree.leaves));
            System.out.println("Sorted Leaves: " + Arrays.toString(t.items));
            System.out.print("Level order traversal: ");
            tree.printLevelOrder();
        }
    }

    /**
     * Algorithm as given in solution
     */
    public static InversionTuple TreeInversionCount(Node t) {
        if (t.val != -1) {
            numOps += 2;
            return new InversionTuple(0, new int[]{t.val});
        }

        InversionTuple left = TreeInversionCount(t.left);
        InversionTuple right = TreeInversionCount(t.right);
        int lFirst = crossCount(left.items, right.items);
        int rFirst = crossCount(right.items, left.items);
        int[] m = merge(left.items, right.items);

        return new InversionTuple(
            left.numInversions + right.numInversions + Math.min(lFirst,rFirst), m);
    }

    /**
     * Counts inversions that cross the left and right arrays
     */
    public static int crossCount(int[] left, int[] right) {
        int l = 0;
        int r = 0;
        int inversions = 0;

        while (l + r < left.length + right.length) {
            numOps += 1;
            if (l == left.length) {
                numOps += 2;
                return inversions;
            }

            if (r == right.length) {
                numOps += 2;
                inversions += r;
                l++;
            }
            else if (left[l] > right[r]) {
                numOps += 2;
                r++;
            } else if (left[l] < right[r] || left[l] == right[r]) {
                numOps += 4;
                inversions += r;
                l++;
            }
        }
        numOps += 1;
        return inversions;
    }

    /**
     * Merge sort style merge function
     */
    public static int[] merge(int[] a, int[] b) {
        int l = 0;
        int r = 0;
        int i = 0;

        int[] ret = new int[a.length + b.length];
        numOps += 1;
        while (l + r < a.length + b.length) {
            numOps += 1;
            if (l < a.length && (r == b.length || a[l] < b[r]) ) {
                numOps += 3;
                ret[i] = a[l];
                l++;
            } else {
                numOps += 2;
                ret[i] = b[r];
                r++;
            }
            numOps += 1;
            i++;
        }

        numOps += 1;
        return ret;
    }
}
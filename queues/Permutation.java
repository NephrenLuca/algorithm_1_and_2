/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        if (k == 0) {
            return;
        }
        int cnt = 0;
        RandomizedQueue<String> q = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            cnt++;
            if (q.size() == k) {
                if (StdRandom.bernoulli(1.0 * k / cnt)) {
                    q.dequeue();
                    q.enqueue(s);
                }
            }
            else {
                q.enqueue(s);
            }
        }
        while (k-- > 0) {
            StdOut.println(q.dequeue());
        }
    }
}

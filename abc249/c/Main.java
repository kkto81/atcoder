import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.lang.Math;

public final class Main {

  static final int MOD = 998244353;
  static final StringBuilder sb = new StringBuilder();
  static final FastReader fs = new FastReader();

  public static void main(String[] args) throws IOException {
    int n = fs.nextInt();
    int k = fs.nextInt();
    String[] arr = new String[n];
    for (int i = 0; i < n; i++) {
      arr[i] = fs.next();
    }

    int ans = 0;

    for (int i = 1; i < Math.pow(2, n); i++) {
      String temp = ("0".repeat(n) + Integer.toBinaryString(i));
      temp = temp.substring(temp.length() - n);
      int count = myConcat(arr, temp, k);
      if (ans < count)
        ans = count;
    }
    System.out.println(ans);

  }

  static int myConcat(String[] arr, String pos, int target) {
    StringBuilder temp = new StringBuilder();
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < arr.length; i++) {
      if (pos.charAt(i) == '1') {
        temp.append(arr[i]);
      }
    }
    for (char ch : temp.toString().toCharArray()) {
      map.put((int) ch, map.getOrDefault((int) ch, 0) + 1);
    }
    int ans = 0;
    for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
      if (entry.getValue() == target)
        ans++;
    }

    return ans;
  }

  static <T extends Comparable<T>> int myLowerBound(List<T> list, T target) {
    return ~Collections.binarySearch(list, target, (x, y) -> x.compareTo(y) >= 0 ? 1 : -1);
  }

  static <T extends Comparable<T>> int myUpperBound(List<T> list, T target) {
    return ~Collections.binarySearch(list, target, (x, y) -> x.compareTo(y) > 0 ? 1 : -1);
  }

  static class ListNode {
    int val;
    ListNode next;
    ListNode prev;

    ListNode(int val) {
      this.val = val;
    }
  }

  static class Pair implements Comparable<Pair> {
    int l;
    int r;

    public Pair(int l, int r) {
      this.l = l;
      this.r = r;
    }

    public int compareTo(Pair o) {
      // ?????????????????????????????????????????????
      if (this.r == o.r)
        return (this.l - o.l);
      return (int) (this.r - o.r);
      // if (this.l == o.l)
      // return (this.r - o.r);
      // return (int) (this.l - o.l);
    }
  }

  static class UnionFind {
    int[] parent;
    int[] rank;

    public UnionFind(int n) {
      // ??????????????????????????????
      this.parent = new int[n];
      this.rank = new int[n];

      // ????????????????????????
      for (int i = 0; i < n; i++) {
        parent[i] = i;
        rank[i] = 0;
      }
    }

    /**
     * ???????????????????????? ????????????????????????1???3???2??????????????????2???find????????????1???3,2????????????????????????????????????
     *
     * @param x
     * @return ??????x??????
     */
    public int find(int x) {
      if (x == parent[x]) {
        return x;
      } else {
        // ??????????????????rank???????????????
        parent[x] = find(parent[x]);
        return parent[x];
      }
    }

    /**
     * ??????????????????????????????????????????????????????????????????
     *
     * @param x
     * @param y
     * @return ????????????????????????true
     */
    public boolean same(int x, int y) {
      return find(x) == find(y);
    }

    /**
     * ??????x???????????????????????????y???????????????????????????????????? ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     *
     * @param x
     * @param y
     */
    public void unite(int x, int y) {
      int xRoot = find(x);
      int yRoot = find(y);

      if (xRoot == yRoot) {
        // ???????????????????????????????????????????????????
        return;
      }

      // rank?????????????????????????????????????????????
      // ???find?????????????????????rank???????????????
      if (rank[xRoot] > rank[yRoot]) {
        // xRoot???rank?????????????????????????????????????????????xRoot?????????
        parent[yRoot] = xRoot;
      } else if (rank[xRoot] < rank[yRoot]) {
        // yRoot???rank?????????????????????????????????????????????yRoot?????????
        parent[xRoot] = yRoot;
      } else {
        // rank??????????????????????????????????????????????????????rank?????????????????????
        parent[xRoot] = yRoot;
        rank[xRoot]++;
      }
    }
  }

  static final class Utils {

    private static class Shuffler {

      private static void shuffle(int[] x) {
        final Random r = new Random();

        for (int i = 0; i <= x.length - 2; i++) {
          final int j = i + r.nextInt(x.length - i);
          swap(x, i, j);
        }
      }

      private static void shuffle(long[] x) {
        final Random r = new Random();

        for (int i = 0; i <= x.length - 2; i++) {
          final int j = i + r.nextInt(x.length - i);
          swap(x, i, j);
        }
      }

      private static void swap(int[] x, int i, int j) {
        final int t = x[i];
        x[i] = x[j];
        x[j] = t;
      }

      private static void swap(long[] x, int i, int j) {
        final long t = x[i];
        x[i] = x[j];
        x[j] = t;
      }
    }

    public static void shuffleSort(int[] arr) {
      Shuffler.shuffle(arr);
      Arrays.sort(arr);
    }

    public static void shuffleSort(long[] arr) {
      Shuffler.shuffle(arr);
      Arrays.sort(arr);
    }

    private Utils() {}
  }

  static class FastReader {

    private static final int BUFFER_SIZE = 1 << 16;
    private final DataInputStream din;
    private final byte[] buffer;
    private int bufferPointer, bytesRead;

    FastReader() {
      din = new DataInputStream(System.in);
      buffer = new byte[BUFFER_SIZE];
      bufferPointer = bytesRead = 0;
    }

    FastReader(String file_name) throws IOException {
      din = new DataInputStream(new FileInputStream(file_name));
      buffer = new byte[BUFFER_SIZE];
      bufferPointer = bytesRead = 0;
    }

    public String readLine() throws IOException {
      final byte[] buf = new byte[1024]; // line length
      int cnt = 0, c;
      while ((c = read()) != -1) {
        if (c == '\n') {
          break;
        }
        buf[cnt++] = (byte) c;
      }
      return new String(buf, 0, cnt);
    }

    public int readSign() throws IOException {
      byte c = read();
      while ('+' != c && '-' != c) {
        c = read();
      }
      return '+' == c ? 0 : 1;
    }

    private static boolean isSpaceChar(int c) {
      return !(c >= 33 && c <= 126);
    }

    private int skip() throws IOException {
      int b;
      // noinspection StatementWithEmptyBody
      while ((b = read()) != -1 && isSpaceChar(b)) {
      }
      return b;
    }

    public char nc() throws IOException {
      return (char) skip();
    }

    public String next() throws IOException {
      int b = skip();
      final StringBuilder sb = new StringBuilder();
      while (!isSpaceChar(b)) { // when nextLine, (isSpaceChar(b) && b != ' ')
        sb.appendCodePoint(b);
        b = read();
      }
      return sb.toString();
    }

    public int nextInt() throws IOException {
      int ret = 0;
      byte c = read();
      while (c <= ' ') {
        c = read();
      }
      final boolean neg = c == '-';
      if (neg) {
        c = read();
      }
      do {
        ret = ret * 10 + c - '0';
      } while ((c = read()) >= '0' && c <= '9');

      if (neg) {
        return -ret;
      }
      return ret;
    }

    public int[] nextIntArray(int n) throws IOException {
      final int[] res = new int[n];
      for (int i = 0; i < n; i++) {
        res[i] = nextInt();
      }
      return res;
    }

    public long nextLong() throws IOException {
      long ret = 0;
      byte c = read();
      while (c <= ' ') {
        c = read();
      }
      final boolean neg = c == '-';
      if (neg) {
        c = read();
      }
      do {
        ret = ret * 10 + c - '0';
      } while ((c = read()) >= '0' && c <= '9');
      if (neg) {
        return -ret;
      }
      return ret;
    }

    public long[] nextLongArray(int n) throws IOException {
      final long[] res = new long[n];
      for (int i = 0; i < n; i++) {
        res[i] = nextLong();
      }
      return res;
    }

    public double nextDouble() throws IOException {
      double ret = 0, div = 1;
      byte c = read();
      while (c <= ' ') {
        c = read();
      }
      final boolean neg = c == '-';
      if (neg) {
        c = read();
      }

      do {
        ret = ret * 10 + c - '0';
      } while ((c = read()) >= '0' && c <= '9');

      if (c == '.') {
        while ((c = read()) >= '0' && c <= '9') {
          ret += (c - '0') / (div *= 10);
        }
      }

      if (neg) {
        return -ret;
      }
      return ret;
    }

    private void fillBuffer() throws IOException {
      bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
      if (bytesRead == -1) {
        buffer[0] = -1;
      }
    }

    private byte read() throws IOException {
      if (bufferPointer == bytesRead) {
        fillBuffer();
      }
      return buffer[bufferPointer++];
    }

    public void close() throws IOException {
      din.close();
    }
  }
}

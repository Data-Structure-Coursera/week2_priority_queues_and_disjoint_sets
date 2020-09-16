import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class BuildHeap {
    private int[] data;
    private List<Swap> swaps;

    private FastScanner in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        new BuildHeap().solve();
    }

    private void readData() throws IOException {
        int n = in.nextInt();
        data = new int[n];
        for (int i = 0; i < n; ++i) {
          data[i] = in.nextInt();
        }
    }

    private void writeResponse() {
        out.println(swaps.size());
        for (Swap swap : swaps) {
          out.println(swap.index1 + " " + swap.index2);
        }
    }

    void swiftUp(int[] priorityQueue, int index, List<Swap> swaps) {
    	if(index == 1 ) return ;
    	int parent = index / 2; 
//    	while(index > 1 && priorityQueue[parent - 1] > priorityQueue[index - 1]) {
//    		swap(priorityQueue, parent, index);
//    		swaps.add(new Swap(parent - 1, index - 1));
//    		index = parent; 
//    		parent = index / 2; 
//    		
//    	}
    	if(priorityQueue[parent - 1] > priorityQueue[index - 1]) {
    		swap(priorityQueue, parent, index);
    		swaps.add(new Swap(parent - 1, index - 1));
    		index = parent; 
    		swiftUp(priorityQueue, index, swaps);
    	}
    	
    }
    void swap(int[] priorityQueue, int index1, int index2) {
    	int tmp = priorityQueue[index1 - 1];
    	priorityQueue[index1 - 1] = priorityQueue[index2 - 1];
    	priorityQueue[index2 - 1] = tmp;
    	
    }
    
    void swiftDown(int[] priorityQueue, int index, List<Swap> swaps) {
    	
    	int minIndex = index; 
    	int leftIndex = index * 2; 
    	int rightIndex = index * 2 + 1; 
    	int size = priorityQueue.length;
    	if(index >= size) return; 
    	if(leftIndex < size && priorityQueue[leftIndex - 1] < priorityQueue[minIndex - 1]) {
    		minIndex = leftIndex;
    	}
    	
    	if(rightIndex < size && priorityQueue[rightIndex - 1] < priorityQueue[minIndex - 1]) {
    		minIndex = rightIndex;
    	}
    	if(minIndex != index) {
    		swap(priorityQueue, minIndex, index);
    		swiftDown(priorityQueue, minIndex, swaps);
    		swaps.add(new Swap(index, minIndex));
    	}
    }
    
    private void generateSwaps() {
      swaps = new ArrayList<Swap>();
      // The following naive implementation just sorts 
      // the given sequence using selection sort algorithm
      // and saves the resulting sequence of swaps.
      // This turns the given array into a heap, 
      // but in the worst case gives a quadratic number of swaps.
      //
      // TODO: replace by a more efficient implementation
//      for(int i = data.length / 2; i >= 1; i--) {
//    	  swiftDown(data, i, swaps);
//      }
      int sublen = data.length % 2 ==1 ? data.length / 2 + 1 : data.length / 2; 
      for(int i = 0; i < sublen; i++) {
    	  swiftUp(data, data.length - i, swaps);
      }
    }

    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        generateSwaps();
        writeResponse();
        out.close();
    }

    static class Swap {
        int index1;
        int index2;

        public Swap(int index1, int index2) {
            this.index1 = index1;
            this.index2 = index2;
        }
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

}
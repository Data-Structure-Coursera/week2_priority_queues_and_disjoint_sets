import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;




public class JobQueue {
	// input 
    private int numWorkers;
    private int[] jobs;
// output 
    private int[] assignedWorker;
    private long[] startTime;

    // my variable
    private int size;
    private workerInfo[] Worklist;
    private int maximumSize;
    //
    private FastScanner in;
    private PrintWriter out;


    public static void main(String[] args) throws IOException {
        new JobQueue().solve();
    }

    private void readData() throws IOException {
        numWorkers = in.nextInt();
        int m = in.nextInt();
        jobs = new int[m];
        for (int i = 0; i < m; ++i) {
            jobs[i] = in.nextInt();
        }
    }

    private void writeResponse() {
        for (int i = 0; i < jobs.length; ++i) {
            out.println(assignedWorker[i] + " " + startTime[i]);
        }
    }
    
    static class workerInfo {
    	long finishTime; 
    	int worker;
    	public workerInfo(int worker, long finishTime) {
    		this.finishTime = finishTime;
    		this.worker = worker; 
    	}
    	public int compareTo(workerInfo another) {
    		// larger than
    		if(another.finishTime < this.finishTime)
    			return 1; 
    		else if(another.finishTime == this.finishTime) {
    			if(another.worker < this.worker)
    				return 1;
    			else return -1; 
    		}
    		else return -1; 
    	}
    }
    void swap(workerInfo[] priorityQueue, int index1, int index2) {
    	workerInfo tmp = priorityQueue[index1 - 1];
    	priorityQueue[index1 - 1] = priorityQueue[index2 - 1];
    	priorityQueue[index2 - 1] = tmp;
    	
    }
    void swiftDown(workerInfo[] priorityQueue, int index) {
    	int minIndex = index; 
    	int leftIndex = index * 2; 
    	int rightIndex = index * 2 + 1; 
    	if(index > size) return;
//    	int size = priorityQueue.length;
    	if(leftIndex <= size && priorityQueue[leftIndex - 1].compareTo(priorityQueue[minIndex - 1]) == -1) {
    		minIndex = leftIndex;
    	}
    	
    	if(rightIndex <= size && priorityQueue[rightIndex - 1].compareTo(priorityQueue[minIndex - 1]) == -1) {
    		minIndex = rightIndex;
    	}
    	if(minIndex != index) {
    		swap(priorityQueue, minIndex, index);
    		swiftDown(priorityQueue, minIndex);
    	}
    }
    
    void swiftUp(workerInfo[] priorityQueue, int index) {
    	if(index > size) return;
    	if(index == 1 ) return ;
    	int parent = index / 2; 
    	if(priorityQueue[parent - 1].compareTo(priorityQueue[index - 1]) == 1 ) {
    		swap(priorityQueue, parent, index);
    		index = parent; 
    		swiftUp(priorityQueue, index);
    	}
    	
    }

    public boolean insert(workerInfo info) {
    	if(size == maximumSize) 
    		return false; 
    	this.size += 1;
    	this.Worklist[size - 1] = info;
    	swiftUp(Worklist, size);
    	return true; 
    }
    
    public boolean Remove(int index) {
    	if(index > size) {
    		return false; 
    	}
    	Worklist[index - 1].finishTime = Integer.MAX_VALUE; 
    	swiftUp(Worklist, index);
    	extractMin();
    	return true; 
    }
    
    public workerInfo extractMin() {
    	if(size == 0) return null;
    	workerInfo res = Worklist[0];
    	if(size == 1) {
    		size = size -1; 
    		return res; 
    	}
    	Worklist[0] = Worklist[size - 1];
    	size = size -1; 
    	swiftDown(Worklist, 1);
    	return res; 
    }
    


    private void assignJobs() {
        // TODO: replace this code with a faster algorithm.
    	// numWorkers, jobs, assignmedWork 
    	// init
    	assignedWorker = new int[jobs.length];
    	startTime = new long[jobs.length];
    	Worklist = new workerInfo[numWorkers];
    	maximumSize = numWorkers;
    	// build heap 
    	for(int i = 0; i < numWorkers; i++) {
    		workerInfo aWorker = new workerInfo(i, jobs[i]);
    		assignedWorker[i] = aWorker.worker;
    		startTime[i] = 0;
    		insert(aWorker);
    	}
    	
    	for(int i = numWorkers; i < jobs.length; i++) {
    		workerInfo minVal = extractMin();
    		assignedWorker[i] = minVal.worker;
    		startTime[i] = minVal.finishTime;
    		workerInfo aWorker = new workerInfo(assignedWorker[i], startTime[i] + jobs[i]);
    		insert(aWorker);
    	}


    }

    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        assignJobs();
        writeResponse();
        out.close();
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.*;
import java.util.Random;

public class SimulationManager implements Runnable {
	public int timeLimit = 100; // max processing time
	public int maxProcessingTime = 10;
	public int minProcessingTime = 2;
	public int numberOfServices = 3;
	public int numberofClients = 100;
	public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_QUEUE;

	private Scheduler scheduler;

	private List<Task> generatedTasks = new ArrayList();

	public SimulationManager(int N, int Q, int simTime, int ta1, int ta2, int ts1, int ts2) throws Exception {
		timeLimit = simTime;
		// maxProcessingTime = ts2;
		// minProcessingTime = ts1;
		numberOfServices = Q;
		numberofClients = N;

		scheduler = new Scheduler(numberOfServices, numberofClients, selectionPolicy);
		generateNRandomTasks(numberofClients, ta1, ta2, ts1, ts2);

	}

	public void generateNRandomTasks(int N, int ta1, int ta2, int ts1, int ts2) throws Exception {
		for (int i = 0; i < N; i++) {
			Task t = new Task();
			t.generateOneTask(i + 1, ta1, ta2, ts1, ts2);
			generatedTasks.add(t);
		}

	}

	@Override
	public void run() {
		int currentTime = 0;
		int closedCounter = 0;
		while (currentTime <= timeLimit) {
			System.out.println("Time:" + currentTime);
			
			Iterator itr = generatedTasks.iterator();
			while(itr.hasNext()) {
				Task task = (Task)itr.next();
				if (task.getArrTime() == currentTime) {
					scheduler.dispatchTask(task);
					itr.remove();
				}
			}
			
			System.out.print("Wainting clients: ");
			for (Task task : generatedTasks) {
				System.out.print("(" + task.getID() + "," + task.getArrTime() + "," + task.getProcTime() + ")");
			}
			System.out.println();

			for (Server s : scheduler.getServers()) {
				System.out.print("Coada " + s.getID() + ": ");
				if (s.getTasks().isEmpty()) {
					System.out.println("closed");
				} else {
					for (Task m : s.getTasks()) {
						System.out.print("(" + m.getID() + "," + m.getArrTime() + "," + m.getProcTime() + ")	");
					}
					System.out.println();
				}
			}
			System.out.println();
			currentTime++;

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for (Server s : scheduler.getServers()) {
				if (s.getTasks().isEmpty()) {
					closedCounter++;
				}
			}
			
			if( (scheduler.getServers().size() == closedCounter) &&  (generatedTasks.isEmpty()) ) {
				System.out.println("Time:" + currentTime);
				System.out.println("Waiting clients:");
				for (Server s : scheduler.getServers()) {
					System.out.println("Coada " + s.getID() + ": closed");
				}
				
				break;
			}
			closedCounter = 0;
		
		}
		//afisam average waiting time-ul
		scheduler.averageWaitingTime = scheduler.averageWaitingTime / numberofClients;
		System.out.println("Average waiting time: " + scheduler.averageWaitingTime);
		
		System.exit(0);
	}

	public static void main(String[] args) throws Exception {

		File file = new File("V:\\AN2\\TP1\\text.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String[] date = new String[5];

		// citim din bufer
		for (int i = 0; i < 5; i++) {
			date[i] = br.readLine();
		}

		// transformam nr de clienti, nr de cozi si simulation time in integer
		int N = Integer.parseInt(date[0]);
		int Q = Integer.parseInt(date[1]);
		int simTime = Integer.parseInt(date[2]);

		String[] arrTime = date[3].split(",");
		String[] serviceTime = date[4].split(",");

		int tMinArr = Integer.parseInt(arrTime[0]);
		int tMaxArr = Integer.parseInt(arrTime[1]);
		int tMinSer = Integer.parseInt(serviceTime[0]);
		int tMaxSer = Integer.parseInt(serviceTime[1]);

		SimulationManager gen = new SimulationManager(N, Q, simTime, tMinArr, tMaxArr, tMinSer, tMaxSer);
		Thread t = new Thread(gen);
		t.start();
	}
}

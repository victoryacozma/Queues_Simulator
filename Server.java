import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

//queue
public class Server implements Runnable {
	private BlockingQueue<Task> tasks = new LinkedBlockingQueue();
	private int waitingPeriod;
	private int ID;

	public Server() {
		waitingPeriod = 0;
	}

	int getID() {
		return ID;
	}

	void setID(int id) {
		ID = id;
	}

	int getwaitingPeriod() {
		return waitingPeriod;
	}

	public void addTask(Task newTask) {
		tasks.add(newTask);
		waitingPeriod += newTask.getProcTime();
	}

	@Override
	public void run() {
		while (true) {
			Task first = new Task();
			try {
				first = tasks.element();
			} catch (Exception e) {
				continue;
			}
			// System.out.println("s-a extras task-ul" + first.getID());

			try {
				int aux = first.getProcTime();
				for (int i = 0; i < aux; i++) {
					Thread.sleep(1000);
					tasks.element().setProcTime(tasks.element().getProcTime() - 1);
				}
				tasks.take();

			} catch (InterruptedException e) {
				System.out.println("exceptie");
			}
			// folosim pentru strategy
			waitingPeriod -= first.getProcTime();

		}
	}

	public BlockingQueue<Task> getTasks() {
		return tasks;
	}

}

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
	private List<Server> servers = new ArrayList();
	private int maxNoServers;
	private int maxTasksPerServer;
	public float averageWaitingTime = 0;
	private Strategy strategy;

	public Scheduler(int maxNoServers, int maxTasksServer, SelectionPolicy policy) {
		this.maxNoServers = maxNoServers;
		this.maxTasksPerServer = maxTasksServer;

		for (int i = 0; i < maxNoServers; i++) {
			Server aux = new Server();
			aux.setID(i);
			Thread t = new Thread(aux);
			servers.add(aux);
			t.start();
		}

		this.changeStrategy(policy);
	}

	public void changeStrategy(SelectionPolicy policy) {
		if (policy == SelectionPolicy.SHORTEST_QUEUE) {
			strategy = new ConcreteStrategyQueue();
		}
		if (policy == SelectionPolicy.SHORTEST_TIME) {
			strategy = new ConcreteStrategyTime();
		}
	}

	public void dispatchTask(Task t) {
		strategy.addTask(servers, t);
		averageWaitingTime += t.getProcTime();
	}

	public List<Server> getServers() {
		return servers;
	}

}

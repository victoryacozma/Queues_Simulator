import java.io.*;
import java.util.Random;

public class Client {
	private int ID;
	private int arrTime;
	private int servTime;

	public void setID(int id) {
		ID = id;
	}

	public void setarrTime(int a) {
		arrTime = a;
	}

	public void setservTime(int s) {
		servTime = s;
	}

	public int getID() {
		return ID;
	}

	public int getarrTime() {
		return arrTime;
	}

	public int getservTime() {
		return servTime;
	}

	public void generateClient(File file, int id) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(file));

		String[] date = new String[5];
		Random rand = new Random();

		// citim din bufer
		for (int i = 0; i < 5; i++) {
			date[i] = br.readLine();
		}

		// selectam min si max pentru arrival time si service time
		String[] arrTime = date[3].split(",");
		String[] serviceTime = date[4].split(",");

		int tMinArr = Integer.parseInt(arrTime[0]);
		int tMaxArr = Integer.parseInt(arrTime[1]);
		int tMinSer = Integer.parseInt(serviceTime[0]);
		int tMaxSer = Integer.parseInt(serviceTime[1]);

		int aux1 = rand.nextInt((tMaxArr - tMinArr) + 1) + tMinArr;
		this.setarrTime(aux1);

		int aux2 = rand.nextInt((tMaxSer - tMinSer) + 1) + tMinSer;
		this.setservTime(aux2);

		this.setID(id);

	}

}

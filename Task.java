import java.io.*;
import java.util.Random;
//Client
public class Task {
	private int ID;
	private int arrivalTime;
	private int processingTime;
	
	public void setID(int id) {
		ID = id;
	}
	
	public int getID() {
		return ID;
	}
	
	void setArrTime(int a) {
		arrivalTime = a;
	}
	
	void setProcTime(int b) {
		processingTime = b;
	}
	
	int getArrTime() {
		return arrivalTime;
	}
	
	int getProcTime() {
		return processingTime;
	}
	
	public void generateOneTask(int id, int tMinArr, int tMaxArr, int tMinSer, int tMaxSer) throws Exception {
		Random rand = new Random();

		int aux1 = rand.nextInt((tMaxArr - tMinArr) + 1) + tMinArr;
		this.setArrTime(aux1);
		

		int aux2 = rand.nextInt((tMaxSer - tMinSer) + 1) + tMinSer;
		this.setProcTime(aux2);
		

		this.setID(id);

	}


}

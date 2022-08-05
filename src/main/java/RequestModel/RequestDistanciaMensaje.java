package RequestModel;

import java.util.List;

public class RequestDistanciaMensaje{
	private double distance;
	private List<String> message;

	public void setDistance(double distance){
		this.distance = distance;
	}

	public double getDistance(){
		return distance;
	}

	public void setMessage(List<String> message){
		this.message = message;
	}

	public List<String> getMessage(){
		return message;
	}
}

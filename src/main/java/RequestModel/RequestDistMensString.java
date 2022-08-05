package RequestModel;

import java.util.List;

public class RequestDistMensString {
	private String distance;
	private List<String> message;

	public void setDistance(String distance){
		this.distance = distance;
	}

	public String getDistance(){
		return distance;
	}

	public void setMessage(List<String> message){
		this.message = message;
	}

	public List<String> getMessage(){
		return message;
	}
}

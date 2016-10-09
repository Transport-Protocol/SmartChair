package valueTypes;

public class Value {
	
	private int id;
	private int value;
	
	public Value(){}
	
	public Value(int id, int value){
		this.id = id;
		this.value = value;
	}
	
	public int getId() {
		return id;
	}
	
	public int getValue() {
		return value;
	}
	
}

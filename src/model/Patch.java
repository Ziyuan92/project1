package model;

public class Patch {
	private int grain; //the amount of grain that this patch have now
	private int grain_capacity; // the max grain amount that this patch could have
	private int flag; // to note whether this patch has a agent on it, 0 represent no,1 represent yes
	
	public Patch(int grain, int grain_cappacity){
		this.grain=grain;
		this.grain_capacity=grain_cappacity;
		this.flag=0;
	}
	
	/**
	 * grow grain at each interval
	 * @param amount
	 */
	public void grow(int amount){
		if(getGrain()+amount<=this.grain_capacity){
		this.grain+=amount;
		}else{
			this.grain=this.grain_capacity;
		}
	}
	
	/**
	 * grain becomes to 0 after an agent eat them
	 */
	public void empty(){
		this.grain=0;
	}
	
	public int getGrain(){
		return this.grain;
	}
	
	public void setFlag(int flag){
		this.flag=flag;
	}
	public int getFlag(){
		return this.flag;
	}

}

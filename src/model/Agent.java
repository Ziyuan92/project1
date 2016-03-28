package model;

public class Agent {
	private int vision; 
	private int metabolism;
	private int lifespan;
	private int wealth; // amount of grain that it has
	private int x_axis; // position, at which patch, x-coordinate
	private int y_axis; //position, y-coordinate
	private int age;
	
	public Agent(int vision, int metabolism, int lifespan, 
			     int num_of_grain, int x, int y){
		this.vision=vision;
		this.metabolism=metabolism;
		this.lifespan=lifespan;
		this.wealth=num_of_grain;
		this.x_axis=x;
		this.y_axis=y;
		this.age=0;
	}
	
	public void move(int x, int y,int grain){
		this.x_axis=x;
		this.y_axis=y;
		collect(grain);
	}
	public void collect(int grain_amount){
		this.wealth+=grain_amount;
	}
	public void consume(){
		this.wealth-=metabolism;
	}
	public void die() throws Throwable{
		this.finalize();
	}
	public int getWealth(){
		return this.wealth;
	}
	public int getVision(){
		return this.vision;
	}
	public int getXAxis(){
		return this.x_axis;
	}
	public void setXAxis(int x){
		this.x_axis=x;
	}
	public int getYAxis(){
		return this.y_axis;
	}
	public void setYAxis(int y){
		this.y_axis=y;
	}
	//age++ for each time tick
	public void becomeOlder(){
		this.age ++;
	}
	public int getAge(){
		return this.age;
	}
	public int getLifespan(){
		return this.lifespan;
	}

}

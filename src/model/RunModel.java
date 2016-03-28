package model;

public class RunModel {
	private int num_people;
	private int max_vision;
	private int metabolism_max;
	private int life_expectancy_min;
	private int life_expectancy_max;
	private int grain_growth_interval;
	private int num_grain_grown;
	private double percent_best_land; // the initial percent of patch that has its own max amount grain
	private int max_grain; //the maximum amount of grain that any patch could grow
	private Patch[][] land;
	private Agent[] agents;
	
	public RunModel(int num_people, int max_vision, int metabolism_max,
			        int life_min, int life_max, int interval, int num_grow,
			        double percent_best_land, int max_grain){
		this.num_people=num_people;
		this.max_vision=max_vision;
		this.metabolism_max=metabolism_max;
		this.life_expectancy_min=life_min;
		this.life_expectancy_max=life_max;
		this.grain_growth_interval=interval;
		this.num_grain_grown=num_grow;
		this.percent_best_land=percent_best_land;
		this.max_grain=max_grain;
	}
	
	public void setup(){
		//initialize land
		land=new Patch[500][500];
		for( int i=0; i<land.length;i++){
			for(int j=0; j<land[i].length;j++){
				int grain=0;
				int grainCapacity=0;
				double rand=Math.random();
				if(rand<percent_best_land){
					grainCapacity=max_grain;
					grain=grainCapacity;
				}else{
					grainCapacity=(int)(Math.random()*(max_grain+1));
					grain=(int)(Math.random()*(grainCapacity+1));
				}
				land[i][j]=new Patch(grain, grainCapacity);
			}
		}
		
		//generate agents
		agents=new Agent[num_people];
		for(int i=0;i<agents.length;i++){
			agents[i]=generateAgent();
		}
	}
	
	public void run(int total_time){
		//use for loop to represent time tick
		// in each loop, there are all the actions that need to happen at every time tick
		for(int time_tick=0;time_tick<total_time;time_tick++){
			//land grow grain--for each patch
			if((time_tick+1)%grain_growth_interval==0){
				for(int i=0;i<land.length;i++){
					for(int j=0; j<land[i].length;j++){
						land[i][j].grow(num_grain_grown);
					}
				}
			}
			//actions for every agent
			for(int i=0;i<agents.length;i++){
			//agent calculate moving direction
				int position_x=agents[i].getXAxis();
				int position_y=agents[i].getYAxis();
				int target_x = position_x;
				int target_y = position_y;
				int grain=land[position_x][position_y].getGrain();
				//check up and down, x stay the same
				for(int p_y=position_y-agents[i].getVision();
						p_y<=position_y+agents[i].getVision();p_y++){
					if(p_y>=0&&p_y<500){
					if(land[position_x][p_y].getGrain()>grain
							&&land[position_x][p_y].getFlag()==0){
						grain=land[position_x][p_y].getGrain();
						target_x=position_x;
						target_y=position_y;
					}
					}
				}
				//check left and right, y stay the same
				for(int p_x=position_x-agents[i].getVision();
						p_x<=position_x+agents[i].getVision();p_x++){
					if(p_x>=0&&p_x<500){
					if(land[p_x][position_y].getGrain()>grain
							&&land[p_x][position_y].getFlag()==0){
						grain=land[p_x][position_y].getGrain();
						target_x=p_x;
						target_y=position_y;
					}
					}
				}
				//set target position to the agent
				agents[i].setXAxis(target_x);
				agents[i].setYAxis(target_y);
				//set flag to target patch and old patch
				land[target_x][target_y].setFlag(1);
				land[position_x][position_y].setFlag(0);
				//agent collect grain
				agents[i].collect(land[target_x][target_y].getGrain());
				//the grain amount of the target patch becomes to 0
				land[target_x][target_y].empty();
				//agent consume grain
				agents[i].consume();
				//agent's age ++
				agents[i].becomeOlder();
				//check whether agents[i] need to go die
				if(agents[i].getWealth()<=0||agents[i].getAge()>agents[i].getLifespan()){
					try {
						agents[i].die();
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//generate one offspring after agents[i] died
					agents[i]=generateAgent();
				}
			}
			//print the current time tick and the grain amount of each agent
			System.out.println("time tick "+time_tick+": ");
			for(int i=0;i<agents.length;i++){
			System.out.println("Agent["+i+"]'s wealth: "+ agents[i].getWealth());
			}
			System.out.println(" ");
		}
	}
	
	private Agent generateAgent(){
		int vision=(int)(Math.random()*(max_vision+1));
		int metabolism=(int)(Math.random()*(metabolism_max+1));
		int lifespan=(int)(Math.random()*life_expectancy_max)%
				(life_expectancy_max-life_expectancy_min+1)+life_expectancy_min;
		int num_of_grain=(int)(Math.random()*80); //totally random between 0 and 100 atm
		
		int x=0;
		int y=0;
		
		while(land[x][y].getFlag()==1){
			x=(int)(Math.random()*500);
			y=(int)(Math.random()*500);
			
		}
		
		land[x][y].setFlag(1);
					
		Agent agent=new Agent(vision,metabolism,lifespan, 
		     num_of_grain,x,y);
		return agent;
	}

}

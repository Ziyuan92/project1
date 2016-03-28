package model;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int num_people=250;
		int max_vision=5;
		int metabolism_max=15;
		int life_min=1;
		int life_max=81;
		double percent_best_land=0.08;
		int interval=1;
		int num_grow=6;
		int max_grain=50; 
		
		RunModel model=new RunModel(num_people, max_vision, metabolism_max,
			        life_min, life_max, interval, num_grow,
			        percent_best_land, max_grain);
		
		model.setup();
		
		model.run(100);
		//int i=(int)(Math.random()*5+1);
		//System.out.println("i=: "+i);

	}

}

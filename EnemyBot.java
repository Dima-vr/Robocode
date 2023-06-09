package dimasPack;

import robocode.ScannedRobotEvent;

public class EnemyBot {

    private double distance;
    private String name;

    public EnemyBot(){
        reset();
    }

    public double getDistance() {return this.distance;}

    public String getName() {return this.name;}

    public void update(ScannedRobotEvent e){
        this.distance = e.getDistance();
        this.name = e.getName();
    }

    public void reset(){
        this.distance = 0.0;
        this.name = "";
    }

    public boolean none(){
        return this.name.equals("");
    }
}
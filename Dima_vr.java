package dimasPack;

import robocode.ScannedRobotEvent;
import robocode.AdvancedRobot;
import robocode.HitWallEvent;
import robocode.RobotDeathEvent;

import java.util.Random;
import java.awt.Color;

public class Dima_vr extends AdvancedRobot {

	private EnemyBot enemy = new EnemyBot();

	public void run() {
		setAdjustRadarForGunTurn(true);
		setAdjustGunForRobotTurn(true);
		
		setBodyColor(Color.CYAN);
		setRadarColor(Color.BLUE);
		setGunColor(Color.YELLOW);
		
		while (true) {
			// rotate the radar
			setTurnRadarRight(360);

			// move
			move();

			execute();
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) {

		// track that one if it`s the same enemy which we were tracking,
		// or we don`t track anyone,
		// or that enemy is closer than previous one
		if ( e.getName().equals(enemy.getName()) || enemy.none() ||
				e.getDistance() < enemy.getDistance() - 50) {

			// track that enemy (record his info)
			enemy.update(e);
			
			//  calculate gun turn toward enemy
			double turn = getHeading() - getGunHeading() + e.getBearing();
			// normalize the turn to take the shortest path there
			setTurnGunRight(normalizeBearing(turn));
			execute();
			// fire!!!
			if (getGunHeat() == 0 && Math.abs(getGunTurnRemaining()) < 10)
				setFire(Math.min(370 / enemy.getDistance(), 3));

		}
	}

	public void onRobotDeath(RobotDeathEvent event) {
		// if the enemy which we were tracking died - clear info
		if (event.getName().equals(enemy.getName())) {
			enemy.reset();
		}
	}   
	
	private void move() {
		//random moves
        Random randomNum = new Random();
		int angle = randomNum.nextInt(90);
		int distanse = randomNum.nextInt(100);
		if (distanse>50){
			setTurnRight(angle);
		} else {
			setTurnLeft(angle);
		}
        ahead(distanse);
    }

	// 	we do normalizeBearing because we want to be fast
	//	if the angle is greater than 180 degrees, for example 270,
	//	it is faster to turn it on at -90 degrees than at 270 in 3 times
	double normalizeBearing(double angle) {
		while (angle >  180) {
			 angle -= 360;
		}
		while (angle < -180) {
			 angle += 360;
		}
		return angle;
	}

	public void onHitWall(HitWallEvent e) {
		setTurnLeft(200);
        ahead(50);
    }
	
	public void onHitRobot(HitWallEvent e) {
        back(50);
		setTurnLeft(50);
    }
}
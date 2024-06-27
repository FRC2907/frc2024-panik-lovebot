package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import frc.robot.Robot.DriveMode;

public class Drivetrain {
    int i_left_leader = 1, i_left_follower_1 = 2, i_left_follower_2 = 3,
     i_right_leader = 14, i_right_follower_1 = 15, i_right_follower_2 = 16;

    TalonFX left_leader = new TalonFX(i_left_leader);
    TalonFX left_follower_1 = new TalonFX(i_left_follower_1);
    TalonFX left_follower_2 = new TalonFX(i_left_follower_2);

    TalonFX right_leader = new TalonFX(i_right_leader);
    TalonFX right_follower_1 = new TalonFX(i_right_follower_1);
    TalonFX right_follower_2 = new TalonFX(i_right_follower_2);

     public DriveMode mode;

     left_follower_1.setControl(new Follower(i_left_leader, false));
     left_follower_2.setControl(new Follower(i_left_leader, false));
     right_follower_1.setControl(new Follower(i_right_leader, false));
     right_follower_2.setControl(new Follower(i_right_leader, false));
 
     left_leader.setInverted(true);
     right_leader.setInverted(false);

     mode = DriveMode.LOCAL_FORWARD; 

     public enum DriveMode{
        AUTO, LOCAL_FORWARD, LOCAL_REVERSED
      }
    
      private void setDriveMode(DriveMode newMode){
        mode = newMode;
      }
    
      private DriveMode getDriveMode(){
        return mode;
      }
    
      private void handleDriving(){
        double speed = driver.getLeftY();
        double rotation = -driver.getRightX();
    
        switch(mode){
          case AUTO:
          case LOCAL_FORWARD:
            break;
          case LOCAL_REVERSED:
            speed = -speed;
            break;
        }
    
        double left = speed + rotation;
        double right = speed - rotation;
    
        if (left > 1.0 || left < -1.0) { right = right / Math.abs(left); left = left / Math.abs(left); }
        if (right > 1.0 || right < -1.0) { left = left / Math.abs(right); right = right / Math.abs(right); }
    
        left = left / 4;
        right = right / 4;
    
        left_leader.set(left);
        right_leader.set(right);
      } 
}

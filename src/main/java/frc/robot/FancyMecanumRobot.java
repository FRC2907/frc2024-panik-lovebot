package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

public class FancyMecanumRobot extends TimedRobot {
  int //i_left_leader = 1, i_left_follower = 2, i_right_leader = 15, i_right_follower = 16,
      i_top_left = 1, i_bottom_left = 5, i_top_right = 16, i_bottom_right = 11, //TODO placeholders, replace
      i_arm_follower_left = 3, i_arm_leader_right = 14, 
      i_shooter_follower = 4, i_shooter_leader = 13, 
      i_intake = 12;

  CANSparkMax top_left = new CANSparkMax(i_top_left, MotorType.kBrushless);
  CANSparkMax bottom_left = new CANSparkMax(i_bottom_left, MotorType.kBrushless);
  CANSparkMax top_right = new CANSparkMax(i_top_right, MotorType.kBrushless);
  CANSparkMax bottom_right = new CANSparkMax(i_bottom_right, MotorType.kBrushless);


  CANSparkMax arm_leader = new CANSparkMax(i_arm_leader_right, MotorType.kBrushless);
  CANSparkMax arm_follower = new CANSparkMax(i_arm_follower_left, MotorType.kBrushless);

  /*CANSparkMax shooter_leader = new CANSparkMax(i_shooter_leader, MotorType.kBrushless);
  CANSparkMax shooter_follower = new CANSparkMax(i_shooter_follower, MotorType.kBrushless);

  CANSparkMax intake = new CANSparkMax(i_intake, MotorType.kBrushless);*/

  MecanumDrive dt;

  PS4Controller driver = new PS4Controller(0);
  PS4Controller operator = new PS4Controller(1);

  Timer autoTimer = new Timer();

  @Override
  public void robotInit() {
    dt = new MecanumDrive(top_left, bottom_left, top_right, bottom_right);

    top_left.restoreFactoryDefaults();
    bottom_left.restoreFactoryDefaults();
    top_right.restoreFactoryDefaults();
    bottom_right.restoreFactoryDefaults();

    arm_follower.restoreFactoryDefaults();
    arm_leader.restoreFactoryDefaults();

    //shooter_follower.restoreFactoryDefaults();
    //shooter_leader.restoreFactoryDefaults();

    //intake.restoreFactoryDefaults();
    
    

    top_left.setInverted(false);
    bottom_left.setInverted(false);
    top_right.setInverted(true);
    bottom_right.setInverted(true);

    arm_follower.follow(arm_leader);
    arm_leader.setInverted(false);
    arm_follower.setInverted(false);

    /*shooter_follower.follow(shooter_leader);
    shooter_leader.setInverted(true);
    shooter_follower.setInverted(true);*/
  }

  @Override
  public void autonomousInit() {
    autoTimer.restart();
  }

  @Override
  public void autonomousPeriodic() {
    if (autoTimer.get() < 6) {// seconds
      top_left.set(0.2);
      bottom_left.set(0.2);
      top_right.set(0.2);
      bottom_right.set(0.2);
    } else {
      top_left.set(1);    
      bottom_left.set(1);
      top_right.set(-1);
      bottom_right.set(-1);
    }
  }

  @Override
  public void teleopInit(){
  }

  @Override
  public void teleopPeriodic() {
    double speedVertical = - driver.getLeftY();
    double speedHorizontal = - driver.getLeftX();
    double rotation = - driver.getRightX();
    dt.driveCartesian(speedVertical, speedHorizontal, rotation);

    arm_leader.set(operator.getRightY());

    //hold to speed up/shoot
    if (operator.getR2Button()){
      //shooter_leader.set(0.5);
    }
    //turns on intake to shoot
    if (operator.getR2ButtonReleased()){
      //intake.set(-0.3);
    }
    //intake
    if (operator.getL2Button()){
      //intake.set(0.3);
    }
    //turn off intake and shooter
    if (operator.getL1ButtonPressed()){
     // intake.set(0);
      //shooter_leader.set(0);
    }
    //outtake
    if (operator.getR1ButtonPressed()){
      //intake.set(-0.3);
    }
  }
}
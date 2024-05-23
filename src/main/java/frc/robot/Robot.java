// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */

  int i_left_leader = 1, i_left_follower_1 = 2, i_left_follower_2 = 3, i_right_leader = 14, i_right_follower_1 = 15, i_right_follower_2 = 16,
      i_shooter = 7, i_shooter_2 = 8, i_shooter_slave = 9, i_shooter_2_slave = 10,
      i_shooter_extension = 11, 
      i_intake = 12, i_intake_slave = 13;

  TalonFX left_leader = new TalonFX(i_left_leader);
  TalonFX left_follower_1 = new TalonFX(i_left_follower_1);
  TalonFX left_follower_2 = new TalonFX(i_left_follower_2);
  TalonFX right_leader = new TalonFX(i_right_leader);
  TalonFX right_follower_1 = new TalonFX(i_right_follower_1);
  TalonFX right_follower_2 = new TalonFX(i_right_follower_2);

  TalonFX shooter = new TalonFX(i_shooter);
  TalonFX shooter2 = new TalonFX(i_shooter_2);
  TalonFX shooter_slave = new TalonFX(i_shooter_slave);
  TalonFX shooter_2_slave = new TalonFX(i_shooter_2_slave);
  TalonFX shooter_extension = new TalonFX(i_shooter_extension);

  PS4Controller driver = new PS4Controller(0);
  PS4Controller operator = new PS4Controller(1);

  Timer autoTimer = new Timer();

  private static Solenoid leftSolenoid_extend;
  private static Solenoid leftSolenoid_retract;
  private static Solenoid rightSolenoid_extend;
  private static Solenoid rightSolenoid_retract;
  private Compressor compressor;

  private boolean compressorIsRunning;
  private boolean pneumaticOn;

  @Override
  public void robotInit() {
    left_follower_1.setControl(new Follower(i_left_leader, false));
    left_follower_2.setControl(new Follower(i_left_leader, false));
    right_follower_1.setControl(new Follower(i_right_leader, false));
    right_follower_2.setControl(new Follower(i_right_leader, false));

    left_leader.setInverted(false);
    right_leader.setInverted(true);

    leftSolenoid_extend = new Solenoid(PneumaticsModuleType.CTREPCM, 1); 
    leftSolenoid_retract = new Solenoid(PneumaticsModuleType.CTREPCM, 0); 
    rightSolenoid_extend = new Solenoid(PneumaticsModuleType.CTREPCM, 3); 
    rightSolenoid_retract = new Solenoid(PneumaticsModuleType.CTREPCM, 2); 
    compressor = new Compressor(PneumaticsModuleType.CTREPCM); 

    pneumaticOn = false;
  }

  public void pneumaticHandler(boolean position){
    leftSolenoid_extend.set(position);
    rightSolenoid_extend.set(position);

    leftSolenoid_retract.set(!position);
    rightSolenoid_retract.set(!position);
  }

  public void compressorHandler(boolean compOn){
    if (compOn == true){
      compressor.enableHybrid(0.5, 1); //TODO figure out what this is and if it's what we need
      compressorIsRunning = true;
      if (compressor.getPressureSwitchValue() == false){
        compressor.disable(); //TODO figure out if this is the right function to use
      }
    } else {
      compressor.disable();
      compressorIsRunning = false;
    }

    System.out.println("Compressor pressure is low: " + compressor.getPressureSwitchValue());
  }

  @Override
  public void autonomousInit() {
    autoTimer.restart();
  }

  @Override
  public void autonomousPeriodic() {
    //if (autoTimer.get() < 8) {// seconds
    //left_leader.set(0.1);
    //right_leader.set(0.1);
    //} else {
    left_leader.set(0.0);
    right_leader.set(0.0);
    //}
  }

  @Override
  public void teleopPeriodic() {
    double speed = driver.getLeftY();
    double rotation = driver.getRightX();
    double left = speed + rotation;
    double right = speed - rotation;

    if (left > 1.0 || left < -1.0) { right = right / Math.abs(left); left = left / Math.abs(left); }
    if (right > 1.0 || right < -1.0) { left = left / Math.abs(right); right = right / Math.abs(right); }

    left_leader.set(left);
    right_leader.set(right);

    if (operator.getCrossButton()){
      if (pneumaticOn == false){
        pneumaticOn = true;
      } else {
        pneumaticOn = false;
      }
    }
    pneumaticHandler(pneumaticOn);
  }
}

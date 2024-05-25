// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
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
      i_left_shooter = 7, i_right_shooter = 8, 
      i_shooter_extension = 9, 
      i_intake_climb = 10, 
      i_hopper_left = 11, i_hopper_right = 12,
      i_intake = 13;

  TalonFX left_leader = new TalonFX(i_left_leader);
  TalonFX left_follower_1 = new TalonFX(i_left_follower_1);
  TalonFX left_follower_2 = new TalonFX(i_left_follower_2);

  TalonFX right_leader = new TalonFX(i_right_leader);
  TalonFX right_follower_1 = new TalonFX(i_right_follower_1);
  TalonFX right_follower_2 = new TalonFX(i_right_follower_2);

  TalonFX left_shooter = new TalonFX(i_left_shooter);
  TalonFX right_shooter = new TalonFX(i_right_shooter);

  TalonSRX intake_climb = new TalonSRX(i_intake_climb);

  TalonSRX shooter_extension = new TalonSRX(i_shooter_extension);

  TalonSRX hopper_left = new TalonSRX(i_hopper_left);
  TalonSRX hopper_right = new TalonSRX(i_hopper_right);

  TalonSRX intake = new TalonSRX(i_intake);



  PS4Controller driver = new PS4Controller(0);
  //PS4Controller operator = new PS4Controller(1);
  PS4Controller operator = driver;

  Timer autoTimer = new Timer();

  private static Solenoid leftSolenoid_extend;
  private static Solenoid leftSolenoid_retract;
  private static Solenoid rightSolenoid_extend;
  private static Solenoid rightSolenoid_retract;
  private Compressor compressor;

  private boolean compressorIsRunning;

  private AddressableLED m_led;
  private AddressableLEDBuffer m_ledBuffer;
  private int m_rainbowFirstPixelHue;

  private boolean pneumaticOn;
  private boolean intakeOn;
  private boolean intakeClimbOn;

  int[] array;

  @Override
  public void robotInit() {
    left_follower_1.setControl(new Follower(i_left_leader, false));
    left_follower_2.setControl(new Follower(i_left_leader, false));
    right_follower_1.setControl(new Follower(i_right_leader, false));
    right_follower_2.setControl(new Follower(i_right_leader, false));

    left_leader.setInverted(false);
    right_leader.setInverted(true);

    left_shooter.setControl(new Follower(i_right_shooter, true));

    intake_climb.setInverted(true);

    intake.setInverted(true);

    hopper_right.follow(hopper_left, FollowerType.PercentOutput);
    hopper_right.setInverted(true);

    leftSolenoid_extend = new Solenoid(PneumaticsModuleType.CTREPCM, 1); 
    leftSolenoid_retract = new Solenoid(PneumaticsModuleType.CTREPCM, 0); 
    rightSolenoid_extend = new Solenoid(PneumaticsModuleType.CTREPCM, 3); 
    rightSolenoid_retract = new Solenoid(PneumaticsModuleType.CTREPCM, 2); 
    compressor = new Compressor(PneumaticsModuleType.CTREPCM); 

    m_led = new AddressableLED(9); //TODO placeholder port
    m_ledBuffer = new AddressableLEDBuffer(60); //TODO find amount of leds
    m_led.setLength(m_ledBuffer.getLength());
    m_led.setData(m_ledBuffer);
    m_led.start();

    array = new int[m_ledBuffer.getLength()];
    
    for (int i = 0; i < m_ledBuffer.getLength(); i++){
      array[i] = i;
    }

    pneumaticOn = false;
    intakeOn = false;
    intakeClimbOn = false;
  }

  private void pneumaticHandler(boolean position){
    leftSolenoid_extend.set(position);
    rightSolenoid_extend.set(position);

    leftSolenoid_retract.set(!position);
    rightSolenoid_retract.set(!position);
  }

  private void rainbow(){
    for (int i = 0; i < m_ledBuffer.getLength(); i++){
      final int hue = (m_rainbowFirstPixelHue + (i * 180 / m_ledBuffer.getLength())) % 180;
      m_ledBuffer.setHSV(i, hue, 255, 128);
    }
    m_rainbowFirstPixelHue += 3;
    m_rainbowFirstPixelHue %= 180;
  }

  private void lionPride(){
    for (int i = 0; i < m_ledBuffer.getLength(); i++){
      if (array[i] < m_ledBuffer.getLength() / 2){
        m_ledBuffer.setHSV(i, 213, 99, 77); //TODO s and v are entered for percentages, change if necessary
      } else if (array[i] > m_ledBuffer.getLength() / 2){
        m_ledBuffer.setHSV(i, 33, 100, 100); //TODO s and v are entered for percentages, change if necessary
      }
    }
    for (int i = 0; i < m_ledBuffer.getLength(); i++){
      array[i] += 1;
      array[i] %= m_ledBuffer.getLength();
    }
  }


  /*public void compressorHandler(boolean compOn){
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
  } *///probably not necessary

  @Override
  public void autonomousInit() {
    autoTimer.restart();
  }

  @Override
  public void autonomousPeriodic() {
    if (autoTimer.get() < 8) {// seconds
    //left_leader.set(0.1);
    //right_leader.set(0.1);
    //} else {
    left_leader.set(0.0);
    right_leader.set(0.0);
    }
  }

  @Override
  public void teleopPeriodic() {
    double speed = driver.getLeftY();
    double rotation = driver.getRightX();
    double left = speed + rotation;
    double right = speed - rotation;

    if (left > 1.0 || left < -1.0) { right = right / Math.abs(left); left = left / Math.abs(left); }
    if (right > 1.0 || right < -1.0) { left = left / Math.abs(right); right = right / Math.abs(right); }

    left = left / 2;
    right = right / 2;

    left_leader.set(left);
    right_leader.set(right);

    if (operator.getCrossButtonPressed()){
      if (pneumaticOn == false){
        pneumaticOn = true;
        intake.set(ControlMode.PercentOutput, 0.75);
        hopper_left.set(ControlMode.PercentOutput, 0.75);
        intakeOn = true;
      } else {
        pneumaticOn = false;
        intake.set(ControlMode.PercentOutput, 0);
        hopper_left.set(ControlMode.PercentOutput, 0);
        intakeOn = false;
      }
    }

    pneumaticHandler(pneumaticOn);

    /*if (driver.getCircleButtonPressed()){
      if (intakeOn == false){
        intake.set(ControlMode.PercentOutput, 0.75);
        hopper_left.set(ControlMode.PercentOutput, 0.75);
        intakeOn = true;
      } else {
        intake.set(ControlMode.PercentOutput, 0);
        hopper_left.set(ControlMode.PercentOutput, 0);
        intakeOn = false;
      } 
    } */

    if (driver.getSquareButtonPressed()){
      if (intakeClimbOn == false){
        intake_climb.set(ControlMode.PercentOutput, 0.75);
        intakeClimbOn = true;
      } else {
        intake_climb.set(ControlMode.PercentOutput, 0);
        intakeClimbOn = false;
      }
    }

    if (driver.getR2Button()){
      right_shooter.set(1);
    } else {
      right_shooter.set(0);
    }

    if (driver.getPOV() == 0){
      shooter_extension.set(ControlMode.PercentOutput, 0.5);
    } else if (driver.getPOV() == 180){
      shooter_extension.set(ControlMode.PercentOutput, -0.5);
    } else {
      shooter_extension.set(ControlMode.PercentOutput, 0);
    }

    rainbow();
    lionPride(); //choose which one
  }
}

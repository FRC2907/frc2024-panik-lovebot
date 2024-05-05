package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

public class MecanumTest extends TimedRobot {
  int i_top_left = 1, i_bottom_left = 5, i_top_right = 16, i_bottom_right = 11; //TODO placeholders, replace

  CANSparkMax top_left = new CANSparkMax(i_top_left, MotorType.kBrushless);
  CANSparkMax bottom_left = new CANSparkMax(i_bottom_left, MotorType.kBrushless);
  CANSparkMax top_right = new CANSparkMax(i_top_right, MotorType.kBrushless);
  CANSparkMax bottom_right = new CANSparkMax(i_bottom_right, MotorType.kBrushless);



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

    top_left.setInverted(false);
    bottom_left.setInverted(false);
    top_right.setInverted(true);
    bottom_right.setInverted(true);
  }

  @Override
  public void autonomousInit() {
    autoTimer.restart();
  }

  @Override
  public void autonomousPeriodic() {
      top_left.set(0.1);
      bottom_left.set(0.1);
      top_right.set(0.1);
      bottom_right.set(0.1);
  }

  @Override
  public void teleopInit(){}

  @Override
  public void teleopPeriodic() {
    double speedVertical = - driver.getLeftY();
    double speedHorizontal = - driver.getLeftX();
    double rotation = - driver.getRightX();
    dt.driveCartesian(speedVertical, speedHorizontal, rotation);
  }
}
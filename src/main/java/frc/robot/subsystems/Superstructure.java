package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.PS5Controller;



public class Superstructure implements ISubsystem{
    private Drivetrain drivetrain;
    private Feed feed;
    private Hopper hopper;
    private Intake intake;
    private LED led;
    private Shooter shooter;
    private ISubsystem[] subsystems;
    
    PS5Controller driver = new PS5Controller(frc.robot.constants.Ports.HID.DRIVER);
    PS5Controller operator = new PS5Controller(frc.robot.constants.Ports.HID.OPERATOR);

    private RobotState state;
    private boolean automateScoring;



    public enum RobotState {
        MOVING_TO_START,

        START, NEUTRAL,

        MOVING_TO_INTAKING, INTAKING, HOLDING_BALL, 

        MOVING_TO_SCORE, READY_TO_SCORE, SCORING
    }

    private Superstructure(RobotState _state, boolean _automation){
        this.state = _state;
        //this.automateScoring(_automation);
        this.drivetrain = Drivetrain.getInstance();
        this.feed = Feed.getInstance();
        this.hopper = Hopper.getInstance();
        this.intake = Intake.getInstance();
        this.led = LED.getInstance();
        this.shooter = Shooter.getInstance();
        this.subsystems = new ISubsystem[]{drivetrain, feed, hopper, intake, led, shooter};
    }

    private void handleDriving(TalonFX leftMotor, TalonFX rightMotor){
        double left;
        double right;
        double speed = driver.getLeftY();
        double rotation = -driver.getRightX();
    
        switch(drivetrain.getDriveMode()){
          case AUTO:
          case LOCAL_FORWARD:
            break;
          case LOCAL_REVERSED:
            speed = -speed;
            break;
        }
    
        left = speed + rotation;
        right = speed - rotation;
    
        if (left > 1.0 || left < -1.0) { right = right / Math.abs(left); left = left / Math.abs(left); }
        if (right > 1.0 || right < -1.0) { left = left / Math.abs(right); right = right / Math.abs(right); }
    
        left = left / 4;
        right = right / 4;

        leftMotor.set(left);
        rightMotor.set(right);
      } 

    private void handleInputs(){ //TODO change to reference states
      if (operator.getCrossButtonPressed()){
      if (intake.pneumaticOn == false){
        intake.pneumaticOn = true;
        intake.motor.set(ControlMode.PercentOutput, 0.75); //TODO change intake motor to different name
        hopper.motor.set(ControlMode.PercentOutput, 0.75);
        intake.intakeOn = true;
        led.ledRunning = true;
        led.orange();
      } else {
        intake.pneumaticOn = false;
        intake.motor.set(ControlMode.PercentOutput, 0);
        hopper.motor.set(ControlMode.PercentOutput, 0);
        intake.intakeOn = false;
        led.ledRunning = false;
      }
    }

    intake.pneumaticHandler(intake.pneumaticOn);

    if (driver.getSquareButtonPressed()){
      if (feed.feedOn == false){
        feed.motor.set(ControlMode.PercentOutput, 0.75);
        feed.feedOn = true;
        led.ledRunning = true;
        led.red();
      } else {
        feed.motor.set(ControlMode.PercentOutput, 0);
        feed.feedOn = false;
        led.ledRunning = false;
      }
    }

    if (driver.getR2Button()){
      shooter.motor.set(1);
      led.ledRunning = true;
      led.green();
    } else {
      shooter.motor.set(0);
      led.ledRunning = false;
    }

    if (driver.getR1ButtonPressed()){
      drivetrain.reverse();
    }
  }

    @Override
    public void onLoop(){
      handleDriving(drivetrain.leftMotor, drivetrain.rightMotor);
      handleInputs();

      for (ISubsystem s : this.subsystems){
        s.onLoop();
      }
    }

    @Override
    public void submitTelemetry(){}

    @Override
    public void receiveOptions(){}
    
}

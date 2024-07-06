package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.constants.Ports;

public class Intake implements ISubsystem{
    //int i_intake = 13;

    private static Solenoid leftExtend;
    private static Solenoid leftRetract;
    private static Solenoid rightExtend;
    private static Solenoid rightRetract;
    public static Solenoid pneumatics;

    public TalonSRX motor;

    public boolean pneumaticOn;
    public boolean intakeOn;

    private Intake(Solenoid _pneumatics, TalonSRX _motor){
      motor = _motor;
      _motor.setInverted(true);
      pneumatics = _pneumatics;
      pneumaticOn = false;
      intakeOn = false;
    }

    private static Intake instance;

    public static Intake getInstance(){
      if (instance == null){
        leftExtend = new Solenoid(PneumaticsModuleType.CTREPCM, frc.robot.constants.Ports.CTREPCM.SOLENOIDS.L_EXTEND); 
        leftRetract = new Solenoid(PneumaticsModuleType.CTREPCM, frc.robot.constants.Ports.CTREPCM.SOLENOIDS.L_RETRACT); 
        rightExtend = new Solenoid(PneumaticsModuleType.CTREPCM, frc.robot.constants.Ports.CTREPCM.SOLENOIDS.R_EXTEND); 
        rightRetract = new Solenoid(PneumaticsModuleType.CTREPCM, frc.robot.constants.Ports.CTREPCM.SOLENOIDS.R_RETRACT); 

        pneumatics = leftExtend;

        TalonSRX motor = new TalonSRX(Ports.talon.intake.MOTOR);

        instance = new Intake(pneumatics, motor);
      }
      return instance;
    }


    public void pneumaticHandler(boolean position){
      leftExtend.set(position);
      rightExtend.set(position);
    
      leftRetract.set(!position);
      rightRetract.set(!position);
    }

    public void pneumaticHandler(){
      if (leftExtend.isDisabled() == true){
        rightExtend.set(false);
        
        leftRetract.set(true);
        rightRetract.set(true);
      }
    }

    @Override
    public void onLoop(){
      pneumaticHandler();
    }

    @Override
    public void submitTelemetry(){}

    @Override
    public void receiveOptions(){}
}

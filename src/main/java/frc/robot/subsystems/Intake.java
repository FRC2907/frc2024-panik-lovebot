package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.constants.Ports;

public class Intake implements ISubsystem{
    int i_intake = 13;

    TalonSRX intake = new TalonSRX(Ports.talon.intake.MOTOR);

    private static Solenoid leftSolenoid_extend;
    private static Solenoid leftSolenoid_retract;
    private static Solenoid rightSolenoid_extend;
    private static Solenoid rightSolenoid_retract;

    private boolean pneumaticOn;
    private boolean intakeOn;

    private Intake(Solenoid pneumatics, TalonSRX intake){
      intake.setInverted(true);

      leftSolenoid_extend = new Solenoid(PneumaticsModuleType.CTREPCM, 1); 
      leftSolenoid_retract = new Solenoid(PneumaticsModuleType.CTREPCM, 0); 
      rightSolenoid_extend = new Solenoid(PneumaticsModuleType.CTREPCM, 3); 
      rightSolenoid_retract = new Solenoid(PneumaticsModuleType.CTREPCM, 2); 
      pneumaticOn = false;
      intakeOn = false;
    }

    private static Intake instance;

    public static Intake getInstance(){
      if (instance == null){

      }
      return instance;
    }


    public void pneumaticHandler(boolean position){
        leftSolenoid_extend.set(position);
        rightSolenoid_extend.set(position);
    
        leftSolenoid_retract.set(!position);
        rightSolenoid_retract.set(!position);
    }

    @Override
    public void onLoop(){}

    @Override
    public void submitTelemetry(){}

    @Override
    public void receiveOptions(){}
}

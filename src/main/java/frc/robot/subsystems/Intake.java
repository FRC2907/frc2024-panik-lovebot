package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.constants.Ports;

public class Intake implements ISubsystem{
    //int i_intake = 13;

    TalonSRX intake = new TalonSRX(Ports.talon.intake.MOTOR);

    private static Solenoid leftExtend;
    private static Solenoid leftRetract;
    private static Solenoid rightExtend;
    private static Solenoid rightRetract;

    public boolean pneumaticOn;
    public boolean intakeOn;

    private Intake(Solenoid pneumatics, TalonSRX intake){
      intake.setInverted(true);

      leftExtend = new Solenoid(PneumaticsModuleType.CTREPCM, frc.robot.constants.Ports.CTREPCM.SOLENOIDS.L_EXTEND); 
      leftRetract = new Solenoid(PneumaticsModuleType.CTREPCM, frc.robot.constants.Ports.CTREPCM.SOLENOIDS.L_RETRACT); 
      rightExtend = new Solenoid(PneumaticsModuleType.CTREPCM, frc.robot.constants.Ports.CTREPCM.SOLENOIDS.R_EXTEND); 
      rightRetract = new Solenoid(PneumaticsModuleType.CTREPCM, frc.robot.constants.Ports.CTREPCM.SOLENOIDS.R_RETRACT); 
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
        leftExtend.set(position);
        rightExtend.set(position);
    
        leftRetract.set(!position);
        rightRetract.set(!position);
    }

    @Override
    public void onLoop(){}

    @Override
    public void submitTelemetry(){}

    @Override
    public void receiveOptions(){}
}

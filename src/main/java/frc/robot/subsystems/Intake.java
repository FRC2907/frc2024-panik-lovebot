package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;

public class Intake {
    int i_intake = 13;

    TalonSRX intake = new TalonSRX(i_intake);

    private static Solenoid leftSolenoid_extend;
    private static Solenoid leftSolenoid_retract;
    private static Solenoid rightSolenoid_extend;
    private static Solenoid rightSolenoid_retract;

    intake.setInverted(true);

    leftSolenoid_extend = new Solenoid(PneumaticsModuleType.CTREPCM, 1); 
    leftSolenoid_retract = new Solenoid(PneumaticsModuleType.CTREPCM, 0); 
    rightSolenoid_extend = new Solenoid(PneumaticsModuleType.CTREPCM, 3); 
    rightSolenoid_retract = new Solenoid(PneumaticsModuleType.CTREPCM, 2); 

    pneumaticOn = false;
    intakeOn = false;

    private void pneumaticHandler(boolean position){
        leftSolenoid_extend.set(position);
        rightSolenoid_extend.set(position);
    
        leftSolenoid_retract.set(!position);
        rightSolenoid_retract.set(!position);
      }
}

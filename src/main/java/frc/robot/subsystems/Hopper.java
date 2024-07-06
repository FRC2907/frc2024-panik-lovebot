package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.util.Util;

public class Hopper implements ISubsystem{
    //int i_hopper_left = 11, i_hopper_right = 12;

    /* TalonSRX hopper_left = new TalonSRX(frc.robot.constants.Ports.talon.hopper.LEFT);
    TalonSRX hopper_right = new TalonSRX(frc.robot.constants.Ports.talon.hopper.RIGHT); */

    public TalonSRX motor;

    private Hopper(TalonSRX _motor){
        motor = _motor;
    }

    private static Hopper instance;

    public static Hopper getInstance(){
        if (instance == null){
            TalonSRX motor = Util.createTalonSRXGroup(frc.robot.constants.Ports.talon.hopper.MOTORS, false, true);
            instance = new Hopper(motor);
        }
        return instance;
    }

    @Override
    public void onLoop(){}

    @Override
    public void submitTelemetry(){}

    @Override
    public void receiveOptions(){}
}

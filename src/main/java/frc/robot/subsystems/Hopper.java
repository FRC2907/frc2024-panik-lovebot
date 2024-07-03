package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Hopper implements ISubsystem{
    int i_hopper_left = 11, i_hopper_right = 12;

    TalonSRX hopper_left = new TalonSRX(frc.robot.constants.Ports.talon.hopper.LEFT);
    TalonSRX hopper_right = new TalonSRX(frc.robot.constants.Ports.talon.hopper.RIGHT);

    private Hopper(TalonSRX hopper){
        hopper_right.follow(hopper_left, FollowerType.PercentOutput);
        hopper_right.setInverted(true);
    }

    private static Hopper instance;

    public static Hopper getInstance(){
        if (instance == null){
            
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

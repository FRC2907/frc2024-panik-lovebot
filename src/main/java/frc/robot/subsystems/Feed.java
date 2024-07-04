package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Feed implements ISubsystem{
    //int i_feed = 10;

    public TalonSRX motor;

    public boolean feedOn;

    private Feed(TalonSRX _feed){
        motor = _feed;
        feedOn = false;
    }

    private static Feed instance;

    public static Feed getInstance(){
        if (instance == null){
            TalonSRX motor = new TalonSRX(frc.robot.constants.Ports.talon.feed.MOTOR);
            motor.setInverted(true);
            instance = new Feed(motor);
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

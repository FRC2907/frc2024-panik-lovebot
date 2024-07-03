package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Feed implements ISubsystem{
    int i_feed = 10;

    TalonSRX feed = new TalonSRX(frc.robot.constants.Ports.talon.feed.MOTOR);

    private boolean feedOn;

    private Feed(TalonSRX feed){
        feed.setInverted(true);
        feedOn = false;
    }

    private static Feed instance;

    public static Feed getInstance(){
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

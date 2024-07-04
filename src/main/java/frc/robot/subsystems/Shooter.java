package frc.robot.subsystems;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;

import frc.robot.util.Util;

public class Shooter implements ISubsystem{
    //int i_left_shooter = 7, i_right_shooter = 8;

    public TalonFX motor;

    private Shooter(TalonFX _motor){
        motor = _motor;
    }

    private static Shooter instance;

    @SuppressWarnings({"resource"})
    public static Shooter getInstance(){
        if (instance == null){
            TalonFX left, right;
            TalonFX leftShooter = new TalonFX(frc.robot.constants.Ports.talon.shooter.LEFT);
            TalonFX rightShooter = new TalonFX(frc.robot.constants.Ports.talon.shooter.RIGHT);
            leftShooter.setControl(new Follower(8, true));
            instance = new Shooter(rightShooter);

            TalonFX motor = Util.createTalonFXGroup(frc.robot.constants.Ports.talon.shooter.MOTORS, false, true);
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

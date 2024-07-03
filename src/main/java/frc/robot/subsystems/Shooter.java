package frc.robot.subsystems;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;

public class Shooter implements ISubsystem{
    int i_left_shooter = 7, i_right_shooter = 8;

    private TalonFX motor;

    private Shooter(TalonFX _motor){
        this.motor = _motor;
    }

    private static Shooter instance;

    @SuppressWarnings({"resource"})
    public static Shooter getInstance(){
        if (instance == null){
            TalonFX left, right;
            TalonFX leftShooter = new TalonFX(frc.robot.constants.Ports.talon.shooter.LEFT);
            TalonFX rightShooter = new TalonFX(frc.robot.constants.Ports.talon.shooter.LEFT);
            leftShooter.setControl(new Follower(8, true));
            instance = new Shooter(rightShooter);
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

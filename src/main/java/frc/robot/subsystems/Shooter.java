package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

public class Shooter {
    int i_left_shooter = 7, i_right_shooter = 8, 
    i_shooter_extension = 9;

    TalonFX left_shooter = new TalonFX(i_left_shooter);
    TalonFX right_shooter = new TalonFX(i_right_shooter);

    left_shooter.setControl(new Follower(i_right_shooter, true));

}

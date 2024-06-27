package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Hopper {
    int i_hopper_left = 11, i_hopper_right = 12;

    TalonSRX hopper_left = new TalonSRX(i_hopper_left);
    TalonSRX hopper_right = new TalonSRX(i_hopper_right);

    hopper_right.follow(hopper_left, FollowerType.PercentOutput);
    hopper_right.setInverted(true);
}

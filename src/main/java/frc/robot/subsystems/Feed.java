package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Feed {
    int i_feed = 10;

    TalonSRX feed = new TalonSRX(i_feed);

    feed.setInverted(true);

    feedOn = false;
}

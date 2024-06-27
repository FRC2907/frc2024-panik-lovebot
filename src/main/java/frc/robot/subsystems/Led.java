package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;

public class Led {

    private AddressableLEDBuffer m_ledBuffer;
    private int m_rainbowFirstPixelHue;
    private PWMSparkMax led;
    private boolean ledRunning;

    int[] array;

    led = new PWMSparkMax(0);
    ledRunning = false;

    array = new int[m_ledBuffer.getLength()];
    
    for (int i = 0; i < m_ledBuffer.getLength(); i++){
      array[i] = i;
    }
}

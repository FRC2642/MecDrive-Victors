package org.usfirst.frc.team2642.robot;


import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.Encoder;


/**
 * This is a demo program showing how to use Mecanum control with the RobotDrive class.
 */
public class Robot extends SampleRobot {
	
    RobotDrive robotDrive;
    Joystick stick;
    Relay relay1;
    Gyro gyro1;
	Encoder frontRightEncoder;
	Encoder frontLeftEncoder;
	Encoder backRightEncoder;
	Encoder backLeftEncoder;
	
    final int frontLeftChannel	= 2;
    final int rearLeftChannel	= 3;
    final int frontRightChannel	= 1;
    final int rearRightChannel	= 0;
    
    // The channel on the driver station that the joystick is connected to
    // final int joystickChannel	= 0;

    public Robot() {
    	robotDrive.setExpiration(0.1);
        robotDrive = new RobotDrive(frontLeftChannel, rearLeftChannel, frontRightChannel, rearRightChannel);
    	robotDrive.setInvertedMotor(MotorType.kFrontLeft, true);	// invert the left side motors
    	robotDrive.setInvertedMotor(MotorType.kRearLeft, true);		// you may need to change or remove this to match your robot
    	stick = new Joystick(0);
    	relay1 = new Relay(1);
    	frontRightEncoder = new Encoder(0, 1);
    	frontLeftEncoder = new Encoder (2, 3);
    	backRightEncoder = new Encoder (4, 5);
    	backLeftEncoder = new Encoder (6, 7);
    
    	gyro1 = new Gyro(4);
    	
    }
        
    public void autonomous() {
    	robotDrive.setSafetyEnabled(false);
        gyro1.reset();
        while (isAutonomous() && frontRightEncoder.getDistance() < 100);
 {
            double angle = gyro1.getAngle(); // get current heading
            robotDrive.drive(-1.0, -angle*4); // drive towards heading 0
            Timer.delay(0.004);

    }
    }
    /**
     * Runs the motors with Mecanum drive.
     */
    public void operatorControl() {
        robotDrive.setSafetyEnabled(true);
        while (isOperatorControl() && isEnabled()) {
            Timer.delay(0.005);		// wait for a motor update time
            if (stick.getRawButton(1)){  // Halves inputs for wheels for greater control
            	robotDrive.mecanumDrive_Cartesian(stick.getX()/2, stick.getY()/2, stick.getZ()/2, 0);
            	
            }else{
            	robotDrive.mecanumDrive_Cartesian(stick.getX(), stick.getY(), stick.getZ(), 0);
            }if (stick.getRawButton(2)){  // Sets buttons for a relay, and stops the relay if a button isn't pressed
            	relay1.set(Relay.Value.kForward);
            }else if(stick.getRawButton(3)) {
            	relay1.set(Relay.Value.kReverse);
            }else{
            	relay1.set(Relay.Value.kOff);
        }
    }
    
}
}
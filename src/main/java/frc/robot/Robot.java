// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import javax.lang.model.util.ElementScanner14;

import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static final int LEFT_BACK_DRIVE_PORT = 1;
    public static final int RIGHT_BACK_DRIVE_PORT = 2;
    public static final int LEFT_FRONT_DRIVE_PORT = 3;
    public static final int RIGHT_FRONT_DRIVE_PORT = 4;

    public static WPI_TalonFX leftBackMotor = new WPI_TalonFX(LEFT_BACK_DRIVE_PORT);
    public static WPI_TalonFX rightBackMotor = new WPI_TalonFX(RIGHT_BACK_DRIVE_PORT);
    public static WPI_TalonFX leftFrontMotor = new WPI_TalonFX(LEFT_FRONT_DRIVE_PORT);
    public static WPI_TalonFX rightFrontMotor = new WPI_TalonFX(RIGHT_FRONT_DRIVE_PORT);

    

    private static final double IN_TO_M = .0254;
        // public static final int MOTOR_ENCODER_COUNTS_PER_REV = 2048; //4096 for CTRE Mag Encoders, 2048 for the Falcons
        public static final double MOTOR_ENCODER_COUNTS_PER_REV = 2048.0;
        private static final double DIAMETER_INCHES = 6.0; // wheel diameter
        private static final double WHEEL_DIAMETER = DIAMETER_INCHES * IN_TO_M; // in meters
        private static final double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;
        public static final double GEAR_RATIO = 10.71;
        public static final double TICKS_PER_METER = (MOTOR_ENCODER_COUNTS_PER_REV * GEAR_RATIO) / (WHEEL_CIRCUMFERENCE);
        public static final double METERS_PER_TICKS = 1 / TICKS_PER_METER;

  public void resetEncoders() {
  leftBackMotor.setSelectedSensorPosition(0);
  rightBackMotor.setSelectedSensorPosition(0);
  leftFrontMotor.setSelectedSensorPosition(0);
  rightFrontMotor.setSelectedSensorPosition(0);
        }
public double distanceTravelledinTicks() {
          return (getBackLeftEncoderPosition() + getBackRightEncoderPosition()) / 2;
        }
      
        public double getBackLeftEncoderPosition() {
          return leftBackMotor.getSelectedSensorPosition();
        }
      
        public double getBackRightEncoderPosition() {
          return rightBackMotor.getSelectedSensorPosition();
        }

  double setpoint = 0;
  double kP = 0.5;
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    leftFrontMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 10);
    rightFrontMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 10);
    leftBackMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 10);
    rightBackMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 10);
  }

  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("encoder value", distanceTravelledinTicks() * METERS_PER_TICKS);
  }

  @Override
  public void autonomousInit() {
    resetEncoders(); 

  }

  @Override
  public void autonomousPeriodic() {
    setpoint = 2;
    double sensorPosition = distanceTravelledinTicks() * METERS_PER_TICKS;
    double error = setpoint - sensorPosition;
    double outputSpeed = error * kP;

    if (outputSpeed > 1) {
      outputSpeed = 1;
    } else if (outputSpeed < -1) {
      outputSpeed = -1;
    } else {
      outputSpeed = outputSpeed;
    }

    leftBackMotor.set(outputSpeed);
    leftFrontMotor.set(outputSpeed);
    rightBackMotor.set(-outputSpeed);
    rightFrontMotor.set(-outputSpeed);
  }

  @Override
  public void teleopInit() {} 

  @Override
  public void teleopPeriodic() {}

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}

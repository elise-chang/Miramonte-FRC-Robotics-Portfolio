// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import javax.print.attribute.standard.JobHoldUntil;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

// // lime light imports




/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  // drive train motors
  private final CANSparkMax leftFrontMotor = new CANSparkMax(Constants.LEFT_FRONT_MOTOR_ID, MotorType.kBrushed);
  private final CANSparkMax leftBackMotor = new CANSparkMax(Constants.LEFT_BACK_MOTOR_ID, MotorType.kBrushed);
  private final CANSparkMax rightFrontMotor = new CANSparkMax(Constants.RIGHT_FRONT_MOTOR_ID, MotorType.kBrushed);
  private final CANSparkMax rightBackMotor = new CANSparkMax(Constants.RIGHT_BACK_MOTOR_ID, MotorType.kBrushed);

  // arm motors
  private final Spark motor1 = new Spark(5);
  private final Spark motor2 = new Spark(6);
  private final Spark motor3 = new Spark(7);
  

  private final MotorControllerGroup leftMotorGroup = new MotorControllerGroup(leftFrontMotor, leftBackMotor);
  private final MotorControllerGroup rightMotorGroup = new MotorControllerGroup(rightFrontMotor, rightBackMotor);

  private final DifferentialDrive drivetrain = new DifferentialDrive(leftMotorGroup, rightMotorGroup);
  
  private final Joystick joy1 = new Joystick(0);
  private final XboxController controller = new XboxController(0);

  private final Timer timer = new Timer();

  NetworkTable table; // The network table for limelight
  boolean ledOn; // A boolean flag for LED status


  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    // LIMELIGHT chatGPT ;)
    // initialize Limelight instance
    table = NetworkTableInstance.getDefault().getTable("limelight"); // Get limelight table
    ledOn = false; // Set LED flag to false initially
    // initialize Xbox controller instance

    rightMotorGroup.setInverted(true);
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    timer.reset();
    timer.start();
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    // drivetrain.arcadeDrive(joy1.getY(), joy1.getZ());
    drivetrain.arcadeDrive(controller.getLeftY(), controller.getRightX()*0.4 + (controller.getRightX()/(Math.abs(controller.getRightX())))*0.3);
    
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry tx = table.getEntry("tx");
    NetworkTableEntry ty = table.getEntry("ty");
    NetworkTableEntry ta = table.getEntry("ta");

    //read values periodically
    double x = tx.getDouble(0.0);
    double y = ty.getDouble(0.0);
    double area = ta.getDouble(0.0);

    //post to smart dashboard periodically
    SmartDashboard.putNumber("LimelightX", x);
    SmartDashboard.putNumber("LimelightY", y);
    SmartDashboard.putNumber("LimelightArea", area);


    // double speed = -joy1.getRawAxis(1)*0.6; // down is positive for this axis
    // double turn = joy1.getRawAxis(4)*0.3; //slow speed to 60% and turn to 30%

    // double left = speed + turn;
    // double right = speed - turn;

    // leftMotorGroup.set(left);
    // rightMotorGroup.set(-right); // invert it so we can go forward

    // LIME LIGHT STUFF (bing ai)
    // Update LED status in teleopPeriodic() method
    // if (controller.getXButtonPressed()) { // If x button is pressed
    //   ledOn = !ledOn; // Toggle LED flag
    //   System.out.print("The X button as been pressed");
    //   if (ledOn) { // If LED flag is true
    //     table.getEntry("ledMode").setNumber(3); // Turn on LED (force mode)
      
    //   } else {   // If LED flag is false
    //     table.getEntry("ledMode").setNumber(1); // Turn off LED (force mode)
      
    //   }
    // }

    if (controller.getRawButtonPressed(3)) { // If x button is pressed
      ledOn = !ledOn; // Toggle LED flag
      System.out.print("The X button as been pressed");
      if (ledOn) { // If LED flag is true
        table.getEntry("ledMode").setNumber(3); // Turn on LED (force mode)
      
      } else {   // If LED flag is false
        table.getEntry("ledMode").setNumber(1); // Turn off LED (force mode)
      }
    }

    // leftFrontMotor.set(left);
    // leftBackMotor.set(left);
    // rightFrontMotor.set(-right);
    // rightBackMotor.set(-right);
  } 

  public void armMovement(){ // might use a timer 
    if (controller.getRawButton(5)){ // motor1 raising the arm
      motor1.set(0.1); //change later
      Timer.delay(2.0);
      motor1.stopMotor();
    }
    // if(timer.get() >= 2.0){ // 
    //   timer.stop();
    //   motor1.set(0);
    // }

    if (controller.getLeftTriggerAxis() > 0.5){ //motor1 lowering the arm
      motor1.set(-0.1); //change later
    }
    if(timer.get() >= 2.0){ // 
      timer.stop();
      motor1.stopMotor();
    }
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}

# Technical review and historical limitations

These observations come from the two uploaded project archives, **not** from a successful build or a robotics hardware test. The code is presented as a snapshot of student-team engineering work, not as production-ready control software.

## 2022 code

- **Working code paths shown:** `teleopPeriodic()` invokes `intakeStuff()`, `shootStuff()`, and `buttonIfStatements()`. The code configures four drivetrain motors, an intake motor, a transfer motor, and a PWM shooter motor.
- **Autonomous behavior:** `autonomousPeriodic()` uses the timer for approximately 2 seconds of forward motion, 0.5 seconds of rotation, and a short shooter activation. Exact motion depends on drivetrain orientation and hardware.
- **Potential mathematical issue:** The steering expression includes `controller.getRightX() / Math.abs(controller.getRightX())`. If the joystick X value is zero, the expression evaluates `0 / 0` as NaN in Java; steering should handle a zero deadband explicitly.
- **Potential mechanism issue:** In `shootStuff()`, the `else` branch after the transfer timing window stops `intakeMotor` instead of `transitionMotor`. Since `intakeStuff()` is called earlier in the same cycle and can also set the transition motor, the logic should be reviewed to avoid unexpected motor state or unintended interactions.
- **Unused routine:** `transitionStuff()` is defined but never called from the active loop.

## 2023 code

- **Working code paths shown:** `teleopPeriodic()` drives the four-motor drivetrain, retrieves Limelight `tx`, `ty`, `ta` entries from NetworkTables, logs them to SmartDashboard, and toggles Limelight LED mode on controller button 3.
- **Autonomous status:** A `SendableChooser` and two autonomous mode names are registered, but their periodic branches contain no autonomous movement commands.
- **Arm status:** Three PWM `Spark` arm motors are declared; only `motor1` appears in the separate `armMovement()` method, which is **not invoked** by `teleopPeriodic()` or another lifecycle method. Do not claim the program actively raises/lowers the arm in teleop.
- **Vision status:** The code reads and displays Limelight targeting data; it does **not** use those measurements to implement a feedback or auto-alignment control loop.
- **Potential mathematical issue:** The arcade-drive steering expression has the same zero-division/NaN risk as in 2022.
- **Blocking call:** `armMovement()` includes `Timer.delay(2.0)`; if later invoked in a 20-ms periodic callback, that delay would block the control loop. Timed, nonblocking state transitions are preferred.
- **Unused imports/fields:** Several imports, the joystick variable, and other fields are unused or are left from experimentation.

## How to discuss this in a portfolio or interview

- Explain the progression from **teleoperated driving, intake, shooter, and simple timed autonomous actions (2022)** to **driver controls and Limelight telemetry/LED integration (2023)**.
- Discuss the distinction between a **sensor readout** and **closed-loop control**. Displaying `tx`/`ty`/`ta` is a foundation for aiming but not automatic tracking.
- Explain the trade-off between quickly prototyping robot behaviors during a competition season and strengthening reliability with deadbands, state machines, testing, and safer motor stop logic.
- Be precise about **your personal involvement**: a role or contribution cannot be inferred from the source files alone.

## Suggested follow-up improvements (not performed)

1. Handle joystick deadbands and zero-input steering without dividing by zero.
2. Refactor intake and shooter commands to avoid conflicting writes to the same motors.
3. Use nonblocking state transitions for manipulator timing.
4. Add simulation or hardware-in-the-loop tests and verify motor IDs/inversion.
5. Implement and tune autonomous routines or Limelight feedback only if this is genuinely part of your later work.

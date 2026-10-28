# Miramonte Robotics | FIRST Robotics Competition (2022–2023)

Two seasons of Java robot-control code from **Miramonte Robotics**, a high-school FIRST Robotics Competition (FRC) team. This portfolio archive brings together the 2022 and 2023 robot programs and links a robotics-club demonstration video.

**[Watch the robotics demonstration on YouTube](https://youtu.be/qLPfhtkcMI0)**

## Project overview

| | **2022 season** | **2023 season** |
|---|---|---|
| Framework | WPILib `TimedRobot`, Java 11, GradleRIO | WPILib `TimedRobot`, Java 11, GradleRIO |
| Drivetrain | Four REV SPARK MAX controllers grouped for differential drive | Four REV SPARK MAX controllers grouped for differential drive |
| Manual driving | Xbox-controller arcade drive | Xbox-controller arcade drive |
| Other mechanisms | Intake motor, transfer motor, timed shooter controls | Three PWM arm motors instantiated; experimental arm movement method |
| Autonomous | Timed forward drive, turn, and shooter action | Selectable autonomous options, with no behavior implemented yet |
| Vision | No Limelight integration in the supplied robot program | Limelight NetworkTables telemetry (`tx`, `ty`, `ta`) sent to SmartDashboard; LED toggle |

### 2022: drive, intake, shooter, autonomous sequence

The 2022 program manages a four-motor drive train; supports controller-triggered intake operation at two speeds, intake reversal, and time-sequenced shooter/transfer actions; and includes a timer-based autonomous sequence with a forward segment, a turn, and a shooter action.

**Explore:** [`2022/src/main/java/frc/robot/Robot.java`](2022/src/main/java/frc/robot/Robot.java), [`2022/src/main/java/frc/robot/Constants.java`](2022/src/main/java/frc/robot/Constants.java).

### 2023: driving and Limelight integration

The 2023 program retains a four-motor drivetrain driven with an Xbox controller. It reads horizontal/vertical target offset and target area from Limelight NetworkTables, publishes those values to SmartDashboard, and supports an LED mode toggle via a controller button. Three arm motors are initialized and an `armMovement()` routine is defined, but **that routine is not called by the active teleoperated loop**.

**Explore:** [`2023/src/main/java/frc/robot/Robot.java`](2023/src/main/java/frc/robot/Robot.java), [`2023/src/main/java/frc/robot/Constants.java`](2023/src/main/java/frc/robot/Constants.java).

## Tools and technologies

Java · WPILib · GradleRIO · REV Robotics SPARK MAX · Xbox controller input · SmartDashboard · Limelight / NetworkTables · motor-control logic · timed autonomous routines

## Repository layout

```text
.
├── README.md                # Portfolio overview and demo
├── TECHNICAL_NOTES.md       # Code review, caveats, and interview talking points
├── 2022/                    # Original 2022 WPILib project files
│   ├── ORIGINAL_README.md
│   ├── src/main/java/frc/robot/
│   ├── build.gradle
│   └── ...
└── 2023/                    # Original 2023 WPILib project files
    ├── ORIGINAL_README.md   # Based on the top-level README in the 2023 archive
    ├── src/main/java/frc/robot/
    ├── build.gradle
    └── ...
```

Each season is its **own independent Gradle project**. The source archive includes the original Gradle wrappers and vendor dependency manifests.

## Opening or building the historical code

1. Install the relevant season's WPILib development environment.
2. Open `2022/` or `2023/` as a separate WPILib Java project (not the repository root).
3. Review that year's motor IDs, controller assignments, and hardware setup **before deploying to any physical robot**.
4. With the necessary dependencies and tooling available, use that season's `./gradlew` to build locally.

## Original repositories and credits

- [Miramonte Robotics — FRC 2022 Timed Robot](https://github.com/MiramonteRobotics/FRC2022-Timed-Robot)
- [Miramonte Robotics — FRC 2023 Timed Robot](https://github.com/MiramonteRobotics/FRC-2023-Timed-Robot)
- [Robot demonstration](https://youtu.be/qLPfhtkcMI0)

This portfolio archive is shared with permission from the team. It preserves the original `WPILib-License.md` included in each project and links to the team's source repositories. The WPILib license applies to WPILib-licensed material; it should not be interpreted as assigning a license to other team-authored code.

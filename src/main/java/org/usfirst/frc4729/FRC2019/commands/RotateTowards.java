/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                                                         */
/* Open Source Software - may be modified and shared by FRC teams. The code     */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                                                                                             */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4729.FRC2019.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc4729.FRC2019.Robot;
import org.usfirst.frc4729.FRC2019.Util;
import org.usfirst.frc4729.FRC2019.subsystems.Navigation.Location;

public class RotateTowards extends Command {
    double targetAngle;

    public RotateTowards(Location location) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.navigation);
        targetAngle = Robot.navigation.angleToLocation(location);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    double slowRange = 90;
    double stopRange = 0;
    double minPower = 0;
    double maxPower = 1;

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.drive.omni(0, 0, rotationPower());
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return (rotationPower() == 0);
    }

    private double rotationPower() {
        return Util.linear(Robot.navigation.relativeAngle(targetAngle),
                           0,
                           minPower,
                           maxPower,
                           slowRange,
                           stopRange);
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.drive.omni(0, 0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}

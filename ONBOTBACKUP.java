/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="OnBot", group="Linear Opmode")

public class OpMode extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor backLeft = null;
    private DcMotor backRight = null;
    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private DcMotor lift = null;
    private DcMotor arm = null;
    private DcMotor tape = null;
    private Servo wrist = null;
    private Servo claw = null;
    private Servo hook = null;
   

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized Version test1");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        backLeft  = hardwareMap.get(DcMotor.class, "backLeft");
        backRight  = hardwareMap.get(DcMotor.class, "backRight");
        frontLeft  = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight  = hardwareMap.get(DcMotor.class, "frontRight");
        lift = hardwareMap.get(DcMotor.class, "lift");
        arm = hardwareMap.get(DcMotor.class, "arm");
        tape = hardwareMap.get(DcMotor.class, "tape");
        wrist = hardwareMap.get(Servo.class, "wrist");
        claw = hardwareMap.get (Servo.class, "claw");
        hook = hardwareMap.get (Servo.class, "hook");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);

        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        lift.setDirection(DcMotor.Direction.FORWARD);
        arm.setDirection(DcMotor.Direction.FORWARD);
        tape.setDirection(DcMotor.Direction.FORWARD);
        
        // INIT SERVOS
        wrist.setPosition(0.03);
        claw.setPosition (1.0);
        hook.setPosition(0.42);

        double bl;
        double br;
        double fl;
        double fr;
 
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            // ------------
            // CONTROLLER 1
            // ------------

            // Strafing Control
            if (gamepad1.left_trigger > 0 && gamepad1.right_trigger == 0) {
                bl = -gamepad1.left_trigger;
                br = gamepad1.left_trigger;
                fl = gamepad1.left_trigger;
                fr = -gamepad1.left_trigger;
            }
            else if (gamepad1.right_trigger > 0 && gamepad1.left_trigger == 0){
                bl = gamepad1.right_trigger;
                br = -gamepad1.right_trigger;
                fl = -gamepad1.right_trigger;
                fr = gamepad1.right_trigger;
            }
            // Tank Drive Control
            else {
                bl = gamepad1.left_stick_y;
                br = gamepad1.right_stick_y;
                fl = gamepad1.left_stick_y;
                fr = gamepad1.right_stick_y;
            }

            // Set Power Levels to variables after modification
            double power = 0.5;
            backLeft.setPower(bl * power);
            backRight.setPower(br * power);
            frontLeft.setPower(fl * power);
            frontRight.setPower(fr * power);
            
            /*
            //CALIBRATION CODE
            if (gamepad1.dpad_up){
                hook.setPosition(hook.getPosition() + 0.001);
            }
            else if (gamepad1.dpad_down){
                hook.setPosition(hook.getPosition() - 0.001);
            }
            */
            
            // HOOK CONTROL
            //DOWN
            if (gamepad1.a){
                hook.setPosition(0.06);
            }
            //UP
            else if (gamepad1.b){
                hook.setPosition(0.20);
            }
            
            //TAPE MEASURE CONTROL
            //OUT
            if (gamepad1.dpad_up){
                tape.setPower(0.6);
            }
            //IN
            else if (gamepad1.dpad_down){
                tape.setPower(-0.6);
            }
            else {
                tape.setPower(0);
            }
            
            




            

            
        
          
            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            //telemetry.addData("Servo", "Position: " + hook.getPosition()); 

            telemetry.update();
        }
    }
}

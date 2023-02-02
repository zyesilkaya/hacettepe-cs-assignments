module combat_control_unit(
    input rst,
    input track_target_command, 
    input clk, 
    input radar_echo, 
    input fire_command,
    output [13:0] distance_to_target, 
    output trigger_radar_transmitter, 
    output launch_missile,
    output [1:0] TTU_state,
    output [1:0] WCU_state,
    output [3:0] remaining_missiles
);

// Instantiate the Target Tracking Unit (TTU)
target_tracking_unit ttu(
    .rst(rst),
    .track_target_command(track_target_command),
    .clk(clk),
    .echo(radar_echo),
    .trigger_radar_transmitter(trigger_radar_transmitter),
    .distance_to_target(distance_to_target),
    .TTU_state(TTU_state)
);

// Instantiate the Weapons Control Unit (WCU)
weapons_control_unit wcu(
    .rst(rst),
    .target_locked(ttu.target_locked),
    .clk(clk),
    .fire_command(fire_command),
    .launch_missile(launch_missile),
    .remaining_missiles(remaining_missiles),
    .WCU_state(WCU_state)
);

endmodule

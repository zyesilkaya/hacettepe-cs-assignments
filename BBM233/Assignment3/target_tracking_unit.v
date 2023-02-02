`timescale 1us/1ns

module target_tracking_unit(
    input rst,
    input track_target_command,
    input clk,
    input echo,
    output reg trigger_radar_transmitter,
    output reg [13:0] distance_to_target,
    output reg target_locked,
    output [1:0] TTU_state
);


// state register
reg [2:0] state;

// state constants
parameter IDLE = 3'b000,
        TRANSMIT = 3'b001,
        LISTEN_FOR_ECHO = 3'b010,
        TRACK = 3'b011;

// constants for time limits in each state
parameter MAX_TRANSMIT_TIME = 8'd50, // 50 us
          MAX_LISTEN_TIME = 8'd100, // 100 us
          MAX_TRACK_TIME = 8'd300; // 300 us

// variable to store start time
reg signed [31:0] start_time = 32'd0;

// variable to store end time
reg [31:0] end_time;

// variable to store elapsed time
reg [31:0] elapsed_time;

// function to calculate distance to target in meters
function [13:0] calc_distance;
    input [31:0] t_time;
    begin
        calc_distance = (3*100*t_time )/ 2;
    end
endfunction

// update start time on falling edge of trigger_radar_transmitter signal
always @(posedge trigger_radar_transmitter) begin
    #50; trigger_radar_transmitter <=0;
    start_time <= ($stime);
end

always @(posedge echo) begin
    distance_to_target <= calc_distance(($stime - start_time));
    target_locked <= 1;
    start_time <= $stime;
end

always @(posedge track_target_command) begin
    trigger_radar_transmitter <= 1;
end


// state machine to control target tracking
always @(posedge clk) begin
    if (rst) begin
        state <= IDLE;
        trigger_radar_transmitter <= 0;
        distance_to_target <= 0;
        target_locked <= 0;
        start_time <= 0;
    end else begin
        case (state)
            IDLE: begin
                if (track_target_command) begin
                    state <= TRANSMIT;
                end
            end
            TRANSMIT: begin
                #50;
                state <= LISTEN_FOR_ECHO;

            end
            LISTEN_FOR_ECHO: begin

                if (target_locked == 1) begin
                    state <= TRACK;
                end else if (($stime - start_time)>=100 ) begin
                    state <= IDLE;
                end
            end
                TRACK: begin
                if (track_target_command) begin
                    state <= TRANSMIT;
                    
                end else if (($stime - start_time)>=300) begin
                    state <= IDLE;
                    distance_to_target <= 0;
                    target_locked <= 0;
                end
            end
        endcase
    end
end


// assign output for state information
assign TTU_state = state;

endmodule
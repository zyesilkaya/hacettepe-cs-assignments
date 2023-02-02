`timescale 1us/1ns

module weapons_control_unit(
    input target_locked,
    input clk,
    input rst,
    input fire_command,
    output reg launch_missile,
    output reg[3:0] remaining_missiles,
    output [1:0] WCU_state
);

// FSM states
parameter [1:0]
    IDLE = 2'b00,
    ARMED = 2'b01,
    FIRING = 2'b10,
    OUT_OF_AMMO_STATE = 2'b11;

// Current state of the FSM
reg [1:0] state;
assign WCU_state = state; 

always @(posedge clk or posedge rst) begin

    if (rst) begin
        state <= IDLE;
        remaining_missiles <= 4'b0100;
        launch_missile <= 1'b0;

    end else begin
        
    case (state)
    IDLE: begin
        if (target_locked) state <= ARMED;
        else state <= IDLE;
    end
    ARMED: begin
        if (target_locked == 0) state <= IDLE;
        else  if (fire_command) state <= FIRING;
    end
    FIRING: begin
        if(target_locked && remaining_missiles != 0) 
            state <= ARMED;
            else begin
            if(!target_locked && remaining_missiles != 0) 
                state <= IDLE;
            else if(!remaining_missiles) begin
                    state <= OUT_OF_AMMO_STATE;
            end
        end
    end
    endcase
    end
end

// Firing sequence
always @(posedge launch_missile) begin
        case (state)
            FIRING: begin
                if(remaining_missiles != 0)
                    remaining_missiles = remaining_missiles - 1;
            end
        endcase
    end


always @(state)begin
    if (state == FIRING) begin 
        launch_missile <= 1;
        #10;
        launch_missile = 0;
    end
end
endmodule



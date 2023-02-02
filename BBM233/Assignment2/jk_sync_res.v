// This module implements a JK flip-flop

module jk_sync_res(J, K, clk, sync_reset, Q);
    input J;
    input K;
    input clk;
    input sync_reset;
    output reg Q;

always @(posedge clk) begin
    // If sync_reset is high, reset Q to 0
    if (sync_reset == 1'b1) begin 
        Q <= 1'b0;
    end 
     // Otherwise, update the value of Q based on the J and K input
    else begin
    case ({J,K})
        2'b00 :  Q <= Q; // No change
        2'b01 :  Q <= 0; // Reset
        2'b10 :  Q <= 1; // Set
        2'b11 :  Q <= ~Q; // Toggle
    endcase
    end
end

endmodule
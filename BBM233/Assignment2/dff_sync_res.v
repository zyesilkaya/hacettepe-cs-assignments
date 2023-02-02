// This module implements a D flip-flop

module dff_sync_res(D, clk, sync_reset, Q);
    input D;
    input clk;
    input sync_reset;
    output reg Q;

    always@(posedge clk)
    begin
         // If sync_reset is high, reset Q to 0
        if(sync_reset == 1'b1)
            Q<=1'b0;
        // Otherwise, update the value of Q to the value of D
        else
            Q <= D;
    end
endmodule 
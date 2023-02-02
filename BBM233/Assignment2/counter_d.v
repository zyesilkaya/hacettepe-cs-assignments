module counter_d(input reset, input clk, input mode, output [2:0] count);

// D flip-flop for bit 2
// The D input of the flip-flop is determined by several conditions
// depending on the values of count[1], count[0], and mode
dff_sync_res dff0(.D((count[1] & ~count[0] & mode) | (~count[2] & count[1] & count[0] & ~mode) | (count[2] & ~count[1] & ~mode)
                    | (count[2] & count[0] & mode) | (count[2] & ~count[0] & ~mode)), .clk(clk), .sync_reset(reset), .Q(count[2]));

// D flip-flop for bit 1
// The D input of the flip-flop is determined by several conditions
// depending on the values of count[1], count[0], and mode
dff_sync_res dff1(.D((count[1] & ~count[0]) | (~count[1] & count[0] & ~mode) | (~count[2] & count[0] & mode)), 
.clk(clk), .sync_reset(reset), .Q(count[1]));

// D flip-flop for bit 0
// The D input of the flip-flop is determined by several conditions
// depending on the values of count[2], count[1], and mode
dff_sync_res dff2(.D((~count[2] & ~count[1] & mode) | (~count[0] & ~mode) | (count[2] & count[1] & mode)), 
.clk(clk), .sync_reset(reset), .Q(count[0]));


endmodule
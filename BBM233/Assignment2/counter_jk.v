
module counter_jk(input reset, input clk, input mode, output [2:0] count);

// JK flip-flop for bit 2
// The J and K inputs of the flip-flop are determined by several conditions
// depending on the values of count[1], count[0], and mode
    jk_sync_res jk0(.J((count[1] & ~count[0] & mode) | (count[1] & count[0] & ~mode)),
                    .K((~count[1] & ~count[0] & mode) | (count[1] & count[0] & ~mode)), 
                    .clk(clk), .sync_reset(reset), .Q(count[2]));

// JK flip-flop for bit 1
// The J and K inputs of the flip-flop are determined by several conditions
// depending on the values of count[0] and mode, and count[2]
    jk_sync_res jk1(.J((count[0] & ~mode) | (~count[2] & count[0])), .K((count[0] & ~mode) | (count[2] & count[0])), 
                    .clk(clk), .sync_reset(reset), .Q(count[1]));

// JK flip-flop for bit 0
// The J and K inputs of the flip-flop are determined by several conditions
// depending on the values of count[2], count[1], and mode
    jk_sync_res jk2(.J((~count[2] & ~count[1]) | (~mode) | (count[2] & count[1])),
                    .K((~mode) | (~count[2] & count[1]) | (count[2] & ~count[1])), 
                    .clk(clk), .sync_reset(reset), .Q(count[0]));


endmodule
`timescale 1ns/1ps

module counter_tb;
    reg reset, clk, mode;
    reg[19:0] input_data; 
    wire [2:0] count;
    integer i;
	
	//Comment the next line out when testing your JK flip flop implementation.
    //counter_d uut(reset, clk, mode, count);
    // Uncomment the next line to test your JK flip flop implementation.
    counter_jk c1(reset, clk, mode, count);

    initial begin
        // Set dumpfile for simulation waveform output
        $dumpfile("result.vcd");
         // Enable dumping of variables
        $dumpvars;
        // Set input data
        input_data = 20'b11111111110000000000;
        i = 0;
        // Set reset to 1 and wait 30 time units
        reset = 1;#30;
        // Set reset to 0 and wait 400 time units
        reset = 0;#400;
        // Set reset to 1 and wait 30 time units
        reset = 1;#30;
        $finish;

    end

    initial begin

        // Generate clock
        clk = 0;
         // Generate clock indefinitely
        forever begin
            // Wait 10 time units
            #10;
            // Toggle clock
            clk = ~clk;
        end
    end

    always@(posedge clk) begin
        #1;
        // Assign mode value from input_data
        mode = input_data >> i;
        // Increment loop variable
        i = i+1;
    end

endmodule
`timescale 1ns/10ps
module four_bit_2x1_mux_tb;
	reg [3:0] In_1;
	reg [3:0] In_0;
	reg Select=0;
	wire [3:0] Out;
	reg[7:0] count = 8'b00000000;
	integer i;
	// Your code goes here.  DO NOT change anything that is already given! Otherwise, you will not be able to pass the tests!
	
	four_bit_2x1_mux UUT(In_1, In_0, Select, Out);

	initial begin 
		$dumpfile("result5.vcd");
		$dumpvars;
		for(i=0;i<256;i++) begin
			{In_1[3],In_1[2],In_1[1],In_1[0],In_0[3],In_0[2],In_0[1],In_0[0]} = count;
			count+=1;
			#10;
		end
		Select=1;
		for(i=0;i<256;i++) begin
			{In_1[3],In_1[2],In_1[1],In_1[0],In_0[3],In_0[2],In_0[1],In_0[0]} = count;
			count+=1;
			#10;
		end
		$finish;
	end
endmodule
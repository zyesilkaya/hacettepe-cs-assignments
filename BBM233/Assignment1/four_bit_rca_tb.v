`timescale 1 ns/10 ps
module four_bit_rca_tb;

  // Your code goes here.  DO NOT change anything that is already given! Otherwise, you will not be able to pass the tests!
    reg[3:0] In_0;
    reg[3:0] In_1;
    reg Cin=0;
    reg[7:0] count = 8'b00000000;
    wire[3:0] S;
    wire Out;
    integer i;
    // Your code goes here.  DO NOT change anything that is already given! Otherwise, you will not be able to pass the tests!

    four_bit_rca UUT(.A(In_0),.B(In_1),.Cin(Cin),.S(S),.Cout(Out));

    initial begin
        $dumpfile("result3.vcd");
        $dumpvars;
        for(i=0;i<256;i++) begin
          {In_1[3],In_1[2],In_1[1],In_1[0],In_0[3],In_0[2],In_0[1],In_0[0]} = count;
          count+=1;
          #10;
        end
        $finish;
    end
endmodule
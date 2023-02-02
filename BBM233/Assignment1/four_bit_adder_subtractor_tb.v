`timescale 1ns/1ps
module four_bit_adder_subtractor_tb;

    // Your code goes here.  DO NOT change anything that is already given! Otherwise, you will not be able to pass the tests!

    reg [3:0] A;
    reg [3:0] B;
    reg subtract;

    wire [3:0] Result;
    wire Cout;

    integer i;
    integer j;
    integer k;


    four_bit_adder_subtractor UUT(.A(A), .B(B), .subtract(subtract), .Result(Result), .Cout(Cout));

    initial begin
        $dumpfile("result6.vcd");
        $dumpvars;

        for(i=0;i<2;i++)begin
            subtract=i;
            for(j=0;j<16;j++)begin 
                A = j;
                for(k=0;k<16;k++)begin 
                    B = k;
                    #10;
                end
            end
        end
        $finish;
    end

endmodule

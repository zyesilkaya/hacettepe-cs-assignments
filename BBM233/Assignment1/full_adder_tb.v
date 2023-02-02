`timescale 1 ns/10 ps
module full_adder_tb;
    reg In_1;
    reg In_0;
    reg Cin=0;
    wire S;
    reg[2:0] count = 3'b000;
    wire Out;
    integer i;
    // Your code goes here.  DO NOT change anything that is already given! Otherwise, you will not be able to pass the tests!

    full_adder UUT(In_1,In_0,Cin,S,Out);

    initial begin
        $dumpfile("result4.vcd");
        $dumpvars;
        for(i = 0;i < 8;i++) begin
            {In_1,In_0,Cin} = count;
            count+=1;
            #10;    
        end
        $finish;
    end

endmodule
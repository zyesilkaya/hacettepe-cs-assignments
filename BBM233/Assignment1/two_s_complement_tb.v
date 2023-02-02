`timescale 1ns/10ps
module two_s_complement_tb;
    reg[3:0] In;
    reg[3:0] count = 4'b0000;
    wire[3:0] Out;
    integer i;
    // Your code goes here.  DO NOT change anything that is already given! Otherwise, you will not be able to pass the tests!

    two_s_complement UUT(In,Out);

    initial begin
        $dumpfile("result2.vcd");
        $dumpvars;
        for(i = 0;i < 16;i++) begin
            {In[3],In[2],In[1],In[0]} = count;
            count+=1;
            #10;
        end
        $finish;
    end

endmodule 

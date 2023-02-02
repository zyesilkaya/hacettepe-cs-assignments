
module four_bit_adder_subtractor(A, B, subtract, Result, Cout);
    input [3:0] A;
    input [3:0] B;
    input subtract;
    output [3:0] Result;
    output Cout;
    wire [3:0] result_of_mux;
    wire Cin=0;
    wire [3:0] complement_B;

    // Your code goes here.  DO NOT change anything that is already given! Otherwise, you will not be able to pass the tests!

    two_s_complement two_s_complementer(.In(B),.Out(complement_B));
    four_bit_2x1_mux mux(.In_1(complement_B),.In_0(B),.Select(subtract),.Out(result_of_mux));
    four_bit_rca rca(.A(A),.B(result_of_mux),.Cin(Cin),.S(Result),.Cout(Cout));

endmodule

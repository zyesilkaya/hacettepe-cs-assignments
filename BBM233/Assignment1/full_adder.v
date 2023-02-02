module full_adder(
    input A,
    input B,
    input Cin,
    output S,
    output Cout
);
    wire sum_1,carry_1, carry_2, carry_3;

    // Your code goes here.  DO NOT change anything that is already given! Otherwise, you will not be able to pass the tests!
    
    // assign sum_1 = A ^ B;
    // assign S = sum_1^Cin;
    // assign carry_1 = A & B;
    // assign carry_2 = B & Cin;
    // assign carry_3 = A & Cin;
    // assign Cout = carry_1 | carry_2 | carry_3;

    assign {Cout, S} = A + B + Cin;

endmodule
module two_s_complement(In,Out);
    input [3:0] In;
    output [3:0] Out;
    
    // Your code goes here.  DO NOT change anything that is already given! Otherwise, you will not be able to pass the tests!

    assign Out[3] = In[3] ^ (In[2] | In[1] | In[0]);
    assign Out[2] = In[2] ^ (In[1] | In[0]);
    assign Out[1] = In[1] ^ In[0];
    assign Out[0] = In[0];

endmodule  

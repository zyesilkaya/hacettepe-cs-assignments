#include <iostream>

#include "TreeManager.h"

int main(int argc, char** argv) {
    //Create a manager object and call run function to start the program
    TreeManager manager;
    std::string input_file_name=argv[1];
    std::string output_file_name1=argv[2];
    std::string output_file_name2=argv[3];

    manager.run(input_file_name,output_file_name1,output_file_name2);

    return 0;
}

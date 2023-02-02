#include <iostream>
#include <string>
#include <fstream>
#include "Apartment.h"

vector<int> create_vector(string s);

using namespace std;

int main(int argc, char *argv[]) {

    ifstream input;
    input.open(argv[1]);

    ofstream output;
    output.open(argv[2]);

    string outputText;

    if(!input.is_open()){
        cout << "Input file could not open." << endl;
        return 1;
    }

    string n;
    Apartment* ap = new Apartment();

    //This part reads the input file and writes some outputs to the output file

    while (input>>n){
        if(n == "add_apartment"){
            string apt_name,position;
            int max_bw;
            input>>apt_name>>position>>max_bw;
            ap->add_apartment(apt_name, position, max_bw);
        }
        if(n == "add_flat"){
            string apt_name;
            int ID,bandwidth,index;
            input>>apt_name>>index>>bandwidth>>ID;
            ap->add_flat(apt_name,index,bandwidth,ID);
        }
        if(n == "remove_apartment"){
            string apt_name;
            input>>apt_name;
            ap->remove_apartment(apt_name);
        }
        if(n == "make_flat_empty"){
            string apt_name;
            int ID;
            input>>apt_name>>ID;
            ap->make_flat_empty(apt_name,ID);
        }
        if(n == "find_sum_of_max_bandwidths"){
            output<<"sum of bandwidth:\t"<<ap->find_sum_of_max_bandwidth()<<"\n";
        }
        if(n == "merge_two_apartments"){
            string apt_name1,apt_name2;
            input>>apt_name1>>apt_name2;
            ap->merge_two_apartments(apt_name1,apt_name2);
        }
        if(n == "relocate_flats_to_same_apartment"){
            string apt_name;
            int ID;
            vector<int> nums;
            string num;
            input>>apt_name>>ID>>num;
            nums = create_vector(num);
            ap->relocate_flats_to_same_apartments(apt_name,ID,nums);

        }
        if(n == "list_apartments"){
            output<<ap->print_list(outputText)<<"\n\n";
        }
    }

    return 0;
}

//this function creates a vector from input
vector<int> create_vector(string s) {
    vector<int> v;
    std::string delimiter= "]";
    s.erase(s.find(delimiter));
    s.erase(0, 1);
    std::string delimiter_char = ",";
    size_t pos = 0;
    std::string token;
    while ((pos = s.find(delimiter_char)) != std::string::npos) {
        token = s.substr(0, pos);
        v.push_back(stoi(token));
        s.erase(0, pos + delimiter_char.length());
    }
    v.push_back(stoi(s));
    return v;
}



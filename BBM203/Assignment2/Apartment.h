//
// Created by zeynep on 16.11.2022.
//

#ifndef ASM2_APARTMENT_H
#define ASM2_APARTMENT_H

#endif //ASM2_APARTMENT_H

#include <string>
#include <vector>
#include "ApartmentNode.cpp"

using namespace std;

class Apartment{
    public:
        NODEPTR head;
        void add_apartment(string name, string position, int max_bandwidth);
        void add_flat(string apartment_name,int index, int initial_bandwidth,int ID);
        NODEPTR remove_apartment(string name);
        NODEPTR findNext(string value);
        NODEPTR findBefore(string value);
        bool is_empty();
        string print_list(string output);
        int find_sum_of_max_bandwidth();
        void make_flat_empty(string apartment_name, int ID);
        void merge_two_apartments(string apartment1, string apartment2);
        string print_flats(NODEPTR currentApt);
        void relocate_flats_to_same_apartments(string apartment_name, int ID, vector<int> flat_ids);

        ~Apartment(){
            delete head;
        }
};
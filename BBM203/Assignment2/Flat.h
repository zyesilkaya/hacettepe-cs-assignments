//
// Created by zeynep on 16.11.2022.
//

#ifndef ASM2_FLAT_H
#define ASM2_FLAT_H

#endif //ASM2_FLAT_H
#include <string>
#include "FlatNode.cpp"

using namespace std;

class Flat{
    public:
        F_NODEPTR head;
        void add_flat(int index, int initial_bandwidth,int ID);
        F_NODEPTR findNext(int index);
        F_NODEPTR findByID(int ID);
        bool is_empty();
        void remove_all_flats();

        ~Flat(){
            delete head;
        }
};
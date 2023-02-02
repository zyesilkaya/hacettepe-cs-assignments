//
// Created by zeynep on 16.11.2022.
//
#include <string>
#include "Flat.h"
class ApartmentNode{ //TODO destructor yap
    public:
        ApartmentNode *next = nullptr;
        Flat* firstFlat = nullptr;
        std::string name;
        int max_bandwidth = 0;
        int remaining_bandwidth=0;
        ApartmentNode(std::string name, int max_bandwidth){
            this->max_bandwidth=max_bandwidth;
            this->remaining_bandwidth=max_bandwidth;
            this->name = name;
        }
        ~ApartmentNode(){
            delete next;
        }

};
typedef ApartmentNode* NODEPTR;
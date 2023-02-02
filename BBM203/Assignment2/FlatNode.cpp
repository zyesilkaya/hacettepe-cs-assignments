//
// Created by zeynep on 16.11.2022.
//
#include <string>
class FlatNode{//TODO destructor yap
    public:
        bool is_empty = 0;
        FlatNode *next = nullptr;
        FlatNode *prev = nullptr;
        int initial_bandwidth;
        int ID;
        FlatNode(int initial_bandwidth, int ID ){
            this->initial_bandwidth = initial_bandwidth;
            this->ID = ID;
        }
        ~FlatNode(){
            delete next;
            delete prev;
        }
};
typedef FlatNode* F_NODEPTR;
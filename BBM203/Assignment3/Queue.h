//
// Created by zeynep on 2.12.2022.
//

#ifndef ASM3_QUEUE_H
#define ASM3_QUEUE_H

#endif //ASM3_QUEUE_H

#include <string>
#include "Node.cpp"

using namespace std;

class Queue{
    public:
        NODEPTR head= nullptr;
        NODEPTR tail= nullptr;
        int size=0;
        int max_size=0;
};

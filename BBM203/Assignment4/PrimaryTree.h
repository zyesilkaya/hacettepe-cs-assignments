//
// Created by zeynep on 20.12.2022.
//

#ifndef ASM4_PRIMARYTREE_H
#define ASM4_PRIMARYTREE_H
#include "PrimaryNode.h"
#include <string>
class PrimaryTree {
    public:
        PrimaryNode *root;
        PrimaryNode* retrieve_item(PrimaryNode *node, const std::string &item);
        PrimaryTree();
        void insert_item(PrimaryNode *&root, std::string key);
        void printLevelOrder(PrimaryNode* node, std::string &output1, std::string &output2);
};


#endif //ASM4_PRIMARYTREE_H

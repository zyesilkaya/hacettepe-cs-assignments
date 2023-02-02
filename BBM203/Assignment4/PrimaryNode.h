//
// Created by zeynep on 20.12.2022.
//

#ifndef ASM4_PRIMARYNODE_H
#define ASM4_PRIMARYNODE_H
#include "SecondaryTree.h"
#include "RedBlackTree.h"
#include <string>

class PrimaryNode {
    public:
        PrimaryNode *left_node, *right_node;
        SecondaryTree *secondary_BST;
        RedBlackTree *RBT;
        std::string key;
        PrimaryNode(std::string key);
};

#endif //ASM4_PRIMARYNODE_H

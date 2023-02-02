//
// Created by zeynep on 20.12.2022.
//

#ifndef ASM4_SECONDARYTREE_H
#define ASM4_SECONDARYTREE_H
#include "SecondaryNode.h"

class SecondaryTree {
    public:

        SecondaryNode *root;
        SecondaryTree();
        SecondaryNode* nodeWithMimumValue(SecondaryNode *node);
        int getBalanceFactor(SecondaryNode *N);
        SecondaryNode *leftRotate(SecondaryNode *node);
        SecondaryNode *rightRotate(SecondaryNode *node);
        int max(int a, int b);
        int height(SecondaryNode *N);
        std::string printTree(SecondaryNode *node);
        SecondaryNode* insert_item(SecondaryNode *root, std::string key, int price);
        SecondaryNode* retrieve_item(SecondaryNode *node, const std::string &item);
        SecondaryNode *deleteNode(SecondaryNode *root, std::string key);
};


#endif //ASM4_SECONDARYTREE_H

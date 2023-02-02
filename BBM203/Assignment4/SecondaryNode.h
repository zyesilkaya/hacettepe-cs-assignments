//
// Created by zeynep on 20.12.2022.
//

#ifndef ASM4_SECONDARYNODE_H
#define ASM4_SECONDARYNODE_H
#include <string>

class SecondaryNode {
    public:
        SecondaryNode *left_node, *right_node;
        int price=0;
        int height;
        std::string name;
        SecondaryNode(std::string name,int price);
};


#endif //ASM4_SECONDARYNODE_H

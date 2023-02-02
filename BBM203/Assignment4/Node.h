//
// Created by zeynep on 21.12.2022.
//

#ifndef ASM4_NODE_H
#define ASM4_NODE_H
#include "SecondaryNode.h"

class Node {
public:
    std::string key;
    Node* left;
    Node* right;
    bool color;
    int price;

    Node(std::string key, int price) : key(key), left(nullptr), right(nullptr), color(true), price(price) {}
};


#endif //ASM4_NODE_H

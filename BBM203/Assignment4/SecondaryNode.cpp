//
// Created by zeynep on 20.12.2022.
//

#include "SecondaryNode.h"

//This function is constructor for secondary node class
SecondaryNode::SecondaryNode(std::string name, int price) {
    left_node = nullptr;
    right_node = nullptr;
    height = 1;
    this->name = name;
    this->price = price;
}

//
// Created by zeynep on 20.12.2022.
//

#include "PrimaryNode.h"

//This function is constructor for primary node class
PrimaryNode::PrimaryNode(std::string key) {
    this->key = key;
    right_node = nullptr;
    left_node = nullptr;
    secondary_BST = nullptr;
    RBT = nullptr;
}

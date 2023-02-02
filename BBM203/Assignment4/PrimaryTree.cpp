//
// Created by zeynep on 20.12.2022.
//

#include "PrimaryTree.h"
#include <iostream>
#include "queue"

PrimaryTree::PrimaryTree() {
    root = nullptr;
}

//This function finds the given item in the tree and returns it
PrimaryNode* PrimaryTree::retrieve_item(PrimaryNode *node, const std::string &item) {

    if(node == nullptr || node->key == item){
        return node;
    }
    if(node->key < item){
        return retrieve_item(node->right_node, item);
    }
    else if(node->key > item){
        return retrieve_item(node->left_node, item);
    }
}

//This function inserts the given item to the tree
void PrimaryTree::insert_item(PrimaryNode *&root, std::string key) {
    if(root == nullptr){
        PrimaryNode *node = new PrimaryNode(key);
        root = node;
        return;
    }
    if(root->key < key){
        insert_item(root->right_node,key);

    }
    else if(root->key > key){
        insert_item(root->left_node,key);

    }
}

//This function prints the primary tree and its items
void PrimaryTree::printLevelOrder(PrimaryNode* node, std::string &output1, std::string &output2) {

    if (node == nullptr) return;
    std::queue<PrimaryNode*> q;
    q.push(node);
    int level = 0;
    while (!q.empty()) {
        int size = q.size();
        while (size > 0) {
            PrimaryNode* x = q.front();
            q.pop();
            output1+="\""+x->key+ "\":\n";
            output2+="\""+x->key+ "\":\n";

            output2+=x->RBT->printLevelOrder();

            output1+=x->secondary_BST->printTree(x->secondary_BST->root);

            if (x->left_node != nullptr) q.push(x->left_node);
            if (x->right_node != nullptr) q.push(x->right_node);
            size--;
        }

        level++;
    }
}


//
// Created by zeynep on 20.12.2022.
//

#include "SecondaryTree.h"
#include <iostream>
#include <queue>

SecondaryTree::SecondaryTree() {
    root = nullptr;
}

// Calculate height
int SecondaryTree::height(SecondaryNode *N) {
    if (N == nullptr)
        return 0;
    return N->height;
}

int SecondaryTree::max(int a, int b) {
    return (a > b) ? a : b;
}

// This function rotates the tree to the right
SecondaryNode* SecondaryTree::rightRotate(SecondaryNode *node) {
    SecondaryNode *x = node->left_node;
    SecondaryNode *T2 = x->right_node;
    x->right_node = node;
    node->left_node = T2;
    node->height = max(height(node->left_node),
                       height(node->right_node)) +
                   1;
    x->height = max(height(x->left_node),
                    height(x->right_node)) +
                1;
    return x;
}

// This function rotates the tree to the left
SecondaryNode* SecondaryTree::leftRotate(SecondaryNode *node) {
    SecondaryNode *y = node->right_node;
    SecondaryNode *T2 = y->left_node;
    y->left_node = node;
    node->right_node = T2;
    node->height = max(height(node->left_node),
                       height(node->right_node)) +
                   1;
    y->height = max(height(y->left_node),
                    height(y->right_node)) +
                1;
    return y;
}

// This function gets the balance factor of each node
int SecondaryTree::getBalanceFactor(SecondaryNode *N) {
    if (N == nullptr)
        return 0;
    return height(N->left_node) - height(N->right_node);
}

//This function inserts the given item to the tree
SecondaryNode *SecondaryTree::insert_item(SecondaryNode *node, std::string key, int price) {
    // Find the correct postion and insert the node
    if (node == nullptr){
        SecondaryNode *new_node = new SecondaryNode(key,price);
        return new_node;
    }

    if (key < node->name)
        node->left_node = insert_item(node->left_node, key, price);
    else if (key > node->name)
        node->right_node = insert_item(node->right_node, key, price);
    else
        return node;

    // Update the balance factor of each node and balance the tree
    node->height = 1 + max(height(node->left_node),
                           height(node->right_node));
    int balanceFactor = getBalanceFactor(node);
    if (balanceFactor > 1) {
        if (key < node->left_node->name) {
            return rightRotate(node);
        } else if (key > node->left_node->name) {
            node->left_node = leftRotate(node->left_node);
            return rightRotate(node);
        }
    }
    if (balanceFactor < -1) {
        if (key > node->right_node->name) {
            return leftRotate(node);
        } else if (key < node->right_node->name) {
            node->right_node = rightRotate(node->right_node);
            return leftRotate(node);
        }
    }
    return node;
}

// This function finds the node with minimum value
SecondaryNode *SecondaryTree::nodeWithMimumValue(SecondaryNode *node) {
    SecondaryNode *current = node;
    while (current->left_node != nullptr)
        current = current->left_node;
    return current;
}

// //This function removes the given item from the tree and balances the tree again
SecondaryNode *SecondaryTree::deleteNode(SecondaryNode *root, std::string key) {
    // Find the node and delete it
    if (root == nullptr)
        return root;
    if (key < root->name)
        root->left_node = deleteNode(root->left_node, key);
    else if (key > root->name)
        root->right_node = deleteNode(root->right_node, key);
    else {
        if ((root->left_node == nullptr) ||
            (root->right_node == nullptr)) {
            SecondaryNode *temp = root->left_node ? root->left_node : root->right_node;
            if (temp == nullptr) {
                temp = root;
                root = nullptr;
            } else
                *root = *temp;
            free(temp);
        } else {
            SecondaryNode *temp = nodeWithMimumValue(root->right_node);
            root->name = temp->name;
            root->right_node = deleteNode(root->right_node,
                                     temp->name);
        }
    }

    if (root == nullptr)
        return root;

    // Update the balance factor of each node and
    // balance the tree
    root->height = 1 + max(height(root->left_node),
                           height(root->right_node));
    int balanceFactor = getBalanceFactor(root);
    if (balanceFactor > 1) {
        if (getBalanceFactor(root->left_node) >= 0) {
            return rightRotate(root);
        } else {
            root->left_node = leftRotate(root->left_node);
            return rightRotate(root);
        }
    }
    if (balanceFactor < -1) {
        if (getBalanceFactor(root->right_node) <= 0) {
            return leftRotate(root);
        } else {
            root->right_node = rightRotate(root->right_node);
            return leftRotate(root);
        }
    }
    return root;
}

//This function prints the secondary tree
std::string SecondaryTree::printTree(SecondaryNode *node){
    std::string output;
    if (node == nullptr){

        output+="{}\n";
        return output;
    }
    std::queue<SecondaryNode*> q;
    q.push(node);
    int level = 0;
    while (!q.empty()) {
        int size = q.size();

        output+="\t";
        while (size > 0) {
            SecondaryNode* x = q.front();
            q.pop();

            output+="\""+x->name +"\":\""+std::to_string(x->price)+"\"";
            if (x->left_node != nullptr) q.push(x->left_node);
            if (x->right_node != nullptr) q.push(x->right_node);
            size--;

            output+="\t";
        }

        output+="\n";
        level++;
    }

    output+="\n";
    return output;
}

//This function finds the given item in the tree and returns it
SecondaryNode* SecondaryTree::retrieve_item(SecondaryNode *node, const std::string &item) {

    if(node == nullptr || node->name == item){
        return node;
    }
    if(node->name < item){
        return retrieve_item(node->right_node, item);
    }
    else if(node->name > item){
        return retrieve_item(node->left_node, item);
    }
}
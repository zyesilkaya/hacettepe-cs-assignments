#include <iostream>
#include <string>
#include "Node.h"
#include <queue>
#include <iostream>

class RedBlackTree {
private:
    Node* root;

// This function rotates the tree to the left
    Node* rotateLeft(Node* node) {
        Node* x = node->right;
        node->right = x->left;
        x->left = node;
        x->color = node->color;
        node->color = true;
        return x;
    }

// This function rotates the tree to the right
    Node* rotateRight(Node* node) {
        Node* x = node->left;
        node->left = x->right;
        x->right = node;
        x->color = node->color;
        node->color = true;
        return x;
    }

    void flipColors(Node* node) {
        node->color = !node->color;
        node->left->color = !node->left->color;
        node->right->color = !node->right->color;
    }
//This function inserts the given item to the tree
    Node* insert(Node* node, std::string key, int price) {
        if (node == nullptr) {
            return new Node(key, price);
        }
        if (key < node->key) {
            node->left = insert(node->left, key, price);
        } else if (key > node->key) {
            node->right = insert(node->right, key, price);
        } else {
            // If the key is already present in the tree, we do nothing
            return node;
        }

        if (isRed(node->right) && !isRed(node->left)) node = rotateLeft(node);
        if (isRed(node->left) && isRed(node->left->left)) node = rotateRight(node);
        if (isRed(node->left) && isRed(node->right)) flipColors(node);

        return node;
    }

    bool isRed(Node* x) {
        if (x == nullptr) return false;
        return x->color;
    }

    Node* moveRedLeft(Node* h) {
        flipColors(h);
        if (isRed(h->right->left)) {
            h->right = rotateRight(h->right);
            h = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }

    Node* moveRedRight(Node* node) {
        flipColors(node);
        if (isRed(node->left->left)) {
            node = rotateRight(node);
            flipColors(node);
        }
        return node;
    }

// This function removes the node with minimum value
    Node* deleteMin(Node* node) {
        if (node->left == nullptr) return nullptr;
        if (!isRed(node->left) && !isRed(node->left->left)) node = moveRedLeft(node);
        node->left = deleteMin(node->left);
        return balance(node);
    }

    Node* balance(Node* h) {
        if (isRed(h->right)) h = rotateLeft(h);
        if (isRed(h->left) && isRed(h->left->left)) h = rotateRight(h);
        if (isRed(h->left) && isRed(h->right)) flipColors(h);
        return h;
    }
// //This function removes the given item from the tree and balances the tree again
    Node* deleteKey(Node* node, std::string key) {
        if (key < node->key) {
            if (!isRed(node->left) && !isRed(node->left->left)) node = moveRedLeft(node);
            node->left = deleteKey(node->left, key);
        } else {
            if (isRed(node->left)) node = rotateRight(node);
            if (key == node->key && node->right == nullptr) return nullptr;
            if (!isRed(node->right) && !isRed(node->right->left)) node = moveRedRight(node);
            if (key == node->key) {
                Node* x = min(node->right);
                node->key = x->key;
                node->right = deleteMin(node->right);
            } else {
                node->right = deleteKey(node->right, key);
            }
        }
        return balance(node);
    }

// This function finds the node with minimum value
    Node* min(Node* h) {
        if (h->left == nullptr) return h;
        return min(h->left);
    }

//This function prints the secondary tree
    std::string printLevelOrder(Node* node) {
        std::string output;
        if (node == nullptr) {

            output+="{}\n";
            return output;
        }
        std::queue<Node*> q;
        q.push(node);
        int level = 0;
        while (!q.empty()) {
            int size = q.size();

            output+="\t";
            while (size > 0) {
                Node* x = q.front();
                q.pop();

                output+="\""+ x->key +"\":\""+std::to_string(x->price)+"\"";
                if (x->left != nullptr) q.push(x->left);
                if (x->right != nullptr) q.push(x->right);
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
    Node* retrieve_item(Node *node, const std::string &item) {

        if(node == nullptr || node->key == item){
            return node;
        }
        if(node->key < item){
            return retrieve_item(node->right, item);
        }
        else if(node->key > item){
            return retrieve_item(node->left, item);
        }
    }


public:
    RedBlackTree() : root(nullptr) {}

    void insert(std::string key, int price) {
        root = insert(root, key, price);
        root->color = false;
    }
// //This function removes the given item from the tree and balances the tree again
    void deleteKey(std::string key) {
        if (!isRed(root->left) && !isRed(root->right)) root->color = true;
        root = deleteKey(root, key);
        if (root != nullptr) root->color = false;
    }

//This function prints the secondary tree
    std::string printLevelOrder() {
        return printLevelOrder(root);
    }
//This function finds the given item in the tree and returns it
    Node* retrieve_item( const std::string &item) {
        retrieve_item(root,item);
    }

};


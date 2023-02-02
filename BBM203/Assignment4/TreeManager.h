//
// Created by zeynep on 24.12.2022.
//

#ifndef ASM4_TREEMANAGER_H
#define ASM4_TREEMANAGER_H

#include "PrimaryTree.h"
#include "SecondaryTree.h"
#include "fstream"

class TreeManager {
public:
    PrimaryTree *tree;
    TreeManager();
    void run(std::string inputFileName,std::string outputFileName1,std::string outputFileName2);
};


#endif //ASM4_TREEMANAGER_H

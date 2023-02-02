//
// Created by zeynep on 24.12.2022.
//

#include "TreeManager.h"
#include <sstream>

TreeManager::TreeManager() {
    tree = nullptr;
}

//This function reads the input with using a while loop and calls the necessary functions to create the output
void TreeManager::run(std::string inputFileName,std::string outputFileName1,std::string outputFileName2) {

    tree = new PrimaryTree();

    std::ofstream output_file1(outputFileName1);
    std::ofstream output_file2(outputFileName2);


    std::ifstream input_file(inputFileName);

    std::string line;

    std::string output1;
    std::string output2;

    if(!input_file.is_open()){
        std::cout<<"input file could not opened";
    }
    while (std::getline(input_file, line, '\n')) {

        std::istringstream iss(line);
        std::string token;
        std::string command;

        int i=0;
        std::string input_array[4];

        while (std::getline(iss, token, '\t')) {
            input_array[i] = token;

            i++;
        }
        command = input_array[0];

        if(command=="insert"){

            if(!tree->retrieve_item(tree->root,input_array[1])) tree->insert_item(tree->root,input_array[1]);

            PrimaryNode *temp = tree->retrieve_item(tree->root,input_array[1]);

            if(!temp->secondary_BST) temp->secondary_BST = new SecondaryTree();

            temp->secondary_BST->root = temp->secondary_BST->
                    insert_item(temp->secondary_BST->root,input_array[2],stoi(input_array[3]));

            if(!temp->RBT) temp->RBT = new RedBlackTree();
            temp->RBT->insert(input_array[2],stoi(input_array[3]));

        }

        if(command=="remove"){

            PrimaryNode *temp = tree->retrieve_item(tree->root,input_array[1]);

            temp->secondary_BST->root = temp->secondary_BST->deleteNode(temp->secondary_BST->root,input_array[2]);

            temp->RBT->deleteKey(input_array[2]);

        }

        if(command=="find"){
            output1 += "command:find\t"+input_array[1]+"\t"+input_array[2]+"{\n";
            
            output2 += "command:find\t"+input_array[1]+"\t"+input_array[2]+"{\n";

            PrimaryNode *temp = tree->retrieve_item(tree->root,input_array[1]);
            if(temp && temp->secondary_BST->retrieve_item(temp->secondary_BST->root,input_array[2])){
		output1+="\""+input_array[1]+"\""+":\n";
                output1 += "\t\""+temp->secondary_BST->retrieve_item(temp->secondary_BST->root,input_array[2])->name+"\":\""+std::to_string(temp->secondary_BST->retrieve_item(temp->secondary_BST->root,input_array[2])->price)+"\"";
            }
            else{
                output1+="{}\n";
            }

            output1+="\n}\n";

            if(temp && temp->RBT->retrieve_item(input_array[2])){
		output2+="\""+input_array[1]+"\""+":\n";
                output2 += "\t\""+temp->RBT->retrieve_item(input_array[2])->key+"\":\""+std::to_string(temp->RBT->retrieve_item(input_array[2])->price)+"\"";
            }
            else{

                output2+="{}\n";
            }

            output2+="\n}\n";

        }

        if(command=="printAllItemsInCategory"){

            output1+="command:printAllItemsInCategory "+input_array[1]+"{\n";
            output1+="\""+input_array[1]+"\""+":\n";

            output2+="command:printAllItemsInCategory "+input_array[1]+"{\n";
            output2+="\""+input_array[1]+"\""+":\n";


            PrimaryNode *temp = tree->retrieve_item(tree->root,input_array[1]);

            output1+=temp->secondary_BST->printTree(temp->secondary_BST->root);

            output2+= temp->RBT->printLevelOrder();

            output1+="\n}\n";
            output2+="\n}\n";

        }

        if(command=="printAllItems"){

            output1+="command:printAllItems "+input_array[1]+"{\n";
            output2+="command:printAllItems "+input_array[1]+"{\n";

            tree->printLevelOrder(tree->root,output1,output2);

            output1+="\n}\n";
            output2+="\n}\n";

        }

        if(command=="updateData"){
            PrimaryNode *temp = tree->retrieve_item(tree->root,input_array[1]);
            temp->secondary_BST->retrieve_item(temp->secondary_BST->root,input_array[2])->price = stoi(input_array[3]);
            temp->RBT->retrieve_item(input_array[2])->price = stoi(input_array[3]);

        }

        if(command=="printItem"){

            output1+="command:printItem\t"+input_array[1]+"\t"+input_array[2]+"{\n";
            output2+="command:printItem\t"+input_array[1]+"\t"+input_array[2]+"{\n";

            PrimaryNode *temp = tree->retrieve_item(tree->root,input_array[1]);
            if(temp->secondary_BST->retrieve_item(temp->secondary_BST->root,input_array[2])){
		output1+="\""+input_array[1]+"\""+":\n";
                output1+="\t\""+temp->secondary_BST->retrieve_item(temp->secondary_BST->root,input_array[2])->name
                         +"\":\""+std::to_string(temp->secondary_BST->retrieve_item(temp->secondary_BST->root,input_array[2])->price)+ "\"\n";
		
		output2+="\""+input_array[1]+"\""+":\n";
                output2+="\t\""+temp->RBT->retrieve_item(input_array[2])->key+"\":\""+std::to_string(temp->RBT->retrieve_item(input_array[2])->price)+ "\"";

            }
            else{
                output1+="\n}\n";
                output2+="\n}\n";
            }
            output1 += "\n}\n";
            output2 += "\n}\n";

        }

    }
    output_file1<<output1;
    output_file2<<output2;
    output_file1.close();
    output_file2.close();
}



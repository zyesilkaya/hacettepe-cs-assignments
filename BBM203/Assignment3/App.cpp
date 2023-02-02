//
// Created by zeynep on 10.12.2022.
//

#include "App.h"
#include "CoffeShopManager.h"
#include <iostream>
#include <fstream>
#include <string>
#include <vector>

using namespace std;
//This class is used for getting inputs and creating new managers.
void App::run(std::string input_file_name, std::string output_file_name) {

    ifstream input;
    input.open(input_file_name);

    ofstream output;
    output.open(output_file_name);

    string outputText;

    if(!input.is_open()){
        cout << "Input file could not open." << endl;
        return;
    }

    string n;
    int num_of_orders,num_of_cashiers;

    input >> num_of_cashiers;
    input>>num_of_orders;


    vector<Order*> orders;
    //x line for orders
    for(int i=0; i<num_of_orders; i++){
        Order* new_order = new Order();
        input>>new_order->arrival_time;
        input>>new_order->order_time;
        input>>new_order->brew_time;
        input>>new_order->price;
        orders.push_back(new_order);
    }

    CoffeShopManager* coffeShopManager = new CoffeShopManager(orders,num_of_cashiers);

    coffeShopManager->take_order();

    coffeShopManager->prepare_order();

    output<<coffeShopManager->print();

    output<<"\n";

    CoffeShopManager* coffeShopManager2 = new CoffeShopManager(orders,num_of_cashiers);

    coffeShopManager2->orders=coffeShopManager->orders;

    coffeShopManager2->cashiers = coffeShopManager->cashiers;

    coffeShopManager2->max_size_of_cashierQ = coffeShopManager->max_size_of_cashierQ;

    coffeShopManager2->divide_orders();

    output<<coffeShopManager2->print();
}

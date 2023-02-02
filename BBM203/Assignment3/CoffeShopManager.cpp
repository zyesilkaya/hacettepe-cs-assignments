//
// Created by zeynep on 3.12.2022.
//
#include "CoffeShopManager.h"
#include <iostream>
#include <algorithm>
#include <sstream>

CoffeShopManager::CoffeShopManager(std::vector<Order*> &orders, int cashier_num){
    this->orders = orders;
    this->cashier_num = cashier_num;
    create_cashiers();
    create_baristas();
    create_order_vectors();
}
//This function is used for creating cashier vectors.
void CoffeShopManager::create_cashiers() {
    for(int i=0;i<cashier_num;i++){
        Cashier* new_cashier = new Cashier(i);
        this->cashiers.push_back(new_cashier);
    }
}

//This function is used for creating barista vectors.
void CoffeShopManager::create_baristas() {
    for(int i=0;i<cashier_num/3;i++){
        Barista* new_barista = new Barista(i);
        this->baristas.push_back(new_barista);
    }
}

//This function is used for creating order vectors.
void CoffeShopManager::create_order_vectors() {
    for(int i=0;i<cashier_num/3;i++){
        vector<Order*> divided_orders;
        this->order_vectors.push_back(divided_orders);
    }
}

//This function is used for taking an order and transfer them to the orderManager class.
void CoffeShopManager::take_order() {
    OrderManager* orderManager = new OrderManager(cashiers,baristas);
    for(Order* order: orders){
        orderManager->take_order(order);
    }
    max_size_of_cashierQ = orderManager->cashierQ->max_size;
    for(Cashier *cashier : cashiers){
        cashier->busy_time += cashier->utilization;
    }
}
//This function is used to receive an order and transfer them to the OrderManager class and send them to the baristas.
void CoffeShopManager::prepare_order() {
    OrderManager* orderManager = new OrderManager(cashiers,baristas);
    sorter();
    for(Order *order : orders){
        orderManager->prepare_order(order);
    }
    while(orderManager->baristaQ->head != nullptr){
        orderManager->check_baristas_for_Q();
    }
    max_sizes.push_back(orderManager->baristaQ->max_size);
    for(Barista *barista : baristas){
        total_running_times.push_back(barista->utilization);
        total_running_time=barista->utilization > total_running_time ? barista->utilization:total_running_time;;
    }
}

//This function is used to receive an order and transfer them to the OrderManager class and send them to the baristas. But for model2.
void CoffeShopManager::prepare_order(vector<Order*> orders) {

    OrderManager* orderManager = new OrderManager(cashiers,baristas);

    for(Order *order : orders){
        orderManager->prepare_order(order);
    }

    while(orderManager->baristaQ->head != nullptr){

        orderManager->check_baristas_for_Q();
    }
    total_running_times.push_back(baristas[0]->utilization);
    total_running_time = baristas[0]->utilization > total_running_time ? baristas[0]->utilization:total_running_time;
    max_sizes.push_back(orderManager->baristaQ->max_size);

}
//This function is used for comparing 2 orders by their cashier times.
bool comp(Order *order1, Order *order2){
    return order1->cashier_time<order2->cashier_time;

}

//This function is used for comparing 2 orders by their arrival times.
bool arrival_time_comparator(Order *order1, Order *order2){
    return order1->arrival_time<order2->arrival_time;

}
//This function is used for sorting 2 orders by their cashier times.
vector<Order*> CoffeShopManager::sorter(){

    std::sort(orders.begin(), orders.end(), comp);

    return orders;
}

//This function is used for sorting 2 orders by their arrival times.
vector<Order*> CoffeShopManager::sort_by_arrival_time(){

    std::sort(orders.begin(), orders.end(), arrival_time_comparator);

    return orders;
}

//This function is used for dividing orders by their cashier ID's.
void CoffeShopManager::divide_orders() {
    for(Order *order : orders){
      order_vectors[order->cashier_ID/3].push_back(order);
    }
    int baristaID=0;
    for(vector<Order*> orderv : order_vectors){

        std::sort(orderv.begin(), orderv.end(), comp);
        Barista *barista = new Barista(baristaID);
        model2_baristas.push_back(barista);
        baristas={(barista)};

        prepare_order(orderv);
        baristaID+=1;
    }
}

//This function is used for printing the values.
string CoffeShopManager::print() {
    output_text += round(total_running_time)+"\n";
    output_text += to_string(max_size_of_cashierQ)+"\n";


    for(auto item: max_sizes){
        output_text+=to_string(item)+"\n";
    }


    for(Cashier *cashier : cashiers){
        output_text+=round(cashier->busy_time/(total_running_time))+"\n";
    }


    if(model2_baristas.empty()){
        for(Barista *barista : baristas){
            barista->busy_time+=barista->utilization;
            output_text+=round(barista->busy_time/(total_running_time))+"\n";
        }

    }else{
        for(Barista *barista : model2_baristas){
            barista->busy_time+=barista->utilization;
            output_text+=round(barista->busy_time/(total_running_time))+"\n";
        }

    }

    for(Order *order : orders){
        sort_by_arrival_time();
        output_text+=round(order->turnaround_time)+"\n";
    }
    return output_text;

}

//This function is used for rounding the double numbers.
string CoffeShopManager::round(double number) {

    std::stringstream stream;

    stream.precision(2);
    stream << std::fixed;

    // Convert double to string
    stream<<number;
    std::string str  = stream.str();

    return str;
}
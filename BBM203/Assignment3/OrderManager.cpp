//
// Created by zeynep on 2.12.2022.
//
#include <iostream>
#include "OrderManager.h"
OrderManager::OrderManager(vector<Cashier *> cashiers, vector<Barista *> baristas) {
    this->baristas = baristas;
    this->cashiers = cashiers;
}


void OrderManager::take_order(Order *order) {

    Cashier* cashier  = check_cashiers(order);

    if(cashier!= nullptr){

        for(int i=0;i<cashiers.size();i++){

            if(cashiers[i]->utilization -  order->arrival_time > 0)continue;

            if(cashierQ->head == nullptr){

                //order->cashier_time +=  cashiers[i]->utilization+order->waiting_time;

                order->cashier_time+=order->order_time+order->arrival_time;

                cashiers[i]->utilization+=order->order_time;

                order->cashier_ID = cashiers[i]->ID;

                cashiers[i]->available=false;
                break;

            }
            cashiers[i]->utilization+=cashierQ->head->order->order_time;

            cashierQ->head->order->cashier_time=cashiers[i]->utilization;

            cashierQ->head->order->cashier_ID = cashiers[i]->ID;

            cashiers[i]->available=false;

            dequeue();

        }


    }
}

//This function is used for checking available cashiers.
Cashier* OrderManager::check_cashiers(Order *order) {
     order->waiting_time= cashiers[0]->utilization-order->arrival_time;

    for(Cashier *cashier: cashiers){
        if(cashier->available){
            cashier->utilization+=order->arrival_time;
            cashier->busy_time-=order->arrival_time;
            return cashier;
        }

        double current_waiting_time = (cashier->utilization - order->arrival_time);
        if(current_waiting_time<order->waiting_time){
            order->waiting_time = current_waiting_time;
            order->cashier_ID = cashier->ID;
        }
    }


    if(order->waiting_time<0){
        return cashiers[order->cashier_ID];
    }

    if(order->waiting_time>=0) {
        order->waiting_time = 0;
        NODEPTR new_node = new Node;
        new_node->order = order;
        enqueue(new_node);

        return nullptr;
    }
}

//This function is also used to transmit the remaining orders to the baristas.
void OrderManager::prepare_order(Order *order){

    Barista* barista = check_baristas(order);

    if(barista!= nullptr){

        if(baristaQ->head == nullptr){

            barista->utilization+=order->brew_time;

            order->turnaround_time = barista->utilization - order->arrival_time;

            barista->available=false;

        }else{
            for(int i=0;i<baristas.size();i++){

                if(baristas[i]->utilization -  order->cashier_time>0)continue;


                baristas[i]->utilization+=baristaQ->head->order->brew_time;

                baristas[i]->available=false;

                baristaQ->head->order->turnaround_time = baristas[i]->utilization- baristaQ->head->order->arrival_time;

                dequeue_barista();

            }

            NODEPTR new_node = new Node();
            new_node->order = order;
            enqueue_barista(new_node);

        }

    }
}


//This functionis used to transmit the remaining orders in the q to the baristas.
void OrderManager::check_baristas_for_Q() {
    Barista* barista= baristas[0];
    baristaQ->head->order->barista_waiting_time =(baristas[0]->utilization) - (baristaQ->head->order->cashier_time);
    baristaQ->head->order->barista_ID = baristas[0]->ID;

    for(int i=0;i<baristas.size();i++){

        double current_waiting_time = (baristas[i]->utilization) - (baristaQ->head->order->cashier_time);

        if(current_waiting_time < baristaQ->head->order->barista_waiting_time){
            baristaQ->head->order->barista_waiting_time = current_waiting_time;
            baristaQ->head->order->barista_ID = baristas[i]->ID;
            barista = baristas[i];
        }
    }
    if(barista!= nullptr) {
        if (baristaQ->head != nullptr) {

            barista->utilization += baristaQ->head->order->brew_time;

            baristaQ->head->order->barista_time+=barista->utilization+baristaQ->head->order->barista_waiting_time;

            baristaQ->head->order->turnaround_time = barista->utilization- baristaQ->head->order->arrival_time;

            dequeue_barista();

        }
    }
}


//This function is used for checking available baristas.
Barista* OrderManager::check_baristas(Order *order) {
    order->barista_waiting_time =(baristas[0]->utilization) - (order->cashier_time);
    order->barista_ID = baristas[0]->ID;

    for(Barista *barista: baristas){
        if(barista->available){
            barista->utilization+=order->cashier_time;
            barista->busy_time-=order->cashier_time;
            return barista;
        }

        double current_waiting_time = (barista->utilization) - order->cashier_time;

        if(current_waiting_time < order->barista_waiting_time){
            order->barista_waiting_time = current_waiting_time;
            order->barista_ID = barista->ID;
        }
    }

    if(order->barista_waiting_time<0){
        for(Barista *barista: baristas){
            if(barista->ID == order->barista_ID) return barista;
        }
    }

    if(order->barista_waiting_time>=0) {

        NODEPTR new_node = new Node();
        new_node->order = order;
        enqueue_barista(new_node);
        return nullptr;
    }
}

void OrderManager::enqueue(NODEPTR node){

    cashierQ->size+=1;
    cashierQ->max_size = cashierQ->size > cashierQ->max_size ? cashierQ->size : cashierQ->max_size;

    if(cashierQ->head == nullptr&&cashierQ->tail== nullptr){
        cashierQ->head = node;
        cashierQ->tail = node;
        return;
    }
    cashierQ->tail->next=node;
    cashierQ->tail=node;

}

void OrderManager::dequeue() {

    if(cashierQ->size == 1){
        cashierQ->head= nullptr;
        cashierQ->tail = nullptr;
    }else{
        cashierQ->head = cashierQ->head->next;
    }
    cashierQ->size-=1;
}

void OrderManager::enqueue_barista(NODEPTR node) {
    //TODO priorityQ yap bunu
    //TODO bunu sonra linkedlist gibi yaz
    if(baristaQ->head == nullptr){
        baristaQ->head = node;
        baristaQ->tail = node;
    }

    else if(baristaQ->size == 1){
        if(baristaQ->head->order->price > node->order->price){
            baristaQ->head->next = node;
            baristaQ->tail = node;
        }else{
            node->next = baristaQ->head;
            baristaQ->head = node;
        }
    }

    else if(node->order->price > baristaQ->head->order->price){
        node->next = baristaQ->head;
        baristaQ->head=node;
    }

    else{
        NODEPTR temp_node = baristaQ->head;
        while(temp_node != nullptr){
            if(temp_node->next == nullptr){
                baristaQ->tail->next = node;
                baristaQ->tail = node;
                break;
            }
            else if(node->order->price<temp_node->order->price && node->order->price>temp_node->next->order->price){
                node->next = temp_node->next;
                temp_node->next = node;
                break;
            }
            temp_node=temp_node->next;
        }
    }
    baristaQ->size+=1;
    baristaQ->max_size = baristaQ->size > baristaQ->max_size ? baristaQ->size : baristaQ->max_size;

}


void OrderManager::dequeue_barista() {

    if(baristaQ->size==1){
        baristaQ->head= nullptr;
        baristaQ->tail = nullptr;
    }else{
        baristaQ->head = baristaQ->head->next;
    }
    baristaQ->size-=1;

}

void OrderManager::printQ() {
    NODEPTR curr = cashierQ->head;

    if(curr == nullptr){
        cout<<"BOS ";
        return;
    }
    std::cout<<"PRINT::   ";
    std::cout<<curr->order->arrival_time<<" ";
    while(curr != cashierQ->tail){
        std::cout<<curr->order->arrival_time<<" ";
        curr=curr->next;
    }
}

void OrderManager::print() {
    NODEPTR curr = baristaQ->head;

    if(curr == nullptr){
        cout<<"BOS ";
        return;
    }
    std::cout<<"PRINT::   ";

    while(curr!= nullptr){

        std::cout<<curr->order->cashier_time<<"\t";
        curr=curr->next;
    }

}

//
// Created by zeynep on 2.12.2022.
//

#ifndef ASM3_CAFEMANAGER_H
#define ASM3_CAFEMANAGER_H

#endif //ASM3_CAFEMANAGER_H

#include "Queue.h"
#include "Cashier.h"
#include "Barista.h"
#include <vector>

using namespace std;

class OrderManager{
    public:

        double total_running_time=0;

        Queue *cashierQ = new Queue();
        Queue *baristaQ = new Queue();

        //TODO barista q arrayi aç
        // arraye at tüm q ları
        vector<Cashier*> cashiers;
        vector<Barista*> baristas;

        OrderManager(vector<Cashier*> cashiers,vector<Barista*> baristas);

        void take_order(Order *order);
        void prepare_order(Order *order);

        Cashier* check_cashiers(Order *order);
        Barista* check_baristas(Order *order);
        void check_baristas_for_Q();

        void enqueue(NODEPTR node);
        void enqueue_barista(NODEPTR node);
        void dequeue();
        void dequeue_barista();

        void printQ();
        void print();



};
//
// Created by zeynep on 3.12.2022.
//

#ifndef ASM3_ORDERMANAGER_H
#define ASM3_ORDERMANAGER_H

#endif //ASM3_ORDERMANAGER_H

#include "OrderManager.h"
#include <set>
#include <vector>

class CoffeShopManager {
    public:

        std::vector<Order*> orders;
        std::vector<Cashier*> cashiers;
        std::vector<Barista*> baristas;
        vector<vector<Order*>> order_vectors;
        std::vector<Barista*> model2_baristas;

        vector<double> total_running_times;
        int cashier_num=0;
        int max_size_of_cashierQ=0;
        double total_running_time=0;
        vector<int> max_sizes;
        string output_text;


    CoffeShopManager(std::vector<Order*> &orders, int cashier_num);

        void create_cashiers();
        void create_baristas();
        void create_order_vectors();

        void take_order();
        void prepare_order();
        void prepare_order(vector<Order*> orders);

        void divide_orders();

        vector<Order*> sorter();
        vector<Order*> sort_by_arrival_time();

        string print();

        string round(double value);
};
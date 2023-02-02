//
// Created by zeynep on 2.12.2022.
//

#ifndef ASM3_ORDER_H
#define ASM3_ORDER_H

#endif //ASM3_ORDER_H

class Order{
    public:
        double waiting_time=0;
        double barista_waiting_time=0;

        int cashier_ID;
        int barista_ID;

        double cashier_time;
        double barista_time;

        double arrival_time;
        double order_time;
        double brew_time=0;
        double price;
        double turnaround_time;
        bool operator < (const Order* other) const {
            return cashier_time < other->cashier_time;
        }
};
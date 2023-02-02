//
// Created by zeynep on 2.12.2022.
//

#ifndef ASM3_CASHIER_H
#define ASM3_CASHIER_H

#endif //ASM3_CASHIER_H

class Cashier{
    public:
        Order* owned_order= nullptr;
        int ID;
        bool available=true;
        double busy_time;
        double utilization=0;
        Cashier(int ID){
            this->ID = ID;
        }
};
//
// Created by zeynep on 2.12.2022.
//

#ifndef ASM3_BARISTA_H
#define ASM3_BARISTA_H

#endif //ASM3_BARISTA_H

class Barista{
    public:
        Order* owned_order= nullptr;
        int ID;
        bool available=true;
        double busy_time=0;
        double utilization=0;
        Barista(int ID){
            this->ID=ID;
        }
};
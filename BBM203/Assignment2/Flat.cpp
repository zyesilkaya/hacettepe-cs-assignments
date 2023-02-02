//
// Created by zeynep on 16.11.2022.
//
#include <iostream>
#include "Flat.h"

//This function adds a flat to the given apartment
void Flat::add_flat(int index, int initial_bandwidth, int ID) {
    F_NODEPTR newFlat = new FlatNode(initial_bandwidth, ID);

    if(is_empty()){
        head = newFlat;
    }
    else{

        F_NODEPTR current = findNext(index);

        if(index==0){
            newFlat->next = head;
            head->prev = newFlat;
            head = newFlat;
        }

        else if(current->next== nullptr){
            current->next = newFlat;
            newFlat->prev = current;
        }

        else{
            newFlat->next = current->next;
            current->next = newFlat;
            (newFlat->next)->prev = newFlat;
            newFlat->prev = current;

        }

    }
}

//This functions finds the node where is the given index
F_NODEPTR Flat::findNext(int index) {
    F_NODEPTR current = head;

    for(int i=0;i<index-1;i++){
        current=current->next;
    }
    return current;
}

//This functions finds the node where is the given ID
F_NODEPTR Flat::findByID(int ID) {
    F_NODEPTR current = head;
    while (current != nullptr){
        if(current->ID == ID){
            return current;
        }
        current = current->next;
    }
}

//This function checks the apartment is null or not
bool Flat::is_empty() {
    return head == nullptr;
}

//This function removes all the flats from given apartment
void Flat::remove_all_flats() {
    F_NODEPTR last = head;

    while(last->next != nullptr){
        last=last->next;
    }

    F_NODEPTR before = last->prev;
    int i=0;
    while(before != nullptr){
        i++;
        last = before;
        before = last->prev;
        delete last->next;

    }
    delete before;
}
//
// Created by zeynep on 16.11.2022.
//
#include <string>
#include "Apartment.h"
#include <iostream>

//This function adds an apartment to the street by checking its position
void Apartment::add_apartment(std::string name, string position, int max_bandwidth){
    NODEPTR newApartment = new ApartmentNode(name, max_bandwidth);
    if(is_empty()){
        head = newApartment;
        head->next= head;
    }else{
        string delimiter = "_";
        string whereToAdd = position.substr(0, position.find(delimiter));
        position.erase(0, position.find(delimiter) + delimiter.length());
        string value = position.substr(0, position.find(delimiter));

        if(position == "head"){
            NODEPTR current = head;
            while(current->next != head){
                current = current->next;
            }
            current->next = newApartment;
            newApartment->next = head;
            head = newApartment;
        }

        else if(whereToAdd == "before"){
            NODEPTR current = findBefore(value);
            if(current == head && value==head->name){
                NODEPTR last = head;
                while(last->next != head){
                    last=last->next;
                }
                last->next= newApartment;
                newApartment->next = head;
                head = newApartment;
            }else{
                newApartment->next = current->next;
                current->next = newApartment;
            }
        }
        else if(whereToAdd == "after"){

            NODEPTR current = findNext(value);
            newApartment->next = current->next;
            current->next = newApartment;

        }
    }
}

//This function removes an apartment from the street
NODEPTR Apartment::remove_apartment(std::string name) {
    NODEPTR beforeApt = findBefore(name);
    NODEPTR currentApt = beforeApt->next;
    if(is_empty()){
        return nullptr;
    }else{

        if(currentApt->firstFlat != nullptr){
            currentApt->firstFlat->remove_all_flats();
        }

        if(currentApt == head){
            currentApt->next = head;
        }
        else if(currentApt == beforeApt){
            head=nullptr;
        }else{
            beforeApt->next = currentApt->next;
            currentApt->next = nullptr;
        }

        delete currentApt->firstFlat;
        delete currentApt;

        return head;
    }
}

//This function adds a flat to the given apartment by checking its index
void Apartment::add_flat(std::string apartment_name, int index, int initial_bandwidth, int ID) {

    NODEPTR apt = findNext(apartment_name);

    if(apt->firstFlat == nullptr){
        apt->firstFlat = new Flat();
    }
    if(apt->remaining_bandwidth<initial_bandwidth){
        initial_bandwidth = apt->remaining_bandwidth;
        apt->remaining_bandwidth = 0;
    }else{
        apt->remaining_bandwidth -= initial_bandwidth;
    }
    apt->firstFlat->add_flat(index,initial_bandwidth,ID);
}

//This function finds the apartment which names' given
NODEPTR Apartment::findNext(std::string value) {
    NODEPTR current = head;
    if(head->next==head){
        return head;
    }
    while(current->next->name != value){
        current = current->next;//
    }

    return current->next;
}

//This function finds the apartment before the given apartment by its name
NODEPTR Apartment::findBefore(std::string value) {
    NODEPTR current = head;
    if(head->next==head){
        return head;
    }
    while(current->next->name != value){
        current = current->next;
    }
    return current;
}

//This function checks the apartment is null or not
bool Apartment::is_empty() {
    return head == nullptr;
}

//This function prints all the apartments and their flats
string Apartment::print_list(string output) {
    NODEPTR current = head;
    if(head == nullptr) return (output="There is no Apartment");
    while(current->next!= head){
        output += (current->name+"("+to_string(current->max_bandwidth)+")"+"\t");
        output += print_flats(current);
        current = current->next;
        output += "\n";
    }
    output += (current->name+"("+to_string(current->max_bandwidth)+")"+"\t");
    output += print_flats(current);

    return output;
}

//This function prints all the flats
string Apartment::print_flats(NODEPTR currentApt) {
    string output;
    if(currentApt->firstFlat != nullptr){
        F_NODEPTR current_flat = currentApt->firstFlat->head;
        while(current_flat->next != nullptr){
            output += "Flat"+to_string(current_flat->ID)+"("+to_string(current_flat->initial_bandwidth)+")"+"\t";
            current_flat = current_flat->next;
        }
        output += "Flat"+to_string(current_flat->ID)+"("+to_string(current_flat->initial_bandwidth)+")"+"\t";
        return output;
    }
}

//This function finds the maximum bandwidths of all apartments
int Apartment::find_sum_of_max_bandwidth() {
    if(head== nullptr) return 0;
    NODEPTR current = head;
    int total=0;
    while(current->next != head){
        total+= current->max_bandwidth;
        current = current->next;
    }
    total+=current->max_bandwidth;
    return total;
}

//This function makes the flat empty by doing their initial_bandwith 0
void Apartment::make_flat_empty(std::string apartment_name, int ID) {
    NODEPTR current = head;

    while(current->name != apartment_name){
        current = current->next;
    }
    F_NODEPTR current_flat = current->firstFlat->head;
    while(current_flat->ID != ID){
        current_flat = current_flat->next;
    }
    current_flat->is_empty = 1;
    current_flat->initial_bandwidth=0;
}

//This function merges given second apartment to the first apartment and removes second apartment
void Apartment::merge_two_apartments(std::string apartment1, std::string apartment2) {

    NODEPTR apt1 = findNext(apartment1);
    NODEPTR apt2 = findNext(apartment2);

    apt1->max_bandwidth += apt2->max_bandwidth;

    if(apt1->firstFlat == nullptr){
        apt1->firstFlat = apt2->firstFlat;
        apt2->firstFlat = nullptr;
        remove_apartment(apartment2);
    }
    else if(apt2->firstFlat == nullptr){
        remove_apartment(apartment2);
    }else{
        F_NODEPTR last = apt1->firstFlat->head;;
        while(last->next != nullptr){
            last=last->next;
        }

        last->next = apt2->firstFlat->head;
        apt2->firstFlat = nullptr;
        remove_apartment(apartment2);
    }

}

//This function relocates given flats to the given apartment and before the given flat
void Apartment::relocate_flats_to_same_apartments(std::string apartment_name, int ID, vector<int> flat_ids) {

    vector<F_NODEPTR> flats;
    for(int i=0;i<flat_ids.size();i++){
        flats.push_back(nullptr);
    }

    NODEPTR apartment = findNext(apartment_name);
    NODEPTR current = head;

    while(current->next != head){
          if(current->firstFlat != nullptr){
              for(int i=0;i<flat_ids.size();i++){
                  F_NODEPTR current_flat = current->firstFlat->head;
                  while(current_flat != nullptr){
                      if(flat_ids[i] == current_flat->ID){

                          current->max_bandwidth -= current_flat->initial_bandwidth;
                          apartment->max_bandwidth += current_flat->initial_bandwidth;
                          current->remaining_bandwidth -= current_flat->initial_bandwidth;
                          apartment->remaining_bandwidth += current_flat->initial_bandwidth;

                          if(current_flat == current->firstFlat->head){// HEAD
                              current->firstFlat->head = current_flat->next;
                              flats[i] = current_flat;
                          }else{

                              if(current_flat->next == nullptr){
                                  current_flat->prev->next = current_flat->next;

                              }else{
                                  current_flat->prev->next = current_flat->next;
                                  current_flat->next->prev = current_flat->prev;
                              }
                              flats[i] = current_flat;

                        }
                    }
                      current_flat = current_flat->next;
                  }
            }
        }
        current = current->next;
    }


    F_NODEPTR first_flat = apartment->firstFlat->findByID(ID);
    for(int i=0;i<flats.size();i++){

        F_NODEPTR flat = flats[i];
        if(first_flat == apartment->firstFlat->head){

            apartment->firstFlat->head = flat;
            flat->next = first_flat;
            first_flat->prev = flat;
        }else{
            first_flat->prev->next = flat;

            flat->prev = first_flat->prev;

            first_flat->prev = flat;
            flat->next = first_flat;
        }

    }

}


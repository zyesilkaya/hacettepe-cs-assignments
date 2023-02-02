OBJS	= main.o Flat.o Apartment.o FlatNode.o ApartmentNode.o
SOURCE	= main.cpp Flat.cpp Apartment.cpp FlatNode.cpp ApartmentNode.cpp
HEADER	= Flat.h Apartment.h
OUT	= networkmap
CC	 = g++
FLAGS	 = -g -c -Wall
LFLAGS	 = 

all: $(OBJS)
	$(CC) -g $(OBJS) -o $(OUT) $(LFLAGS)

main.o: main.cpp
	$(CC) $(FLAGS) main.cpp -std=c++11

Flat.o: Flat.cpp
	$(CC) $(FLAGS) Flat.cpp -std=c++11

Apartment.o: Apartment.cpp
	$(CC) $(FLAGS) Apartment.cpp -std=c++11

FlatNode.o: FlatNode.cpp
	$(CC) $(FLAGS) FlatNode.cpp -std=c++11

ApartmentNode.o: ApartmentNode.cpp
	$(CC) $(FLAGS) ApartmentNode.cpp -std=c++11


clean:
	rm -f $(OBJS) $(OUT)
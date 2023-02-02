OBJS	= main.o Node.o OrderManager.o CoffeShopManager.o App.o
SOURCE	= main.cpp Node.cpp OrderManager.cpp CoffeShopManager.cpp App.cpp
HEADER	= Queue.h OrderManager.h Cashier.h Barista.h Order.h CoffeShopManager.h App.h
OUT	= Assignment3
CC	 = g++
FLAGS	 = -g -c -Wall
LFLAGS	 = 

all: $(OBJS)
	$(CC) -g $(OBJS) -o $(OUT) $(LFLAGS)

main.o: main.cpp
	$(CC) $(FLAGS) main.cpp -std=c++11

Node.o: Node.cpp
	$(CC) $(FLAGS) Node.cpp -std=c++11

OrderManager.o: OrderManager.cpp
	$(CC) $(FLAGS) OrderManager.cpp -std=c++11

CoffeShopManager.o: CoffeShopManager.cpp
	$(CC) $(FLAGS) CoffeShopManager.cpp -std=c++11

App.o: App.cpp
	$(CC) $(FLAGS) App.cpp -std=c++11


clean:
	rm -f $(OBJS) $(OUT)
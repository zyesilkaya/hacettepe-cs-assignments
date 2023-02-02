OBJS	= main.o PrimaryTree.o PrimaryNode.o SecondaryTree.o SecondaryNode.o TreeManager.o
SOURCE	= main.cpp PrimaryTree.cpp PrimaryNode.cpp SecondaryTree.cpp SecondaryNode.cpp TreeManager.cpp
HEADER	= PrimaryTree.h PrimaryNode.h SecondaryTree.h SecondaryNode.h RedBlackTree.h Node.h TreeManager.h
OUT	= webstore
CC	 = g++
FLAGS	 = -g -c -Wall
LFLAGS	 =

all: $(OBJS)
	$(CC) -g $(OBJS) -o $(OUT) $(LFLAGS)

main.o: main.cpp
	$(CC) $(FLAGS) main.cpp -std=c++11

PrimaryTree.o: PrimaryTree.cpp
	$(CC) $(FLAGS) PrimaryTree.cpp -std=c++11

PrimaryNode.o: PrimaryNode.cpp
	$(CC) $(FLAGS) PrimaryNode.cpp -std=c++11

SecondaryTree.o: SecondaryTree.cpp
	$(CC) $(FLAGS) SecondaryTree.cpp -std=c++11

SecondaryNode.o: SecondaryNode.cpp
	$(CC) $(FLAGS) SecondaryNode.cpp -std=c++11

TreeManager.o: TreeManager.cpp
	$(CC) $(FLAGS) TreeManager.cpp -std=c++11


clean:
	rm -f $(OBJS) $(OUT)
#include <iostream>
#include <fstream>
#include <utility>

using namespace std;

//This class is for matrices. It contains row size, column size and 2d array pointer fields.
// It has createMatrix functions which creates pointer matrix by using given input file.
class Matrix{
    public:
        int row;
        int col;
        int** array;
        Matrix( int row, int col){
            this->row=row;
            this->col=col;
        }
        ~Matrix(){
           for(int i=0;i<row;i++){
               delete []array[i];
           }
           delete []array;
           
        }
        void createMatrix(string fileName){
            ifstream file (fileName);
            this->array = new int*[row];
            if (file.is_open()) {
                for(int i=0;i<row;i++){
                    this->array[i] = new int[col];
                    for(int j=0;j<col;j++){
                        file>>this->array[i][j];
                        
                    }
                }
                file.close();
                
            }
        }
};

//This class is for running the game. It contains key and map matrix objects, game's direction and output text fields.
//It has findTreasure method which is moving in the matrix. findDirection method finds next direction by using the result.
//checkDirection method checks the next move by using the direction. If it cannot move in that direction, it changes direction with the opposite.
class Game{
    public:
        Matrix* keyMatrix;
        Matrix* mapMatrix;
        string outputText;
        //direction[1] aşağı - yukarı row kontrolü
        int* direction = new int[2];

        Game(Matrix* keyMatrix, Matrix* mapMatrix, string outputText){
            this->mapMatrix = mapMatrix;
            this->keyMatrix = keyMatrix;
            direction[0]=1;
            direction[1]=0;
            this->outputText=std::move(outputText);
        }
        ~Game(){
            delete []direction;
        }

        void findTreasure(int row,int col){
            int total=0;
            for(int i=0;i<keyMatrix->row;i++){
                for(int j=0;j<keyMatrix->col;j++){
                    total += keyMatrix->array[i][j]*(mapMatrix->array[i+row][j+col]);
                }
            }
            total = total<0?total%5+5:total;
            
            int result=total%5;

            outputText+=(to_string((row+(keyMatrix->row/2)))+","+ to_string(col+(keyMatrix->row/2))+":"+to_string(total));

            if(result==0){
                return;
            }
            findDirection(result);
            checkDirection(row,col);

            outputText+="\n";

            return findTreasure(row+(direction[1]*keyMatrix->row),col+(direction[0]*keyMatrix->col));
        }

        int findDirection(int result) {
            if(result==1){//up
                direction[1]=-1;
                direction[0]=0;
            }
            else if(result==2){//down
                direction[1]=1;
                direction[0]=0;
            }
            else if(result==3){//right
                direction[0]=1;
                direction[1]=0;
            }
            else if(result==4){//left
                direction[0]=-1;
                direction[1]=0;
            }
        }

        int checkDirection( int row, int col) {

            if((row+(direction[1])*(keyMatrix->row) < 0 || row+(direction[1])*(keyMatrix->row)>=mapMatrix->row)){
                
                direction[1]*=-1;
            }
            if((col+(direction[0])*(keyMatrix->row) < 0 || col+(direction[0])*(keyMatrix->row)>=mapMatrix->col)){
               
                direction[0]*=-1;
            }
        }

};



int main(int argc, char *argv[]) {
    string mapSize,keySize,mapFile,keyFile,outputFile;
    mapSize=argv[1];
    keySize=argv[2];
    mapFile=argv[3];
    keyFile=argv[4];
    outputFile=argv[5];

    ofstream output (outputFile.c_str());

    string delimiter = "x";
    string mapRow = mapSize.substr(0, mapSize.find(delimiter));
    mapSize.erase(0, mapSize.find(delimiter) + delimiter.length());
    string mapCol = mapSize.substr(0, mapSize.find(delimiter));

    Matrix *keyMatrix = new Matrix(stoi(keySize),stoi(keySize));
    Matrix *mapMatrix = new Matrix(stoi(mapRow),stoi(mapCol));

    keyMatrix->createMatrix(keyFile);
    mapMatrix->createMatrix(mapFile);

    Game *game = new Game(keyMatrix,mapMatrix, "");

    game->findTreasure(0,0);

    output<<game->outputText;
    output.close();

    delete keyMatrix;
    delete mapMatrix;
    delete game;

    //15x20 5 mapmatrix1.txt keymatrix1.txt output.txt
    //18x18 3 mapmatrix1.txt keymatrix1.txt output.txt

    return 0;
}

import sys
index_list = []
score_dict = {"B" :9, "G":8, "W":7, "Y":6, "R":5, "P":4, "O":3, "D":2, "F":1, "X":0, " ":0}
i = 0
score = 0
with open(sys.argv[1],"r",encoding="utf-8") as file:
    matrix = file.readlines()
    matrix = [line.strip("\n").split() for line in matrix]

def search(row,column):#searchs every neighbour cells for ball.If it finds same color ball,explode that balls.
    global score
    if x == " ":
        pass

    elif len(matrix)>0:
        if column+1<len(matrix[-1]) and x == matrix[row][column+1]:#!!!!!!!!!
            index_list.append(tuple([row,column]))
            index_list.append(tuple([row,column+1]))
            matrix[row][column],matrix[row][column+1] = " "," "
            search(row,column+1)

        if column-1>=0 and x == matrix[row][column-1] :
            index_list.append(tuple([row,column]))
            index_list.append(tuple([row,column-1]))
            matrix[row][column],matrix[row][column-1] = " "," "
            search(row,column-1)
            
        if row-1>=0 and x == matrix[row-1][column]:
            index_list.append(tuple([row,column]))
            index_list.append(tuple([row-1,column]))
            matrix[row][column],matrix[row-1][column] = " "," "
            search(row-1,column)
   
        if row+1<len(matrix) and x == matrix[row+1][column]:#!!!!!!!!!!
            index_list.append(tuple([row,column]))
            index_list.append(tuple([row+1,column]))
            matrix[row][column],matrix[row+1][column]  = " "," "
            search(row+1,column)

def delete_column():#!!!!!!!!!!!!!!!!!!!!!delete every empty columns.
    try:
        global i
        liste1 = []
        if len(matrix)>0 and i>len(matrix[-1])-1:
            i = 0
        elif matrix != []:
            for row in reversed(matrix):
                liste1.append(row[i])
            
            if all(a == " " for a in liste1):
                for row in matrix:
                    row.pop(i)
                i -= 1
            i +=1
            delete_column()       
    except IndexError:
        pass

def delete_row():#delete every empty rows.
    if matrix != []:
        for row in reversed(matrix):
            if all(i == " " for i in row):
                matrix.remove(row)

def is_x(y,column):#if input is 'X'(bomb) explodes that column and row.If that column or row has bomb,does same process for that bomb.
    global score
    liste2 = []
    matrix[y][column] = " "
    for row in matrix:
        if row[column] == "X":
            is_x(matrix.index(row),column)
        liste2.append(row[column])
        row[column] = " "
        
    if "X" in matrix[y]:
        is_x(y,matrix[y].index("X"))
    liste2.extend(matrix[y])
    for i in liste2:
        score += score_dict[i]
    matrix[y] = [" " for i in matrix[y]]

def move_down(): #!!!!!!!!!! if there is empty cell under a ball,replace them.
    for row in matrix:
        a = 0
        for i in row:
            if a < len(matrix[-1]):
                b = matrix.index(row)
                if b < len(matrix)-1 and matrix[b+1][a] == " " and matrix[b][a]!=" ":
                    matrix[b][a],matrix[b+1][a] = matrix[b+1][a],matrix[b][a]
                    move_down()
            a +=1
            
def game_over():#if there is no cell which has no neighbor with ball of the same color and also there is no bomb (x) in a cell function returns false and game over.
    b=0
    for row in matrix:
        a = 0
        if "X" in row:
            return True
        else:
            while a<len(row)-1:
                if row[a] != " " and row[a] == row[a+1]:       
                    return True
                a += 1
    try:
        if len(matrix)<=0:
            return False
        else:
            for i in range(len(matrix)):
                for b in range(len(matrix[-1])):
                    if matrix[i][b] !=" " and matrix[i][b] == matrix[i+1][b]:
                        return True 
        
    except IndexError:
        pass
    return False    

def view_matrix():
    print()
    for row in matrix:
        print(' '.join([str(i) for i in row]))
            
def main():
    global x,score,index_list
    while game_over():
        row,column = (input("\nPlease enter a row and column number:")).split()
        row,column = int(row),int(column)
        try:
            x = matrix[row][column]
            if x == " ":
                print("\nPlease enter a valid size!")
            elif x == "X":
                is_x(row,column)
                delete_row()
                delete_column()
                view_matrix()
                print("\nYour score is: ",score)
            else:
                search(row,column)
                move_down()
                delete_row()
                delete_column()
                view_matrix()
                score += score_dict[x]*(len(set(index_list)))
                index_list = []
                print("\nYour score is: ",score)
                
        except IndexError:
            print("\nPlease enter a valid size!")
    print("\nGame Over!")
view_matrix()
print("\nYour score is: 0")
main()
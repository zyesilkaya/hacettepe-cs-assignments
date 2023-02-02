print("""<-----RULES----->
1. BRUSH DOWN
2. BRUSH UP
3. VEHICLE ROTATES RIGHT
4. VEHICLE ROTATES LEFT
5. MOVE UP TO X
6. JUMP
7. REVERSE DIRECTION
8. VIEW THE MATRIX
0. EXIT
Please enter the commands with a plus sign (+) between them.
""")

deneme = False

while deneme == False:
    commands = input()
    commandlist = commands.split("+")
    for i in commandlist[1:]:
        if i[0:2]=="5_" and len(i)>2 :
            deneme = True
        elif int(i) <= 8 and int(i) >= 0 and i != "5":
            deneme = True
        else:
            deneme = False
            print("You entered an incorrect command. Please try again!")
            break

brush = False  # brush ın ilk durumu
initial_direction = "right"
positionx = 1  # başlangıç x konumu
positiony = 1  # başlangıç y konumu

n = int(commandlist[0])
matrixx = [[" "] * (n + 2) for i in range(n + 3)]

for i in range(n + 2): 
    for j in range(n + 2):
        if i == 0:
            matrixx[i][j] = "+"
        elif i == n + 1:
            matrixx[i][j] = "+"
        elif i != 0 and i != n + 1 and j == 0:
            matrixx[i][j] = "+"
        elif i != 0 and i != n + 1 and j == n + 1:
            matrixx[i][j] = "+"


def brushup():
    global brush
    brush = False


def brushdown():
    global positionx, positiony, brush
    brush = True
    matrixx[positiony][positionx] = "*"


def turnright():
    global initial_direction

    if initial_direction == "right":
        initial_direction = "down"
    elif initial_direction == "left":
        initial_direction = "up"
    elif initial_direction == "down":
        initial_direction = "left"
    elif initial_direction == "up":
        initial_direction = "right"


def turnleft():
    global initial_direction

    if initial_direction == "right":
        initial_direction = "up"
    elif initial_direction == "left":
        initial_direction = "down"
    elif initial_direction == "down":
        initial_direction = "right"
    elif initial_direction == "up":
        initial_direction = "left"


def moveupx(i):  # positiony ve positionx olarak yazdım şimdilik
    global positionx, positiony, initial_direction, brush, matrixx, n
    x = int(i[2:])
    while x > 0:
        if initial_direction == "right":
            positionx += 1
            if positionx > n:
                positionx = 1
            if brush == True:
                matrixx[positiony][positionx] = "*"  # a = matrix
        if initial_direction == "left":
            positionx -= 1
            if positionx < 1:
                positionx = n
            if brush == True:
                matrixx[positiony][positionx] = "*"
        if initial_direction == "up":
            positiony -= 1
            if positiony < 1:
                positiony = n
            if brush == True:
                matrixx[positiony][positionx] = "*"
        if initial_direction == "down":
            positiony += 1
            if positiony > n:
                positiony = 1
            if brush == True:
                matrixx[positiony][positionx] = "*"

        x -= 1


def jump():
    brushup()
    global positionx, positiony, n,initial_direction

    if initial_direction == "right":
        positionx += 3
        if positionx > n:
            positionx = positionx - n
    if initial_direction == "left":
        positionx -= 3
        if positionx < 1:
            positionx += n
    if initial_direction == "up":
        positiony -= 3
        if positiony < 1:
            positiony += n
    if initial_direction == "down":
        positiony += 3
        if positiony > n:
            positiony = positiony - n


def reverse():
    global initial_direction

    if initial_direction == "right":
        initial_direction = "left"
    elif initial_direction == "left":
        initial_direction = "right"
    elif initial_direction == "down":
        initial_direction = "up"
    elif initial_direction == "up":
        initial_direction = "down"


def viewmatris():
    for row in matrixx:
        print(''.join(row))


def main():
    for i in commandlist[1:]:

        if i == "1":
            brushdown()
        elif i == "2":
            brushup()
        elif i == "3":
            turnright()
        elif i == "4":
            turnleft()
        elif i == "6":
            jump()
        elif i == "7":
            reverse()
        elif i == "8":
            viewmatris()
        elif i == "0":
            break
        elif i[0] == "5":
            moveupx(i)


main()

import sys
users_dict = {}
command_list = []
with open(sys.argv[1],"r",encoding="utf-8") as file:
    for line in file:
        line = line.strip(" \n")
        line = line.split()
        command_list.append(line)     

with open(sys.argv[2],"r",encoding="utf-8") as file2:
    for line in file2:
        liste = []
        line =  line.strip(" \n")
        line = line.split(":")
        for i in line[1].split():
            liste.append(i)
            users_dict[line[0]] = set(liste)   
with open("output.txt","w",encoding="utf-8") as file3:  
    file3.write("""Welcome to Assignment 3
----------------------------------\n""")
    def add_user(username) : #add new user
        if users_dict.get(username) == None: 
            users_dict[username]=set([])
            file3.write("User '%s' has been added to the social network successfully\n"%(username))
        else:
            file3.write("ERROR: Wrong input type! for'ANU'! -- This user already exists!!\n")
            
    def delete_user(username):  #delete existing user
        if username in users_dict.keys():
            for lisst in users_dict.values():
                if username in lisst:
                    lisst.remove(username)            
            del users_dict[username]        
                         
            file3.write("User '%s' and his/her all relations have been deleted successfully\n"%(username))
        else:
            file3.write("ERROR: Wrong input type! for 'DEU'!--There is no user named '%s'!!\n"%(username))
 
    def add_friend(user,newfriend):  #add new friend   
        
        if user not in users_dict.keys() and newfriend not in users_dict.keys():
            file3.write("ERROR: Wrong input type! for 'ANF'! -- No user named '%s'  and '%s'  found!!\n"%(user,newfriend)) 
        elif user not in users_dict.keys():
            file3.write("ERROR: Wrong input type! for 'ANF'! -- No user named '%s' found!!\n"%(user))
        elif newfriend in users_dict[user]:
            file3.write("ERROR: A relation between '%s' and '%s' already exists!!\n"%(user,newfriend))    
        else:
            users_dict[user].add(newfriend)
            users_dict[newfriend].add(user)
            file3.write("Relation between '%s' and '%s' has been added successfully\n"%(user,newfriend))
            
    def delete_friend(user,exfriend): #delete existing friend
        
        if user not in users_dict.keys() and exfriend not in users_dict.keys():
            file3.write("ERROR: Wrong input type! for 'DEF'! -- No user named '%s' and '%s' found!\n"%(user,exfriend))
        elif user not in users_dict.keys():   
            file3.write("ERROR: Wrong input type! for 'DEF'! -- No user named '%s' found!\n"%(user))
        elif exfriend not in users_dict.keys():
            file3.write("ERROR: Wrong input type! for 'DEF'! -- No user named '%s' found!\n"%(user))   
        elif exfriend not in users_dict[user]:
            file3.write("ERROR: No relation between '%s' and '%s' found!!\n"%(user,exfriend))
        else:
            users_dict[user].remove(exfriend)
            users_dict[exfriend].remove(user)
            file3.write("Relation between '%s' and '%s' has been deleted successfully\n"%(user,exfriend))
            
    def count_friend(user):   #count friend
        if user not in users_dict.keys():
            file3.write("ERROR: Wrong input type! for 'CF'! -- No user named '%s' found!.\n"%(user))
        else:
            number = len(users_dict[user])
            file3.write("User '%s' has %d friends.\n" % (user,number))
            
    def find_friend(user,md):  #find possible friends
        
        if user not in users_dict.keys():
            file3.write("ERROR: Wrong input type! for 'FPF'! -- No user named '%s' found!\n"%(user))
        elif int(md)<1 or int(md)>3:
            file3.write("ERROR: Maximum Distance is out of range!!\n")
            
        elif md == "1":
            file3.write("User '%s' has %d possible friends when maximum distance is 1\n"%(user,len(users_dict[user])))
            file3.write('These possible friends:{%s} \n'%(", ".join(sorted(users_dict[user]))))

        elif md == "2":
            fpf_set = users_dict[user]
            for friend in users_dict[user]:
                fpf_set = set.union(fpf_set,users_dict[friend])
            fpf_set.remove(user)
            file3.write("User '%s' has %d possible friends when maximum distance is 2\n"%(user,len(fpf_set)))
            file3.write('These possible friends:{%s} \n'% (", ".join(sorted(fpf_set))))
            
        elif md == "3":
            fpf_set = users_dict[user]
            for friend in users_dict[user]:
                fpf_set = set.union(fpf_set,users_dict[friend])
                for friend2 in users_dict[friend]:
                    fpf_set = set.union(fpf_set,users_dict[friend2])
            fpf_set.remove(user)
            file3.write("User '%s' has %d possible friends when maximum distance is 3\n"%(user,len(fpf_set)))
            file3.write('These possible friends:{%s} \n'% (", ".join(sorted(fpf_set))))
            
    def suggest_friend(user,md):   #suggest friend to existing user
        global liste
        if user not in users_dict.keys():
            file3.write("Error: Wrong input type! for 'SF'! -- No user named '%s' found!!\n"%(user))
        
        elif int(md) < 2 or int(md) > 3:
            file3.write("Error: Mutually Degree cannot be less than 2 or greater than 3\n")
            
        else:     
            if md == "2":
                sf_set = set([])
                sf_set2 = set([])
                file3.write("Suggestion List for '%s' (when MD is 2):\n"%(user))
                for friend in users_dict.keys():
                    if len(set.intersection(users_dict[user],users_dict[friend])) == 2 and friend != user and friend !=users_dict[user] :
                        sf_set.add(friend)
                              
                for i in users_dict[user]:
                    for k in users_dict[i]:  
                        liste.append(k)                                                             
                for f in sorted(sf_set):
                    if f in users_dict[user] or f == user or f not in liste:
                        sf_set.remove(f)
                liste = []    
                
                for friend in users_dict.keys():    
                    if len(set.intersection(users_dict[user],users_dict[friend])) == 3 and friend != user and friend !=users_dict[user]:
                        sf_set2.add(friend)
                        if len(sf_set2) == 0:
                            file3.write("'%s' hasn't 3 mutual friends.\n"%(user))
                            file3.write("There is no suggested friends for '%s'.\n"%(user))    
                                                    
                for f in sorted(sf_set):
                    if f not in sf_set2:
                        file3.write(" '%s' has 2 mutual friends with '%s'\n" %(user, f))               
                for i in users_dict[user]:
                    for k in users_dict[i]:
                        for l in users_dict[k]:        
                            for q in users_dict[l]:
                                liste.append(q)                                  
                for f in sorted(sf_set2):
                    if f in users_dict[user] or f == user or f not in liste:
                            sf_set2.remove(f)
                liste = []
    
                for f in sorted(sf_set2):
                    file3.write(" '%s' has 3 mutual friends with '%s'\n"%(user,f))
                file3.write("The suggested friends for '%s': %s\n"%(user,{", ".join(sorted(set.union(sf_set,sf_set2)))}))
                
            elif md == "3":
                sf_set3 = set([])
                if len(users_dict[user]) < 3 :
                    file3.write("'%s' hasn't 3 mutual friends.\n"%(user))
                    file3.write("There is no suggested friends for '%s'!\n"%(user))
                else:
                    file3.write("Suggestion List for '%s' (when MD is 3):\n"%(user))
                    for friend in users_dict.keys():
                        if len(set.intersection(users_dict[user],users_dict[friend])) == 3 and friend != user and friend!=users_dict[user]:
                            sf_set3.add(friend)            
                    for i in users_dict[user]:
                        for k in users_dict[i]:
                            for l in users_dict[k]:        
                                for q in users_dict[l]:
                                    liste.append(q)   
                      
                    for f in sorted(sf_set3):
                        if f in users_dict[user] or f == user or f not in liste:
                            sf_set3.remove(f)
                    liste = []
                    for f in sorted(sf_set3):
                        file3.write(" '%s' has 3 mutual friends with '%s'\n"%(user, f))
                    file3.write("The suggested friends for '%s': %s\n" %(user, {", ".join(sorted(sf_set3))}) )            
                    
    def main():
        for command in command_list:
            try:
                if command[0] == "ANU":
                    add_user(command[1])
                elif command[0] == "DEU":
                    delete_user(command[1])
                elif command[0] == "ANF":
                    add_friend(command[1],command[2])
                elif command[0] == "DEF":
                    delete_friend(command[1],command[2])
                elif command[0] == "CF":
                    count_friend(command[1])
                elif command[0] == "FPF":
                    find_friend(command[1],command[2])
                elif command[0] == "SF":
                    suggest_friend(command[1],command[2])
            except IndexError:
                file3.write("ERROR!Wrong command type.Try again.\n")
    main()
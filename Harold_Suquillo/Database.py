import mysql.connector
import datetime



# These set of GET methods acquire a valid input for the respective entry 
def GetSsn():
    Ssn = 0
    while(1):   # Minit (NULL - NO, char(9))
        try: 
            Ssn = input("Enter the Social Security number XXXXXXXXX: ")
            if (Ssn.isnumeric() == False):
                raise ValueError
            if (Ssn.upper() == "NULL"):
                raise NameError
            if (len(Ssn) != 9):
                raise IndexError
        except ValueError:
            print("Error: Ssn must be of type char(9) with all numerical values\n")
        except NameError:
            print("Error: Ssn cannot be NULL\n")
        except IndexError:
            print("Error: Ssn must be exactly 9 digits long\n")
        else:
            return Ssn
def GetSuperSsn():
    Super_ssn = ""
    while(1):   # Super_ssn (NULL - Yes, char(9))
        try: 
            Super_ssn = input("Enter the Supervisor Social Security number XXXXXXXXX: ")
            if (Super_ssn.isnumeric() == False):
                raise ValueError
            if (len(Super_ssn) != 9):
                raise IndexError
        except ValueError:
            print("Error: Super_ssn must be of type char(9) with all numerical values\n")
        except IndexError:
            print("Error: Super_ssn must be exactly 9 digits long\n")
        else:
            return Super_ssn

def GetDno():
    Dn = 0
    while(1):   # Dno (NULL - NO, int(11))
        try: 
            Dno = input("Enter the department number : ")
            if (Dno.isnumeric() == False):
                raise ValueError
            if (Dno.upper() == "NULL"):
                raise NameError
        except ValueError:
            print("Error: Dno must be of type int(11)\n")
        except NameError:
            print("Error: Dno cannot be NULL\n")
        else:
            return Dno

def GetFname():
    Fname = ""
    while(1):   # Fname (NULL - NO, varchar(15))
        try: 
            Fname = input("Enter the First Name: ")
            if (Fname.isnumeric() == True):
                raise ValueError
            if (Fname.upper() == "NULL"):
                raise NameError
            if (len(Fname) > 15):
                raise IndexError
        except ValueError:
            print("Error: Fname must be of type varchar(15)\n")
        except NameError:
            print("Error: Fname cannot be NULL\n")
        except IndexError:
            print("Error: Fname can only be 15 characters max length\n")
        else:
            return Fname

def GetMinit():
    Minit = ""
    while(1):   # Minit (NULL - YES, char(1))
        try: 
            Minit = input("Enter the Middle Initial: ")
            if (Minit.isnumeric() == True):
                raise ValueError
            if (len(Minit) > 1):
                raise IndexError
        except ValueError:
            print("Error: Minit must be of type char(1)\n")
        except IndexError:
            print("Error: Minit can only be 1 character max length\n")
        else:
            return Minit

def GetLname():
    Lname = ""
    while(1):   # Fname (NULL - NO, varchar(15))
        try: 
            Lname = input("Enter the Last Name: ")
            if (Lname.isnumeric() == True):
                raise ValueError
            if (Lname.upper() == "NULL"):
                raise NameError
            if (len(Lname) > 15):
                raise IndexError
        except ValueError:
            print("Error: Lname must be of type varchar(15)\n")
        except NameError:
            print("Error: Lname cannot be NULL\n")
        except IndexError:
            print("Error: Lname can only be 15 characters max length\n")
        else:
            return Lname
    
def ValidSsn():
    Ssn = ""
    FoundSsn = True
    while(FoundSsn):
        Ssn = GetSsn()
        Query = "select COUNT(Ssn) from EMPLOYEE WHERE Ssn = %s"
        mycursor.execute(Query, (Ssn,)) 
        result = mycursor.fetchall()
        for i in result:
            if (i[0] == 0):
                FoundSsn = False
            else:
                print("Error Primary Key Constraint Ssn must be unique\n")
    return Ssn



def GetBYear():
    BYear = ""
    while(1):   # BYear (NULL - YES, date)
        try: 
            BYear = input("Enter the Year XXXX: ")
            if (BYear.isalpha()):
                if (BYear.upper() == "NULL"):
                    break
            if (BYear.isnumeric() == False):
                raise ValueError
            if (len(BYear) != 4):
                raise IndexError
        except ValueError:
            print("Error: Year must be of type Year\n")
        except IndexError:
            print("Error: Year format is wrong (XXXX)\n")
        else:
            return BYear
    return BYear
    

def GetBMonth():
    BMonth = ""
    while(1):   # BMonth(NULL - YES, date)
        try: 
            BMonth  = input("Enter the Month: ")
            if (BMonth.isalpha()):
                if (BMonth.upper() == "NULL"):
                    break
            if (BMonth .isnumeric() == False):
                raise ValueError
            if (int(BMonth) > 12 or  int(BMonth) < 1):
                raise IndexError
        except ValueError:
            print("Error: Month must be of type Month\n")
        except IndexError:
            print("Error: Month can be between (1 - 12)\n")
        else:
            return BMonth
    return BMonth
    

def GetBDay():

    BDay = ""
    while(1):   # BMonth(NULL - YES, date)
        try: 
            BDay  = input("Enter the day X or XX: ")
            if (BDay.isalpha()):
                if (BDay.upper() == "NULL"):
                    break
            if (BDay .isnumeric() == False):
                raise ValueError
            if (int(BDay) > 31 or  int(BDay) < 1):
                raise IndexError
        except ValueError:
            print("Error: day must be of type day\n")
        except IndexError:
            print("Error: day can be between 1 - 31\n")
        else:
            return BDay
    return BDay
    



def GetBdate():
    Bdate = ""
    BYear = GetBYear()
    BMonth = GetBMonth()
    BDay = GetBDay()
    if (BDay.upper() == "NULL" or BMonth.upper() == "NULL" or BYear.upper() == "NULL"):
        Bdate = "NULL"
    else:
        Bdate = datetime.date(int(BYear), int(BMonth), int(BDay))
    return Bdate

def GetAddress():
    Address = ""
    while(1):   # Address (NULL - YES, varchar(30))
        try: 
            Address = input("Enter the Address: ")
            if (Address.isnumeric() == True):
                raise ValueError
            if (len(Address) > 30):
                raise IndexError
        except ValueError:
            print("Error: Address must be of type varchar(30)\n")
        except IndexError:
            print("Error: Address can only be 30 character max length\n")
        else:
            return Address

def GetSex():
    Sex = ""
    while(1):   # Minit (NULL - YES, char(1))
        try: 
            Sex = input("Enter the Sex (M/F): ")
            if (Sex.isnumeric() == True):
                raise ValueError
            if (len(Sex) > 1):
                raise IndexError
        except ValueError:
            print("Error: Sex must be of type char(1)\n")
        except IndexError:
            print("Error: Sex can only be 1 character M or F\n")
        else:
            return Sex

def GetSalary():
    Salary = 0
    while(1):   # Salary (NULL - YES, decimal(10,2))
        try: 
            Salary = input("Enter the salary: ")
            if (Salary.isalpha() == True):
                raise ValueError
        except ValueError:
            print("Error: Salary must be of type decimal(10,2)\n")
        else:
            return Salary

def SuperSsn():
    Super_ssn = ""
    foundSuperSsn = True
    while(foundSuperSsn):
        Super_ssn = GetSuperSsn()
        Query = "select COUNT(Ssn) from EMPLOYEE WHERE Ssn = %s"
        mycursor.execute(Query, (Super_ssn,)) 
        result = mycursor.fetchall()
        for i in result:
            if (i[0] == 1):
                foundSuperSsn = False
            else:
                print("Error Foreign Key Constraint Super_ssn must reference an existing ssn\n")    
    return Super_ssn



def ValidDno():
    Dno = 0
    Keeplooking = True
    while (Keeplooking):
        Dno = GetDno()
        Query = "select COUNT(Dnumber) from DEPARTMENT WHERE Dnumber = %s"
        mycursor.execute(Query, (Dno,)) 
        result = mycursor.fetchall()
        for i in result:
            if (i[0] == 1):
                Keeplooking = False
            else:
                print("Error Foreign Key Constraint Dno must reference an existing Department\n")
    return Dno


def AddNewEmployee(database):
    mycursor = database.cursor()

    # Input constraints on the input values
    Fname = GetFname()
    Minit = GetMinit()
    Lname = GetLname()
    Ssn = ValidSsn()          # Constraint 1: Ssn Must be unique
    Bdate = GetBdate()
    Address = GetAddress()
    Sex = GetSex()
    Salary = GetSalary()
    Super_ssn = SuperSsn()    # Constraint 2: Super_ssn must reference an existing Ssn entry
    Dno = ValidDno()          # Constrint 3: Dno must reference an existing Department 

    # Insert into the database and commit chaanges
    Insert_New_Employee = (
                    """INSERT INTO EMPLOYEE (Fname, Minit, Lname, Ssn, Bdate, Address, Sex, Salary, Super_ssn, Dno)
                    VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s)""")
                   
    mycursor.execute(Insert_New_Employee, (Fname, Minit, Lname, Ssn, Bdate, Address, Sex, Salary, Super_ssn, Dno))
    
    # Commit and close the connection
    database.commit()
    mycursor.close()



def ViewEmployee(database):
    # Employee info
    Employee_ssn = input("Enter an employee ssn: ")
    Query = "select * from EMPLOYEE WHERE Ssn = %s"
    mycursor.execute(Query, (Employee_ssn,)) 
    result = mycursor.fetchall()
    print("Employee Information\n----------------------")
    for (Fname, Minit, Lname, Ssn, Bdate, Address, Sex, Salary, Super_ssn, Dno) in result:
        # Employee Information
        print("\nFirst name: "+ Fname)
        print("Middle initial: " + Minit)
        print("Last name: "+ Lname)
        print("SSN:" + Ssn)
        print("Birth date: "+ str(Bdate))
        print("Address: "+ Address)
        print("Sex: "+ Sex)
        print("Salary: $" + str(Salary))
        print("Supervisor SSN: " +Super_ssn)
        print("Department number: "+ str(Dno))

        # Supervisor Name
        Query = "select Fname, Minit, Lname from EMPLOYEE WHERE Ssn = %s"
        mycursor.execute(Query, (Super_ssn,)) 
        result = mycursor.fetchall()
        print("\nSupervisor Name\n----------------------")
        for Fname, Minit, Lname in result:
            print("First name: " + Fname)
            print("Middle initial: " + Minit)
            print("Last name: "+ Lname)
        
        # Department Name
        Query = "select Dname from DEPARTMENT WHERE Dnumber = %s"
        mycursor.execute(Query, (Dno,)) 
        result = mycursor.fetchall()
        print("\nDepartment Name\n----------------------")
        for Dname in result:
            print("Department Name: "+Dname[0])
        
        # Dependents
        Query = "select * from DEPENDENT WHERE Essn = %s"
        mycursor.execute(Query, (Ssn,)) 
        result = mycursor.fetchall()
        print("\nDependents\n----------------------")
        for i in result:
            print(i)
        print()

    # Commit and close the connection
    database.commit()
    mycursor.close()

def ModifyMenu():
    print("Select an attribute to modify\n\t\t(1)--Fname\n\t\t(2)--Minit\n\t\t(3)--Lname\n\t\t(4)--Ssn\n\t\t(5)--Bdate\n\t\t"+
          "(6)--Address\n\t\t(7)--Sex\n\t\t(8)--Salary\n\t\t(9)--Super_ssn\n\t\t(10)-Dno\n")
    
def ModifyEmployee(database):
    mycursor = database.cursor()
    # Query the tuple and lock it
    Employee = input("Enter the employee ssn you want to modify: ")
    Query = ("SELECT * FROM EMPLOYEE WHERE Ssn = %s FOR UPDATE")
    mycursor.execute(Query, (Employee, )) 
    result = mycursor.fetchall()
    for i in result:
        print("\nProfile in Modification\n---------------------")
        print(i)
        print()

    ModifyMenu()
    option = 100
    while (1):                  # Input error handling
        try:
            option = int(input("Enter an option: "))
            if (option < 1 or option > 10):
                raise ValueError
        except ValueError:
            print("Try again and enter a valid number")
        else:
            break

    if (option == 1):   # Fname
        Fname = GetFname()
        Query = "UPDATE EMPLOYEE SET Fname = %s WHERE Ssn = %s"
        mycursor.execute(Query, (Fname, Employee, )) 
        

    if (option == 2):   # Minit
        Minit = GetMinit()
        Query = "UPDATE EMPLOYEE SET Minit = %s WHERE Ssn = %s"
        mycursor.execute(Query, (Minit, Employee, )) 
            
    if (option == 3):   # Lname
        Lname = GetLname()
        Query = "UPDATE EMPLOYEE SET Lname = %s WHERE Ssn = %s"
        mycursor.execute(Query, (Lname, Employee, )) 

    if (option == 4):   # Ssn
        Ssn = GetSsn()
        Query = "UPDATE EMPLOYEE SET Ssn = %s WHERE Ssn = %s"
        mycursor.execute(Query, (Ssn, Employee, )) 

    if (option == 5):   # Bdate
        print("Enter information about Birth day")
        Bdate = GetBdate()
        Query = "UPDATE EMPLOYEE SET Bdate = %s WHERE Ssn = %s"
        mycursor.execute(Query, (Bdate, Employee, )) 

    if (option == 6):   # Address
        Address = GetAddress()
        Query = "UPDATE EMPLOYEE SET Address = %s WHERE Ssn = %s"
        mycursor.execute(Query, (Address, Employee, )) 

    if (option == 7):   # Sex
        Sex = GetSex()
        Query = "UPDATE EMPLOYEE SET Sex = %s WHERE Ssn = %s"
        mycursor.execute(Query, (Sex, Employee, )) 

    if (option == 8):   # Salary
        Salary = GetSalary()
        Query = "UPDATE EMPLOYEE SET Salary = %s WHERE Ssn = %s"
        mycursor.execute(Query, (Salary, Employee, )) 

    if (option == 9):   # Super_ssn
        Super_ssn = SuperSsn()
        Query = "UPDATE EMPLOYEE SET Super_ssn  = %s WHERE Ssn = %s"
        mycursor.execute(Query, (Super_ssn, Employee, )) 

    if (option == 10):  # Dno
        Dno = ValidDno()
        Query = "UPDATE EMPLOYEE SET Dno  = %s WHERE Ssn = %s"
        mycursor.execute(Query, (Dno, Employee, )) 



    # Commit and close the connection
    database.commit()
    mycursor.close()

    

def AddNewDependent(database):
    mycursor = database.cursor()
    # Lock the employee record
    Employee = input("Enter the employee ssn you want to add a dependent to: ")
    Query = ("SELECT * FROM EMPLOYEE WHERE Ssn = %s FOR UPDATE")
    mycursor.execute(Query, (Employee, )) 
    result = mycursor.fetchall()
    for i in result:
        print("\nEmployee getting a new dependent\n---------------------")
        print(i)
        print()
    
    # Display all Dependents
    Query = ("SELECT * FROM DEPENDENT WHERE Essn = %s")
    mycursor.execute(Query, (Employee, )) 
    result = mycursor.fetchall()
    print("\nList of Dependents\n---------------------")
    for i in result:
        print(i)
    print()
    
    # Inputs for new dependent
    Essn = Employee
    Dependent_Name = GetFname()
    Sex = GetSex()
    Bdate = GetBdate()
    Relationship = input("Enter the relationship to the Employee: ")

    # Inert Dependent into database
    Insert_New_Dependent = (
                    """INSERT INTO DEPENDENT (Essn, Dependent_name, Sex, Bdate, Relationship)
                    VALUES (%s, %s, %s, %s, %s)""")
    mycursor.execute(Insert_New_Dependent, (Essn, Dependent_Name, Sex, Bdate, Relationship))

    Query = ("SELECT * FROM DEPENDENT WHERE Essn = %s")
    mycursor.execute(Query, (Employee, )) 
    result = mycursor.fetchall()
    print("\nList of Dependents\n---------------------")
    for i in result:
        print(i)
    print()
    database.commit()
    mycursor.close()




def RemoveDependent(database):
    mycursor = database.cursor()
    # Lock the employee record
    Employee = input("Enter the employee ssn you want to add a dependent to: ")
    Query = ("SELECT * FROM EMPLOYEE WHERE Ssn = %s FOR UPDATE")
    mycursor.execute(Query, (Employee, )) 
    result = mycursor.fetchall()
    for i in result:
        print("\nEmployee getting a new dependent\n---------------------")
        print(i)
        print()
    
    # Display all Dependents
    Query = ("SELECT * FROM DEPENDENT WHERE Essn = %s")
    mycursor.execute(Query, (Employee, )) 
    result = mycursor.fetchall()
    print("\nList of Dependents\n---------------------")
    for i in result:
        print(i)
    print()

    # Remove the dependent from the database
    Dependent_Name = GetFname()
    Query = "DELETE from DEPENDENT WHERE Dependent_name = %s"
    mycursor.execute(Query, (Dependent_Name, )) 
    database.commit()

    # Display all Dependents
    Query = ("SELECT * FROM DEPENDENT WHERE Essn = %s")
    mycursor.execute(Query, (Employee, )) 
    result = mycursor.fetchall()
    print("\nList of Dependents after modification\n----------------------------")
    for i in result:
        print(i)
    print()
    mycursor.close()


def AddNewDepartment(database):
    mycursor = database.cursor()
    # Constraint 1: Mgr_ssn must reference an employee ssn
        # Mgr_ssn cannot be null
    
    FoundSsn = True
    Mgr_ssn = ""
    while(FoundSsn):
        Mgr_ssn = input("Enter a Manager Ssn: ")
        if (Mgr_ssn.upper() == "NULL"):
            print("Mgr_ssn cannot be NULL")
            continue
        Query = "select COUNT(Ssn) from EMPLOYEE WHERE Ssn = %s"
        mycursor.execute(Query, (Mgr_ssn,)) 
        result = mycursor.fetchall()
        for i in result:
            if (i[0] == 1):
                FoundSsn = False
            else:
                print("Error: Foreign Key Constraint Mgr_ssn must reference an existing employe ssn\n")

    # Constraint 2: Dname ust be unique
        # Dname connot be NULL
    FoundSsn = True
    Dname = ""
    while(FoundSsn):
        Dname = input("Enter department Name: ")
        if (Dname.upper() == "NULL"):
            print("Department name cannot be NULL")
            continue
        Query = "select COUNT(Dname) from DEPARTMENT WHERE Dname = %s"
        mycursor.execute(Query, (Dname,)) 
        result = mycursor.fetchall()
        for i in result:
            if (i[0] == 0):
                FoundSsn = False
            else:
                print("Error: Unique Key Constraint Dname must be unique\n")

    # COnstraint 3: Dnumber has to be unique
        # Dnumber cannot be NULL
    FoundSsn = True
    Dnumber = ""
    while(FoundSsn):
        Dnumber = input("Enter department Number: ")
        if (Dnumber.upper() == "NULL"):
            print("Department number cannot be NULL\n")
            continue
        if (Dnumber.isalpha() == True):
            print("Department number has to be an integer\n")
            continue
        Query = "select COUNT(Dnumber) from DEPARTMENT WHERE Dnumber = %s"
        mycursor.execute(Query, (Dnumber,)) 
        result = mycursor.fetchall()
        for i in result:
            if (i[0] == 0):
                FoundSsn = False
            else:
                print("Error Unique Key Constraint Dnumber must be unique\n")

    
    # Mgr_start_date can be null
    print("Enter information about the manager start date")
    Mgr_start_date = GetBdate()

    # Insert department into the database
    Insert_New_Department = (
                    """INSERT INTO DEPARTMENT (Dname, Dnumber, Mgr_ssn, Mgr_start_date)
                    VALUES (%s, %s, %s, %s)""")
                   
    mycursor.execute(Insert_New_Department, (Dname, Dnumber, Mgr_ssn, Mgr_start_date))
    database.commit()


    # Display all Dependents
    Query = ("SELECT * FROM DEPARTMENT")
    mycursor.execute(Query) 
    result = mycursor.fetchall()
    print("\nList of Departments\n---------------------")
    for i in result:
        print(i)
    print()

def AddDepartmentLocation(database):
    mycursor = database.cursor()
    # Ask for  Dnumber
    Dnumber = input("Enter the department number: ")
    Query = ("SELECT * FROM DEPARTMENT WHERE Dnumber = %s FOR UPDATE")
    mycursor.execute(Query, (Dnumber, )) 
    result = mycursor.fetchall()

    # Show all locations
    Query = ("SELECT * FROM DEPT_LOCATIONS WHERE Dnumber = %s")
    mycursor.execute(Query, (Dnumber, )) 
    result = mycursor.fetchall()
    print("\nList of Department Locations\n---------------------")
    for i in result:
        print(i)
    print()

    # Ask for new location
    New_location = input("Enter a new location: ")
    # Create a new location record and insert into the database
    Insert_New_Dlocation = (
                    """INSERT INTO DEPT_LOCATIONS (Dnumber, Dlocation)
                    VALUES (%s, %s)""")
    mycursor.execute(Insert_New_Dlocation, (Dnumber, New_location))
    database.commit()


def RemoveDepartmentLocation(database):
    mycursor = database.cursor()
    # Ask for Dnumber and lock the record
    Dnumber = input("Enter the department number: ")
    Query = ("SELECT * FROM DEPARTMENT WHERE Dnumber = %s FOR UPDATE")
    mycursor.execute(Query, (Dnumber, )) 
    result = mycursor.fetchall()

    # Show all locations
    Query = ("SELECT * FROM DEPT_LOCATIONS WHERE Dnumber = %s")
    mycursor.execute(Query, (Dnumber, )) 
    result = mycursor.fetchall()
    print("\nList of Department Locations\n---------------------")
    for i in result:
        print(i)
    print()

    # Ask for the location to be removed
    Location = input("Enter the location to be deleted: ")

    # remove the location
    Query = "DELETE from DEPT_LOCATIONS WHERE Dlocation = %s AND Dnumber = %s"
    mycursor.execute(Query, (Location, Dnumber, )) 
    database.commit()


    print()
    # CLose the connection

def RemoveEmployee(database):
    mycursor = database.cursor()
    # Ask For Employee Ssn
    Ssn = input("Enter an employee SSN: ")

    # Lock and show employee information
    Query = ("SELECT * FROM EMPLOYEE WHERE Ssn = %s FOR UPDATE")
    mycursor.execute(Query, (Ssn, )) 
    result = mycursor.fetchall()
    print("\nEmployee entry for deletion\n---------------------")
    for i in result:
        print(i)
    print()

    # Ask confirmation to delete
    confirm = True
    while (confirm):
        Confirmation = input("\n1) Enter \"Yes\" if you wish to delete\n2) Enter \"No\" to cancel\n")
        if (Confirmation.isalpha() == True):
            if (Confirmation.upper() == "YES"):
                confirm = False
            elif (Confirmation.upper() == "NO"):
                return


    # Check if any dependencies exist
    print()
    Dependencies = False

    # Constraint 1: Foreign key Constraint EMPLOYEE Super_ssn references employee Ssn
    Query = "select COUNT(Super_ssn) from EMPLOYEE WHERE Super_ssn = %s"
    mycursor.execute(Query, (Ssn,)) 
    result = mycursor.fetchall()
    for i in result:
        if (i[0] != 0):
            print("Error: Foreign key Constraint, EMPLOYEE Super_ssn references employee ssn\n----------------------------------------------------")
            Dependencies = True
            # Print the dependencies
            Query = "select * from EMPLOYEE WHERE Super_ssn = %s"
            mycursor.execute(Query, (Ssn,)) 
            result = mycursor.fetchall()
            for i in result:
                print(i)
            print()

    # Constraint 2: Foreign Key Constraint, DEPENDENT Essn references employee SSN
    Query = "select COUNT(Essn) from DEPENDENT WHERE Essn = %s"
    mycursor.execute(Query, (Ssn,)) 
    result = mycursor.fetchall()
    for i in result:
        if (i[0] != 0):
            print("Error: Foreign key Constraint, DEPENDENT Essn references employee ssn\n----------------------------------------------------")
            Dependencies = True
            # Print the dependencies
            Query = "select * from DEPENDENT WHERE Essn = %s"
            mycursor.execute(Query, (Ssn,)) 
            result = mycursor.fetchall()
            for i in result:
                print(i)
            print()

    # Constraint 3: Foreign Key Constraint, DEPARTMENT Mgr_ssn references employee ssn
    Query = "select COUNT(Mgr_ssn) from DEPARTMENT WHERE Mgr_ssn = %s"
    mycursor.execute(Query, (Ssn,)) 
    result = mycursor.fetchall()
    for i in result:
        if (i[0] != 0):
            print("Error: Foreign Key Constraint, DEPARTMENT Mgr_ssn references employee ssn\n----------------------------------------------------")
            Dependencies = True
            # Print the dependencies
            Query = "select * from DEPARTMENT WHERE Mgr_ssn = %s"
            mycursor.execute(Query, (Ssn,)) 
            result = mycursor.fetchall()
            for i in result:
                print(i)
            print()


    # Constraint 4: Foreign Key Constraint, WORKS_ON Essn references employee SSN
    Query = "select COUNT(Essn) from WORKS_ON WHERE Essn = %s"
    mycursor.execute(Query, (Ssn,)) 
    result = mycursor.fetchall()
    for i in result:
        if (i[0] != 0):
            print("Error: Foreign Key Constraint, WORKS_ON Essn references employee ssn\n----------------------------------------------------")
            Dependencies = True
            # Print the dependencies
            Query = "select * from WORKS_ON WHERE Essn = %s"
            mycursor.execute(Query, (Ssn,)) 
            result = mycursor.fetchall()
            for i in result:
                print(i)
            print()
    
    # Delete if no dependencies, else ask them to remove the dependencies
    if (Dependencies == True):
        print("Dependencies exist, remove them first before deleting employee profile\n")
        database.commit()
        return
    else:
        print("No dependencies exist the profile will be deleted.")
        Query = "DELETE from EMPLOYEE WHERE Ssn = %s"
        mycursor.execute(Query, (Ssn, )) 
        database.commit()


def RemoveDepartment(database):
    mycursor = database.cursor()
    # Ask for the Dnumber
    department = input("Enter an Dnumber: ")

    # Lock and show department information
    Query = ("SELECT * FROM DEPARTMENT WHERE Dnumber = %s FOR UPDATE")
    mycursor.execute(Query, (department, )) 
    result = mycursor.fetchall()
    print("\nDepartment entry for deletion\n---------------------")
    for i in result:
        print(i)
    print()

    # Ask confirmation for deletion
    confirm = True
    while (confirm):
        Confirmation = input("\n1) Enter \"Yes\" if you wish to delete\n2) Enter \"No\" to cancel\n")
        if (Confirmation.isalpha() == True):
            if (Confirmation.upper() == "YES"):
                confirm = False
            elif (Confirmation.upper() == "NO"):
                print()
                return

    # Check if any dependencies exist
    print()
    Dependencies = False


    # Constraint 1: Foreign Key Constraint, DEPT_LOCATIONS Dnumber references department Dnumber
    Query = "select COUNT(Dnumber) from DEPT_LOCATIONS WHERE Dnumber = %s"
    mycursor.execute(Query, (department,)) 
    result = mycursor.fetchall()
    for i in result:
        if (i[0] != 0):
            print("Error: Foreign Key Constraint, DEPT_LOCATIONS Dnumber references department Dnumber\n----------------------------------------------------")
            Dependencies = True
            # Print the dependencies
            Query = "select * from DEPT_LOCATIONS WHERE Dnumber = %s"
            mycursor.execute(Query, (department,)) 
            result = mycursor.fetchall()
            for i in result:
                print(i)
            print()



    # Constraint 2: Foreign Key Constraint, EMPLOYEE Dno references department Dnumber
    Query = "select COUNT(Dno) from EMPLOYEE WHERE Dno = %s"
    mycursor.execute(Query, (department,)) 
    result = mycursor.fetchall()
    for i in result:
        if (i[0] != 0):
            print("Error: Foreign Key Constraint, EMPLOYEE Dno references department Dnumber\n----------------------------------------------------")
            Dependencies = True
            # Print the dependencies
            Query = "select * from EMPLOYEE WHERE Dno= %s"
            mycursor.execute(Query, (department,)) 
            result = mycursor.fetchall()
            for i in result:
                print(i)
            print()


    # Constraint 3: Foreign Key Constraint, PROJECT Dnum references department DNumber
    Query = "select COUNT(Dnum) from PROJECT WHERE Dnum = %s"
    mycursor.execute(Query, (department,)) 
    result = mycursor.fetchall()
    for i in result:
        if (i[0] != 0):
            print("Error: Foreign Key Constraint, PROJECT Dnum references department DNumber\n----------------------------------------------------")
            Dependencies = True
            # Print the dependencies
            Query = "select * from PROJECT WHERE Dnum= %s"
            mycursor.execute(Query, (department,)) 
            result = mycursor.fetchall()
            for i in result:
                print(i)
            print()


    # Delete if no dependencies, else ask them to remove the dependencies
    if (Dependencies == True):
        print("Dependencies exist, remove them first before deleting department")
        database.commit()
        return
    else:
        print("No dependencies exist the department will be deleted.")
        Query = "DELETE from DEPARTMENT WHERE  Dnumber= %s"
        mycursor.execute(Query, (department, )) 
        database.commit()

def ViewDepartment(database):  
    mycursor = database.cursor()
    # input the depratment number
    Dnumber = input("Enter the Department number: ")
    print()

    # Show the managers name
    Query = ("SELECT Fname, Minit, Lname FROM DEPARTMENT, EMPLOYEE WHERE Dnumber = %s AND Mgr_ssn = Ssn")
    mycursor.execute(Query, (Dnumber, )) 
    result = mycursor.fetchall()
    print("Department Manager\n---------------------")
    for Fname, Minit, Lname in result:
        print("First name: "+ Fname)
        print("Middle intial: "+ Minit)
        print("last name: "+ Lname)
    print()



    # Display all the locations
    print("All locations\n--------------")
    Query = ("SELECT Dlocation FROM DEPARTMENT, DEPT_LOCATIONS WHERE DEPARTMENT.Dnumber = %s AND DEPARTMENT.Dnumber = DEPT_LOCATIONS.Dnumber")
    mycursor.execute(Query, (Dnumber, )) 
    result = mycursor.fetchall()
    for i in result:
        print(i[0])
    print()




def Menu(database):
    # when selecting an option type the number corresponding to that option
    ExitProgram = 0
    Menu = ("Select an option:\n\t\t(1)--Add new employee\n\t\t(2)--View employee\n\t\t(3)--Modify employee\n\t\t(4)--Remove employee" +
            "\n\t\t(5)--Add new dependent\n\t\t(6)--Remove dependent\n\t\t(7)--Add new department\n\t\t(8)--View department" +
            "\n\t\t(9)--Remove department\n\t\t(10)-Add department location\n\t\t(11)-Remove department location\n\t\t(12)-Exit program")  


    while (ExitProgram == 0):       
        print(Menu)
        option = 100
        while (1):                  # Input error handling
            try:
                option = int(input("Enter an option: "))
                if (option < 1 or option > 12):
                    raise ValueError
            except ValueError:
                print("Try again and enter a valid number")
            else:
                break

        if (option == 1):           # Option handling
            AddNewEmployee(database)
        elif (option == 2):
                ViewEmployee(database)
        elif (option == 3):
                ModifyEmployee(database)
        elif (option == 4):
                RemoveEmployee(database)
        elif (option == 5):
                AddNewDependent(database)
        elif (option == 6):
                RemoveDependent(database) 
        elif (option == 7):
                AddNewDepartment(database)
        elif (option == 8):
                ViewDepartment(database)
        elif (option == 9):
                RemoveDepartment(database)
        elif (option == 10):
                AddDepartmentLocation(database)
        elif (option == 11):
                RemoveDepartmentLocation(database)
        elif (option == 12):
                ExitProgram = 1
                database.close()


if __name__ == "__main__":

	# password has been removed 
    mydb = mysql.connector.connect(user='root', password='', host='localhost', database='COMPANY')      

    mycursor = mydb.cursor()
    Menu(mydb)
 
    mydb.close()
    mycursor.close()







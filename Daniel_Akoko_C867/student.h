#pragma once
#include <string>
#include "degree.h"

using namespace std;

class Student {
private:
    string studentID;
    string firstName;
    string lastName;
    string emailAddress;
    int age;
    int daysInCourse[3];
    DegreeProgram degreeProgram;

public:
    Student(); // Constructor
  
    // Accessors (getters)
    string getStudentID();
    string getFirstName();
    string getLastName();
    string getEmailAddress();
    int getAge();
    int* getDaysInCourse();
    DegreeProgram getDegreeProgram();

    // Mutators (setters)
    void setStudentID(string studentID);
    void setFirstName(string firstName);
    void setLastName(string lastName);
    void setEmailAddress(string emailAddress);
    void setAge(int age);
    void setDaysInCourse(int daysInCourse1, int daysInCourse2, int daysInCourse3);
    void setDegreeProgram(DegreeProgram degreeProgram);
    
    void print();  // Print student data
    
    Student(string newStudentID,
            string newFirstName,
            string newLastName,
            string newEmailAddress,
            int newAge,
            int* newDaysInCourse,
            DegreeProgram degreeProgram);
    
    ~Student(); //Destructor
};

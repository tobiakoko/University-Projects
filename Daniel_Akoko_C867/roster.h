#pragma once
#include "student.h"

class Roster {
public:
    const static int rosterSize = 5;
    Student* classRosterArray[rosterSize]; //pointer array for roster of students
    int lastIndex = -1;


public:
    Roster();
    ~Roster();  // Destructor
    void parse(string row);
    void add(string studentID, string firstName, string lastName, string emailAddress, int age, int daysInCourse1,
             int daysInCourse2, int daysInCourse3, DegreeProgram degreeProgram);
    void remove(string studentID);  // Remove student from classRosterArray by student ID
    void printAll();    // Print all student data
    void printAverageDaysInCourse(string studentID);    // Print average number of days in course for a specific student
    void printInvalidEmails();   // Print invalid email addresses
    void printByDegreeProgram(DegreeProgram degreeProgram); // Print student data for a specific degree program
};

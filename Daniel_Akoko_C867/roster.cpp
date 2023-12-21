#include <iostream>
#include <string>

#include "student.h"
#include "roster.h"

using namespace std;

Roster::Roster() {}

void Roster::parse(string studentData){
    
    DegreeProgram degreeProgram = SECURITY; //default value
    if (studentData.back() == 'K') degreeProgram = NETWORK;
    else if (studentData.back() == 'E') degreeProgram = SOFTWARE;

    int rhs = studentData.find(",");   //parsing student data
    string studentID = studentData.substr(0, rhs);

    int lhs = rhs + 1;
    rhs = studentData.find(",", lhs);
    string firstName = studentData.substr(lhs, rhs - lhs);

    lhs = rhs + 1;
    rhs = studentData.find(",", lhs);
    string lastName = studentData.substr(lhs, rhs - lhs);

    lhs = rhs + 1;
    rhs = studentData.find(",", lhs);
    string emailAddress = studentData.substr(lhs, rhs - lhs);
    
    lhs = rhs + 1;
    rhs = studentData.find(",", lhs);
    int age = stoi(studentData.substr(lhs, rhs - lhs));
    
    lhs = rhs + 1;
    rhs = studentData.find(",", lhs);
    int daysInCourse1 = stoi(studentData.substr(lhs, rhs - lhs));
                 

    lhs = rhs + 1;
    rhs = studentData.find(",", lhs);
    int daysInCourse2 = stoi(studentData.substr(lhs, rhs - lhs));

    lhs = rhs + 1;
    rhs = studentData.find(",", lhs);
    int daysInCourse3 = stoi(studentData.substr(lhs, rhs - lhs));


    add(studentID, firstName, lastName, emailAddress, age, daysInCourse1, daysInCourse2, daysInCourse3, degreeProgram); //add student object

 }

// Add student to classRosterArray
void Roster::add(string studentID, string firstName, string lastName, string emailAddress, int age, int daysInCourse1,
                 int daysInCourse2, int daysInCourse3, DegreeProgram degreeProgram) {
    
    int daysInCourse[3] = { daysInCourse1, daysInCourse2, daysInCourse3 };

    classRosterArray[++lastIndex] = new Student(studentID, firstName, lastName, emailAddress, age, daysInCourse, degreeProgram);
}

// Remove student from classRosterArray by student ID
void Roster::remove(string studentID) {
    
    bool found = false;
    for (int i = 0; i < Roster::lastIndex; i++) {
        if (classRosterArray[i]->getStudentID() == studentID) {
            found = true;
            if (i < rosterSize -1){
                Student* temp = classRosterArray[i];
                classRosterArray[i] = classRosterArray[rosterSize - 1];
                classRosterArray[rosterSize - 1] = temp;
            }
            Roster::lastIndex--;
            
        }
    }

    if (!found){
        cerr <<'\n' <<"The student with the ID: " << studentID << " was not found." << endl;
    }else {
    }
}

// Print all student data
void Roster::printAll() {
    for (int i = 0; i <= Roster::lastIndex; i++)
    {
        cout << classRosterArray[i]->getStudentID(); cout << '\t';
        cout << classRosterArray[i]->getFirstName(); cout << '\t';
        cout << classRosterArray[i]->getLastName(); cout << '\t';
        cout << classRosterArray[i]->getAge(); cout << '\t' << '\t';
        cout << "{" << classRosterArray[i]->getDaysInCourse()[0]; cout << ",";
        cout << classRosterArray[i]->getDaysInCourse()[1]; cout << ",";
        cout << classRosterArray[i]->getDaysInCourse()[2]; cout << "}" << '\t';
        cout << degreeProgramStrings[classRosterArray[i]->getDegreeProgram()] << std::endl;
    }
}


// Print invalid email addresses
void Roster::printInvalidEmails() {
    bool found = false;

for (int i = 0; i <= Roster::lastIndex; i++)
{
    string EmailAddress = (classRosterArray[i]->getEmailAddress());
    if (EmailAddress.find("@") == string::npos || (EmailAddress.find('.') == string::npos) || (EmailAddress.find(' ') != string::npos))
    {
        found = true;
        cout << EmailAddress << " - is invalid." << std::endl;

    }
}
if (!found) cout << "NONE" << std::endl;


}

// Print average number of days in course for a specific student
void Roster::printAverageDaysInCourse(string studentID) {
    
    for (int i = 0; i <= Roster::lastIndex; i++)
    {
        if (classRosterArray[i]->getStudentID()==studentID)
        {
            cout << " Student ID: ";
            cout << studentID ;
            cout << ", average days in course is: ";
            cout << (classRosterArray[i]->getDaysInCourse()[0] +
                classRosterArray[i]->getDaysInCourse()[1] +
                classRosterArray[i]->getDaysInCourse()[2])/3;
        }

    }cout << endl;
}

// Print student data for a specific degree program
void Roster::printByDegreeProgram(DegreeProgram degreeProgram) {
    
    for (int i = 0; i <= Roster::lastIndex; i++) {
        if (Roster::classRosterArray[i]->getDegreeProgram() == degreeProgram) classRosterArray[i]->print();
    }
    cout << std::endl;
    
}

Roster::~Roster() {
    
    for (int i = 0; i < rosterSize; i++) {
        delete classRosterArray[i];
        classRosterArray[i] = nullptr;
        }
    }

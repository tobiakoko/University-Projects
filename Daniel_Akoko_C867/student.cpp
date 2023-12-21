#include <iostream>
#include <string>

#include "student.h"

using namespace std;

Student::Student() {
    this->studentID = "";
    this->firstName = "";
    this->lastName = "";
    this->emailAddress = "";
    this->age =0;
    int daysInCourse[3];
    this->degreeProgram = DegreeProgram::SOFTWARE;
    
}


Student::Student(string newStudentID,
                 string newFirstName,
                 string newLastName,
                 string newEmailAddress,
                 int newAge,
                 int* newDaysInCourse,
                 DegreeProgram degreeProgram) {
    
    studentID = newStudentID;
    firstName = newFirstName;
    lastName = newLastName;
    emailAddress = newEmailAddress;
    age = newAge;
    daysInCourse[0] = newDaysInCourse[0];
    daysInCourse[1] = newDaysInCourse[1];
    daysInCourse[2] = newDaysInCourse[2];
    this->degreeProgram = degreeProgram;
}

string Student::getStudentID()
{
    return studentID;
}

void Student::setStudentID(string newStudentID)
{
    this->studentID = newStudentID;
}

string Student::getFirstName()
{
    return firstName;
}

void Student::setFirstName(string newFirstName)
{
    this->firstName = newFirstName;
}

string Student::getLastName()
{
    return lastName;
}

void Student::setLastName(string newLastName)
{
    this->lastName = newLastName;
}

string Student::getEmailAddress()
{
    return emailAddress;
}

void Student::setEmailAddress(string newEmailAddress)
{
    this->emailAddress = newEmailAddress;
}

int Student::getAge()
{
    return age;
}

void Student::setAge(int newAge)
{
    this->age = newAge;
}

int* Student::getDaysInCourse()
{
    return daysInCourse;
}

void Student::setDaysInCourse(int newDaysInCourse1, int newDaysInCourse2, int newDaysInCourse3)
{
    this->daysInCourse[0] = newDaysInCourse1;
    this->daysInCourse[1] = newDaysInCourse2;
    this->daysInCourse[2] = newDaysInCourse3;

}

void Student::setDegreeProgram(DegreeProgram degreeProgram)
{
    this->degreeProgram = degreeProgram;
}

DegreeProgram Student::getDegreeProgram()
{
    return this->degreeProgram;
}

void Student::print(){  // Print student data
    
    cout << this->getStudentID();
    cout << '\t'<< this->getFirstName();
    cout << '\t' << this->getLastName();
    cout << '\t' << this->getAge();
    cout << '\t' << "{"; cout << this->getDaysInCourse()[0] << "," << this->getDaysInCourse()[1] << "," << this->getDaysInCourse()[2] << "}";
    cout << '\t' << degreeProgramStrings[this->getDegreeProgram()] << endl;
}

Student::~Student() {}

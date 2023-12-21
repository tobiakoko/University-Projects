#include <iostream>
#include <string>

#include "roster.h"

using namespace std;


int main() {
    cout << "C867 - Scripting & Programming: Applications" <<endl;
    cout << "Language: C++" <<endl;
    cout << "Student ID: #011093660" <<endl;
    cout << "Name: Daniel Akoko" <<endl;
    
    
    
    const string studentData[5] = {
        "A1,John,Smith,John1989@gm ail.com,20,30,35,40,SECURITY",
        "A2,Suzan,Erickson,Erickson_1990@gmailcom,19,50,30,40,NETWORK",
        "A3,Jack,Napoli,The_lawyer99yahoo.com,19,20,40,33,SOFTWARE",
        "A4,Erin,Black,Erin.black@comcast.net,22,50,58,40,SECURITY",
        "A5,Daniel,Akoko,dakoko@wgu.edu,25,30,28,14,SOFTWARE" };
    
    const int rosterSize = 5;
    
    Roster classRoster; //Instance of the Roster Class

    
    for (int i = 0; i < rosterSize; i++) classRoster.parse(studentData[i]);

    
    cout << '\n' <<"Displaying all students: " << std::endl;
    classRoster.printAll();
    cout << std::endl;

    cout << "Displaying invalid emails:" << endl;
    cout << "\n";
    classRoster.printInvalidEmails();
    cout << std::endl;

    
    cout << "\n";
    for (int i = 0; i < rosterSize; i++)
    {
        classRoster.printAverageDaysInCourse(classRoster.classRosterArray[i]->getStudentID());

    }
    
    cout << "\n";
    cout << "Showing students in degree program: " << degreeProgramStrings[2] << std::endl;
    cout << "\n";
    classRoster.printByDegreeProgram(SOFTWARE);
    
    cout << std::endl;
    

    cout << "Removing A3: " << std::endl;
    classRoster.remove("A3");
    cout << std::endl;
    
    classRoster.printAll();
    
    cout <<'\n'<<"Removing A3 again" << std::endl;
    classRoster.remove("A3");
    cout << "DONE." << '\n' <<endl;

    classRoster.~Roster();
}

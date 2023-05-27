#include <iostream>
using namespace std;
int sizeList = 0;
struct Node {
    int valuation;
    Node *next;
    Node(int value) : valuation(value), next(nullptr){
    }
};
struct List {
    Node *first;
    Node *last;
    List() : first(nullptr), last(nullptr) {}

    bool emptyStatus() {
        return first == nullptr;
    }

    void pushBack(int valuation) {
        Node *current = new Node(valuation);

        if (emptyStatus()) {
            first = current;
            last = current;
            return;
        }

        last -> next = current;
        last = current;
    }

    void printList(){
        if (emptyStatus())
            return;
        Node *current = first;

        for(int i = 0; i < sizeList; i++){
            cout << current -> valuation << " ";
            current = current -> next;
        }
        cout << endl;
    }

    double popLast(){
        Node* newLastElement = first;
        double lastValue = last -> valuation;

        while(newLastElement -> next != last) {
            newLastElement = newLastElement->next;
        }
        newLastElement -> next = nullptr;
        delete last;
        last = newLastElement;
        sizeList -= 1;

        return lastValue;
    }

    void swapElements()
    {
        Node *current = first;
        double firstElement = popLast();
        double secondElement = popLast();
        while (current -> valuation != firstElement or current -> valuation != secondElement)
             current = current -> next;
        current = current -> next;
        if (current -> valuation == firstElement)
            current -> valuation = secondElement;
        else
            current -> valuation = firstElement;
        current = current -> next;
        while (current -> valuation != firstElement or current -> valuation != secondElement)
            current = current -> next;
        if (current -> valuation == firstElement)
            current -> valuation = secondElement;
        else
            current -> valuation = firstElement;
    }
};
int main() {
    List list;
    int a;

    while (cin >> a) {
        list.pushBack(a);
        sizeList += 1;
    }

    list.swapElements();
    list.printList();

    return 0;
}

//Основу списка подглядел на Хабре
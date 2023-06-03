// C++ program for implementation of selection sort 
#include <bits/stdc++.h>
using namespace std;
  
void swap(int *xp, int *yp) 
{ 
    int temp = *xp; 
    *xp = *yp; 
    *yp = temp; 
}
  
void selectionSort(int arr[], int n) 
{ 
    int i, j, min_idx; 
  
    // One by one move boundary of unsorted subarray 
    for (i = 0; i < n-1; i++) 
    { 
        // Find the minimum element in unsorted array 
        min_idx = i; 
        for (j = i+1; j < n; j++) 
        if (arr[j] < arr[min_idx]) 
            min_idx = j; 
  
        // Swap the found minimum element with the first element 
        swap(&arr[min_idx], &arr[i]); 
    } 
} 
  
int median(int arr[], int n) 
{   
    int median;
    if(n % 2 != 0) median = (n+1)/2;
    else median = n/2;
    for (int i = 0; i < n; i++)
    {
        int count = 0;
        for (int j = 0; j < n; j++)
        {
            if(j != i && arr[j] < arr[i]) count ++;
        }
        
        if(count == median) return arr[i];
    }

    return -1;
} 
  
/* Function to print an array */
void printArray(int arr[], int size) 
{ 
    int i; 
    for (i=0; i < size; i++) 
        cout << arr[i] << " "; 
    cout << endl; 
} 

int minValue(int arr[], int n){
    int min = arr[0];
    for (int i = 0; i < n; i++)
    {
        if(arr[i] < min) min = arr[i];
    }

    return min;
}

bool p1(int arr[], int value, int n){
    for (int i = 0; i < n; i++)
    {
        for (int j = 0; j < n; j++)
        {
            if(i != j && arr[i] + arr[j] == value){
                cout << endl << arr[j] <<" " << arr[i] << endl;
                return true;
            }
        }
        
    }

    return false;
    
}
// Driver program to test above functions 
int main() 
{ 
    int arr[] = {64, 12, 22, 1,15,105,19}; 
    p1(arr,34,7);
    return 0; 
} 

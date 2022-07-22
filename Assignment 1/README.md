## Assignment 1

Topics: arrays, Java Linked List, storing Java objects, strings

The class defines a memory array and a linked list of objects. These objects define the intervals in the array where the strings are stored. The class has various methods for putting, getting, and removing a string to/from the memory.

The memory consists of an array of characters, and so each string will be stored in an interval of positions in that array. The goal is to put strings into the memory array, get strings from the memory array, or remove strings from the memory array. In doing so, we need to keep track of which strings are
stored in memory (each string will have an id) and where the strings are. We will use a linked list for this bookkeeping task.

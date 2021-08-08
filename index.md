## Systematic Bit-Flip Fault Injection and Exploration using Java PathFinder

This is a Google Summber of Code ([GSoC](https://summerofcode.withgoogle.com)) project on extending the Java PathFinder project ([JPF](https://github.com/javapathfinder/jpf-core)).

### Project Overview

Computer hardware is susceptible to errors. For example, radiation may induce error to the hardware and some bit might be flipped. It is important to improve the resiliency of software against hardware errors. One way to evaluate the resiliency of software against hardware errors is by fault injection. The project uses Java PathFinder to systematically inject and explore bit-flip faults in the user specified variables in Java programs. The project supports injecting bit flips to (1) static and instance fields, (2) method arguments, and (3) local variables, whose type can be all [primitive data types](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html). The number of bits to flip in a variable is also configurable. The users can use three different ways to specify the variables: (1) directly calling the `getBitFlip` API in the application code, (2) adding `@BitFlip` annotation to the variables, and (3) specifying in the command line arguments without changing the application code.

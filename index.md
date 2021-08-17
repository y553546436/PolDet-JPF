## Systematic Bit-Flip Fault Injection and Exploration using Java PathFinder

This is a Google Summber of Code ([GSoC](https://summerofcode.withgoogle.com)) project on extending the Java PathFinder project ([JPF](https://github.com/javapathfinder/jpf-core)). The project is done by Pu (Luke) Yi, under the supervison of Profs. Cyrille Artho and Pavel Parízek.
The project code is publicly available at [this PR](https://github.com/javapathfinder/jpf-core/pull/295).

### Project Overview

The project uses Java PathFinder to systematically inject and explore bit-flip faults in the user specified variables in Java programs. The project supports injecting bit flips to (1) static and instance fields, (2) method arguments, and (3) local variables, whose type can be all [primitive data types](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html). The number of bits to flip in a variable is also configurable. The users can use three different ways to specify the variables: (1) directly calling the `getBitFlip` API in the application code, (2) adding `@BitFlip` annotation to the variables, and (3) specifying in the command line arguments without changing the application code.

### Detailed Information

#### Implementation

1. `getBitFlip` API ([code](https://github.com/y553546436/jpf-core/blob/FaultInjection/src/main/gov/nasa/jpf/vm/JPF_gov_nasa_jpf_vm_Verify.java#L468)). The users can invoke `getBitFlip(v, k)` in the program to get the value of `v` with `k` bits flipped, and the JPF will explore all $\binom{n}{k}$ possible flipping positions, where $n$ is the length of variable `v`.

   To provide such an API for all primitive data types, we first implement for `long` type, and for the other types, we first cast them to `long`, perform the bit flip, and then cast them back. The key challenge is to register only one choice generator even when `k>0`, because it confuses the JPF to register several choice generators at the same application code point. We resolve this issue by registering only one `IntIntervalGenerator` that produces an integer in range $[0,\binom{n}{k})$, and then decode the integer using binomial coefficients to get a set of $k$ bits to flip ([code](https://github.com/y553546436/jpf-core/blob/FaultInjection/src/main/gov/nasa/jpf/vm/JPF_gov_nasa_jpf_vm_Verify.java#L492)).

2. `@BitFlip` Annotation ([annotation class](https://github.com/y553546436/jpf-core/blob/FaultInjection/src/annotations/gov/nasa/jpf/annotation/BitFlip.java)). The users can add `@BitFlip(k)` annotations to fields, parameters, or local variables in the code, where `k` is the number of bits to flip for the variable and by default 1. For the annotated parameters, the JPF will inject the bit flip when the method is invoked. For the annotated fields and local variables, the JPF will inject the bit flip when they are written.

   The implementation is based on the JPF listener and modifying the operand stack. We implemented the [BitFlipListener](https://github.com/y553546436/jpf-core/blob/FaultInjection/src/main/gov/nasa/jpf/listener/BitFlipListener.java) which before a store instruction is executed, checks the annotations of the target fields ([code](https://github.com/y553546436/jpf-core/blob/FaultInjection/src/main/gov/nasa/jpf/listener/BitFlipListener.java#L304)) and local variables ([code](https://github.com/y553546436/jpf-core/blob/FaultInjection/src/main/gov/nasa/jpf/listener/BitFlipListener.java#L328)), and before an invocation instruction is executed, checks the parameter annotations ([code](https://github.com/y553546436/jpf-core/blob/FaultInjection/src/main/gov/nasa/jpf/listener/BitFlipListener.java#L269)). If the `@BitFlip` annotation is identified, the `BitFlipListener` registers a Choice Generator to the current system state in a similar way we do for `getBitFlip` API, and re-execute the instruction to start the exploration. To inject bit flips to the variables, we inject bit flips to the values in the operand stack that are to be assigned to them.

3. Command Line argument support. If the users do not want to change the source code, they can use command line arguments (or alternatively the configuration file) to specify the variables to inject bit flips.

   The constructor method of the `BitFlipListener` reads the command line arguments and adds the specified variables to a watch list ([code](https://github.com/y553546436/jpf-core/blob/FaultInjection/src/main/gov/nasa/jpf/listener/BitFlipListener.java#L122)). Then before a store or invocation instruction is executed, the `BitFlipListener` checks the watch list besides the annotations. As a common practice, if the `@BitFlip` annotation and the command line specification are both present for a variable, we let the command line arguments surpress the annotations.

#### Testing and Examples

1. JPF tests. We write a JPF test class [BitFlipTest](https://github.com/y553546436/jpf-core/blob/FaultInjection/src/tests/gov/nasa/jpf/test/mc/data/BitFlipTest.java), which contains 16 regression tests that checks the bit flip injection mechanism and also documents the basic usage.
2. We also created a [repository](https://github.com/y553546436/Fault-Injection-Examples) to show the usage of our tool for Cyclic redundancy check ([CRC](https://en.wikipedia.org/wiki/Cyclic_redundancy_check)) and International Standard Book Number ([ISBN](https://en.wikipedia.org/wiki/International_Standard_Book_Number)) algorithms. You can use our tool to see how resilient these algorithms are against bit flip fault.

#### Future Plan

It would be worthwhile to try out Symbolic PathFinder ([SPF](https://github.com/SymbolicPathFinder/jpf-symbc)) to see how symbolic execution can help speed up the exploration of all possible bit flips.

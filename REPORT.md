# Report

## Project

Name: Apache Commons Collections

URL: https://github.com/apache/commons-collections

The Apache Commons Collections package contains types that extend and augment the Java Collections Framework.

## Onboarding experience

The overall onboarding experience of Apache Commons Collections was very smooth, and we decided to continue with the project. As described in the README, The only required tools were a Java JDK and Maven, which we had already installed. The other dependencies were automatically installed by Maven. The build compiled without errors and the tests ran successfully. There were a small number of warnings but these could safely be ignored.

## Complexity

We used Lizard as our tool to automatically measure the complexity of the project. We chose to focus on four large functions: `TreeBidiMap::swapPosition`, `Snake::getMiddleSnake`, `Flat3Map::equals`, and `AbstractPatriciaTrie::nextEntryImpl`.

The results of the automatic and manual CC counts are given in the table below. For every function, the manual count matched the count by the Lizard tool. The operators that resulted in new branches were clear overall.

| Function | Automatic CC | Manual CC |
|----------|----------|----------|
| `swapPosition` | 17 | 17 |
| `nextEntryImpl` | 17 | 17 |
|`getMiddleSnake`| 27 | 27 |
|`equals`| 15 | 15 |

In general, high CC is correlated with a high LOC, as all complex functions chosen were also among the longest. However, this correlation is not particularly strong, and there is a large variation as seen in the table below. There are many functions with a large number of non-branching lines of code, as well as many functions with numerous short, simple branches.

| Function | CC | LOC | LOC : CC ratio |
|----------|----------|----------|----------|
| `swapPosition` | 17 | 72 | 1.8 |
| `nextEntryImpl` | 17 | 47 | 2.8 |
|`getMiddleSnake`| 27 | 49 | 4.2 |
|`equals`| 15 | 59 | 4.0 |

The purpose of all the functions is related to their high CC, as seen in the table below.

| Function | Purpose                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
|----------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `swapPosition` | The function swaps two nodes in a `TreeBidiMap`. It reassigns the parents and left/right children of the nodes, and takes care of edge cases where one is the parent of the other or the root of the tree. It has a high CC because of many edge cases and `null` checks. Swapping the nodes structurally rather than swapping the contents is necessary because each node participates in two trees, one sorted by key and one by value. The function is used during node deletion to rearrange the nodes after one is removed.                                                                                                                                                                                                                                                                                            |
| `nextEntryImpl` | This function scans for the lexicographically next node in the tree, taking into account the previous node, using a recursive approach. It tests for every possible edge case, which is the reason it has high CC.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          |
|`getMiddleSnake`| The function gets the middle snake corresponding to two subsequences of the main sequences. It has high CC because it has nested loops, covers both forward and back passes, and has to handle edge cases.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
|`equals`| The function compares this `Flat3Map` with another object to determine equality. It implements the `Map` interface's equals contract by first handling fast-path cases, returning true immediately if comparing the same object reference or delegating to the internal `HashMap` when the map has grown beyond three entries. For objects of a different type, it returns false. The core logic verifies that both maps have identical sizes, then performs element-by-element comparison in flat mode. For maps with 1-3 entries, it uses an optimized switch statement with fall-through cases to verify that the other map contains all the same key-value pairs. This design prioritizes performance for small maps while maintaining correctness for the equals contract. It has high CC because of these many cases. | 

Lizard does not count all exceptions as separate branches. This is reasonable because an exception thrown directly in the method is always guaranteed to be thrown, and therefore does not result in a new branch. This is the case for `getMiddleSnake`. A checked exception within a called function could result in a new branch, but this is not present in any of the functions.

The documentation is generally appropriate and sufficient within all functions. For more involved algorithms, there are either extensive comments or an external link to a detailed explanation. For functions that only have high CC because of a lot of simple checks, the documentation is more minimal but the functions are still not too difficult to understand.

## Refactoring

TODO

## Coverage

### External tool

The tool we used was IntelliJ IDEA’s built-in code coverage for Java plugin. This tool is well integrated into the IDE and can be run in one click. The IDE then highlights the lines covered in each file and can generate a HTML report. It was not always obvious where to find certain options, since the IDE is so complex, but the overall experience was relatively smooth.

### Internal tool

Our own tool is very simple. Before any tests are run, an array of counters is initialized. In the method being tested, each branch manually increments the counter at the index corresponding to the number assigned to the branch. When all tests are complete, the contents of the array are printed to the console, showing how many times the branch was executed. The tool can be used for any kind of branching where a line of code can be manually added.

To view the implementation:
```
git checkout coverage-measurement
git diff ba48e2013f589f86b506a312093d12128160e2b6 2c916245cbe75df875ffc976a2dc5e854509ff3d
```

The coverage measurement is reasonably accurate as it records the number of times every branch point was executed where it is possible to add an additional line of code to increment the counter. Some constructs do not support multiple lines of code, such as in-line conditionals or logical operators. These need to be manually converted to if-else statements for accurate measurement. Otherwise, the results are identical to the external tool. However, it is not particularly scalable because the functions themselves need to be changed, and the output is not user friendly.

### Improvement

TODO

## Way of working

Our way of working feels stable and well-established, and we have progressed from principles established on the Essence checklist to working well.

The team has established the principles, constraints, and approaches for our collaboration. The foundation has also been established: we have selected the necessary tools (e.g. version control software and IDE) and practices (e.g. rules for issues and commits) and integrated them into our way of working. We discussed our gaps in knowledge and experience beforehand as well, so that we were aware of our limitations.

At the in use and in place level, the practices and tools we established are actively applied to enable work and support communication. We believe that we have reached the level of working well, as we have developed strong familiarity with the processes so that they flow smoothly with minimal effort and most tasks can be completed without extra coordination.

Although we have reflected on and adapted our way of working somewhat between the last projects, there remains room for further examination of each practice to refine our collaboration. For instance, some of our documentation could be more standardized and we could make use of more tools provided by GitHub, such as assigning people to tasks and code reviews.

## Conclusion

Overall, working with the Commons Collections project gave us a realistic view of how you handle a larger open-source codebase. Onboarding was smooth since the project builds and runs with standard tools. When we looked at cyclomatic complexity, it became clear that the most complex methods are also the hardest to understand and test, which is why refactoring and breaking logic into smaller pieces can make a big difference.
The coverage work was also very useful. Using an existing coverage tool was straightforward, and building our own DIY branch coverage tool made us understand what coverage numbers actually mean. Finally, improving coverage by writing targeted tests aimed at specific missed branches was much more effective than adding random tests. Overall, it was a good learning experience in testing, measuring, and improving real production code.

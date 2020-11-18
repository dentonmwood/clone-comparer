# Overview

This is a tool to compare the output of multiple clone detection tools to check the results against each other. It's
implemented to get a benchmark of the [Cyclone](https://github.com/iresbaylor/codeDuplicationParser) tool against
other tools. We use two tools for comparison:
- [NiCad](https://www.txl.ca/txl-nicaddownload.html), a text-based parsing tool developed by Dr. James R. Cordy and Chanchal
K. Roy. NiCad has had several papers written about it, but you can find the original [here](https://www.cs.usask.ca/~croy/papers/2011/CR-NiCad-Tool-ICPC11.pdf).
- [Moss](https://theory.stanford.edu/~aiken/moss/), a hash-based plagiarism detection algorithm developed by Dr. Alex Aiken. Moss is
mentioned in a paper [here](https://theory.stanford.edu/~aiken/publications/papers/sigmod03.pdf).

This project is part of the IRES Baylor 2019 research trip to the Czech Republic. It is also being completed for course
credit for Dr. Tomas Cerny's CSI 5324 Fall 2019 section.

# Usage

This tool will compare one set of files at a time. As such, it's used in a 
[bash script](https://github.com/iresbaylor/clone-comparer-script) which runs the tool multiple times to get a full CSV
file output. Cloning that repository with the submodules will also grab this repo. 

If you do want to use the tool individually, clone it and make sure you have Java and Maven installed. Run the JAR with
the -h option to see the description of how to use it. There are three main modes:
- Header mode (-M h) - prints the header line for the CSV file. Specify the algorithms being used.
- Single mode (-M s) - specify that the algorithms have been run in single-repository mode (i.e. a repo against itself)
- Dual mode (-M d) - specify that the algorithms have been run in dual-repository mode (i.e. two repos against each other)

# External Libraries

This project uses multiple external libraries, including:
- Google's [SimpleJSON](https://code.google.com/archive/p/simplejson/) library to parse the Cyclone JSON files.
- Java's [SAX parser](https://docs.oracle.com/javase/tutorial/jaxp/sax/parsing.html) to handle NiCad's XML files.
- The [HTMLUnit](http://htmlunit.sourceforge.net/) library to scrape the results from the Moss website.
- The Apache [Commons CLI](http://commons.apache.org/proper/commons-cli/) library to handle command-line argument parsing.

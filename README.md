# AppiumTestProject
This project contains end to end solution to mobile automation needs.

# Solutions 
1. Parellel run - Supports 3 threads 
      - Automatically checks the connected devices on system and saves them in queue(thread safe), picks a device and assign it to test and removes from the queue so that it is not available for other tests. Returns the mobile device back to queue after the test execution.
      - Make sure number of emulators/devices available is equal to the thread count mentioned in build.gradle file.
      - Thread count can be changed from build.gradle file
2. Reporting - Supports testng and extent reports.
3. Screenshots - In case of failure screenshots will be captured and placed under TestReport/screenshots folder.
4. Creates appium server on the fly(per test) and kills it after executing the test so that server is not used by other tests. Not using selenium grid in this solution.

# System Requirements

1. JAVA - Java [JDK or JRE - 8 or above](https://www.oracle.com/technetwork/java/javase/downloads/index.html)

# Tools Used
1. [Gradle](https://gradle.org/) (4.10)- This is the build tool used to configure and install all the required dependent libraries and run tests from command line on local and remote machines.
2. [TestNG](https://testng.org/doc/index.html) - Test framework used to organize and run tests.
3. [Java](https://www.java.com/en/)(8) - As programming language.
4. [Appium](http://appium.io/) - This is used to automate the mobile screens
5. Emulators - To run the tests on app.

# Steps to Setup
1. Install Appium through npm - `npm install -g appium@1.14.1`
2. Take a clone of this repository to local machine.
3. In IntelliJ (or any IDE), goto file -> open -> browse to the folder where the clone is taken -> select the folder -> click on open.
4. Now project is ready to execute, provided the above mentioned setup of tools is done successfully and all the dependencies are downloaded in the IDE.

# Steps to run
1. To run as gradle - `./gradlew clean runTests -Dtag=calculator`

# Reports 
1. TestNG report can be seen at - `/build/<tag name>/html/index.html` (open in browser)
2. Extent report can be seen at - `TestReport/Test-Automaton-Report.html` (open in browser)


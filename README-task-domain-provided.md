# Technology Used
* Java 8
* Maven 3
* jacoco for test coverage for java 8.
* junit 4 for unit testing

# Assumptions
* Did not change entities provided except changed the package to com.redenergy.* and com.redenergy.app which contains test harness.
* Not Sure if java Doc was required. But provided for the implemented classes.
* No logging framework used.
* Assumed input File will have right format.

# How to run
* Checkout the project
* Go to the project directory
* Use Maven command `mvn clean install`
* The above will run the tests as well as the application with the sample file provided.
* Run The jar file from project directory `java -jar target/meter-reader-1.0-SNAPSHOT.jar src/test/resources/SimpleNem12.csv`
* The input File can be relative or Full path file.


# SimpleNem12Parser

## Task
* Expected duration: 3-4 hours
* Requires Java 8
* Solution can be in Java (preferably Java8) or Groovy

NEM12 is a file format used to represent read data from smart meters.

You are given a sample of a simplified version of the NEM12 file.  You are also given the domain model to represent the data in this file.  Here are more details.

Refer to comments for following files.
* `EnergyUnit.java`
* `MeterRead.java`
* `MeterVolume.java`
* `Quality.java`
* `SimpleNem12Parser.java`
* `TestHarness.java`

### SimpleNem12.csv format specifications
* You can assume, for this exercise, that no quotes or extraneous commas appear in the CSV data.
* First item on every line is the RecordType
* RecordType 100 must be the first line in the file
* RecordType 900 must be the last line in the file
* RecordType 200 represents the start of a meter read block.  This record has the following subsequent items (after RecordType).
You can assume each file does not contain more than one RecordType 200 with the same NMI.
  * NMI - String ID representing the meter on the site, modelled in `MeterRead.nmi`.  Value should always be 10 chars long. 
  * EnergyUnit - Energy unit of the meter reads, modelled in `EnergyUnit` enum type and `MeterRead.energyUnit`.  Value should always be KWH.
* RecordType 300 represents the volume of the meter read for a particular date.  This record has the following subsequent items (after RecordType).
You can assume each file does not contain more than one RecordType 300 with the same date.
  * Date - In the format yyyyMMdd (e.g. 20170102 is 2nd Jan, 2017).  Modelled in `MeterRead.volumes` map key.
  * Volume - Signed number value.  Modelled in `MeterVolume.volume`.
  * Quality - Represents quality of the meter read, whether it's Actual (A) or Estimate (E).  Value should always be A or E.  Modelled in `MeterVolume.quality`

Your task is to create a new class (e.g. SimpleNem12ParserImpl) that implement interface `SimpleNem12Parser`.

You cannot modify SimpleNem12.csv.  Any changes to the provided domain model is fine though it shouldn't be necessary.

Please send your submission to tommy.li@redenergy.com.au either in a zip file or online source code sharing services such as GitHub or Bitbucket.

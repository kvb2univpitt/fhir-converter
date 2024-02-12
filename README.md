# fhir-converter
A command-line tool for converting health data to FHIR resources .

## Building the Software

### Prerequisites

The following tools are required:

- [OpenJDK 17](https://docs.microsoft.com/en-us/java/openjdk/download)
- [Apache Maven 3.x.x](https://maven.apache.org/download.cgi)

The following third-party Maven dependencies are required:

- [fhir-resource-mapper](https://github.com/kvb2univpitt/fhir-resource-mapper)

### Building the JAR File

> Below are instructions for compiling the code to build the JAR file.  If you prefer not to compile to the code yourself, you can download the pre-built JAR file [fhir-converter-0.1.1.jar](https://pitt-dbmi.s3.amazonaws.com/tools/fhir/fhir-converter-0.1.1.jar).

Open up a terminal in the folder **fhir-resource-mapper** and execute the following command to build the JAR file:

```
mvn clean package
```

The JAR file ***fhir-converter-0.1.1-jar-with-dependencies.jar*** is located in the directory ```fhir-converter/target```.  Note that the pre-buildt JAR filename is **fhir-converter-0.1.1.jar**.

## Using the Software

> Here are some sample health data you can use to try out the tool: [sample_health_data_v2.zip](https://pitt-dbmi.s3.amazonaws.com/tools/fhir/sample_health_data_v2.zip)

### Prerequisites

- [OpenJDK 17](https://docs.microsoft.com/en-us/java/openjdk/download) to run the JAR file.

Open up a terminal to where the JAR file is and execute the following command:

```
java -jar fhir-converter-0.1.1.jar
```

You should see the following error and help menu:

```
Missing required options: data, type, format
usage: java -jar fhir-converter-0.1.1.jar --data <arg> [--dir-out <arg>] --format <arg> --type <arg>
    --data <arg>      Health data.
    --dir-out <arg>   Directory to write output file to.
    --format <arg>    Output resource format: json, ndjson, xml, rdf.
    --type <arg>      Type of health data being converted: patient, encounter, observation, medication-administration, location.
```

Based on the output above, you can see that the required parameters are ***data***, ***type***, and ***format***.  Note that parameter in bracket is optional.

### Parameters

| Parameter | Description                                                             | Value                                                                           | Required |
|-----------|-------------------------------------------------------------------------|---------------------------------------------------------------------------------|----------|
| data      | Input data file.                                                        | A tab-separated values (tsv) file containing health data.                       | Yes      |
| type      | The type of data of the input file.                                     | Either patient, encounter, observation, medication-administration, or location. | Yes      |
| format    | The format of the output data file.                                     | Either json, ndjson, xml, or rdf                                                | Yes      |
| dir-out   | The directory to write out data.  The current directory is the default. | N/A                                                                             | No       |

The output filename is the same as the input filename. The output file extension is based on the format of the specified format of the data.  For an example, if the format is ***ndjson*** then the output file extension is ***.ndjson***. 

### Examples

The following examples use the data from the [sample_health_data_v2.zip](https://pitt-dbmi.s3.amazonaws.com/tools/fhir/sample_health_data_v2.zip).

#### Converting Patient Health Data

To convert the patient health data to FHIR patient resources as NDJSON format, execute the following:

```
java -jar fhir-converter-0.1.1.jar --data sample_health_data_v2/patients.tsv --type patient --format ndjson
```

There should be an output file ***patients.ndjson***.

To write out the output file to specific directory, use the ***dir-out*** parameter.  For an example:

```
java -jar fhir-converter-0.1.1.jar --data sample_health_data_v2/patients.tsv --type patient --format ndjson --dir-out results/
```

The above command will put the file ***patients.ndjson*** in the **results** folder.

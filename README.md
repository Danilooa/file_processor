## The only implemented requirement was this one:

_This application runs all the time, without any breaks. Everytime new files become available, everything should be executed._

# file_processor

This is a data analysis able to import lots of flat files, read and analyse the data, and output a report.

## Flat file layout

There are 3 kinds of data inside those files. For each kind of data there is a different layout.

### Salesman data

Salesman data has the format id 001 and the line will have the following format.<br/>

_001çCPFçNameçSalary_

### Customer data

Customer data has the format id 002 and the line will have the following format.<br/>

_002çCNPJçNameçBusinessArea_

### Sales data

Sales data has the format id 003. Inside the sales row, there is the list of items, which is
wrapped by square brackets []. The line will have the following format.<br/>

_003çSaleIDç[ItemID-ItemQuantity-ItemPrice]çSalesmanname_

### Sample file data

The following is a sample of the data that this application is able to read. Note that this
is a sample, real data could be 100% different.<br/>

_001ç1234567891234çDiegoç50000<br/>_
_001ç3245678865434çRenatoç40000.99<br/>_
_002ç2345675434544345çJosedaSilvaçRural<br/>_
_002ç2345675433444345çEduardoPereiraçRural<br/>_
_003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego<br/>_
_003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çRenato<br/>_

## Data analysis

This app reads .dat files from the default directory, located at %HOMEPATH%/data/in.
After processing all files inside the input default directory, the app creates a flat file
inside the default output directory, located at %HOMEPATH%/data/out. The output file
follows this pattern, {flat_file_name}.done.dat.

The output contains following data:<br/>

_● Amount of clients in the input file<br/>_
_● Amount of salesman in the input file<br/>_
_● ID of the most expensive sale<br/>_
_● Worst salesman ever<br/>_

This application runs all the time, without any breaks. Everytime new files
become available, everything should be executed.

## Application Construction

This application was implemented in 'Java 8' with 'Maven' as dependency manager.

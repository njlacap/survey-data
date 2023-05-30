# Read Me First
* Java version: 17
* Maven version: 3.8.1
***
# Getting Started
### Build the Project
```
mvn clean install
```
***
## REST CONTRACT
* ### GET | No Parameters
### ```http://localhost:8000/survey```
```
[
    {
        "age": "18-24",
        "industry": "Market Research",
        "annualSalary": 36330.0,
        "currency": "USD",
        "location": "Las Vegas, NV",
        "workExperience": "2 - 4 years",
        "jobTitleOther": "",
        "currencyOther": "",
        "title": "Market Research Assistant"
    },
    ...
]
```
***
* ### GET | Filter Options
#### Allowable fields for filtering
* age : String
* industry : String
* annualSalary : String
* currency : String
* location : String
* workExperience : String
### ```http://localhost:8000/survey?age=18&industry=Government```
```
[
    {
        "age": "18-24",
        "industry": "Government",
        "annualSalary": 4000000.0,
        "currency": "USD",
        "location": "Nashville, TN",
        "workExperience": "2 - 4 years",
        "jobTitleOther": "",
        "currencyOther": "",
        "title": "Adminstrative Assistant"
    },
    ...
]
```
***
* ### GET | Sort Option
### ```http://localhost:8000/survey?sort=annualSalary```
```
[
    {
        "age": "35-44",
        "industry": "none",
        "annualSalary": 0.0,
        "currency": "USD",
        "location": "",
        "workExperience": "1 year or less",
        "jobTitleOther": "",
        "currencyOther": "",
        "title": "none"
    },
    {
        "age": "25-34",
        "industry": "Student",
        "annualSalary": 5000.0,
        "currency": "USD",
        "location": "Pasadena California",
        "workExperience": "2 - 4 years",
        "jobTitleOther": "",
        "currencyOther": "",
        "title": "Student"
    },
    ...
]
```
***
* ### GET | Include Specified Fields
#### Allowable options
They are already grouped for simplification
* age,industry,jobTitle
* annualSalary,currency,location
* age,industry,jobTitle,annualSalary,currency,location
### ```http://localhost:8000/survey?fields=age,industry,jobTitle```
```
[
    {
        "age": "18-24",
        "industry": "Market Research",
        "title": "Market Research Assistant"
    },
    {
        "age": "18-24",
        "industry": "E-commerce",
        "title": "Business Analyst"
    },
    ...
]
```
***
* ### GET | Aggregate Function
### ```http://localhost:8000/survey?age=18&industry=Government&count=true```
```
44
```
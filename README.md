# Number-conversion

Decimal to roman and hexadecimal Rest API

### API

Endpoints:
* localhost:8080/api/status - status of api
* localhost:8080/api/status/{ conversion }/{ number } - endpoint for converting numbers


```bash
conversion:
  roman - roman system conversion
  hexadecimal - hexadecimal system conversion
number - integer number
```

### Installation

  1. Clone repository to your machine
  
  2. Use sbt to run application
    ```
    sbt run
    ```


### Tests
```bash
sbt test
```

### Usage
```bash
localhost:8080/api/hexadecimal/10
```

### Dependecies
* akkaHttp
* scalatest

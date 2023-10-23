# Project_VGHF

``` mermaid
classDiagram

    Item "*" <--> "1" ItemType
    Item "*" <--> "1" Platform
    Item "*" <--> "1" Museum
    Item "*" <--> "*" Publisher
    Museum "1" <--> "*" VisitorLog
    VisitorLog "*" <--> "1" Visitor

    class Item {
        id : INT
        name : TEXT
        price : INT
        discription : TEXT
        typeid : INT
        platformid : INT
        series : TEXT
        releaseDate : TEXT
        museumID : INT
    }

    class ItemType {
        id : INT
        name : TEXT
    }

    class Platform {
        id : INT
        name : TEXT
        discription : TEXT
        releaseDate : TEXT
    }
    
    class Publisher {
        id : INT
        name : TEXT
        discription : TEXT
        website : TEXT
    }
    
    class Museum {
        id : INT
        location : TEXT
    }
    
    class VisitorLog {
        museumid : INT
        visitorid : INT
        date : TEXT
        donation : INT
    }
    
    class Visitor {
        id : INT
        name : TEXT
        phone : TEXT
        email : TEXT
        address : TEXT
    }
 ```

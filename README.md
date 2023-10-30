# Project_VGHF

``` mermaid
classDiagram

    Item "*" --> "1" ItemType
    Item "*" --> "1" Platform
    Item "*" --> "1" Museum
    Item "*" --> "*" Publisher
    Museum "1" --> "*" VisitorLog
    VisitorLog "*" --> "1" Visitor

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
### Doel:
Het doel van het Public Education project is om in pop-up musea verschillende video games en video games gerelareede items tentoon te stellen. De database moet dienen als ondersteuning door de inventaris, classificatie en distributie van items in musea te beheren, evenals het bijhouden van bezoekersinformatie.
 
### item:
Een item representateert de voorwerpen die in de musea worden getoond. Een item heeft verschillende attributen, waaronder een unieke id, naam, prijs, beschrijving, typeid, platformid, serie, releasedatum en museumid. De relatie "Item * <--> 1 ItemType" geeft aan dat elk item is gekoppeld aan precies één itemtype, dus items worden gecategoriseerd op basis van hun type. De relatie "Item * <--> 1 Platform" geeft aan dat items ook gerelateerd zijn aan platforms. Elk item heeft een bepaald platform zoals Playstation 4, PC, GameBoy, etc. De relatie "Item * <--> 1 Museum" geeft aan dat items zich in specifieke musea bevinden, en de relatie "Item * <--> * Publisher" geeft aan dat meerdere uitgevers betrokken kunnen zijn bij de distributie van items.

### ItemType:
Dit is een klasse die de verschillende types items definieert. Het heeft een id en een naam als attributen. De relatie "Item * <--> 1 ItemType" geeft aan dat meerdere items kunnen worden toegewezen aan hetzelfde itemtype.

### Platform:
Deze klasse bevat informatie over de platforms. Platforms hebben attributen zoals een id, naam, beschrijving en releasedatum van dat platform zoals 12 november 2020 voor de PlayStation 5. De relatie "Item * <--> 1 Platform" geeft aan dat items worden geassocieerd met specifieke platforms.

### Publisher:
Deze klasse vertegenwoordigt uitgevers. Uitgevers hebben attributen zoals een id, naam, beschrijving en website. De relatie "Item * <--> * Publisher" geeft aan dat meerdere uitgevers betrokken kunnen zijn bij het publiceren van items.

### Museum:
Deze klasse vertegenwoordigt musea. Musea hebben een unieke id en locatie. De relatie "Museum 1 <--> * VisitorLog" geeft aan dat elk museum geassocieerd is met meerdere bezoekerslogs. Dit betekent dat elk museum bijhoudt wie het bezoekt en wanneer. De relatie "Item * <--> 1 Museum" geeft aan dat items in specifieke musea worden tentoongesteld.

### VisitorLog:
Deze klasse houd informatie bij over de logboeken van bezoekers in musea. Het heeft attributen zoals museumid, visitorid, datum en donatie. De relatie "Museum 1 <--> * VisitorLog" geeft aan dat elk museum meerdere bezoekerslogs kan hebben, en de relatie "VisitorLog * <--> 1 Visitor" geeft aan dat elk bezoekerslog is gekoppeld aan een specifieke bezoeker.

### Visitor:
Dit is de klasse die bezoekers vertegenwoordigt. Bezoekers hebben een unieke id, naam, telefoonnummer, e-mailadres en adres. De relatie "VisitorLog * <--> 1 Visitor" geeft aan dat elk bezoekerslog is gekoppeld aan een specifieke bezoeker.

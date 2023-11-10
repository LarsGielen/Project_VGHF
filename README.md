# Project_VGHF

``` mermaid
classDiagram

    Item "0..*" --> "1" ItemType
    Item "0..*" --> "0..1" Platform
    Item "0..*" --> "1..*" Publisher
    Item "0..*" --> "1" Location
    Item "0..*" --> "1..*" Genre
    VisitorLog "0..*" <-- "1" Location
    VisitorLog "0..*" --> "1" Visitor
    Location "0..*" --> "1" LocationType

    class Item {
        id : INT
        name : TEXT
        price : REAL
        discription : TEXT
        typeid : INT
        platformid : INT
        series : TEXT
        genreID : INT
        releaseDate : NUMERIC
        locationid : INT
    }

    class ItemType {
        id : INT
        name : TEXT
    }

    class Genre {
        id : INT
        name : TEXT
    }

    class Platform {
        id : INT
        name : TEXT
        discription : TEXT
        releaseDate : NUMERIC
    }
    
    class Publisher {
        id : INT
        name : TEXT
        discription : TEXT
        website : TEXT
    }
    
    class Location {
        id : INT
        locationType : INT
        locationName : TEXT
        country : TEXT
        city : TEXT
        street : TEXT
        houseNumber : TEXT
    }
    
    class LocationType {
        id : INT
        name : TEXT
    }

    class VisitorLog {
        locationid : INT
        visitorid : INT
        date : NUMERIC
        donation : REAL
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

De Video Game History Foundation (VGHF) heeft verschillende projecten, waaronder het behouden van broncode en het bouwen van een bibliotheek voor video game magazines. Dit databaseproject richt zich op het Public Education-project. Er worden verschillende pop-upmusea georganiseerd zodat video games en andere video game-gerelateerde items aan het publiek kunnen worden tentoongesteld. De database moet dienen ter ondersteuning van het beheer van de inventaris, classificatie en distributie van items in musea en warenhuizen. De database kan ook bezoekersinformatie bijhouden, zodat kan worden bekeken hoeveel donaties de verschillende musea hebben ontvangen en hoeveel bezoekers er zijn langsgekomen.

### Item:

Een item representeert de voorwerpen die in de musea worden getoond of zijn opgeslagen in een warenhuis. Een item heeft verschillende attributen, waaronder een unieke id, naam, prijs, beschrijving, typeid, platformid, serie, releasedatum en locationid. De relatie "Item * <--> 1 ItemType" geeft aan dat elk item is gekoppeld aan precies één itemtype. Een item kan bijvoorbeeld digitaal, een cd of een magazine zijn. De relatie "Item * <--> 1 Platform" geeft aan dat items ook gerelateerd zijn aan platforms. Elk item heeft één bepaald platform, zoals PlayStation 4, PC of GameBoy. Deze kolom kan ook niets bevatten indien we spreken over een magazine of handleiding. De relatie "Item * <--> 1 Location" geeft aan dat items zich in specifieke musea of warenhuizen bevinden; een museum kan meerdere items tentoonstellen, maar een item kan wel maar op 1 plaats liggen. De relatie "Item * <--> * Publisher" is een veel-op-veelrelatie aangezien er meerdere uitgevers betrokken kunnen zijn bij de distributie van items.

### ItemType:

Dit is een tabel die de verschillende typen items definieert. Elk type heeft een id en een naam als attributen. Een paar voorbeelden van verschillende typen zijn: cd, digitaal, magazine, handleiding, etc. Elk item heeft gegarandeerd 1 ItemType, maar een ItemType kan wel gedeeld worden door verschillende items of bestaan zonder dat hier een item van is. Dit is dus een één-op-veelrelatie.

### Platform:

Deze tabel bevat informatie over de platforms waarop de games kunnen worden gespeeld. Platforms hebben attributen zoals een id, naam, beschrijving en releasedatum van dat platform. Bijvoorbeeld 12 november 2020 voor de PlayStation 5. Het kan zijn dat een game op zowel de PS4 als de pc gespeeld kan worden, maar in dit geval zijn dit wel 2 aparte cd's. Elke game in de database heeft dus één platform. Indien het item geen game is (zoals een handleiding) kan het zijn dat dit item geen platform bevat. De relatie tussen Item en Platform is dus een 0-of-1-op-veelrelatie.

### Publisher:

Deze tabel vertegenwoordigt de verschillende uitgevers. Uitgevers hebben attributen zoals een id, naam, beschrijving en website. Een item heeft 1 of meer uitgevers, terwijl een uitgever niet noodzakelijk een item in de database heeft staan. De relatie tussen Item en Publisher is dus één-op-veel-op-nul-op-veel.

### Location:

Deze tabel vertegenwoordigt de locaties waar een item zich kan bevinden. Locaties hebben een unieke id, naam en adres. Hiernaast heeft een locatie ook een type, zoals museum of warenhuis. Op een locatie kunnen geen tot meerdere items worden bewaard, maar elk item is op één locatie. Een locatie kan ook bezoekers hebben, en een bezoeker kan naar meerdere locaties gaan. Dit is dus een veel-op-veelrelatie. Om deze bij te houden wordt er een VisitorLog-tabel gemaakt die dient als tussentabel, maar ook wat meer informatie bijhoudt over het bezoek.

### VisitorLog:

Deze tabel houdt informatie bij over de bezoekers van locaties. De tabel dient om de veel-op-veelrelatie tussen bezoekers en locaties te onderhouden. Dit wordt gedaan met de attributen locationid en visitorid. Daarnaast houdt deze tabel ook nog wat meer informatie bij over het bezoek, zoals de datum en of er een bedrag is gedoneerd aan het project.

### Visitor:

Dit is de tabel die bezoekers bijhoudt. Bezoekers hebben een unieke id, naam, telefoonnummer, e-mailadres en adres. Een bezoeker kan meerdere keren naar een museum gaan of naar verschillende musea gaan. Dit wordt bijgehouden in de VisitorLog-tabel.

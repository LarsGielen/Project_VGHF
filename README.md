# Project_VGHF

``` mermaid
classDiagram

    Item "0..*" --> "1" ItemType
    Item "0..*" --> "0..1" Platform
    Item "0..*" --> "1..*" Publisher
    Item "0..*" --> "1" Location
    VisitorLog "0..*" <-- "1" Location
    VisitorLog "0..*" --> "1" Visitor
    Location "0..*" --> "1" LocationType

    class Item {
        id : INT
        name : TEXT
        price : INT
        discription : TEXT
        typeid : INT
        platformid : INT
        series : TEXT
        releaseDate : TEXT
        locationid : INT
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
    
    class Location {
        id : INT
        locationType : INT
        locationName : TEXT
        address : TEXT
    }
    
    class LocationType {
        id : INT
        name : TEXT
    }

    class VisitorLog {
        locationid : INT
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
De Video Game History Foundation heft verschillende projecten waaronder het behouden van broncode en het bouwen van een bibliotheek voor video game magazines. Dit database project focust zich op het Public Education project. Er worden verschillende pop-up musea georganiseerd zodat video games en andere video games gerelateerde items kunnen worden tentoongesteld aan het publiek. De database moet dienen als ondersteuning door de inventaris, classificatie en distributie van items in musea  en warenhuizen te beheren. De database kan ook bezoekersinformatie bijhouden, op deze manier kan er gekeken worden hoeveel donatie de verschillende musea kregen en hoe veel bezoekers er zijn langs gekomen.
 
### item:
Een Item representeert de voorwerpen die in de musea worden getoond of zijn opgeslagen in een warenhuis. Een item heeft verschillende attributen, waaronder een unieke id, naam, prijs, beschrijving, typeid, platformid, serie, releasedatum en locationid. De relatie "Item * <--> 1 ItemType" geeft aan dat elk item is gekoppeld aan precies één itemtype. Een item kan bijvoorbeeld digitaal, een cd of een magazine zijn. De relatie "Item * <--> 1 Platform" geeft aan dat items ook gerelateerd zijn aan platforms. Elk item heeft één bepaald platform zoals Playstation 4, PC, GameBoy. Deze kolom kan ook niks bevatten indien we spreken over een magazine of handleiding. De relatie "Item * <--> 1 location" geeft aan dat items zich in specifieke musea of warenhuizen bevinden, een muzeum kan meerdere items tentoonstellen, maar een item kan wel maar op 1 plaats liggen. De relatie "Item * <--> * Publisher" is een veel op veel relatie Aangezien er meerdere uitgevers betrokken kunnen zijn bij de distributie van items.

### ItemType:
Dit is een tabel die de verschillende types van items definieert. Elk type heeft een id en een naam als attributen. Een paar voorbeelden van verschillende types zijn: cd, digitaal, magazine, handleiding, etc. Elk Item heeft gegarandeerd 1 ItemType, maar een ItemType kan wel gedeeld worden door verschillende Items of bestaan zonder dat hier een Item van is. Dit is dus een 1 op veel relatie.

### Platform:
Deze tabel bevat informatie over de platforms waar de games op kunnen gespeeld worden. Platforms hebben attributen zoals een id, naam, beschrijving en releasedatum van dat platform. Bijvoorbeeld 12 november 2020 voor de PlayStation 5. Het kan zijn dat een game op zowel de PS4 als de pc gespeeld kan worden, maar in dit geval zijn dit wel 2 aparte cd’s. Elke game in de database heeft dus één platform. Indien de Item geen game is (zoals een handleiding) kan het zijn dat dit Item geen Platform bevat. De relatie tussen Item en Platform is dus een 0 of 1 op veel relatie.

### Publisher:
Deze tabel vertegenwoordigt de verschillende uitgevers. Uitgevers hebben attributen zoals een id, naam, beschrijving en website. Een Item heeft 1 of meer uitgevers terwijl een uitgever niet noodzakelijk een Item in de database heeft staan. De relatie tussen Item en Publisher is dus 1 tot veel op 0 tot veel. 

### Location:
Deze tabel vertegenwoordigt de locaties waar een Item zich kan bevinden. locaties hebben een unieke id, naam en adres. Hiernaast heeft een locatie ook een type zoals musea of warenhuis. Op een locatie kunnen geen tot meerdere Items worden bewaard, maar elke item is op één locatie. Een locatie kan ook bezoekers hebben, en een bezoeker kan naar meerdere locaties gaan. Dit is dus een veel op veel relatie. Om deze bij te houden wordt er een VisitorLog tabel gemaakt die dient als tussen tabel, maar ook wat meer informatie bijhoud over het bezoek.

### VisitorLog:
Deze tabel houd informatie bij over de bezoekers van locaties. De tabel dient om de veel op veel relatie tussen bezoekers en locaties te onderhouden. Dit wordt gedaan met de attributen locationid en visitorid. Daarnaast houd deze tabel ook nog wat meer informatie bij over het bezoek zoals de datum en of er een bedrag gedoneerd is aan het project.

### Visitor:
Dit is de tabel die bezoekers bijhoud. Bezoekers hebben een unieke id, naam, telefoonnummer, e-mailadres en adres. Een bezoeker kan meerdere keren naar een museum gaan of naar verschillende musea gaan. Dit wordt bijgehouden in de VisitorLog tabel.


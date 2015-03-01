Osztályok
=========

Cell
----

A játéktér egy celláját megtestesítő osztály. Egy cellán több objektum is elhelyezkedhet. Amennyiben egy játékos a cellára lép,
a cella erről értesíti a rajta található objektumokat.

Ősosztályok: nincs
Interfészek: nincs
Attribútumok:

* objects: A cellán elhelyezkedő objektumok listája.
* currentPlayer: Az aktuálisan cellán álló játékos.
* type: A cella típusa, lehet érvényes (VALID), vagy érvénytelen (INVALID). Amennyiben egy játékos érvénytelen cellára lép, azonnal elveszíti a játékot.

Metódusok:
* void interact(): Jelzi a cella összes objektumának, hogy p játékos a cellára lépett. Ezen metódus segítségével tudják
a cellán lévő foltok kifejteni hatásukat. [PROTECTED]

Entity
------

Minden játékelemnek ezt az interfészt kell implementálnia. Metódusai segítségével tudunk a Game osztály eseményeire reagálni, illetve
szükség esetén kirajzolni az entitást.

Ősosztályok: nincs

Metódusok:
* void onTurnStart(): A kör eleje eseményt kezelő metódus
* void onTurnEnd(): A kör vége eseményt kezelő metódus
* void draw(): Amennyiben újra kell rajzolnunk a pályát, ezzel a metódussal rajzolhatjuk ki az adott entitást.


Game
----

A rendszer központi osztálya. Nyilvántartja a játékosokat és a játékelemeket. Az eseményközpontú rendszer hírforrása,
minden regisztrált játékelem ide iratkozik fel.

Ősosztályok: nincs
Interfészek: nincs
Attribútomok:

* map: a játéktér tárolására
* entities: az egyes játékelemek (foltok, stb.) listája
* players: a játékosok listája
* turnCount: az éppen aktuális kör száma

Metódusok:

* void start(): Jelzi a játék indulását, hatására felépül a pálya és megkezdődik az első kör.
* void reset(): Újraindítja az aktuális játékot. Minden játékos visszakerül a kezdőpozícióba.
* void quit(): Befejezi a játékot és kilép a programból.
* void endTurn(): Jelzi a kör végét az összes feliratkozott entitásnak [PROTECTED]
* void beginTurn(): Jelzi a kör elejét az összes feliratkozott entitásnak [PROTECTED]

Map
---

A játékteret megtestesítő osztály. Ő tartalmazza a játéktér celláit és az adott játéktérhez kapcsolódó információkat (pl. a maximális körök számát).

Ősosztályok: nincs
Intefészek: nincs
Attribútumok:

* cells: Egy kétdimenziós adatszerkezet, amely a játék celláit tartalmazza.
* maxTurns: A maximális körök száma.

Metódusok: nincs

Object
------

Egy nem játékos objektum, ami interakcióba léphet egy játékossal. Ezek lehetnek például a játékosra ható effektek. Ez az osztály egy absztarkt ősosztály.

Ősosztályok: nincs
Intefészek: Entity
Attribútumok: nincs
Metódusok:

* interact(Player player): Alkalmazza az adott hatást a _player_ játékosra.

Player
------

Egy-egy játékos karaktert megtestesítő objektum. Minden körben az éppen aktuális játékost tudjuk a bemenetek segítségével manipulálni.

Ősosztályok: nincs
Interfészek: Entity
Attribútumok:

* index: A játékos azonosítója. Egyedinek kell lennie.
* currentCell: Az a cella, ahol a játékos éppen áll.
* storedStains: Egy kulcs-érték alapú adatszerkezet, amelyben nyilvántartjuk, hogy egy adott típusú foltból hány darab áll a játékos rendelkezésére.
* speed: Az adott játékos sebessége.
* canChangeDirection: Jelzi, hogy egy játékos képes-e irányt változtatni (ez alapértelmezésben igaz, de pl. olajfoltra lépés esetén nem).

Metódusok:

* void move(Cell cell): Átlépteti a játékost egy másik cellára.

Stain
-----

A specifikációban szereplő foltok absztrakt ősosztálya. Önálló szerepe nincs, csupán egy későbbi esetleges bővíthetőség miatt van jelen.

Ősosztályok: Object
Interfészek: Entity
Attribútumok:
nincs

Metódusok:
nincs









# SEG3503 - Lab 3 : Mesures de couverture

| Outline   | Value                    |
| --------- | ------------------------ |
| Course    | SEG 3503                 |
| Date      | Summer 2026              |
| Student   | Alexandre Turgeon        |
| Email     | aturg052@uottawa.ca      |
| Professor | Mouhcine Guennoun        |
| TA        | Mohamed Nefsi            |

Ce repertoire contient le travail du laboratoire 3, organise en deux projets Java :

- [`date/`](./date) - tests JUnit pour la classe `Date`.
- [`computation/`](./computation) - tests JUnit pour la classe `Computation`.

Le document du laboratoire demande d'utiliser le depot `seg3503_playground`, de
creer le repertoire `lab03`, d'extraire `date.zip` et `computation.zip`, puis de
valider que le code compile, que les tests s'executent et que JaCoCo peut produire
les mesures de couverture.

## Execution des tests

Les commandes suivantes ont ete executees sous Windows avec Java 23. Comme la
version de JaCoCo fournie avec le laboratoire est ancienne, les classes sont
compilees avec `--release 8`.

### Date

```powershell
cd lab03/date
javac --release 8 -encoding UTF-8 --source-path src -d build/classes src/*.java
javac --release 8 -encoding UTF-8 --source-path test -d build/test-classes -cp "build/classes;lib/junit-platform-console-standalone-1.7.1.jar" test/*.java
java "-javaagent:lib/jacocoagent.jar=destfile=jacoco.exec,append=false,includes=Date*" -jar lib/junit-platform-console-standalone-1.7.1.jar --class-path "build/classes;build/test-classes" --scan-class-path
java -jar lib/jacococli.jar report jacoco.exec --classfiles build/classes --sourcefiles src --html report --xml report/jacoco.xml
```

Resultat : 55 tests executes, 55 reussis.

Couverture de `Date.java` :

- Instructions : 362/362
- Lignes : 51/51
- Branches : 66/68
- Methodes : 13/13

### Computation

```powershell
cd lab03/computation
javac --release 8 -encoding UTF-8 --source-path src -d build/classes src/*.java
javac --release 8 -encoding UTF-8 --source-path test -d build/test-classes -cp "build/classes;lib/junit-platform-console-standalone-1.7.1.jar" test/*.java
java "-javaagent:lib/jacocoagent.jar=destfile=jacoco.exec,append=false,includes=Computation*" -jar lib/junit-platform-console-standalone-1.7.1.jar --class-path "build/classes;build/test-classes" --scan-class-path
java -jar lib/jacococli.jar report jacoco.exec --classfiles build/classes --sourcefiles src --html report --xml report/jacoco.xml
```

Resultat : 6 tests executes, 6 reussis.

Couverture de `Computation.java` :

- Instructions : 86/94
- Lignes : 29/32
- Branches : 9/10
- Methodes : 7/7

Les lignes restantes non couvertes dans `Computation.java` correspondent au bloc
`catch` de `catchesException()`, qui est inatteignable sans modifier le code source
car `i` est initialise a `0` et la condition `i == 13` ne peut jamais etre vraie.

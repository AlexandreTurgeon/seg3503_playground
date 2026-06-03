# SEG3503 — Lab 2 : Classes d'équivalence

| Outline   | Value                    |
| --------- | ------------------------ |
| Course    | SEG 3503                 |
| Date      | Summer 2026              |
| Student   | Alexandre Turgeon        |
| Email     | aturg052@uottawa.ca      |
| Professor | Mouhcine Guennoun        |
| TA        | Mohamed Nefsi            |

Ce répertoire contient le travail du laboratoire 2, organisé en deux projets :

- [`ecs/`](./ecs) — projet Java + JUnit (classes `Date` et `Bit`) utilisé pour
  l'**exercice 2** (tests de `nextDate`).
- [`registration/`](./registration) — l'application web `user-registration-app`
  (Spring Boot / Tomcat embarqué) utilisée pour l'**exercice 1** (test manuel par
  classes d'équivalence).

> Les scripts `bin/*` sont des scripts **bash**. Sous Windows, exécutez-les depuis
> **Git Bash** (le séparateur de classpath est détecté automatiquement).

---

## Comment exécuter

### Application d'enregistrement (exercice 1)

L'application utilise Spring Boot 2.0.1 (2018). Sur un JDK récent, `java -jar` simple
échoue au démarrage avec
`IllegalStateException: Cannot load configuration class: com.stephane.Application`
(CGLIB n'a plus accès à `ClassLoader.defineClass` par réflexion). Le contournement est
le drapeau `--add-opens` :

```bash
cd registration

# JDK récent (testé : Java 23) — le drapeau --add-opens est requis :
java --add-opens java.base/java.lang=ALL-UNNAMED -jar user-registration-app-0.1.0.jar

# JDK 8 — fonctionne directement, sans drapeau :
# java -jar user-registration-app-0.1.0.jar

# puis visiter http://localhost:8080/
```

### Tests ECS / JUnit (exercice 2)

```bash
cd ecs
./bin/test
```

Le script compile `src/` et `test/`, puis lance le *JUnit Platform Console Launcher*
(JUnit 5 *Jupiter* + JUnit 4 *Vintage*). Fonctionne avec un JDK récent (testé sur Java 23).

---

## Exercice 1 — Test manuel par classes d'équivalence

L'application valide les champs côté serveur via *Bean Validation* (annotations sur la
classe `UserForm`). Les règles ci-dessous sont **extraites du bytecode** du `.jar`
(regex, bornes et messages exacts) :

| Champ       | Contraintes réelles                                              | Message d'erreur |
| ----------- | --------------------------------------------------------------- | ---------------- |
| UserName    | `@NotNull`, `@Size(6..12)`, `@Pattern("[a-zA-Z][a-zA-Z0-9]+")`   | *UserName is mandatory* / *Size of UserName must be between 6 and 12* / *Wrong UserName format* |
| FirstName   | `@Pattern("^$|[a-zA-Z][a-zA-Z ]*")` (vide autorisé, lettres/espaces) | *Wrong FirstName format* |
| LastName    | `@Pattern("^$|[a-zA-Z][a-zA-Z ]*")` (vide autorisé, lettres/espaces) | *Wrong LastName format*  |
| Age         | `@NotNull`, `@Min(18)`, `@Max(64)`                               | *Age is mandatory* |
| Email       | `@NotNull`, `@Pattern("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$")` | *An Email address is mandatory* / *Wrong Email format* |
| City        | menu déroulant {Ottawa, Toronto, Montreal, Halifax} — **aucune** annotation de validation | — |
| Postal Code | `@Pattern("^(?!.*[DFIOQU])[A-VXY][0-9][A-Z] ?[0-9][A-Z][0-9]$")` — majuscules, espace **optionnel**, lettres `D F I O Q U` interdites, 1re lettre ∈ A–V,X,Y | *Wrong Postal Code format* |

- **Acceptation** → l'application redirige (HTTP 302) vers `/results` :
  « *Congratulations … You are now a member of our site...* ».
- **Rejet** → le formulaire est réaffiché (HTTP 200) avec le(s) message(s) d'erreur en
  rouge à côté du champ fautif.

> Note : pour certaines contraintes, le message affiché à l'écran provient du validateur
> par défaut plutôt que de l'attribut `message` de l'annotation — p. ex. `@Min/@Max`
> affichent *« must be greater/less than or equal to … »*, et un âge non numérique
> déclenche une erreur de conversion de type (`NumberFormatException`) avant la validation.

**Entrée de référence (toutes valeurs valides)** :
`userName=johndoe, firstName=John, lastName=Doe, email=john@example.com, age=30, city=Ottawa, postalCode=K1N 6N5`.
Chaque cas ci-dessous ne fait varier **qu'un seul champ** par rapport à cette base.

### Classes d'équivalence et cas de test

> Colonne **« Résultats Escomptés »** = comportement déduit des contraintes ci-dessus.
> Colonnes **« Résultats Actuels »** / **« Verdict »** = comportement **observé** en
> exécutant l'application (Java 23, `--add-opens`) et en envoyant chaque cas sur `/` :
> *Accepté* = redirection HTTP 302 vers `/results` ; *Rejeté* = HTTP 200 avec message
> d'erreur. Verdict = *Succès* si l'observé correspond à l'attendu, sinon *Échec*.

| Cas | Champ — classe d'équivalence                 | Entrée (champ modifié) | Résultats Escomptés                    | Résultats Actuels                                  | Verdict |
| --- | --------------------------------------------- | ---------------------- | -------------------------------------- | -------------------------------------------------- | ------- |
| TC01 | (base) toutes valeurs valides                | —                      | Accepté                                | Accepté (302 → /results)                           | Succès |
| TC02 | UserName — trop court (< 6)                   | `john1`                | Rejeté (*Size 6 à 12*)                  | Rejeté — *Size of UserName must be between 6 and 12* | Succès |
| TC03 | UserName — trop long (> 12)                   | `johndoe123456`        | Rejeté (*Size 6 à 12*)                  | Rejeté — *Size of UserName must be between 6 and 12* | Succès |
| TC04 | UserName — longueur valide (borne 6)         | `johnny`               | Accepté                                | Accepté (302 → /results)                           | Succès |
| TC05 | UserName — format invalide (début chiffre)   | `1johndoe`             | Rejeté (*Wrong UserName format*)        | Rejeté — *Wrong UserName format*                   | Succès |
| TC06 | UserName — vide                              | `` (vide)              | Rejeté (champ requis)                   | Rejeté — *Size…* + *Wrong UserName format*         | Succès |
| TC07 | Age — sous la borne (< 18)                    | `17`                   | Rejeté (`@Min(18)`)                     | Rejeté — *must be greater than or equal to 18*     | Succès |
| TC08 | Age — borne basse valide                      | `18`                   | Accepté                                | Accepté (302 → /results)                           | Succès |
| TC09 | Age — borne haute valide                      | `64`                   | Accepté                                | Accepté (302 → /results)                           | Succès |
| TC10 | Age — au-dessus de la borne (> 64)           | `65`                   | Rejeté (`@Max(64)`)                     | Rejeté — *must be less than or equal to 64*        | Succès |
| TC11 | Age — non numérique                           | `abc`                  | Rejeté (type invalide)                  | Rejeté — *Failed to convert … NumberFormatException* | Succès |
| TC12 | Email — sans `@`                              | `johnexample.com`      | Rejeté (*Wrong Email format*)           | Rejeté — *Wrong Email format*                      | Succès |
| TC13 | Email — sans domaine de 1er niveau           | `john@example`         | Rejeté (*Wrong Email format*)           | Rejeté — *Wrong Email format*                      | Succès |
| TC14 | Email — valide (autre forme)                 | `j.doe@mail.co`        | Accepté                                | Accepté (302 → /results)                           | Succès |
| TC15 | Postal — avec espace                          | `K1N 6N5`              | Accepté                                | Accepté (302 → /results)                           | Succès |
| TC16 | Postal — sans espace (séparateur optionnel)  | `K1N6N5`               | Accepté                                | Accepté (302 → /results)                           | Succès |
| TC17 | Postal — minuscules                          | `k1n 6n5`              | Rejeté (*Wrong Postal Code format*)     | Rejeté — *Wrong Postal Code format*                | Succès |
| TC18 | Postal — lettre interdite en tête (`D`)      | `D1N 6N5`              | Rejeté (*Wrong Postal Code format*)     | Rejeté — *Wrong Postal Code format*                | Succès |
| TC19 | Postal — format invalide                     | `12345`                | Rejeté (*Wrong Postal Code format*)     | Rejeté — *Wrong Postal Code format*                | Succès |
| TC20 | City — hors de la liste (POST forgé)         | `Laval`                | Rejeté (ville hors liste autorisée)     | **Accepté (302 → /results)**                       | **Échec** |

**Bilan : 19/20 en Succès.** Le seul **Échec**, TC20, est un *vrai défaut* : la ville
`Laval` (hors de la liste {Ottawa, Toronto, Montreal, Halifax}) est acceptée car le champ
`city` n'a aucune validation côté serveur (voir ci-dessous).

### Défauts révélés par l'analyse / le test

1. **La ville n'est pas validée côté serveur** (champ `city` sans aucune annotation).
   Le menu déroulant restreint le choix dans l'interface, mais une requête POST forgée
   avec une ville arbitraire (`Laval`) contourne la liste autorisée (TC20).
2. **Bug du gabarit du menu déroulant** : dans `form.html`, chaque `<option>` est rendue
   avec `th:value="${''}"` — toutes les options ont une **valeur vide**. Sélectionner
   une ville dans l'interface envoie donc `city=""` quelle que soit la sélection.

### Captures d'écran

**Rejet — UserName trop court** (`AlexTT`, 5 caractères) : le formulaire est réaffiché
avec le message *« Size of UserName must be between 6 and 12 »* (cf. TC02).

![Formulaire avec erreur de validation sur UserName](assets/Screenshot1.png)

**Acceptation — toutes les valeurs valides** : redirection vers `/results` affichant
« *Congratulations Alexandre Turgeon!. You are now a member of our site..* » (cf. TC01).

![Page de confirmation après une inscription valide](assets/Screenshot2.png)

---

## Exercice 2 — Tests JUnit de `Date.nextDate()`

La classe `Date` ([`ecs/src/Date.java`](./ecs/src/Date.java)) valide ses arguments dans
le constructeur (`year >= 0`, `1 ≤ month ≤ 12`, `1 ≤ day ≤` nombre de jours du mois, avec
gestion des années bissextiles — y compris la règle des années séculaires divisibles par
400) et expose `nextDate()` qui retourne le lendemain.

La suite de 20 cas de test de la spécification est implémentée de **trois** façons :

| Fichier                                                                                   | Runner                    | Cas couverts                       |
| ----------------------------------------------------------------------------------------- | ------------------------- | ---------------------------------- |
| [`ecs/test/DateTest.java`](./ecs/test/DateTest.java)                                       | JUnit 5 explicite         | les 20 cas (un `@Test` par cas)    |
| [`ecs/test/DateNextDateOkTest.java`](./ecs/test/DateNextDateOkTest.java)                   | JUnit 4 `Parameterized`   | 15 cas sans exception              |
| [`ecs/test/DateNextDateExceptionTest.java`](./ecs/test/DateNextDateExceptionTest.java)     | JUnit 4 `Parameterized`   | 5 cas levant `IllegalArgumentException` |

### Cas de test (spécification)

| TC | Entrée (y m d) | Résultat attendu        |
| -- | -------------- | ----------------------- |
| 1  | 1700 06 20     | 1700 06 21              |
| 2  | 2005 04 15     | 2005 04 16              |
| 3  | 1901 07 20     | 1901 07 21              |
| 4  | 3456 03 27     | 3456 03 28              |
| 5  | 1500 02 17     | 1500 02 18              |
| 6  | 1700 06 29     | 1700 06 30              |
| 7  | 1800 11 29     | 1800 11 30              |
| 8  | 3453 01 29     | 3453 01 30              |
| 9  | 444 02 29      | 444 03 01 (bissextile)  |
| 10 | 2005 04 30     | 2005 05 01              |
| 11 | 3453 01 30     | 3453 01 31              |
| 12 | 3456 03 30     | 3456 03 31              |
| 13 | 1901 07 31     | 1901 08 01              |
| 14 | 3453 01 31     | 3453 02 01              |
| 15 | 3456 12 31     | 3457 01 01              |
| 16 | 1500 02 31     | IllegalArgumentException |
| 17 | 1500 02 29     | IllegalArgumentException (1500 non bissextile) |
| 18 | -1 10 20       | IllegalArgumentException |
| 19 | 1458 15 12     | IllegalArgumentException |
| 20 | 1975 6 -50     | IllegalArgumentException |

### Résultat d'exécution (`./bin/test`)

Les **59 tests passent** (vérifié sur Java 23) : `DateTest` (sample + tc01..tc20 = 21),
`BitTest` (14), `BitAndTest` (4), `DateNextDateOkTest` (15), `DateNextDateExceptionTest` (5).

```text
.
├─ JUnit Jupiter ✔
│  ├─ DateTest ✔
│  │  ├─ tc01_1700_06_20() ✔   ...   ├─ tc20_negativeDay() ✔   (+ nextDate_sample)
│  └─ BitTest ✔
└─ JUnit Vintage ✔
   ├─ BitAndTest ✔                        (4 paramètres)
   ├─ DateNextDateOkTest ✔                (15 paramètres)
   └─ DateNextDateExceptionTest ✔         (5 paramètres)

[        59 tests found           ]
[        59 tests successful      ]
[         0 tests failed          ]
```

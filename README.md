# seg3503_playground

## SEG3503 — Lab 1

| Outline   | Value                    |
| --------- | ------------------------ |
| Course    | SEG 3503                 |
| Date      | Summer 2026              |
| Student   | Alex Turgeon             |
| Email     | aturg052@uottawa.ca      |
| Professor |                          |
| TA        |                          |

Bac à sable pour le laboratoire 1 du cours SEG3503. Le dépôt contient deux petites
implémentations d'une fonction de division entière :

- [`newmath_java/`](./newmath_java) — Java + JUnit 5
- [`newmath_ex/`](./newmath_ex) — Elixir + ExUnit

Les scripts `bin/run`, `bin/test`, `bin/compile` sont des scripts **bash**. Sous
Windows, exécutez-les depuis **Git Bash** (et non PowerShell), car ils utilisent
des séparateurs de chemin Unix.

---

## newmath_java (Java + JUnit)

### Prérequis
- JDK 11+ (testé avec OpenJDK 23)
- Git Bash sous Windows (ou un shell bash sous macOS/Linux)

### Exécuter le programme interactif

```bash
cd newmath_java
./bin/run
```

Le programme demande un numérateur et un dénominateur, affiche le résultat de
la division entière, et recommence jusqu'à ce que l'on tape `exit`.

### Exécuter les tests JUnit

```bash
cd newmath_java
./bin/test
```

Deux tests sont attendus : `div_ok()` et `div_by_zero()`.

### Capture d'écran — Java run

_(insérer une capture d'écran de `./bin/run` ici)_

### Capture d'écran — Java test (JUnit)

_(insérer une capture d'écran de `./bin/test` ici)_

---

## newmath_ex (Elixir + ExUnit)

### Prérequis
- Elixir 1.14+ (installe automatiquement Erlang/OTP comme dépendance)

### Exécuter le programme interactif (IEx)

```bash
cd newmath_ex
./bin/run
```

Puis, dans la session IEx :

```elixir
iex> NewmathEx.div(5, 2)
{:ok, 2.5}
iex> NewmathEx.div(5, 0)
{:error, "Cannot divide by zero"}
```

### Exécuter les tests ExUnit

```bash
cd newmath_ex
./bin/test
```

Trois assertions sont attendues : 1 doctest + 2 tests.

### Capture d'écran — Elixir run

_(insérer une capture d'écran de `./bin/run` ici)_

### Capture d'écran — Elixir test (ExUnit)

_(insérer une capture d'écran de `./bin/test` ici)_

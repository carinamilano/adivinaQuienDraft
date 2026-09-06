# Adivina Quién — TP Programación III

Implementación del juego "Adivina Quién" (23 superhéroes) en dos modalidades —
**Jugador vs Máquina** y **Máquina vs Máquina** — por consola y por interfaz
gráfica (Swing), usada como vehículo para aplicar los algoritmos de la
materia: **Divide y Conquista** (Merge Sort y Búsqueda Binaria) y **Greedy**.

Este documento es la justificación formal pedida para la entrega: qué
algoritmo se usó en cada punto, por qué ese y no otro visto en clase, y qué
decisiones de diseño no serían obvias solo leyendo el código.

## 1. Arquitectura, en una frase por paquete

| Paquete | Responsabilidad |
|---|---|
| `entidades` | `Personaje`: solo datos (id, género, nombre, 8 atributos booleanos). Cero lógica. |
| `algoritmos` | Los tres algoritmos y la clase `Caracteristica` ("tarjeta de pregunta"). |
| `motor` | `GestorPartida`: carga los 23 personajes, corre el Merge Sort inicial y muestra el menú. No implementa ningún modo de juego. |
| `motor.humanoVsMaquina` | `PartidaHumanoVsMaquina`: modo 1, por consola. |
| `motor.maquinaVsMaquina` | `PartidaMaquinaVsMaquina`: modo 2, por consola. |
| `GUI` | `MiVentana`: misma lógica de `algoritmos`, expuesta con botones/diálogos Swing en vez de `Scanner`. |

La separación en paquetes es a propósito: cada algoritmo vive en su propia
clase dentro de `algoritmos`, y cada modo de juego en su propia clase, para
que la responsabilidad de cada una quede clara (principio de responsabilidad
única). No se usaron interfaces ni herencia — no hacía falta: hay exactamente
dos modos de juego fijos, y agregar un patrón de diseño clásico (Strategy,
Factory) para dos casos hubiese sido complejidad sin beneficio real. El foco
del TP es la aplicación correcta y justificada de los algoritmos de la
materia, no la arquitectura de software en sí.

## 2. Algoritmos aplicados

### 2.1 Merge Sort — Divide y Conquista
**Dónde:** `algoritmos/OrdenadorMerge.java`, llamado una sola vez desde
`GestorPartida.iniciar()` antes de que arranque cualquier partida.

**Por qué:** la consigna arranca con los personajes ordenados *solo* por
género, con IDs mezclados a propósito (`GestorPartida.cargarPersonajes()`).
Hace falta dejarlos ordenados 1..23 antes de jugar, porque después
`BuscadorBinario` necesita el array ordenado para funcionar.

**Cómo aplica Divide y Conquista:** dividir el array al medio, ordenar cada
mitad recursivamente (caso base: 0 o 1 elemento), y **combinar** ambas
mitades ya ordenadas comparando de a un elemento. El paso de "combinar" es
lo que distingue a Merge Sort de la Búsqueda Binaria de abajo.

**Complejidad:** O(n log n).

**Por qué no otra cosa:** se podría insertar cada personaje ordenado uno por
uno usando búsqueda binaria para encontrar la posición (inserción binaria),
pero los corrimientos de elementos la vuelven O(n²) en el peor caso. Merge
Sort es el ejemplo real de Divide y Conquista *con combinación* visto en
teoría.

### 2.2 Búsqueda Binaria — Divide y Conquista
**Dónde:** `algoritmos/BuscadorBinario.java`. Usado en:
- `PartidaHumanoVsMaquina` (consola): para resolver el ID que el jugador
  elige como su secreto, y para resolver un "arriesgo" de ID directo en
  cualquier turno.
- `GUI/MiVentana.java` (`iniciarPartidaHumanoVsMaquina` y el botón
  **Arriesgar**): los combos muestran `"ID - Nombre"`, pero la selección
  siempre se resuelve por ID con `buscadorBinario.buscarPorId(...)`, nunca
  por posición dentro de la lista o del combo. Esto se unificó a propósito
  para que la GUI use el mismo algoritmo que la consola en vez de resolver
  la selección "gratis" con un `HashMap`.

**Por qué:** la consigna pide poder "lanzar directamente la suposición" de
un ID. Como el array ya está ordenado (gracias a 2.1), no hace falta
recorrerlo entero: se compara con el elemento del medio y se descarta la
mitad que no puede contener el ID buscado.

**Complejidad:** O(log n). Con solo 23 personajes la diferencia contra
lineal es cosmética, pero el ejercicio pedía demostrar el algoritmo, y ya
que el array está ordenado no tenía sentido no aprovecharlo.

**Nota de diseño:** la grilla de personajes de la GUI (clicks sobre
personajes) sigue usando un `HashMap<JButton, Personaje>` para saber qué
Personaje corresponde a qué botón — eso es UI pura (asociar un widget con un
dato), no una "búsqueda" en el sentido algorítmico, así que no es un lugar
donde correspondiera aplicar Búsqueda Binaria.

### 2.3 Greedy (voraz)
**Dónde:** `algoritmos/DecisorGreedy.elegirMejorPregunta()`, usado por la
máquina en ambos modos (nunca por el jugador humano, que elige su pregunta
manualmente de un menú).

**Elementos del algoritmo** (mismo molde que "el problema del cambio" visto
en teoría):
- **Conjunto de candidatos:** las 7 características todavía no preguntadas
  (`vuela`, `usaCapa`, `esDC`, `tienePoderesMagicos`, `usaMascara`,
  `tieneSuperFuerza`, `esHumano`).
- **Función de selección:** la que divide el grupo de "vivos" más parejo
  posible (más cerca de 50/50).
- **Función de factibilidad:** descarta preguntas ya hechas, y las que no
  separan nada (si todos los vivos comparten el mismo valor).
- **Función solución:** se llega cuando queda 1 solo candidato.

**Por qué "mejor" = "más pareja" y no "la que más descarta":** la máquina no
controla qué respuesta le va a tocar, así que tiene que asumir el peor caso
(quedarse con el subgrupo más grande de los dos posibles). La pregunta que
minimiza ese peor caso es la que divide más parejo. Por eso el código usa
`Math.max(cantSi, cantNo)` como "puntaje de peor caso" y busca el mínimo de
esos máximos.

**Empates:** si dos o más características empatan en el mismo peor caso, se
elige una al azar entre las empatadas (`DecisorGreedy.java:142-149`), para
que la máquina no repita siempre la misma primera pregunta en cada partida.

**Fallback (candidatos "gemelos"):** con solo 7 características hay pares de
personajes que nunca se diferencian entre sí en el pool de preguntas
disponible (ej. Spider-Gwen y Spiderman solo difieren en `esMujer`, que se
excluye a propósito — ver 3.2). Cuando `elegirMejorPregunta` devuelve `null`
(no hay ninguna característica que separe al grupo), la máquina arriesga al
azar entre los candidatos restantes en vez de trabarse sin poder terminar la
partida.

**Dónde entra Divide y Conquista dentro del Greedy:** `reducirGrupo()`, una
vez conocida la respuesta, **parte** el grupo de vivos en dos subgrupos
(cumple / no cumple) y descarta el que no corresponde — es la misma idea de
"dividir y descartar una mitad" de la Búsqueda Binaria, aplicada sobre un
grupo filtrado por atributo en vez de sobre un rango ordenado.

## 3. Decisiones de diseño no obvias

### 3.1 Por qué el juego es simétrico (ambos preguntan)
En los dos modos, ambas partes (jugador y máquina, o máquina 1 y máquina 2)
mantienen su propia lista de "vivos" y se van preguntando por turnos. Esto
permite que el mismo `DecisorGreedy` se ejercite en ambos lados y que el
modo Máquina vs Máquina tenga sentido como demostración pura del algoritmo.

### 3.2 Por qué `esMujer` no es una pregunta del Greedy
Está en `Personaje` y se usa para el orden inicial (agrupado por género),
pero **no** se agrega a `todasLasCaracteristicas` en `DecisorGreedy`. Motivo
documentado en el propio código
(`DecisorGreedy.java:63-66`): con 12 mujeres y 11 varones el split es casi
50/50, así que sería *siempre* la primera pregunta elegida por el Greedy en
cualquier partida — le quita variedad a la demostración del algoritmo.

### 3.3 Consistencia consola/GUI
Ambas interfaces llaman literalmente a las mismas clases de `algoritmos`
(no hay una reimplementación paralela de Greedy o Búsqueda Binaria para la
GUI). La GUI existe para mostrar el mismo motor con una experiencia más
amigable, no como una versión alternativa del juego.

### 3.4 Máquina vs Máquina: revelado turno a turno
`MiVentana.iniciarPartidaMaquinaVsMaquina()` corre la partida completa en
memoria (reutilizando `PartidaMaquinaVsMaquina` tal cual), pero la captura
de consola se corta por turno y se muestra en un diálogo con un botón
**"Turno siguiente"**: cada click revela un turno más (pregunta de M1 +
pregunta de M2) sin mostrar los que faltan, y como el texto se acumula, al
llegar al final quedan visibles todas las decisiones tomadas durante la
partida.

## 4. Algoritmos vistos en clase que **no** se aplicaron (y por qué)

| Algoritmo | Por qué no aplica acá |
|---|---|
| **Mochila fraccionaria** (Greedy) | Rankea por relación valor/peso. Las preguntas del juego no tienen costo ni beneficio distinto entre sí — solo "cuán parejo divide". |
| **Huffman** (Greedy) | Arma un árbol completo de antemano conociendo todas las frecuencias fijas. Acá la máquina decide la siguiente pregunta *sobre la marcha*, turno a turno, con candidatos que cambian dinámicamente. |
| **Matrimonios estables** (Greedy) | Resuelve emparejamiento con preferencias cruzadas entre dos conjuntos distintos. No hay dos conjuntos que emparejar en este juego. |
| **Búsqueda lineal** | Serviría igual con solo 23 elementos (O(n) es despreciable acá), pero no demuestra Divide y Conquista, y como el array ya está ordenado (por Merge Sort) no tenía sentido no aprovechar esa propiedad con Búsqueda Binaria. |
| **Ordenamiento por inserción binaria** | Válido como variante de Divide y Conquista, pero es O(n²) en el peor caso por los corrimientos de elementos; se prefirió Merge Sort por ser O(n log n) real y por incluir el paso de "combinar" que pide la teoría. |
| **Programación dinámica** | No hay subproblemas superpuestos ni necesidad de memoización: cada pregunta achica el grupo de candidatos de forma directa e irreversible, no hay recomputo de estados repetidos. |
| **Patrones de diseño GoF** (Strategy, Factory, Singleton) | El TP tiene exactamente dos modos de juego fijos y un solo punto de construcción de personajes; introducir polimorfismo o fábricas para dos casos agrega complejidad sin un problema real que resuelva. Se prefirió separación por paquete/clase (responsabilidad única) antes que un patrón formal. |

## 5. Preguntas típicas de defensa (con respuesta corta)

**¿Por qué el Greedy no garantiza el mínimo número de preguntas posible?**
Porque es voraz: en cada paso toma la decisión localmente óptima (la
pregunta más pareja para el peor caso *de ese turno*), sin mirar para
adelante. Un árbol de decisión óptimo global (tipo Huffman con frecuencias
conocidas de antemano) podría necesitar menos preguntas en promedio, pero
acá no conocemos de antemano cuál personaje es el secreto, así que no hay
"frecuencias" que optimizar globalmente — por eso Greedy es la elección
correcta y no una aproximación de algo mejor.

**¿Qué pasa si el Greedy no encuentra ninguna pregunta útil?**
Devuelve `null` (ver 2.3, "candidatos gemelos") y el modo de juego que lo
llama arriesga al azar entre los candidatos restantes, para no trabar la
partida.

**¿Por qué Búsqueda Binaria y no Búsqueda Lineal si son solo 23 elementos?**
Ver tabla de la sección 4: la complejidad no es la razón práctica (con 23
elementos es imperceptible), la razón es demostrar Divide y Conquista y
aprovechar que el array ya quedó ordenado por Merge Sort.

**¿Reducir el grupo de "vivos" (`reducirGrupo`) es Greedy o Divide y
Conquista?**
Es el paso de Divide y Conquista *dentro* del ciclo Greedy: la elección de
qué preguntar es voraz, pero una vez que se sabe la respuesta, partir el
grupo en dos y quedarse con el subgrupo correcto es "dividir y descartar
una mitad" — la misma idea que Búsqueda Binaria, aplicada sobre un filtro
de atributo en vez de un rango numérico.

**¿Por qué la GUI y la consola no comparten un modo de juego con
polimorfismo (interfaz común)?**
Ver 4, última fila: son solo dos modos fijos; ambos reutilizan las mismas
clases de `algoritmos` sin reimplementar nada, así que el beneficio de una
interfaz común sería mínimo frente a la complejidad de introducirla.

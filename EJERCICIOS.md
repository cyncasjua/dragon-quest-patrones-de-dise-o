# Solución al Taller de Patrones de Diseño: Combat Simulator

Este documento contiene la identificación y justificación de los patrones de diseño a aplicar en los distintos escenarios propuestos para refactorizar el proyecto **Combat Simulator**, junto con la definición de cada patrón según el material del taller.

---

## 1. Añadir un nuevo tipo de ataque

**Situación:** Quieres añadir el ataque "Meteoro" y notas que en `CombatEngine` hay sentencias `switch` que crecen continuamente tanto en `createAttack()` como en `calculateDamage()`.

* **¿Qué problema te encuentras al añadir "Meteoro"?**
    El principal problema es que te ves obligado a modificar una clase fundamental y existente (`CombatEngine`) para añadir un nuevo comportamiento. Esto viola directamente el **Principio de Abierto/Cerrado (Open/Closed Principle - OCP)** de SOLID.
* **¿Qué pasa si mañana piden 10 ataques más?**
    La sentencia `switch` crecerá de forma descontrolada. El código se volverá muy difícil de leer y mantener, y aumentará la probabilidad de introducir errores.
* **¿Qué patrón permitiría añadir ataques sin modificar `CombatEngine`?**
    El patrón **Factory Method**. En la presentación se define como: *"Una fábrica de objetos para simplificar nuestro código"*[cite: 44]. Este patrón delega la lógica de instanciación a una clase fábrica, aislando al `CombatEngine` de los detalles de creación.

---

## 2. Añadir una nueva fórmula de daño

**Situación:** Diferentes tipos de ataques (NORMAL, SPECIAL, STATUS, CRÍTICO) requieren fórmulas de daño completamente distintas y el `switch` actual en `calculateDamage()` no es escalable.

* **¿Qué principio SOLID se viola al añadir otro case en el switch?**
    Se violan dos principios principalmente:
    1.  **OCP (Open/Closed Principle)**: Porque hay que modificar el método cada vez que se añade una nueva fórmula.
    2.  **SRP (Single Responsibility Principle)**: Porque `CombatEngine` asume la responsabilidad de conocer todas las reglas matemáticas de todos los tipos de ataque.
* **¿Qué patrón permitiría tener fórmulas de daño intercambiables sin tocar el código existente?**
    El patrón **Strategy**. Este patrón sirve para definir "EL CÓMO" [cite: 96] aislando cada familia de algoritmos (como el cálculo de distintos precios [cite: 76]) y encapsulando cada uno en su propia clase implementando una interfaz común para hacerlos intercambiables dinámicamente.

---

## 3. Crear personajes con muchas estadísticas

**Situación:** La clase `Character` empieza a tener constructores con más de 10 parámetros, algunos opcionales (equipamiento, buffs, clase).

* **¿Qué problema tiene un constructor con muchos parámetros?**
    Genera el antipatrón *Telescoping Constructor*. Como señala la presentación: *"Cuando un método tiene 15 parámetros ya no mola"*[cite: 60]. Es fácil confundir el orden de los argumentos y manejar parámetros opcionales es engorroso.
* **¿Cómo harías para que `new Character(...)` sea legible cuando hay valores por defecto?**
    Se debería utilizar un objeto intermediario que asigne valores por defecto desde el principio y ofrezca métodos descriptivos para sobreescribir únicamente las propiedades que nos interesen.
* **¿Qué patrón permite construir objetos complejos paso a paso?**
    El patrón **Builder**. El taller lo define como: *"Un chef que arma objetos paso a paso"*[cite: 59]. Permite separar la construcción de un objeto complejo de su representación, logrando inicializaciones muy limpias.

---

## 4. Un único almacén de batallas

**Situación:** `BattleRepository` necesita almacenar las batallas en memoria de forma global, pero hacer `new BattleRepository()` constantemente crea riesgos de aislar los datos.

* **¿Qué pasaría si dos clases crean su propio `BattleRepository` sin el modificador `static`?**
    Se crearían múltiples instancias diferentes del repositorio en memoria y los datos no se compartirían entre las diferentes partes de la aplicación.
* **¿Cómo asegurar que toda la aplicación use la misma instancia de almacenamiento?**
    Haciendo el constructor de la clase privado y exponiendo un método estático que devuelva siempre la misma instancia previamente instanciada.
* **¿Qué patrón garantiza una única instancia de una clase?**
    El patrón **Singleton**. En las diapositivas se ilustra como: *"Un objeto único para gobernarlos a todos"*[cite: 38]. 

---

## 5. Recibir datos de un API externo

**Situación:** El `BattleController` recibe distintos formatos JSON de proveedores externos y hace el mapeo manualmente hacia el dominio (`Character`, `Battle`).

* **¿Qué problema hay en poner la lógica de conversión en el controller?**
    Viola el **Principio de Responsabilidad Única (SRP)**. El controlador está diseñado para gestionar el tráfico HTTP, no para acoplar las interfaces incompatibles de proveedores externos.
* **¿Cómo aislar la conversión "formato externo → nuestro dominio" para no ensuciar el controller?**
    Creando una capa intermedia o una clase dedicada específicamente a transformar la estructura externa a la estructura requerida por nuestro dominio.
* **¿Qué patrón permite que un objeto "adaptado" se use como si fuera uno de los nuestros?**
    El patrón **Adapter**. Según la presentación, su propósito se puede resumir en la pregunta: *"Ey, cómo conecto mi impresora de los 90 a mi Macbook Pro?"*[cite: 65]. Funciona adaptando una interfaz antigua o externa al formato que necesitamos para funcionar internamente.
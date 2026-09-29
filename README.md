# Inventario TDA (Actividad 02 - Estructura de Datos)

**Autor:** Galileo Villegas Sanchez
**Matricula:** 20251015

Aplicacion de escritorio en Java con JavaFX que implementa un TDA de
Producto para administrar un inventario, aplicando encapsulamiento,
herencia, recursividad y buenas practicas de programacion.

## Requisitos para ejecutar

- JDK 17 o superior
- Maven 3.8 o superior (el plugin de JavaFX descarga los binarios
  necesarios automaticamente, no requiere instalar JavaFX por separado)

## Como ejecutar

```bash
mvn clean javafx:run
```

Esto compila el proyecto y abre la ventana de la aplicacion.

## Estructura del proyecto

```
InventarioTDA/
├── pom.xml
└── src/main/java/com/inventario/
    ├── InventarioApp.java          (interfaz grafica JavaFX)
    └── modelo/
        ├── Producto.java           (TDA base)
        ├── ProductoConCaducidad.java (subclase - herencia)
        └── Inventario.java         (colecciones + metodos recursivos)
```

## Como se cubren los requisitos de la actividad

- **TDA:** clase `Producto` con 4 atributos encapsulados (codigo,
  nombre, precio, cantidad) y mas de 8 metodos, 5 de ellos de logica
  de dominio: `calcularValorTotal`, `aplicarDescuento`,
  `aumentarStock`, `disminuirStock`, `estaDisponible`.
- **Herencia:** `ProductoConCaducidad extends Producto`, agrega
  atributos propios (`diasParaCaducar`, `lote`) y sobrescribe
  `aplicarDescuento`.
- **Constructores con validaciones:** en `Producto`,
  `ProductoConCaducidad` e `Inventario`.
- **Recursividad:** `Inventario.buscarRecursivo` (busqueda por codigo)
  y `Inventario.calcularValorRecursivo` (suma del valor del
  inventario), ambos con caso base y condicion de avance claramente
  comentados en el codigo.
- **GUI:** ventana JavaFX con formulario, tabla, botones para agregar,
  eliminar, buscar y calcular el valor total.
- **Autoria:** nombre y matricula en la cabecera de cada archivo y
  visibles en la parte superior de la interfaz.

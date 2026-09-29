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



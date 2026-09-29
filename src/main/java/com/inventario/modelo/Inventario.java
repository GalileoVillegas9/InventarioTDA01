package com.inventario.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Inventario
 * Autor: Galileo Villegas Sanchez
 * Matricula: 20251015
 *
 * Administra una coleccion de productos (TDA Producto) y ofrece
 * operaciones de negocio sobre ellos, incluyendo busqueda y
 * calculo de valores mediante recursividad.
 */
public class Inventario {

    private List<Producto> productos;
    private String nombreAlmacen;

    public Inventario(String nombreAlmacen) {
        if (nombreAlmacen == null || nombreAlmacen.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del almacen no puede estar vacio.");
        }
        this.nombreAlmacen = nombreAlmacen;
        this.productos = new ArrayList<>();
    }

    public String getNombreAlmacen() {
        return nombreAlmacen;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    /**
     * Agrega un producto al inventario, validando que el codigo sea unico.
     */
    public void agregarProducto(Producto producto) {
        if (buscarProductoPorCodigo(producto.getCodigo()) != null) {
            throw new IllegalStateException("Ya existe un producto con ese codigo.");
        }
        productos.add(producto);
    }

    /**
     * Elimina un producto del inventario segun su codigo.
     */
    public boolean eliminarProducto(String codigo) {
        Producto p = buscarProductoPorCodigo(codigo);
        if (p != null) {
            productos.remove(p);
            return true;
        }
        return false;
    }

    /**
     * Busca un producto por codigo. Punto de entrada publico
     * del metodo recursivo buscarRecursivo.
     */
    public Producto buscarProductoPorCodigo(String codigo) {
        return buscarRecursivo(codigo, 0);
    }

    /**
     * Metodo RECURSIVO requerido por la actividad.
     * Recorre la lista de productos comparando el codigo.
     *
     * Caso base 1 (fin de la lista): indice fuera de rango -> no se
     *   encontro el producto, se retorna null.
     * Caso base 2 (coincidencia): el producto en el indice actual
     *   coincide con el codigo buscado -> se retorna.
     * Condicion de avance: si no coincide, se llama de nuevo la
     *   funcion con indice + 1 hasta cumplir alguno de los casos base.
     */
    private Producto buscarRecursivo(String codigo, int indice) {
        if (indice >= productos.size()) {
            return null;
        }
        if (productos.get(indice).getCodigo().equalsIgnoreCase(codigo)) {
            return productos.get(indice);
        }
        return buscarRecursivo(codigo, indice + 1);
    }

    /**
     * Calcula el valor total del inventario. Punto de entrada publico
     * del metodo recursivo calcularValorRecursivo.
     */
    public double calcularValorTotalInventario() {
        return calcularValorRecursivo(0);
    }

    /**
     * Metodo recursivo auxiliar que suma el valor de cada producto.
     * Caso base: el indice llega al final de la lista, se retorna 0.
     * Condicion de avance: se suma el valor del producto actual con
     * el resultado de la llamada recursiva sobre el resto de la lista.
     */
    private double calcularValorRecursivo(int indice) {
        if (indice >= productos.size()) {
            return 0.0;
        }
        return productos.get(indice).calcularValorTotal() + calcularValorRecursivo(indice + 1);
    }

    /**
     * Cuenta cuantos productos tienen existencias disponibles.
     */
    public int contarProductosDisponibles() {
        int contador = 0;
        for (Producto p : productos) {
            if (p.estaDisponible()) {
                contador++;
            }
        }
        return contador;
    }
}

package com.inventario.modelo;

/**
 * TDA Producto
 * Autor: Galileo Villegas Sanchez
 * Matricula: 20251015
 *
 * Representa un producto generico dentro del inventario.
 * Encapsula los atributos basicos y la logica de negocio
 * relacionada con el manejo de stock y precios.
 */
public class Producto {

    // Atributos propios del modelo (encapsulados)
    private String codigo;
    private String nombre;
    private double precio;
    private int cantidad;

    /**
     * Constructor con validaciones de los datos de entrada.
     */
    public Producto(String codigo, String nombre, double precio, int cantidad) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El codigo del producto no puede estar vacio.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacio.");
        }
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa.");
        }
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    // ---------------------------------------------------------
    // Getters y setters (encapsulamiento)
    // ---------------------------------------------------------

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre;
        }
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        if (precio >= 0) {
            this.precio = precio;
        }
    }

    public int getCantidad() {
        return cantidad;
    }

    // ---------------------------------------------------------
    // Metodos de logica / procesamiento especificos del dominio
    // ---------------------------------------------------------

    /**
     * Calcula el valor monetario total de este producto en inventario
     * (precio unitario multiplicado por la cantidad en existencia).
     */
    public double calcularValorTotal() {
        return precio * cantidad;
    }

    /**
     * Aplica un descuento porcentual al precio del producto.
     * @param porcentaje valor entre 0 y 100
     */
    public void aplicarDescuento(double porcentaje) {
        if (porcentaje < 0 || porcentaje > 100) {
            throw new IllegalArgumentException("El porcentaje de descuento debe estar entre 0 y 100.");
        }
        this.precio = this.precio - (this.precio * porcentaje / 100.0);
    }

    /**
     * Aumenta el stock disponible del producto.
     */
    public void aumentarStock(int unidades) {
        if (unidades < 0) {
            throw new IllegalArgumentException("No se puede aumentar el stock con un valor negativo.");
        }
        this.cantidad += unidades;
    }

    /**
     * Disminuye el stock disponible, validando que exista suficiente cantidad.
     */
    public void disminuirStock(int unidades) {
        if (unidades < 0) {
            throw new IllegalArgumentException("No se puede disminuir el stock con un valor negativo.");
        }
        if (unidades > this.cantidad) {
            throw new IllegalStateException("No hay suficiente stock disponible para esa operacion.");
        }
        this.cantidad -= unidades;
    }

    /**
     * Indica si el producto tiene existencias disponibles para su venta.
     */
    public boolean estaDisponible() {
        return this.cantidad > 0;
    }

    @Override
    public String toString() {
        return String.format("%s | %s | $%.2f | %d u.", codigo, nombre, precio, cantidad);
    }
}

package com.inventario.modelo;

/**
 * ProductoConCaducidad
 * Autor: Galileo Villegas Sanchez
 * Matricula: 20251015
 *
 * Extiende (herencia) el TDA Producto agregando el comportamiento
 * especifico de articulos con fecha de caducidad (ej. alimentos).
 */
public class ProductoConCaducidad extends Producto {

    private int diasParaCaducar;
    private String lote;

    /**
     * Constructor con validaciones propias de esta subclase.
     */
    public ProductoConCaducidad(String codigo, String nombre, double precio, int cantidad,
                               int diasParaCaducar, String lote) {
        super(codigo, nombre, precio, cantidad);
        if (diasParaCaducar < 0) {
            throw new IllegalArgumentException("Los dias para caducar no pueden ser negativos.");
        }
        if (lote == null || lote.trim().isEmpty()) {
            throw new IllegalArgumentException("El lote no puede estar vacio.");
        }
        this.diasParaCaducar = diasParaCaducar;
        this.lote = lote;
    }

    public int getDiasParaCaducar() {
        return diasParaCaducar;
    }

    public void setDiasParaCaducar(int dias) {
        if (dias >= 0) {
            this.diasParaCaducar = dias;
        }
    }

    public String getLote() {
        return lote;
    }

    /**
     * Indica si el producto esta proximo a caducar (5 dias o menos).
     */
    public boolean estaProximoACaducar() {
        return diasParaCaducar <= 5;
    }

    /**
     * Sobrescribe (polimorfismo) el descuento heredado: si el producto
     * esta proximo a caducar, se suma un 10% adicional al descuento pedido.
     */
    @Override
    public void aplicarDescuento(double porcentaje) {
        double porcentajeFinal = porcentaje;
        if (estaProximoACaducar()) {
            porcentajeFinal = Math.min(porcentaje + 10, 100);
        }
        super.aplicarDescuento(porcentajeFinal);
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Lote: %s | Caduca en %d dia(s)", lote, diasParaCaducar);
    }
}

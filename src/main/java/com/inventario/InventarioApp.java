package com.inventario;

import com.inventario.modelo.Inventario;
import com.inventario.modelo.Producto;
import com.inventario.modelo.ProductoConCaducidad;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * InventarioApp
 * Autor: Galileo Villegas Sanchez
 * Matricula: 20251015
 *
 * Actividad 02: Implementacion de Tipo de Dato Abstracto (TDA) en Java.
 * Interfaz grafica (JavaFX) para dar de alta, eliminar, buscar y
 * valorizar productos administrados por el TDA Inventario.
 */
public class InventarioApp extends Application {

    private final Inventario inventario = new Inventario("Abarrotes Capilla");
    private final ObservableList<Producto> datosTabla = FXCollections.observableArrayList();
    private final Label lblValorTotal = new Label("Valor total del inventario: $0.00");

    @Override
    public void start(Stage stage) {

        // ---------- Encabezado con autoria (visible en la interfaz) ----------
        Label lblTitulo = new Label("Sistema de Inventario - Abarrotes Capilla");
        lblTitulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label lblAutor = new Label("Autor: Galileo Villegas Sanchez  |  Matricula: 20251015");
        lblAutor.setStyle("-fx-font-size: 12px; -fx-text-fill: #555555;");

        VBox encabezado = new VBox(4, lblTitulo, lblAutor);
        encabezado.setPadding(new Insets(10));
        encabezado.setAlignment(Pos.CENTER);

        // ---------- Formulario de captura ----------
        TextField txtCodigo = new TextField();
        txtCodigo.setPromptText("Codigo");
        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre");
        TextField txtPrecio = new TextField();
        txtPrecio.setPromptText("Precio");
        TextField txtCantidad = new TextField();
        txtCantidad.setPromptText("Cantidad");

        CheckBox chkPerecedero = new CheckBox("Es perecedero");
        TextField txtDias = new TextField();
        txtDias.setPromptText("Dias para caducar");
        txtDias.setDisable(true);
        TextField txtLote = new TextField();
        txtLote.setPromptText("Lote");
        txtLote.setDisable(true);

        chkPerecedero.selectedProperty().addListener((obs, oldVal, newVal) -> {
            txtDias.setDisable(!newVal);
            txtLote.setDisable(!newVal);
        });

        GridPane form = new GridPane();
        form.setHgap(8);
        form.setVgap(8);
        form.setPadding(new Insets(10));
        form.addRow(0, new Label("Codigo:"), txtCodigo, new Label("Nombre:"), txtNombre);
        form.addRow(1, new Label("Precio:"), txtPrecio, new Label("Cantidad:"), txtCantidad);
        form.addRow(2, chkPerecedero, txtDias, new Label("Lote:"), txtLote);

        Label lblMensaje = new Label();

        // ---------- Tabla de productos ----------
        TableView<Producto> tabla = new TableView<>(datosTabla);

        TableColumn<Producto, String> colCodigo = new TableColumn<>("Codigo");
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));

        TableColumn<Producto, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Producto, Double> colPrecio = new TableColumn<>("Precio");
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        TableColumn<Producto, Integer> colCantidad = new TableColumn<>("Cantidad");
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        tabla.getColumns().addAll(colCodigo, colNombre, colPrecio, colCantidad);
        tabla.setPrefHeight(220);

        // ---------- Botones y su logica ----------
        Button btnAgregar = new Button("Agregar producto");
        btnAgregar.setOnAction(e -> agregarProducto(txtCodigo, txtNombre, txtPrecio, txtCantidad,
                chkPerecedero, txtDias, txtLote, lblMensaje));

        Button btnEliminar = new Button("Eliminar seleccionado");
        btnEliminar.setOnAction(e -> {
            Producto seleccionado = tabla.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                inventario.eliminarProducto(seleccionado.getCodigo());
                datosTabla.setAll(inventario.getProductos());
                mostrarMensaje(lblMensaje, "Producto eliminado.", true);
            } else {
                mostrarMensaje(lblMensaje, "Selecciona un producto de la tabla.", false);
            }
        });

        Button btnBuscar = new Button("Buscar por codigo");
        btnBuscar.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setHeaderText("Buscar producto");
            dialog.setContentText("Codigo:");
            dialog.showAndWait().ifPresent(codigo -> {
                Producto encontrado = inventario.buscarProductoPorCodigo(codigo.trim());
                Alert.AlertType tipo = encontrado != null ? Alert.AlertType.INFORMATION : Alert.AlertType.WARNING;
                String texto = encontrado != null
                        ? "Producto encontrado:\n" + encontrado
                        : "No se encontro un producto con ese codigo.";
                new Alert(tipo, texto).showAndWait();
            });
        });

        Button btnCalcular = new Button("Calcular valor total");
        btnCalcular.setOnAction(e -> {
            double total = inventario.calcularValorTotalInventario();
            lblValorTotal.setText(String.format("Valor total del inventario: $%.2f  |  Disponibles: %d",
                    total, inventario.contarProductosDisponibles()));
        });

        HBox botones = new HBox(10, btnAgregar, btnEliminar, btnBuscar, btnCalcular);
        botones.setPadding(new Insets(10));
        botones.setAlignment(Pos.CENTER);

        // ---------- Layout general ----------
        VBox raiz = new VBox(10, encabezado, form, lblMensaje, botones, tabla, lblValorTotal);
        raiz.setPadding(new Insets(10));

        Scene escena = new Scene(raiz, 740, 580);
        stage.setTitle("Abarrotes Capilla - Inventario TDA");
        stage.setScene(escena);
        stage.show();
    }

    /**
     * Lee el formulario, valida y agrega el producto correspondiente
     * (normal o perecedero) al inventario.
     */
    private void agregarProducto(TextField txtCodigo, TextField txtNombre, TextField txtPrecio,
                                  TextField txtCantidad, CheckBox chkPerecedero, TextField txtDias,
                                  TextField txtLote, Label lblMensaje) {
        try {
            String codigo = txtCodigo.getText().trim();
            String nombre = txtNombre.getText().trim();
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            int cantidad = Integer.parseInt(txtCantidad.getText().trim());

            Producto nuevo;
            if (chkPerecedero.isSelected()) {
                int dias = Integer.parseInt(txtDias.getText().trim());
                String lote = txtLote.getText().trim();
                nuevo = new ProductoConCaducidad(codigo, nombre, precio, cantidad, dias, lote);
            } else {
                nuevo = new Producto(codigo, nombre, precio, cantidad);
            }

            inventario.agregarProducto(nuevo);
            datosTabla.setAll(inventario.getProductos());
            mostrarMensaje(lblMensaje, "Producto agregado correctamente.", true);

            txtCodigo.clear();
            txtNombre.clear();
            txtPrecio.clear();
            txtCantidad.clear();
            txtDias.clear();
            txtLote.clear();
            chkPerecedero.setSelected(false);

        } catch (NumberFormatException ex) {
            mostrarMensaje(lblMensaje, "Precio, cantidad y dias deben ser numericos.", false);
        } catch (IllegalArgumentException | IllegalStateException ex) {
            mostrarMensaje(lblMensaje, ex.getMessage(), false);
        }
    }

    private void mostrarMensaje(Label lbl, String texto, boolean exito) {
        lbl.setStyle(exito ? "-fx-text-fill: #27ae60;" : "-fx-text-fill: #c0392b;");
        lbl.setText(texto);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

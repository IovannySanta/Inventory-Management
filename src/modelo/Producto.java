package modelo;

import java.time.LocalDate;

/*
Clase que almacena la información básica necesaria para la gestión del sistema de inventario
*/

public class Producto {
    private String productCode; // Código único
    private String productName; // Nombre del producto
    private String category; // Categoría perteneciente del producto
    private double unitPrice; // Precio unitario del producto
    private int stockQuantity; // Cantidad disponible en el inventario
    private int minimumStock; // Cantidad mínima permitida antes de generar alerta
    private String supplier; // Nombre del proveedor del producto
    private LocalDate entryDate; // Fecha de ingreso del producto

    /*
    Constructor que inicializa un objeto Producto
    */
    
    public Producto(String productCode, String productName, String category, double unitPrice, int stockQuantity, int minimumStock, String supplier, LocalDate entryDate) {
        this.productCode = productCode;
        this.productName = productName;
        this.category = category;
        this.unitPrice = unitPrice;
        this.stockQuantity = stockQuantity;
        this.minimumStock = minimumStock;
        this.supplier = supplier;
        this.entryDate = entryDate;
    }

    /* 
    Getters de la clase
    */
    
    public String getProductCode() { return productCode; }

    public String getProductName() { return productName; }

    public String getCategory() { return category; }

    public double getUnitPrice() { return unitPrice; }

    public int getStockQuantity() { return stockQuantity; }

    public int getMinimumStock() { return minimumStock; }

    public String getSupplier() { return supplier; }

    public LocalDate getEntryDate() { return entryDate; } 
}
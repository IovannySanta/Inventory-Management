package vista;

import dao.ProductoDAO;
import modelo.Producto;
import util.Validador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Clase que representa la ventana principal del sistema de inventario.
 * Permite registrar, actualizar, eliminar y visualizar productos.
 * Captura los datos ingresados por el usuario, los valida y
 * delega las operaciones al DAO.
 */

public class FrmInventario extends JFrame {

    private JTextField txtProductCode;
    private JTextField txtProductName;
    private JTextField txtCategory;
    private JTextField txtUnitPrice;
    private JTextField txtStockQuantity;
    private JTextField txtMinimumStock;
    private JTextField txtSupplier;
    private JTextField txtEntryDate;

    private JTable productTable;
    private DefaultTableModel tableModel;

    private final ProductoDAO productoDAO = new ProductoDAO();

    /**
     * Constructor que inicializa la interfaz gráfica.
     */
    public FrmInventario() {
        setTitle("Sistema de Inventario - La minorista");
        setSize(1100, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(crearPanelBotones(), BorderLayout.NORTH);
        add(crearPanelCentral(), BorderLayout.CENTER);

        cargarTabla();
    }

     // Crea el panel superior con los botones de acción.
    private JPanel crearPanelBotones() {
        JPanel panelBotones = new JPanel(new BorderLayout());

        JPanel panelIzquierdo = new JPanel();
        JButton btnGuardar = new JButton("Guardar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        panelIzquierdo.add(btnGuardar);
        panelIzquierdo.add(btnEliminar);
        panelIzquierdo.add(btnLimpiar);

        JPanel panelDerecho = new JPanel();
        JButton btnSalir = new JButton("Salir");
        panelDerecho.add(btnSalir);

        panelBotones.add(panelIzquierdo, BorderLayout.WEST);
        panelBotones.add(panelDerecho, BorderLayout.EAST);

        btnGuardar.addActionListener(evento -> guardar());
        btnEliminar.addActionListener(evento -> eliminar());
        btnLimpiar.addActionListener(evento -> limpiarCampos());
        btnSalir.addActionListener(evento -> System.exit(0));

        return panelBotones;
    }

     // Crea el panel central de la interfaz.
    private JPanel crearPanelCentral() {
        JPanel panelCentral = new JPanel(new BorderLayout(10, 0));
        panelCentral.add(crearFormulario(), BorderLayout.WEST);
        panelCentral.add(crearTabla(), BorderLayout.CENTER);
        return panelCentral;
    }

     // Crea el formulario de ingreso de datos.
    private JPanel crearFormulario() {
        JPanel panelFormulario = new JPanel(new GridLayout(8, 2, 5, 35));

        txtProductCode = new JTextField();
        txtProductName = new JTextField();
        txtCategory = new JTextField();
        txtUnitPrice = new JTextField();
        txtStockQuantity = new JTextField();
        txtMinimumStock = new JTextField();
        txtSupplier = new JTextField();
        txtEntryDate = new JTextField();

        panelFormulario.add(new JLabel("Código:")); panelFormulario.add(txtProductCode);

        panelFormulario.add(new JLabel("Nombre:")); panelFormulario.add(txtProductName);

        panelFormulario.add(new JLabel("Categoría:")); panelFormulario.add(txtCategory);

        panelFormulario.add(new JLabel("Precio:")); panelFormulario.add(txtUnitPrice);

        panelFormulario.add(new JLabel("Stock:")); panelFormulario.add(txtStockQuantity);

        panelFormulario.add(new JLabel("Stock mínimo:")); panelFormulario.add(txtMinimumStock);

        panelFormulario.add(new JLabel("Proveedor:")); panelFormulario.add(txtSupplier);

        panelFormulario.add(new JLabel("Fecha (AAAA-MM-DD):")); panelFormulario.add(txtEntryDate);

        return panelFormulario;
    }

    // Crea la tabla donde se visualizan los productos.
    private JScrollPane crearTabla() {
        String[] columnas = {
                "Código", "Nombre", "Categoría", "Precio",
                "Stock", "Stock mínimo", "Proveedor", "Fecha"
        };

        tableModel = new DefaultTableModel(columnas, 0);
        productTable = new JTable(tableModel);

        productTable.getSelectionModel().addListSelectionListener(evento -> {
            int selectedRow = productTable.getSelectedRow();

            if (selectedRow != -1) {
                txtProductCode.setText(tableModel.getValueAt(selectedRow, 0).toString());
                txtProductName.setText(tableModel.getValueAt(selectedRow, 1).toString());
                txtCategory.setText(tableModel.getValueAt(selectedRow, 2).toString());
                txtUnitPrice.setText(tableModel.getValueAt(selectedRow, 3).toString());
                txtStockQuantity.setText(tableModel.getValueAt(selectedRow, 4).toString());
                txtMinimumStock.setText(tableModel.getValueAt(selectedRow, 5).toString());
                txtSupplier.setText(tableModel.getValueAt(selectedRow, 6).toString());
                txtEntryDate.setText(tableModel.getValueAt(selectedRow, 7).toString());
            }
        });

        return new JScrollPane(productTable);
    }

    /**
     * Valida los datos y guarda o actualiza un producto.
     */
    private void guardar() {

        if (Validador.estaVacio(txtProductCode.getText()) ||
            Validador.estaVacio(txtProductName.getText())) {

            JOptionPane.showMessageDialog(this,
                    "Código y nombre son obligatorios");
            return;
        }

        if (!Validador.esDecimalNoNegativo(txtUnitPrice.getText())) {
            JOptionPane.showMessageDialog(this,
                    "Precio inválido");
            return;
        }

        if (!Validador.esEnteroNoNegativo(txtStockQuantity.getText())) {
            JOptionPane.showMessageDialog(this,
                    "Stock inválido");
            return;
        }

        if (!Validador.esEnteroNoNegativo(txtMinimumStock.getText())) {
            JOptionPane.showMessageDialog(this,
                    "Stock mínimo inválido");
            return;
        }

        LocalDate entryDate;

        try {
            entryDate = LocalDate.parse(txtEntryDate.getText());
        } catch (Exception errorFecha) {
            JOptionPane.showMessageDialog(this,
                    "Fecha inválida. Use formato AAAA-MM-DD");
            return;
        }

        Producto producto = new Producto(
                txtProductCode.getText(),
                txtProductName.getText(),
                txtCategory.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtStockQuantity.getText()),
                Integer.parseInt(txtMinimumStock.getText()),
                txtSupplier.getText(),
                entryDate
        );

        boolean existe = productoDAO.existe(txtProductCode.getText());
        
        boolean resultado = existe
                ? productoDAO.actualizar(producto)
                : productoDAO.guardar(producto);

        if (resultado) {
            JOptionPane.showMessageDialog(this,
                    existe ? "Producto actualizado correctamente"
                           : "Producto guardado correctamente");

            limpiarCampos();
            cargarTabla();
        }
    }

    /**
     * Carga todos los productos en la tabla.
     */
    private void cargarTabla() {
        tableModel.setRowCount(0);

        List<Producto> listaProductos = productoDAO.listarTodos();

        for (Producto producto : listaProductos) {
            tableModel.addRow(new Object[]{
                    producto.getProductCode(),
                    producto.getProductName(),
                    producto.getCategory(),
                    producto.getUnitPrice(),
                    producto.getStockQuantity(),
                    producto.getMinimumStock(),
                    producto.getSupplier(),
                    producto.getEntryDate()
            });
        }
    }

    /**
     * Elimina un producto seleccionado.
     */
    private void eliminar() {
        int selectedRow = productTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un producto");
            return;
        }

        String productCode = tableModel.getValueAt(selectedRow, 0).toString();

        if (productoDAO.eliminar(productCode)) {
            limpiarCampos();
            cargarTabla();
        }
    }

    /**
     * Limpia todos los campos del formulario.
     */
    private void limpiarCampos() {
        txtProductCode.setText("");
        txtProductName.setText("");
        txtCategory.setText("");
        txtUnitPrice.setText("");
        txtStockQuantity.setText("");
        txtMinimumStock.setText("");
        txtSupplier.setText("");
        txtEntryDate.setText("");
    }
}
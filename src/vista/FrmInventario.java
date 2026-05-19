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
 * Captura, valida y gestiona productos mediante el DAO.
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

    public FrmInventario() {
        setTitle("Sistema de Inventario");
        setSize(1100, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(crearPanelBotones(), BorderLayout.NORTH);
        add(crearPanelCentral(), BorderLayout.CENTER);

        cargarTabla();
    }

    private JPanel crearPanelBotones() {
        JPanel buttonPanel = new JPanel(new BorderLayout());

        JPanel leftPanel = new JPanel();
        JButton btnGuardar = new JButton("Guardar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        leftPanel.add(btnGuardar);
        leftPanel.add(btnEliminar);
        leftPanel.add(btnLimpiar);

        JPanel rightPanel = new JPanel();
        JButton btnSalir = new JButton("Salir");
        rightPanel.add(btnSalir);

        buttonPanel.add(leftPanel, BorderLayout.WEST);
        buttonPanel.add(rightPanel, BorderLayout.EAST);

        btnGuardar.addActionListener(event -> guardar());
        btnEliminar.addActionListener(event -> eliminar());
        btnLimpiar.addActionListener(event -> limpiarCampos());
        btnSalir.addActionListener(event -> System.exit(0));

        return buttonPanel;
    }

    private JPanel crearPanelCentral() {
        JPanel centralPanel = new JPanel(new BorderLayout(10, 0));
        centralPanel.add(crearFormulario(), BorderLayout.WEST);
        centralPanel.add(crearTabla(), BorderLayout.CENTER);
        return centralPanel;
    }

    private JPanel crearFormulario() {
        JPanel formPanel = new JPanel(new GridLayout(8, 2, 5, 35));

        txtProductCode = new JTextField();
        txtProductName = new JTextField();
        txtCategory = new JTextField();
        txtUnitPrice = new JTextField();
        txtStockQuantity = new JTextField();
        txtMinimumStock = new JTextField();
        txtSupplier = new JTextField();
        txtEntryDate = new JTextField();

        formPanel.add(new JLabel("Código:"));
        formPanel.add(txtProductCode);

        formPanel.add(new JLabel("Nombre:"));
        formPanel.add(txtProductName);

        formPanel.add(new JLabel("Categoría:"));
        formPanel.add(txtCategory);

        formPanel.add(new JLabel("Precio:"));
        formPanel.add(txtUnitPrice);

        formPanel.add(new JLabel("Stock:"));
        formPanel.add(txtStockQuantity);

        formPanel.add(new JLabel("Stock mínimo:"));
        formPanel.add(txtMinimumStock);

        formPanel.add(new JLabel("Proveedor:"));
        formPanel.add(txtSupplier);

        formPanel.add(new JLabel("Fecha (AAAA-MM-DD):"));
        formPanel.add(txtEntryDate);

        return formPanel;
    }

    private JScrollPane crearTabla() {
        String[] columnas = {
                "Código", "Nombre", "Categoría", "Precio",
                "Stock", "Stock mínimo", "Proveedor", "Fecha"
        };

        tableModel = new DefaultTableModel(columnas, 0);
        productTable = new JTable(tableModel);

        productTable.getSelectionModel().addListSelectionListener(event -> {
            int row = productTable.getSelectedRow();

            if (row != -1) {
                txtProductCode.setText(tableModel.getValueAt(row, 0).toString());
                txtProductName.setText(tableModel.getValueAt(row, 1).toString());
                txtCategory.setText(tableModel.getValueAt(row, 2).toString());
                txtUnitPrice.setText(tableModel.getValueAt(row, 3).toString());
                txtStockQuantity.setText(tableModel.getValueAt(row, 4).toString());
                txtMinimumStock.setText(tableModel.getValueAt(row, 5).toString());
                txtSupplier.setText(tableModel.getValueAt(row, 6).toString());
                txtEntryDate.setText(tableModel.getValueAt(row, 7).toString());
            }
        });

        return new JScrollPane(productTable);
    }

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

        Producto producto = new Producto(
                txtProductCode.getText(),
                txtProductName.getText(),
                txtCategory.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtStockQuantity.getText()),
                Integer.parseInt(txtMinimumStock.getText()),
                txtSupplier.getText(),
                LocalDate.parse(txtEntryDate.getText())
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

    private void cargarTabla() {
        tableModel.setRowCount(0);

        List<Producto> lista = productoDAO.listarTodos();

        for (Producto producto : lista) {
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

    private void eliminar() {
        int fila = productTable.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un producto");
            return;
        }

        String productCode = tableModel.getValueAt(fila, 0).toString();

        if (productoDAO.eliminar(productCode)) {
            limpiarCampos();
            cargarTabla();
        }
    }

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
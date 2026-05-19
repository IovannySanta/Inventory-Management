package dao;

import conexion.ConexionBD;
import modelo.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    
    /*
    Gestiona las operaciones para la base de datos.
    */ 
         
    /*Guardar un nuevo producto en la base de datos*/
    public boolean guardar(Producto producto) {
        String sql = "INSERT INTO products(product_code, product_name, category, unit_price, stock_quantity, minimum_stock, supplier, entry_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection connection = ConexionBD.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, producto.getProductCode());
            preparedStatement.setString(2, producto.getProductName());
            preparedStatement.setString(3, producto.getCategory());
            preparedStatement.setDouble(4, producto.getUnitPrice());
            preparedStatement.setInt(5, producto.getStockQuantity());
            preparedStatement.setInt(6, producto.getMinimumStock());
            preparedStatement.setString(7, producto.getSupplier());
            preparedStatement.setDate(8, Date.valueOf(producto.getEntryDate()));
            
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException errorGuardar) {
            System.out.println("Error al guardar el producto: " + errorGuardar.getMessage());
            return false;
        }
    }
    
    /*Verifica si un producto existe actualmente con el mismo codigo*/
    public boolean existe(String productCode) {
        String sql = "SELECT product_code FROM product WHERE product_code = ?";
        
        try (Connection connection = ConexionBD.connect();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            preparedStatement.setString(1, productCode);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            return resultSet.next();
        } catch (SQLException errorExiste) {
            System.out.println("Error al verificar producto: " + errorExiste.getMessage());
            return false;
        }
    }
    
    /*Actualizar un producto existente*/
    public boolean actualizar(Producto producto) {
        String sql = "UPDATE products SET product_name=?, category=?, unit_price=?, stock_quantity=?, minimum_stock=?, supplier=?, entry_date=? WHERE product_code=?";

        try (Connection connection = ConexionBD.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, producto.getProductName());
            preparedStatement.setString(2, producto.getCategory());
            preparedStatement.setDouble(3, producto.getUnitPrice());
            preparedStatement.setInt(4, producto.getStockQuantity());
            preparedStatement.setInt(5, producto.getMinimumStock());
            preparedStatement.setString(6, producto.getSupplier());
            preparedStatement.setDate(7, Date.valueOf(producto.getEntryDate()));
            preparedStatement.setString(8, producto.getProductCode());

            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException errorActualizar) {
            System.out.println("Error al actualizar producto: " + errorActualizar.getMessage());
            return false;
        }
    }
    
    /*Lista de todos los productos registrados*/
    public List<Producto> listarTodos() {
        List<Producto> productList = new ArrayList<>();

        String sql = "SELECT * FROM products";

        try (Connection connection = ConexionBD.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Producto producto = new Producto(
                        resultSet.getString("product_code"),
                        resultSet.getString("product_name"),
                        resultSet.getString("category"),
                        resultSet.getDouble("unit_price"),
                        resultSet.getInt("stock_quantity"),
                        resultSet.getInt("minimum_stock"),
                        resultSet.getString("supplier"),
                        resultSet.getDate("entry_date").toLocalDate()
                );
                productList.add(producto);
            }
        } catch (SQLException errorListar) {
            System.out.println("Error al listar productos: " + errorListar.getMessage());
        }
        return productList;
    }
    
    /*eliminar un producto a traves de su codigo único*/
    public boolean eliminar(String productCode) {
        String sql = "DELETE FROM products WHERE product_code = ?";

        try (Connection connection = ConexionBD.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            preparedStatement.setString(1, productCode);
            
            preparedStatement.executeUpdate();
            return true;
            
        } catch (SQLException errorEliminar) {
            System.out.println("Error al eliminar producto: " + errorEliminar.getMessage());
            return false;
        }
    }
}

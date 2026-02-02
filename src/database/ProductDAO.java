package database;

import model.FreshProduct;
import model.PackagedProduct;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // -------------------------
    // CREATE (INSERT)
    // -------------------------

    public void insertFreshProduct(String name, double price, int stock,
                                   int daysToExpire, String farmName) {

        // Insert fresh product into DB
        String sql =
                "INSERT INTO product (name, price, stock_quantity, product_type, days_to_expire, farm_name) " +
                        "VALUES (?, ?, ?, 'FRESH', ?, ?)";

        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) return;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, stock);
            ps.setInt(4, daysToExpire);
            ps.setString(5, farmName);

            ps.executeUpdate(); // INSERT/UPDATE/DELETE -> executeUpdate()

        } catch (SQLException e) {
            System.out.println("Insert Fresh failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
    }

    public void insertPackagedProduct(String name, double price, int stock,
                                      double packageWeight, boolean recyclable) {

        // Insert packaged product into DB
        String sql =
                "INSERT INTO product (name, price, stock_quantity, product_type, package_weight, recyclable) " +
                        "VALUES (?, ?, ?, 'PACKAGED', ?, ?)";

        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) return;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, stock);
            ps.setDouble(4, packageWeight);
            ps.setBoolean(5, recyclable);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Insert Packaged failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
    }

    // -------------------------
    // READ (SELECT)
    // -------------------------

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        String sql = "SELECT * FROM product ORDER BY product_id";

        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) return products;

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // SELECT -> executeQuery()
            while (rs.next()) {
                Product p = extractProduct(rs);
                if (p != null) products.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Select failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(conn);
        }

        return products;
    }

    public Product getProductById(int productId) {
        String sql = "SELECT * FROM product WHERE product_id = ?";

        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) return null;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, productId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return extractProduct(rs);
            }

        } catch (SQLException e) {
            System.out.println("Get by ID failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(conn);
        }

        return null;
    }

    // -------------------------
    // UPDATE (Week 8)
    // -------------------------

    public boolean updateFreshProduct(FreshProduct p) {

        // Update fresh product and clear packaged-only fields
        String sql =
                "UPDATE product SET name = ?, price = ?, stock_quantity = ?, " +
                        "product_type = 'FRESH', days_to_expire = ?, farm_name = ?, " +
                        "package_weight = NULL, recyclable = NULL " +
                        "WHERE product_id = ?";

        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) return false;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getName());
            ps.setDouble(2, p.getPrice());
            ps.setInt(3, p.getStockQuantity());
            ps.setInt(4, p.getDaysToExpire());
            ps.setString(5, p.getFarmName());
            ps.setInt(6, p.getProductId());

            int rows = ps.executeUpdate();
            return rows > 0; // true if a row was updated

        } catch (SQLException e) {
            System.out.println("Update Fresh failed!");
            e.printStackTrace();
            return false;
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
    }

    public boolean updatePackagedProduct(PackagedProduct p) {

        // Update packaged product and clear fresh-only fields
        String sql =
                "UPDATE product SET name = ?, price = ?, stock_quantity = ?, " +
                        "product_type = 'PACKAGED', package_weight = ?, recyclable = ?, " +
                        "days_to_expire = NULL, farm_name = NULL " +
                        "WHERE product_id = ?";

        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) return false;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getName());
            ps.setDouble(2, p.getPrice());
            ps.setInt(3, p.getStockQuantity());
            ps.setDouble(4, p.getPackageWeight());
            ps.setBoolean(5, p.isRecyclable());
            ps.setInt(6, p.getProductId());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Update Packaged failed!");
            e.printStackTrace();
            return false;
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
    }

    // -------------------------
    // DELETE (Week 8)
    // -------------------------

    public boolean deleteProduct(int productId) {
        String sql = "DELETE FROM product WHERE product_id = ?";

        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) return false;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, productId);

            int rows = ps.executeUpdate();
            return rows > 0; // true if a row was deleted

        } catch (SQLException e) {
            System.out.println("Delete failed!");
            e.printStackTrace();
            return false;
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
    }

    // -------------------------
    // SEARCH (Week 8)
    // -------------------------

    public List<Product> searchByName(String name) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE name ILIKE ? ORDER BY name";

        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) return list;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + name + "%"); // Wildcards for partial match

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product p = extractProduct(rs);
                    if (p != null) list.add(p);
                }
            }

        } catch (SQLException e) {
            System.out.println("Search failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(conn);
        }

        return list;
    }

    public List<Product> searchByPriceRange(double min, double max) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE price BETWEEN ? AND ? ORDER BY price DESC";

        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) return list;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, min);
            ps.setDouble(2, max);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product p = extractProduct(rs);
                    if (p != null) list.add(p);
                }
            }

        } catch (SQLException e) {
            System.out.println("Search failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(conn);
        }

        return list;
    }

    public List<Product> searchByMinPrice(double min) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE price >= ? ORDER BY price DESC";

        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) return list;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, min);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product p = extractProduct(rs);
                    if (p != null) list.add(p);
                }
            }

        } catch (SQLException e) {
            System.out.println("Search failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(conn);
        }

        return list;
    }

    // -------------------------
    // Helper: build correct object from DB row
    // -------------------------

    private Product extractProduct(ResultSet rs) throws SQLException {

        int id = rs.getInt("product_id");
        String name = rs.getString("name");
        double price = rs.getDouble("price");
        int stock = rs.getInt("stock_quantity");
        String type = rs.getString("product_type");

        if (type == null) type = "BASIC"; // Safe fallback

        if ("FRESH".equalsIgnoreCase(type)) {

            int days = rs.getInt("days_to_expire");
            boolean daysNull = rs.wasNull(); // checks last-read column
            if (daysNull) days = 0;

            String farm = rs.getString("farm_name");
            if (farm == null || farm.trim().isEmpty()) farm = "Unknown Farm";

            return new FreshProduct(id, name, price, stock, days, farm);
        }

        if ("PACKAGED".equalsIgnoreCase(type)) {

            // Read weight and immediately check if it was NULL
            double w = rs.getDouble("package_weight");
            boolean weightNull = rs.wasNull();

            // Read recyclable and immediately check if it was NULL
            boolean rec = rs.getBoolean("recyclable");
            boolean recNull = rs.wasNull();

            // Prevent validation crash if DB has NULL/0 weight
            if (weightNull || w <= 0) w = 1.0;

            // If recyclable is NULL, choose safe default false
            if (recNull) rec = false;

            return new PackagedProduct(id, name, price, stock, w, rec);
        }

        // Fallback for unknown/BASIC rows: create a safe packaged product
        return new PackagedProduct(id, name, price, stock, 1.0, false);
    }
}

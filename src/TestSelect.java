import database.ProductDAO;

public class TestSelect {
    public static void main(String[] args) {
        ProductDAO dao = new ProductDAO();
        dao.getAllProducts();
    }
}

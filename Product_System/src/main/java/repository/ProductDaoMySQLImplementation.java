package repository;import model.Product;import java.sql.Connection;import java.sql.ResultSet;import java.sql.SQLException;import java.sql.Statement;import java.util.ArrayList;import java.util.List;public class ProductDaoMySQLImplementation implements ProductDao {    private static final Connection connection = ConnectionSingleton.getConnection();    private Statement statement;    {        try {            statement = connection.createStatement();        } catch (SQLException e) {            e.printStackTrace();        }    }    @Override    public List<Product> readAll() {        String query = "select * from product_jb";        List<Product> productList = new ArrayList<>();        try {            ResultSet resultSet = statement.executeQuery(query);            while (resultSet.next()){                Product product = new Product(                        resultSet.getInt("productID"),                        resultSet.getString("productName"),                        resultSet.getString("quantityPerUnit"),                        resultSet.getDouble("unitPrice"),                        resultSet.getDouble("unitsInStock"),                        resultSet.getDouble("unitsOnOrder"),                        resultSet.getDouble("reorderLevel"),                        resultSet.getString("discontinued").charAt(0) == '1'                );                productList.add(product);            }            return productList;        } catch (SQLException e) {            e.printStackTrace();        }        return null;    }    @Override    public Product getSingleProduct(int id) {        String query = String.format("select * from product_jb where productId=%d",id);        try {            ResultSet resultSet = statement.executeQuery(query);             resultSet.next();            return new Product(                    resultSet.getInt("productID"),                    resultSet.getString("productName"),                    resultSet.getString("quantityPerUnit"),                    resultSet.getDouble("unitPrice"),                    resultSet.getDouble("unitsInStock"),                    resultSet.getDouble("unitsOnOrder"),                    resultSet.getDouble("reorderLevel"),                    resultSet.getString("discontinued").charAt(0) == '1'            );        } catch (SQLException e) {            e.printStackTrace();        }        return  null;    }    @Override    public void createProduct(Product product) {        String query = String.format("insert into product_jb values(%d,'%s','%s',%f,%f,%f,%f,%b)",                product.getProductId(),                product.getProductName(),                product.getQuantityPerUnit(),                product.getUnitPrice(),                product.getUnitsInStock(),                product.getUnitsOnOrder(),                product.getReorderLevel(),                product.getDiscontinued()                );        try {            statement.executeUpdate(query);        } catch (SQLException e) {            e.printStackTrace();        }    }    @Override    public void updateProduct(Product product,int id) {        String query = String.format("update product_jb set productName='%s' where productId=%d",                product.getProductName(),                product.getProductId()                );        try {            statement.executeUpdate(query);            System.out.println("Successfully updated "+id+" no. product");        } catch (SQLException e) {            e.printStackTrace();        }    }    @Override    public void deleteProduct(int productid) {        String query = String.format("delete from product_jb where productid=%d",productid);        try {            statement.executeUpdate(query);            System.out.println(productid+" no product Successfully deleted");        } catch (SQLException e) {            e.printStackTrace();        }    }    @Override    public void deleteAll() {        String query = "delete from product_jb";        try {            int rowCount =  statement.executeUpdate(query);            System.out.println(rowCount+" rows are affected..");        } catch (SQLException e) {            e.printStackTrace();        }    }    public void CloseConnection(){        try {            statement.close();            connection.close();            System.out.println("Close the connection to the database.");        } catch (SQLException e) {            e.printStackTrace();        }    }}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import model.Product;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


/**
 *
 * @author Joniyed
 */
public class ProductDaoMySQLImplementationTest {
    ProductDao productDao;
    
    public ProductDaoMySQLImplementationTest() {
        productDao = new ProductDaoMySQLImplementation();
    }

    @BeforeAll
    public static void deleteAll(){
        ProductDao productDao1 = new ProductDaoMySQLImplementation();
        System.out.println("Delete all...");
        productDao1.deleteAll();
    }

    @Test
    public void testReadAll() {
        List<String> name = Arrays.asList("jb","mk","kmh");
        List<Product> productList = new ArrayList<>();
        for(int i=1;i<=name.size();i++){
            Product product = new Product(
                    200+i,
                    "Casio G-Shock",
                    "1 per box",
                    100*i,
                    15+i,
                    19+i,
                    5+i,
                    false);
            productDao.createProduct(product);
            productList.add(product);


        }


        List<Product> productList1 = productDao.readAll();
        for(int i=0;i<productList.size();i++){
            Assertions.assertEquals(productList.get(i).getProductId(),productList1.get(i).getProductId());
            Assertions.assertEquals(productList.get(i).getProductName(),productList1.get(i).getProductName());
            Assertions.assertEquals(productList.get(i).getQuantityPerUnit(),productList1.get(i).getQuantityPerUnit());
            Assertions.assertEquals(productList.get(i).getUnitPrice(),productList1.get(i).getUnitPrice());
            Assertions.assertEquals(productList.get(i).getUnitsInStock(),productList1.get(i).getUnitsInStock());
            Assertions.assertEquals(productList.get(i).getUnitsOnOrder(),productList1.get(i).getUnitsOnOrder());
            Assertions.assertEquals(productList.get(i).getReorderLevel(),productList1.get(i).getReorderLevel());
            Assertions.assertEquals(productList.get(i).getDiscontinued(),productList1.get(i).getDiscontinued());
        }

        deleteAll();
    }

    @Test
    public void testGetSingleProduct() {
        testCreateProduct();
    }

    @Test
    public void testCreateProduct() {
          Product product = new Product(
                200,
                "Casio G-Shock",
                "1 per box",
                100,
                15,
                19,
                5,
                false);
        productDao.createProduct(product);
        Product productFromDatabase = productDao.getSingleProduct(200);
        
        Assertions.assertEquals(product.getProductId(), productFromDatabase.getProductId());
        Assertions.assertEquals(product.getProductName(), productFromDatabase.getProductName());
        Assertions.assertEquals(product.getQuantityPerUnit(), productFromDatabase.getQuantityPerUnit());
        Assertions.assertEquals(product.getUnitPrice(), productFromDatabase.getUnitPrice());
        Assertions.assertEquals(product.getUnitsInStock(), productFromDatabase.getUnitsInStock());
        Assertions.assertEquals(product.getUnitsOnOrder(), productFromDatabase.getUnitsOnOrder());
        Assertions.assertEquals(product.getReorderLevel(), productFromDatabase.getReorderLevel());
        Assertions.assertEquals(product.getDiscontinued(), productFromDatabase.getDiscontinued());
        deleteAll();
    }

    @Test
    public void testUpdateProduct() {
        Product product = new Product(
                200,
                "Casio G-Shock",
                "1 per box",
                100,
                15,
                19,
                5,
                false);
        productDao.createProduct(product);
        Product product1 = new Product(
                200,
                "Rename",
                "1 per box",
                100,
                15,
                19,
                5,
                false);
        productDao.updateProduct(product1,200);


        Product product2 = productDao.getSingleProduct(200);
        Assertions.assertEquals(product1.getProductId(),product2.getProductId());
        Assertions.assertEquals(product1.getProductName(),product2.getProductName());

        deleteAll();

    }

    @Test
    public void testDeleteProduct() {
        Product product = new Product(
                200,
                "Casio G-Shock",
                "1 per box",
                100,
                15,
                19,
                5,
                false);
        productDao.createProduct(product);
        productDao.deleteProduct(200);
        Assertions.assertEquals(0,productDao.readAll().size());
        deleteAll();
    }
}

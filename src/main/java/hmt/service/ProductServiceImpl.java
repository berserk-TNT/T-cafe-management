package hmt.service;

import hmt.model.Products;
import hmt.utils.MySQLConnUtils;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductServiceImpl implements IProductService {

    private static String SELECT_PRODUCT_BY_ID = "" +
            "SELECT * FROM products AS p" +
            "FROM products AS p " +
            "WHERE p.id = ?;";

    private static String SP_ADD_PRODUCT = "{CALL sp_add_product(?, ?, ?, ?, ?, ?)}";

    private static String SP_EDIT_PRODUCT_BY_ID = "{CALL sp_edit_product_by_id(?, ?, ?, ?, ?, ?, ?)}";

    private static String SP_DELETE_PRODUCT_BY_ID = "{CALL sp_delete_product_by_id(?, ?, ?)}";

    private int noOfRecords;

    @Override
    public List<Products> showAndSearch(int offset, int noOfRecords, String q) {
        List<Products> productList = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = MySQLConnUtils.getConnection();
            String query = "SELECT SQL_CALC_FOUND_ROWS * FROM products AS p " +
                    "WHERE p.name LIKE ? OR p.price LIKE ? OR p.quantity LIKE ? " +
                    "ORDER BY p.id DESC " +
                    "LIMIT " + offset + ", " +noOfRecords + ";";
            statement = connection.prepareStatement(query);
            statement.setString(1, "%" + q + "%");
            statement.setString(2, "%" + q + "%");
            statement.setString(3, "%" + q + "%");

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String imageUrl = rs.getString("image_url");
                String name = rs.getString("name");
                BigDecimal price = rs.getBigDecimal("price");
                int quantity = rs.getInt("quantity");
                productList.add(new Products(id, imageUrl, name, price, quantity));
            }
            rs.close();

            rs = statement.executeQuery("SELECT FOUND_ROWS()");
            if (rs.next()) {
                this.noOfRecords = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            MySQLConnUtils.printSQLException(e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                MySQLConnUtils.printSQLException(e);
            }
        }
        return productList;
    }

    @Override
    public int getNoOfRecords() {
        return noOfRecords;
    }


    @Override
    public Products findProductById(int productId) {
        Products product = null;
        try {
            Connection connection = MySQLConnUtils.getConnection();
            PreparedStatement statement = connection.prepareCall(SELECT_PRODUCT_BY_ID);
            statement.setInt(1, productId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String imageUrl = rs.getString("image_url");
                String name = rs.getString("name");
                BigDecimal price = rs.getBigDecimal("price");
                int quantity = rs.getInt("quantity");
                product = new Products(id, imageUrl, name, price, quantity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            MySQLConnUtils.printSQLException(e);
        }
        return product;
    }

    @Override
    public Map<String, String> add(Products product) {
        Map<String, String> result = new HashMap<>();
        try {
            Connection connection = MySQLConnUtils.getConnection();
            CallableStatement statement = connection.prepareCall(SP_ADD_PRODUCT);
            statement.setString(1, product.getImageUrl());
            statement.setString(2, product.getName());
            statement.setBigDecimal(3, product.getPrice());
            statement.setInt(4, product.getQuantity());
            statement.registerOutParameter(5, Types.BOOLEAN);
            statement.registerOutParameter(6, Types.VARCHAR);
            statement.execute();

            Boolean isSuccess = statement.getBoolean("isSuccess");
            String message = statement.getString("message");

            result.put("isSuccess", isSuccess.toString());
            result.put("message", message);

        } catch (SQLException e) {
            e.printStackTrace();
            MySQLConnUtils.printSQLException(e);
        }
        return result;
    }

    @Override
    public Map<String, String> edit(Products product) {
        Map<String, String> result = new HashMap<>();
        try {
            Connection connection = MySQLConnUtils.getConnection();
            CallableStatement statement = connection.prepareCall(SP_EDIT_PRODUCT_BY_ID);

            statement.setInt(1, product.getId());
            statement.setString(2, product.getImageUrl());
            statement.setString(3, product.getName());
            statement.setBigDecimal(4, product.getPrice());
            statement.setInt(5, product.getQuantity());
            statement.registerOutParameter(6, Types.BOOLEAN);
            statement.registerOutParameter(7, Types.VARCHAR);
            System.out.println(statement);
            statement.execute();

            Boolean isSuccess = statement.getBoolean("isSuccess");
            String message = statement.getString("message");

            result.put("isSuccess", isSuccess.toString());
            result.put("message", message);

        } catch (SQLException e) {
            e.printStackTrace();
            MySQLConnUtils.printSQLException(e);
        }
        return result;
    }

    @Override
    public boolean delete(int id) {
        boolean isSuccess = false;
        try {
            Connection connection = MySQLConnUtils.getConnection();
            CallableStatement statement = connection.prepareCall(SP_DELETE_PRODUCT_BY_ID);
            statement.setInt(1, id);
            statement.registerOutParameter(2, Types.BOOLEAN);
            statement.registerOutParameter(3, Types.VARCHAR);
            statement.execute();

            isSuccess = statement.getBoolean("isSuccess");

        } catch (SQLException e) {
            e.printStackTrace();
            MySQLConnUtils.printSQLException(e);
        }
        return isSuccess;
    }
}

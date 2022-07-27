package hmt.service;
import hmt.model.Products;

import java.util.List;
import java.util.Map;

public interface IProductService {

    List<Products> showAndSearch(int offset, int noOfRecords, String q);

    int getNoOfRecords();

    Products findProductById(int productId);

    Map<String, String> edit(Products product);

    Map<String, String> add(Products product);

    boolean delete(int productId);

}

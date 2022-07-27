package hmt.controller;

import hmt.model.Products;;
import hmt.service.IProductService;
import hmt.service.ProductServiceImpl;
import hmt.utils.MySQLConnUtils;
import hmt.utils.ValidateUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ProductServlet", urlPatterns = "/products")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 50,
        maxRequestSize = 1024 * 1024 * 50)
public class ProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    IProductService productService;

    @Override
    public void init() throws ServletException {
        productService = new ProductServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = "";
        Cookie[] cookies = null;

        cookies = req.getCookies();
        for (Cookie c : cookies) {
            if (c.getName().equals("userC")) {
                name = c.getName();
            }
        }
        if (name.equals("")) {
            resp.sendRedirect("/login");
        } else {
            String action = req.getParameter("action");
            if (action == null) {
                action = "";
            }
            switch (action) {
                case "list":
                    showListPage(req, resp);
                    break;
                case "add":
                    showAddPage(req, resp);
                    break;
                case "edit":
                    showEditPage(req, resp);
                    break;
                case "detail":
                    showDetailPage(req, resp);
                    break;
                case "delete":
                    doDelete(req, resp);
                    break;
                default:
                    showListPage(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");

        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "list":
                showListPage(req, resp);
            case "add":
                doAdd(req, resp);
                break;
            case "edit":
                doEdit(req, resp);
        }
    }

    public void showListPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = 1;
        int recordsPerPage = 5;
        String q = "";

        if (req.getParameter("q") != null) {
            q = req.getParameter("q");
        }
        if (req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        }

        List<Products> productList = productService.showAndSearch((page - 1)*recordsPerPage, recordsPerPage, q);
        int noOfRecords = productService.getNoOfRecords();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        req.setAttribute("productList", productList);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", page);
        req.setAttribute("q", q);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/cp/products/list-products.jsp");
        dispatcher.forward(req, resp);
    }

    public void showAddPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("cp/products/add-products.jsp");
        dispatcher.forward(req, resp);
    }

    public void showEditPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Products product = productService.findProductById(id);
        if (product != null) {
            req.setAttribute("product", product);
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("/cp/products/edit-products.jsp");
        dispatcher.forward(req, resp);
    }

    public void showDetailPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Products product = productService.findProductById(id);
        if (product != null) {
            req.setAttribute("product", product);
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("/cp/products/detail-products.jsp");
        dispatcher.forward(req, resp);
    }

    public void doAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name").trim();
        BigDecimal price = BigDecimal.valueOf(Long.parseLong(req.getParameter("price").trim()));
        int quantity = Integer.parseInt(req.getParameter("quantity").trim());

        boolean isPriceNumber = ValidateUtils.isNumber(String.valueOf(price));
        boolean isQuantityNumber = ValidateUtils.isNumber(String.valueOf(quantity));

        List<String> errors = new ArrayList<>();
        if (name.equals("")) {
            errors.add("Name can't be empty!");
        } else {
            if (name.length() > 100) {
                errors.add("Name is too long!");
            }
        }

        if (String.valueOf(price).equals("")) {
            errors.add("Price can't be empty!");
            if (!isPriceNumber) {
                errors.add("Price must be a number!");
            } else {
                if (price.longValue() < 1000 || price.longValue() > 100000000) {
                    errors.add("Price is from 1.000 to 100.000.000!");
                }
            }
        }

        if (String.valueOf(quantity).equals("")) {
            errors.add("Quantity can't be empty!");
            if (!isQuantityNumber) {
                errors.add("Quantity must be a number!");
            } else {
                if (quantity < 1 || quantity > 1000) {
                    errors.add("Quantity is from 1 to 1000!");
                }
            }
        }

        Products product = new Products(name, price, quantity);
        for (Part part : req.getParts()) {
            if(part.getName().equals("imageUrl")){
                String fileName = extractFileName(part);
                fileName = new File(fileName).getName();
                part.write("D:\\JAVA\\T-cafe-management\\src\\main\\webapp\\images\\" + fileName);
                String servletRealPath = this.getServletContext().getRealPath("/") + "\\images\\" + fileName;
                part.write(servletRealPath);
                product.setImageUrl("images\\" + fileName);
            }
        }

//        String imageUrl = req.getParameter("imageUrl");
//        if (imageUrl.equals("")) {
//            errors.add("Image not have been uploaded yet!");
//        }

        if (errors.size() == 0) {
            Map<String, String> result = productService.add(product);
            Boolean isSuccess = Boolean.parseBoolean(result.get("isSuccess"));
            String message = result.get("message");

            if (isSuccess.equals(true)) {
                req.setAttribute("isSuccess", true);
                req.setAttribute("message", message);
            } else {
                errors.add(message);
                req.setAttribute("isError", true);
            }
        } else {
            req.setAttribute("errors", errors);
        }

        req.setAttribute("product", product);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/cp/products/add-products.jsp");
        dispatcher.forward(req, resp);
    }

    public String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }

    public File getFolderUpload() {
        File folderUpload = new File(System.getProperty("user.home") + "/Uploads");
        System.out.println(System.getProperty("user.home") + "/Uploads");
        if (!folderUpload.exists()) {
            folderUpload.mkdirs();
        }
        return folderUpload;
    }

    public void doEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id").trim());
        String name = req.getParameter("name").trim();
        BigDecimal price = BigDecimal.valueOf(Long.parseLong(req.getParameter("price").trim()));
        int quantity = Integer.parseInt(req.getParameter("quantity").trim());

        boolean isIdNumber = ValidateUtils.isNumber(String.valueOf(id));
        boolean isPriceNumber = ValidateUtils.isNumber(String.valueOf(price));
        boolean isQuantityNumber = ValidateUtils.isNumber(String.valueOf(quantity));

        List<String> errors = new ArrayList<>();
        if (!isIdNumber) {
            errors.add("ID must be a number!");
        }

        if (name.equals("")) {
            errors.add("Name can't be empty!");
        } else {
            if (name.length() > 100) {
                errors.add("Name is too long!");
            }
        }

        if (String.valueOf(price).equals("")) {
            errors.add("Price can't be empty!");
            if (!isPriceNumber) {
                errors.add("Price must be a number!");
            } else {
                if (price.longValue() < 1000 || price.longValue() > 10000000) {
                    errors.add("Price is from 1.000 to 10.000.000!");
                }
            }
        }

        if (String.valueOf(quantity).equals("")) {
            errors.add("Quantity can't be empty!");
            if (!isQuantityNumber) {
                errors.add("Quantity must be a number!");
            } else {
                if (quantity < 1 || quantity > 1000) {
                    errors.add("Quantity is from 1 to 1000!");
                }
            }
        }

        Products product = new Products(id, name, price, quantity);
        for (Part part : req.getParts()) {
            if(part.getName().equals("imageUrl")){
                String fileName = extractFileName(part);
                fileName = new File(fileName).getName();
                part.write("D:\\JAVA\\T-cafe-management\\src\\main\\webapp\\images\\" + fileName);
                String servletRealPath = this.getServletContext().getRealPath("/") + "\\images\\" + fileName;
                part.write(servletRealPath);
                product.setImageUrl("images\\" + fileName);
            }
        }

//        String imageUrl = req.getParameter("imageUrl");
//        if (imageUrl.equals("")) {
//            errors.add("Image not have been uploaded yet!");
//        }

        if (errors.size() == 0) {
            Map<String, String> result = productService.edit(product);
            Boolean isSuccess = Boolean.parseBoolean(result.get("isSuccess"));
            String message = result.get("message");

            if (isSuccess.equals(true)) {
                req.setAttribute("isSuccess", true);
            } else {
                errors.add(message);
                req.setAttribute("isError", true);
            }
        } else {
            req.setAttribute("errors", errors);
        }

        req.setAttribute("product", product);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/cp/products/edit-products.jsp");
        dispatcher.forward(req, resp);
    }

    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        boolean isSuccess = productService.delete(id);
        if (isSuccess) {
            req.setAttribute("isSuccess", true);
        } else {
            req.setAttribute("isError", true);
        }

        int page = 1;
        int recordsPerPage = 5;
        String q = "";
        List<Products> productList = productService.showAndSearch((page - 1)*recordsPerPage, recordsPerPage, q);
        req.setAttribute("productList", productList);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/cp/products/list-products.jsp");
        dispatcher.forward(req, resp);
    }

}

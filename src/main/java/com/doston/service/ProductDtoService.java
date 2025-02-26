package com.doston.service;

import com.doston.dao.ProductDao;
import com.doston.model.ProductDto;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

@RequiredArgsConstructor
public class ProductDtoService {
    private final ProductDao productDao;

    public List<ProductDto> listProductByOrderId(int orderId) {
        List<ProductDto> productsByOrderId = productDao.getProductsByOrderId(orderId);
        setFirstImageToImages(productsByOrderId);
        return productsByOrderId;
    }

    private void setFirstImageToImages(List<ProductDto> productList) {
        for (ProductDto product: productList) {
            JSONArray jsonArray = new JSONArray(product.getImages());
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            String path = jsonObject.getString("path");
            product.setImages(path);
        }
    }
}

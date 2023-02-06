package br.com.techsoft.productapi.core.exception;

public class ProductStockValidateException extends RuntimeException{
    public ProductStockValidateException(String message) {
        super(message);
    }
}

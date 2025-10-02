package com.codesnippet.ecom.service;

import com.codesnippet.ecom.Entity.Product;
import com.codesnippet.ecom.Repository.ProductRepository;
import com.codesnippet.ecom.Service.ProductService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CheckoutServiceTest {

    @Mock
    ProductRepository productRepository;   // ia m the dependecy mock

    @InjectMocks
    ProductService productService;   // irequire the mocks bean into me using notataion :::: @Injectmocks


    @BeforeAll
    public  static void init(){
        //jdbc connectionn, or object hared among various mthod for trets
        System.out.println("I run before all test cases");
    }


    @BeforeEach
    public void intiAEch(){
        System.out.println("I run before each test cases");
    }


    @Test
    public void addTotal(){
        System.out.println("My first Test case");


        //dumpy product
        Product p = new Product();
        p.setId(1);
        p.setName("Iphone17");
        p.setDescription("IPHONE PREMIUM");
        p.setStock(2);
        p.setPrice(90000);

        Mockito.when(productRepository.save(p)).thenReturn(p); //mocking caalllll
        // the above line explainthat “When someone calls save(p) on productRepository, don’t really hit DB. Instead, just return the same product p I gave you.”

        Product addedProduct = productService.addProduct(p);

        assertNotNull(addedProduct);
        assertEquals(p.getId(), addedProduct.getId());
        assertEquals(p.getId(), addedProduct.getId() );
    }



    // what if we want to delete method which does not retun any thing example deleteById()    does nto return nothings
    @Test
    public void checkDeleteMethod(){
        Mockito.doNothing().when(productRepository).deleteById(1);
        productService.deleteProduct(1);
        Mockito.verify(productRepository, Mockito.times(1)).deleteById(1);

    }


    @Test
    public void testPrivateValidateName() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method validateProductName = ProductService.class.getDeclaredMethod("validateProductName", String.class);
        validateProductName.setAccessible(true);
        Boolean book = (Boolean) validateProductName.invoke(productService, "Book");
        Assertions.assertTrue(book);
    }


    //testing exception
    @Test
    public void addProductShouldThrowExceptionForInvalidProductName(){
        //dummpy product
        Product p = new Product();
        p.setId(1);
        p.setName("");
        p.setDescription("IPHONE PREMIUM");
        p.setStock(2);
        p.setPrice(90000);

        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> {productService.addProduct(p);});
        Assertions.assertEquals("Invalid Name Of Product", runtimeException.getMessage());
    }




}
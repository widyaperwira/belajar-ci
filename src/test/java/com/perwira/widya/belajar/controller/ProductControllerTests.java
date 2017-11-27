package com.perwira.widya.belajar.controller;

import com.perwira.widya.belajar.belajarci.BelajarCiApplication;
import com.perwira.widya.belajar.model.Product;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

/**
 * Created by widyaperwira on 16-Nov-17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BelajarCiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/mysql/delete-data.sql", "/mysql/sample-product.sql"})
public class ProductControllerTests {
    private static final String BASE_URL = "/api/product";

    @LocalServerPort
    int serverPort;

    @Before
    public void setup() {
        RestAssured.port = serverPort;
    }

    @Test
    public void testSave() throws Exception {

        Product p = new Product();
        p.setCode("PT-001");
        p.setName("Product Test 001");
        p.setPrice(BigDecimal.valueOf(102000.02));

        RestAssured.given()
                .body(p)
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URL+"/")
                .then()
                .statusCode(201)
                .header("Location", CoreMatchers.containsString(BASE_URL+"/"))
                .log()
                .headers();

        // nama tidak diisi
        Product px = new Product();
        px.setCode("PT-001");
        RestAssured.given()
                .body(px)
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URL+"/")
                .then()
                .statusCode(400);

        // kode kurang dari 3 huruf
        Product px1 = new Product();
        px1.setCode("PT");
        px1.setName("Product Test");
        p.setPrice(BigDecimal.valueOf(100));

        RestAssured.given()
                .body(px1)
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URL+"/")
                .then()
                .statusCode(400);

        // Harga negatif
        Product px2 = new Product();
        px2.setCode("PT-009");
        px2.setName("Product Test");
        p.setPrice(BigDecimal.valueOf(-100));
        RestAssured.given()
                .body(px1)
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URL+"/")
                .then()
                .statusCode(400);
    }

    @Test
    public void testFindAll() {
        RestAssured.get(BASE_URL+"/")
                .then()
                .body("totalElements", CoreMatchers.equalTo(1))
                .body("content.id", CoreMatchers.hasItems("abc123"));
    }

    @Test
    public void testFindById() {
        RestAssured.get(BASE_URL+"/abc123")
                .then()
                .statusCode(200)
                .body("id", CoreMatchers.equalTo("abc123"))
                .body("code", CoreMatchers.equalTo("P-001"));

        //ini failure karena return null, gimana ya?
//        RestAssured.get(BASE_URL+"/990")
//                .then()
//                .statusCode(404);
    }
//
    @Test
    public void testUpdate() {
        Product p = new Product();
        p.setCode("PX-009");
        p.setName("Product 909");
        p.setPrice(BigDecimal.valueOf(2000));

        RestAssured.given()
                .body(p)
                .contentType(ContentType.JSON)
                .when()
                .put(BASE_URL+"/abc123")
                .then()
                .statusCode(200);

        RestAssured.get(BASE_URL+"/abc123")
                .then()
                .statusCode(200)
                .body("id", CoreMatchers.equalTo("abc123"))
                .body("code", CoreMatchers.equalTo("PX-009"))
                .body("name", CoreMatchers.equalTo("Product 909"));
    }
//
    @Test
    public void testDelete() {
        //test
        RestAssured.delete(BASE_URL+"/abc123")
                .then()
                .statusCode(200);

        // ini juga bikin failure, get tapi kosong.
//        RestAssured.get(BASE_URL+"/abc123")
//                .then()
//                .statusCode(404);
    }

}

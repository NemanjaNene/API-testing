import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class ApiTest {

    @Test
    public void testCreateUser() {
        // Postavljanje baze URL-a
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Kreiramo novog korisnika slanjem POST zahteva
        Response response = given()
                .header("Content-Type", "application/json")
                .body("{ \"name\": \"Nemanja Nikitovic\", \"email\": \"nemanja@gmail.com\", \"username\": \"nemanja123\" }")
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .extract()
                .response();


        assertEquals(201, response.getStatusCode());

        // Provera da je odgovor sadržao ID za novog korisnika
        Integer userId = response.jsonPath().getInt("id");
        assertNotNull(userId);
        System.out.println("Kreiran korisnik ID: " + userId);
    }
    @Test
    public void testGetUser() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        int userId = 1;

        Response response = when()
                .get("/users/" + userId)
                .then()
                .statusCode(200)
                .extract()
                .response();

        assertEquals(200, response.getStatusCode());

        String userName = response.jsonPath().getString("name");
        String userEmail = response.jsonPath().getString("email");

        assertNotNull(userName);
        assertNotNull(userEmail);

        System.out.println("Preuzet korisnik: " + userName + ", Email: " + userEmail);
    }

    @Test
    public void testGetNonExistingUser() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        int userId = 999;

        Response response = when()
                .get("/users/" + userId)
                .then()
                .statusCode(404)
                .extract()
                .response();

        assertEquals(404, response.getStatusCode());

        System.out.println("Korisnik sa ID: " + userId + " nije pronađen.");
    }
    @Test
    public void testUpdateUser() {
        // Postavljanje baze URL-a
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";


        int userId = 1;

        // Ažuriranje korisnika sa novim podacima
        Response response = given()
                .header("Content-Type", "application/json")
                .body("{ \"name\": \"Nemanja Nikitovic\", \"email\": \"nemanja_updated@gmail.com\", \"username\": \"nemanja456\" }")
                .when()
                .put("/users/" + userId)
                .then()
                .statusCode(200)
                .extract()
                .response();

        // Provera status koda
        assertEquals(200, response.getStatusCode());

        // Provera da li su podaci ažurirani ispravno
        String updatedEmail = response.jsonPath().getString("email");
        String updatedUsername = response.jsonPath().getString("username");

        assertEquals("nemanja_updated@gmailA.com", updatedEmail);
        assertEquals("nemanja456", updatedUsername);

        System.out.println("Ažuriran korisnik ID: " + userId);
    }

    @Test
    public void testDeleteUser() {
        // Postavljanje baze URL-a
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";


        int userId = 1;

        // Brisanje korisnika slanjem DELETE zahteva
        Response response = when()
                .delete("/users/" + userId)
                .then()
                .statusCode(200)
                .extract()
                .response();


        assertEquals(200, response.getStatusCode());

        System.out.println("Korisnik ID: " + userId + " je obrisan.");
    }
}


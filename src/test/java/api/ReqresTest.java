package api;

import api.helpers.Specifications;
import api.pojo.*;
import org.junit.Assert;
import org.junit.Test;

import java.time.Clock;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class ReqresTest {
    private final static String URL = "https://reqres.in/";
    private final static String xApiKey = "reqres-free-v1";

    /**
     * Тест 1
     * Проверка получения списка пользователей со второй страницы
     * Проверка совпадения имен файлов-аватаров пользователей.
     * Проверка окончания email пользователей на "reqres.in".
     */
    @Test
    public void checkAvatarAndIdTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL, xApiKey), Specifications.responseSpecOK200());
        List<UserData> users = given()
                .when()
                .get("api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserData.class);
        users.forEach(x -> Assert.assertTrue(x.getAvatar().contains(x.getId().toString())));

        Assert.assertTrue(users.stream().allMatch(x -> x.getEmail().endsWith("@reqres.in")));

        List<String> avatars = users.stream().map(UserData::getAvatar).collect(Collectors.toList());
        List<String> ids = users.stream().map(x -> x.getId().toString()).collect(Collectors.toList());

        for (int i = 0; i < avatars.size(); i++) {
            Assert.assertTrue(avatars.get(i).contains(ids.get(i)));
        }
    }

    /**
     * Тест 2.1
     * Успешная регистрация пользователя
     */
    @Test
    public void successRegisterTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL, xApiKey), Specifications.responseSpecOK200());
        Integer id = 4;
        String token = "QpwL5tke4Pnpja7X4";
        Register user = new Register("eve.holt@reqres.in", "pistol");
        SuccessReg successReg = given()
                .body(user)
                .when()
                .post("api/register")
                .then()
                .log().all()
                .extract().as(SuccessReg.class);
        Assert.assertNotNull(successReg.getId());
        Assert.assertNotNull(successReg.getToken());
        Assert.assertEquals(id, successReg.getId());
        Assert.assertEquals(token, successReg.getToken());
    }

    /**
     * Тест 2.1
     * Ошибка регистрации пользователя
     */

    @Test
    public void unSuccessRegisterTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL, xApiKey), Specifications.responseSpecError400());
        Register user = new Register("sydney@fife", "");
        String strError = "Missing password";
        UnSuccessReg unSuccessReg = given()
                .body(user)
                .when()
                .post("api/register")
                .then()
                .log().all()
                .extract().as(UnSuccessReg.class);
        Assert.assertEquals(strError, unSuccessReg.getError());
    }

    /**
     * Тест 3
     * Проверка сортировки по годам
     */
    @Test
    public void checkSortYearsTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL, xApiKey), Specifications.responseSpecOK200());
        List<ColorsData> colors = given()
                .when()
                .get("api/unknown")
                .then().log().all()
                .extract().body().jsonPath().getList("data", ColorsData.class);
        List<Integer> years = colors.stream().map(ColorsData::getYear).collect(Collectors.toList());
        List<Integer> sortedYears = years.stream().sorted().collect(Collectors.toList());
        Assert.assertEquals(sortedYears, years);
    }

    /**
     * Тест 4.1
     * Проверка удаления второго пользователя
     */
    @Test
    public void deleteUsersTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL, xApiKey), Specifications.responseSpecUnique(204));
        given()
                .when()
                .delete("api/users/2")
                .then().log().all();
    }

    /**
     * Тест 4.2
     * Сравнение текущей даты и времени с данными пользователя.
     * Может быть погрешность +- 1-2 секунды из-за задержки интернета
     */
    @Test
    public void checkDataTimeTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL, xApiKey), Specifications.responseSpecOK200());
        UserTime userTime = new UserTime("morpheus", "zion resident");
        UserTimeResponse userTimeResponse = given()
                .body(userTime)
                .when()
                .put("api/users/2")
                .then().log().all()
                .extract().as(UserTimeResponse.class);
        String currentTime = Clock.systemUTC().instant().toString().replaceAll("(.{11})$", "");
        Assert.assertEquals(currentTime, userTimeResponse.getUpdatedAt().replaceAll("(.{5})$", ""));
    }
}
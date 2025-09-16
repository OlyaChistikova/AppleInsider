package Hh;

import core.BaseTest;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class HhTest extends BaseTest {
    private final static String URL = "https://ulyanovsk.hh.ru/applicant/resumes/view?resume=519093020007287cb10039ed1f536835324d62";

    @Test
    public void checkAttributesHashMap(){
        HhResumePage hhResumePage = new HhResumePage(URL);
        Map<String, Object> expectedAttributes = new HashMap<>();
        expectedAttributes.put(HhResumePage.GENDER, "М");
        expectedAttributes.put(HhResumePage.AGE, 27);
        expectedAttributes.put(HhResumePage.CITY, "Санкт-Петербург");
        expectedAttributes.put(HhResumePage.READY_TO_RELOCATE, true);

        Map<String, Object> actualAttributes = hhResumePage.getAttributes();
        Assert.assertEquals(expectedAttributes, actualAttributes);
    }

    @Test
    public void checkAttributesClass(){
        HhResumePage hhResumePage = new HhResumePage(URL);
        Resume expectedAttributes = new Resume("М", 27, "Санкт-Петербург", true);
        Resume actualAttributes = new Resume(hhResumePage.getGender(), hhResumePage.getAge(), hhResumePage.getCity(),
                hhResumePage.isReadyToRelocate());
        Assert.assertTrue(EqualsBuilder.reflectionEquals(expectedAttributes, actualAttributes));
    }
}

package Hh;

public class Resume {
    private String gender;
    private int age;
    private String city;
    private boolean isReadyToRelocate;

    public Resume(String gender, int age, String city, boolean isReadyToRelocate) {
        this.gender = gender;
        this.age = age;
        this.city = city;
        this.isReadyToRelocate = isReadyToRelocate;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }

    public boolean isReadyToRelocate() {
        return isReadyToRelocate;
    }
}

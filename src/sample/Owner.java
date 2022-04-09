package sample;

public class Owner {
    private int id;
    private String firstName;
    private String lastName;
    private String father;
    private String gender;
    private String nationalId;
    private String shenas;
    private String phoneNumber;
    private String city;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getShenas() {
        return shenas;
    }

    public void setShenas(String shenas) {
        this.shenas = shenas;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Owner() {
    }

    public Owner(int id, String firstName, String lastName, String nationalId, String shenas) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalId = nationalId;
        this.shenas = shenas;
    }

    public Owner(String firstName, String lastName, String father, String gender, String nationalId, String shenas, String phoneNumber, String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.father = father;
        this.gender = gender;
        this.nationalId = nationalId;
        this.shenas = shenas;
        this.phoneNumber = phoneNumber;
        this.city = city;
    }
    public Owner(int id, String firstName, String lastName, String father, String gender, String nationalId, String shenas, String phoneNumber, String city) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.father = father;
        this.gender = gender;
        this.nationalId = nationalId;
        this.shenas = shenas;
        this.phoneNumber = phoneNumber;
        this.city = city;
    }
}

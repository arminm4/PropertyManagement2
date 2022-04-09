package sample;

public class Property {
    private int id;
    private String pelakNumber;
    private double area;
    private int landType;
    private int ownerId;
    private int manCheck;
    private int roodCheck;
    private int karVisit;
    private int karReport;
    private int daftarAsnad;
    private int sanad;
    private String city;
    private String roosta;
    private String owner;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPelakNumber() {
        return pelakNumber;
    }

    public void setPelakNumber(String pelakNumber) {
        this.pelakNumber = pelakNumber;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public int getLandType() {
        return landType;
    }

    public void setLandType(int landType) {
        this.landType = landType;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getManCheck() {
        return manCheck;
    }

    public void setManCheck(int manCheck) {
        this.manCheck = manCheck;
    }

    public int getRoodCheck() {
        return roodCheck;
    }

    public void setRoodCheck(int roodCheck) {
        this.roodCheck = roodCheck;
    }

    public int getKarVisit() {
        return karVisit;
    }

    public void setKarVisit(int karVisit) {
        this.karVisit = karVisit;
    }

    public int getKarReport() {
        return karReport;
    }

    public void setKarReport(int karReport) {
        this.karReport = karReport;
    }

    public int getDaftarAsnad() {
        return daftarAsnad;
    }

    public void setDaftarAsnad(int daftarAsnad) {
        this.daftarAsnad = daftarAsnad;
    }

    public int getSanad() {
        return sanad;
    }

    public void setSanad(int sanad) {
        this.sanad = sanad;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRoosta() {
        return roosta;
    }

    public void setRoosta(String roosta) {
        this.roosta = roosta;
    }

    public Property() {
    }

    public Property(String pelakNumber, double area, int landType, int ownerId, int manCheck, int roodCheck, int karVisit, int karReport, int daftarAsnad, int sanad, String city, String roosta, String owner) {
        this.pelakNumber = pelakNumber;
        this.area = area;
        this.landType = landType;
        this.ownerId = ownerId;
        this.manCheck = manCheck;
        this.roodCheck = roodCheck;
        this.karVisit = karVisit;
        this.karReport = karReport;
        this.daftarAsnad = daftarAsnad;
        this.sanad = sanad;
        this.city = city;
        this.roosta = roosta;
        this.owner = owner;
    }

    public Property(int id, String pelakNumber, double area, int landType, int ownerId, int manCheck, int roodCheck, int karVisit, int karReport, int daftarAsnad, int sanad, String city, String roosta, String owner) {
        this.id = id;
        this.pelakNumber = pelakNumber;
        this.area = area;
        this.landType = landType;
        this.ownerId = ownerId;
        this.manCheck = manCheck;
        this.roodCheck = roodCheck;
        this.karVisit = karVisit;
        this.karReport = karReport;
        this.daftarAsnad = daftarAsnad;
        this.sanad = sanad;
        this.city = city;
        this.roosta = roosta;
        this.owner = owner;
    }
}

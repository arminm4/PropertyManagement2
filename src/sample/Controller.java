package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import java.io.File;
import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller {
    private enum Operations {
        ADD,
        EDIT,
    }

    private Operations ownerOperation;

    private Operations propertyOperation;

    private String[] ownerSearchParameters;

    private String[] ownerSearchParametersDbNames;

    private String[] propertySearchParameters;

    private String[] propertySearchParametersDbNames;

    private String[] propertyTypes;

    private static Connection conn;

    private String searchParameter;

    private int selectedOwnerId;

    @FXML
    private JFXButton homeBtn;

    @FXML
    private JFXButton ownerBtn;

    @FXML
    private JFXButton propertyBtn;

    @FXML
    private VBox homePanel;

    @FXML
    private VBox ownerPanel;

    @FXML
    private VBox propertyPanel;

    @FXML
    private VBox addOwnerPanel;

    @FXML
    private VBox addPropertyPanel;

    @FXML
    private VBox ownerInfoPanel;

    @FXML
    private VBox propertyInfoPanel;

    @FXML
    private TextField ownerSearchText;

    @FXML
    private JFXButton ownerSearchBtn;

    @FXML
    private JFXComboBox ownerSearchParamCombo;

    @FXML
    private JFXTextField ownerFirstNameText;

    @FXML
    private JFXTextField ownerLastNameText;

    @FXML
    private JFXTextField ownerFatherText;

    @FXML
    private JFXRadioButton ownerMaleRadio;

    @FXML
    private JFXRadioButton ownerFemaleRadio;

    @FXML
    private JFXTextField ownerNationalIdText;

    @FXML
    private JFXTextField ownerShenasText;

    @FXML
    private JFXTextField ownerPhoneText;

    @FXML
    private JFXTextField ownerCityText;

    @FXML
    private Label addOwnerErrorMessageLbl;

    @FXML
    private JFXButton submitOwnerInfoBtn;

    @FXML
    private JFXButton cancelOwnerSubmissionBtn;

    @FXML
    private JFXTextField propertySearchText;

    @FXML
    private JFXButton propertySearchBtn;

    @FXML
    private JFXComboBox propertyTypesCombo;

    @FXML
    private JFXComboBox propertySearchParamCombo;

    @FXML
    private TableView ownerTable;

    @FXML
    private TableColumn ownerIdCol;

    @FXML
    private TableColumn ownerFirstNameCol;

    @FXML
    private TableColumn ownerLastNameCol;

    @FXML
    private TableColumn ownerNationalIdCol;

    @FXML
    private TableColumn ownerShenasCol;

    @FXML
    private TableView propertyTable;

    @FXML
    private TableColumn propertyIdCol;

    @FXML
    private TableColumn propertyPelakNumberCol;

    @FXML
    private TableColumn propertyOwnerCol;

    @FXML
    private TableColumn propertyLandTypeCol;

    @FXML
    private TableColumn propertyCityCol;

    @FXML
    private TableColumn propertyRoostaCol;

    @FXML
    private Label addOwnerTitleLbl;

    @FXML
    private Label ownerInfoFirstNameLbl;

    @FXML
    private Label ownerInfoLastNameLbl;

    @FXML
    private Label ownerInfoFatherLbl;

    @FXML
    private Label ownerInfoGenderLbl;

    @FXML
    private Label ownerInfoNationalIdLbl;

    @FXML
    private Label ownerInfoShenasLbl;

    @FXML
    private Label ownerInfoPhoneLbl;

    @FXML
    private Label ownerInfoCityLbl;

    static class GetAllOwnersTask extends Task<ObservableList<Owner>> {
        private final String sql;
        private final PreparedStatement stmt;

        public GetAllOwnersTask() throws SQLException {
            this.sql = "SELECT * FROM owner";
            this.stmt = conn.prepareStatement(sql);
        }
        public GetAllOwnersTask(int id) throws SQLException {
            this.sql = "SELECT * FROM owner WHERE id = ?";
            this.stmt = conn.prepareStatement(sql);
            this.stmt.setInt(1, id);
        }
        public GetAllOwnersTask(String searchKey, String searchValue) throws SQLException {
            this.sql = "SELECT * FROM owner WHERE " + searchKey + " LIKE ?";
            this.stmt = conn.prepareStatement(sql);
            this.stmt.setString(1, "%" + searchValue + "%");
        }
        @Override
        protected ObservableList<Owner> call() throws Exception {
            ObservableList<Owner> data = null;
            try {
                data = FXCollections.observableArrayList();
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    data.add(new Owner(rs.getInt("id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("father"),
                            rs.getString("gender"),
                            rs.getString("national_id"),
                            rs.getString("shenas"),
                            rs.getString("phone_no"),
                            rs.getString("city"))
                    );
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
            return data;
        }
    }

    static class InsertOwnerTask extends Task<Integer> {
        private final Owner owner;

        public InsertOwnerTask(Owner owner) {
            this.owner = owner;
        }

        @Override
        protected Integer call() throws Exception {
            int result = 0;
            String sql = "INSERT INTO owner (first_name, last_name, father, gender, national_id, shenas, phone_no, city) VALUES (?,?,?,?,?,?,?,?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, owner.getFirstName());
            ps.setString(2, owner.getLastName());
            ps.setString(3, owner.getFather());
            ps.setString(4, owner.getGender());
            ps.setString(5, owner.getNationalId());
            ps.setString(6, owner.getShenas());
            ps.setString(7, owner.getPhoneNumber());
            ps.setString(8, owner.getCity());
            ps.executeUpdate();
            return result;
        }
    }

    static class UpdateOwnerTask extends Task<Integer> {
        private final int id;
        private final Owner owner;

        public UpdateOwnerTask(int id, Owner owner) {
            this.id = id;
            this.owner = owner;
        }

        @Override
        protected Integer call() throws Exception {
            int result = 0;
            String sql = "UPDATE owner SET first_name = ?, last_name = ?, father = ?, gender = ?, national_id = ?, shenas = ?, phone_no = ?, city = ? WHERE id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, owner.getFirstName());
            ps.setString(2, owner.getLastName());
            ps.setString(3, owner.getFather());
            ps.setString(4, owner.getGender());
            ps.setString(5, owner.getNationalId());
            ps.setString(6, owner.getShenas());
            ps.setString(7, owner.getPhoneNumber());
            ps.setString(8, owner.getCity());
            ps.setInt(9, id);
            ps.executeUpdate();
            return result;
        }
    }

    static class GetAllPropertiesTask extends Task<ObservableList<Property>> {
        private final int id;
        private final String sql;
        private final PreparedStatement stmt;

        public GetAllPropertiesTask() throws SQLException {
            this.id = 0;
            this.sql = "SELECT * FROM property";
            this.stmt = conn.prepareStatement(sql);
        }
        public GetAllPropertiesTask(int id) throws SQLException {
            this.id = id;
            this.sql = "SELECT * FROM property WHERE id = ?";
            this.stmt = conn.prepareStatement(sql);
            this.stmt.setInt(1, id);
        }
        public GetAllPropertiesTask(String searchKey, String searchValue) throws SQLException {
            this.id = 0;
            this.sql = "SELECT * FROM property WHERE " + searchKey + " LIKE ?";
            this.stmt = conn.prepareStatement(sql);
            this.stmt.setString(1, "%" + searchValue + "%");
        }
        @Override
        protected ObservableList<Property> call() throws Exception {
            ObservableList<Property> data = null;
            try {
                data = FXCollections.observableArrayList();
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    PreparedStatement stmt2 = conn.prepareStatement("SELECT * FROM owner WHERE id = ?");
                    stmt2.setInt(1, rs.getInt("owner_id"));
                    ResultSet rs2 = stmt2.executeQuery();
                    while (rs2.next()) {
                        data.add(new Property(rs.getInt("id"),
                                rs.getString("pelak_no"),
                                rs.getDouble("area"),
                                rs.getInt("land_type"),
                                rs.getInt("owner_id"),
                                rs.getInt("man_check"),
                                rs.getInt("rood_check"),
                                rs.getInt("kar_visit"),
                                rs.getInt("kar_report"),
                                rs.getInt("daftar_asnad"),
                                rs.getInt("sanad"),
                                rs.getString("city"),
                                rs.getString("roosta"),
                                rs2.getString("first_name") + " " + rs2.getString("last_name"))
                        );
                    }
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
            return data;
        }
    }

    public void connect() {
        conn = null;
        try {
            String filePath = Main.projectPath  + "/data.db";
            String url = "jdbc:sqlite:" + filePath;
            File file = new File (filePath);
            boolean shouldCreateTables = !file.exists();
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");
            if (shouldCreateTables) {
                String ownerTableSQL = "CREATE TABLE owner (id INTEGER PRIMARY KEY, first_name VARCHAR(100), last_name VARCHAR(100), father VARCHAR(100), national_id VARCHAR(10), shenas VARCHAR(10), gender VARCHAR(1), phone_no VARCHAR(11), city VARCHAR(100));";
                String propertyTableSQL = "CREATE TABLE property (id INTEGER PRIMARY KEY, pelak_no TEXT NOT NULL, area REAL NOT NULL, land_type INTEGER NOT NULL, owner_id INTEGER NOT NULL, man_check INTEGER, rood_check INTEGER, kar_visit INTEGER, kar_report INTEGER, daftar_asnad INTEGER, sanad INTEGER, city TEXT NOT NULL, roosta TEXT NOT NULL, FOREIGN KEY(owner_id) REFERENCES owner(id))";
                Statement stmt = conn.createStatement();
                stmt.execute(ownerTableSQL);
                stmt.execute(propertyTableSQL);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Controller() {
        ownerSearchParameters = new String[] {"نام", "نام خانوادگی", "کد ملی", "شماره شناسنامه"};
        ownerSearchParametersDbNames = new String[] {"first_name", "last_name", "national_id", "shenas"};
        propertySearchParameters = new String[] {"شماره پلاک", "مالک", "نوع زمین", "شهر", "روستا"};
        propertySearchParametersDbNames = new String[] {"pelak_no", "owner_id", "land_type", "city", "roosta"};
        propertyTypes = new String[] {"مسکونی", "دیم", "آبی", "صنعتی"};
        connect();
    }

    private void hideAllPanels() {
        homePanel.setVisible(false);
        ownerPanel.setVisible(false);
        propertyPanel.setVisible(false);
        addOwnerPanel.setVisible(false);
        addPropertyPanel.setVisible(false);
        ownerInfoPanel.setVisible(false);
        propertyInfoPanel.setVisible(false);
    }

    private void clearHomePanel() {}

    private void clearOwnerPanel() {
        ownerSearchText.clear();
        ownerSearchParamCombo.getSelectionModel().select(1);
        ownerSearchBtn.setDisable(false);
        ownerSearchText.requestFocus();
    }

    private void clearPropertyPanel() {
        propertySearchText.clear();
        propertySearchParamCombo.getSelectionModel().select(1);
        propertySearchBtn.setDisable(false);
        propertySearchText.requestFocus();
    }

    private void clearAddOwnerPanel() {
        ownerFirstNameText.clear();
        ownerLastNameText.clear();
        ownerFatherText.clear();
        ownerMaleRadio.setSelected(false);
        ownerFemaleRadio.setSelected(false);
        ownerNationalIdText.clear();
        ownerShenasText.clear();
        ownerPhoneText.clear();
        ownerCityText.clear();
        submitOwnerInfoBtn.setDisable(false);
        submitOwnerInfoBtn.setText("ثبت اطلاعات");
        addOwnerErrorMessageLbl.setText("");
        ownerFirstNameText.requestFocus();
        addOwnerTitleLbl.setText("ثبت مالک جدید");
        ownerOperation = Operations.ADD;
    }

    private void clearEditOwnerPanel() {
        submitOwnerInfoBtn.setDisable(false);
        submitOwnerInfoBtn.setText("ویرایش اطلاعات");
        addOwnerTitleLbl.setText("ویرایش اطلاعات مالک");
        addOwnerErrorMessageLbl.setText("");
        ownerFirstNameText.requestFocus();
        ownerOperation = Operations.EDIT;
    }

    private void clearAddPropertyPanel() {

    }

    private void clearOwnerInfoPanel() {

    }

    private void clearPropertyInfoPanel() {
    }

    private void showHomePanel() {
        hideAllPanels();
        clearHomePanel();
        homePanel.setVisible(true);
    }

    private void showOwnerPanel() {
        hideAllPanels();
        clearOwnerPanel();
        ownerPanel.setVisible(true);
    }

    private void showPropertyPanel() {
        hideAllPanels();
        clearPropertyPanel();
        propertyPanel.setVisible(true);
    }

    private void showAddOwnerPanel() {
        hideAllPanels();
        clearAddOwnerPanel();
        addOwnerPanel.setVisible(true);
    }

    private void showEditOwnerPanel() {
        hideAllPanels();
        clearEditOwnerPanel();
        addOwnerPanel.setVisible(true);
    }

    private void showAddPropertyPanel() {
        hideAllPanels();
        clearAddPropertyPanel();
        addPropertyPanel.setVisible(true);
    }

    private void showOwnerInfoPanel() {
        hideAllPanels();
        clearOwnerInfoPanel();
        ownerInfoPanel.setVisible(true);
    }

    private void showPropertyInfoPanel() {
        hideAllPanels();
        clearPropertyInfoPanel();
        propertyInfoPanel.setVisible(true);
    }

    private void setOwnerSearchTextPlaceHolder(String placeHolder) {
        ownerSearchText.promptTextProperty().setValue("جستجو بر اساس " + placeHolder);
    }

    private void setPropertySearchTextPlaceHolder(String placeHolder) {
        propertySearchText.promptTextProperty().setValue("جستجو بر اساس " + placeHolder);
    }

    private void initializeOwnerSearchParamChoiceBox() {
        for (String parameter:
             ownerSearchParameters) {
            ownerSearchParamCombo.getItems().add(parameter);
        }
        searchParameter = ownerSearchParameters[1];
        ownerSearchParamCombo.getSelectionModel().select(0);
        setOwnerSearchTextPlaceHolder(ownerSearchParameters[0]);
    }

    private void initializeOwnersTable(String searchKey, String searchValue) {
        try {
            GetAllOwnersTask task;
            if (searchKey.trim().isEmpty()) task = new GetAllOwnersTask();
            else task = new GetAllOwnersTask(searchKey, searchValue);

            task.setOnRunning((successEvent) -> {});

            task.setOnSucceeded((succeededEvent) -> {
                ObservableList<Owner> data = task.getValue();
                if (data != null) {
                    ownerIdCol.setCellValueFactory(new PropertyValueFactory<Owner, String>("id"));
                    ownerFirstNameCol.setCellValueFactory(new PropertyValueFactory<Owner, String>("firstName"));
                    ownerLastNameCol.setCellValueFactory(new PropertyValueFactory<Owner, String>("lastName"));
                    ownerNationalIdCol.setCellValueFactory(new PropertyValueFactory<Owner, String>("nationalId"));
                    ownerShenasCol.setCellValueFactory(new PropertyValueFactory<Owner, String>("shenas"));
                    ownerTable.setItems(data);
                }
            });

            ExecutorService executorService
                    = Executors.newFixedThreadPool(1);
            executorService.execute(task);
            executorService.shutdown();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            //statusMessage.setValue("خطایی هنگام دریافت اطلاعات رخ داده است!");
        }
        ownerSearchBtn.setDisable(false);
    }

    private void initializeOwnerInfoPanel(int id) {
        try {
            GetAllOwnersTask task;
            task = new GetAllOwnersTask(id);

            task.setOnRunning((successEvent) -> {});

            task.setOnSucceeded((succeededEvent) -> {
                ObservableList<Owner> data = task.getValue();
                if (data != null) {
                    ownerInfoFirstNameLbl.setText(((Owner)data.get(0)).getFirstName());
                    ownerInfoLastNameLbl.setText(((Owner)data.get(0)).getLastName());
                    ownerInfoFatherLbl.setText(((Owner)data.get(0)).getFather());
                    ownerInfoGenderLbl.setText(((Owner)data.get(0)).getGender().equalsIgnoreCase("m") ? "مرد" : ((Owner)data.get(0)).getGender().equalsIgnoreCase("f") ? "زن" : "");
                    ownerInfoNationalIdLbl.setText(((Owner)data.get(0)).getNationalId());
                    ownerInfoShenasLbl.setText(((Owner)data.get(0)).getShenas());
                    ownerInfoPhoneLbl.setText(((Owner)data.get(0)).getPhoneNumber());
                    ownerInfoCityLbl.setText(((Owner)data.get(0)).getCity());
                    selectedOwnerId = ((Owner)data.get(0)).getId();
                }
            });

            ExecutorService executorService
                    = Executors.newFixedThreadPool(1);
            executorService.execute(task);
            executorService.shutdown();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            //statusMessage.setValue("خطایی هنگام دریافت اطلاعات رخ داده است!");
        }
    }

    private void initializePropertyTypesComboBox() {
        for (String type:
                propertyTypes) {
            propertyTypesCombo.getItems().add(type);
        }
    }

    private void initializePropertySearchParamComboBox() {
        for (String parameter:
                propertySearchParameters) {
            propertySearchParamCombo.getItems().add(parameter);
        }
        searchParameter = propertySearchParameters[1];
        propertySearchParamCombo.getSelectionModel().select(1);
        setPropertySearchTextPlaceHolder(propertySearchParameters[1]);
    }

    private void initializePropertiesTable(String searchKey, String searchValue) {
        try {
            GetAllPropertiesTask task;
            if (searchKey.trim().isEmpty()) task = new GetAllPropertiesTask();
            else task = new GetAllPropertiesTask(searchKey, searchValue);

            task.setOnRunning((successEvent) -> {});

            task.setOnSucceeded((succeededEvent) -> {
                ObservableList<Property> data = task.getValue();
                if (data != null) {
                    propertyIdCol.setCellValueFactory(new PropertyValueFactory<Property, String>("id"));
                    propertyPelakNumberCol.setCellValueFactory(new PropertyValueFactory<Property, String>("pelakNumber"));
                    propertyOwnerCol.setCellValueFactory(new PropertyValueFactory<Property, String>("owner"));
                    propertyLandTypeCol.setCellValueFactory(new PropertyValueFactory<Property, String>("landType"));
                    propertyCityCol.setCellValueFactory(new PropertyValueFactory<Property, String>("city"));
                    propertyRoostaCol.setCellValueFactory(new PropertyValueFactory<Property, String>("roosta"));
                    propertyTable.setItems(data);
                }
            });

            ExecutorService executorService
                    = Executors.newFixedThreadPool(1);
            executorService.execute(task);
            executorService.shutdown();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        propertySearchBtn.setDisable(false);
    }

    @FXML
    private void ownerSearchParamComboAction(ActionEvent event) {
        setOwnerSearchTextPlaceHolder(ownerSearchParameters[ownerSearchParamCombo.getSelectionModel().getSelectedIndex()]);
    }

    @FXML
    private void propertySearchParamComboAction(ActionEvent event) {
        setPropertySearchTextPlaceHolder(propertySearchParameters[propertySearchParamCombo.getSelectionModel().getSelectedIndex()]);
    }

    @FXML
    private void ownerSearchBtnClicked(ActionEvent event) {
        String searchKey = ownerSearchParametersDbNames[ownerSearchParamCombo.getSelectionModel().getSelectedIndex()];
        String searchValue = ownerSearchText.getText();
        ownerSearchBtn.setDisable(true);
        if (searchValue.trim().isEmpty())
            initializeOwnersTable("", "");
        else
            initializeOwnersTable(searchKey, searchValue);
    }

    @FXML
    private void ownerTableDoubleClicked(MouseEvent event) {
        if (event.getClickCount() >= 2) {
            int selectedId = ((Owner)ownerTable.getSelectionModel().getSelectedItem()).getId();
            System.out.println("Double Clicked !! " + selectedId);
            showOwnerInfoPanel();
            initializeOwnerInfoPanel(selectedId);
        }
    }

    @FXML
    private void submitOwnerInfoBtnClicked(ActionEvent event) {
        try {
            submitOwnerInfoBtn.setDisable(true);
            String firstName = ownerFirstNameText.getText();
            String lastName = ownerLastNameText.getText();
            String father = ownerFatherText.getText();
            String gender = "";
            if (ownerMaleRadio.isSelected())
                gender = "m";
            else if (ownerFemaleRadio.isSelected())
                gender = "f";
            String nationalId = ownerNationalIdText.getText();
            String shenas = ownerShenasText.getText();
            String phoneNumber = ownerPhoneText.getText();
            String city = ownerCityText.getText();
            if (firstName.trim().isEmpty()
                    || lastName.trim().isEmpty()
                    || father.trim().isEmpty()
                    || gender.trim().isEmpty()
                    || nationalId.trim().isEmpty()
                    || shenas.trim().isEmpty()
                    || phoneNumber.trim().isEmpty()
                    || city.trim().isEmpty()) {
                submitOwnerInfoBtn.setDisable(false);
                addOwnerErrorMessageLbl.setText("لطفاً تمامی فیلد‌هایی که با * مشخص شده‌اند را وارد کنید");
                return;
            }
            if (ownerOperation == Operations.EDIT) {
                final Owner owner = new Owner(
                        selectedOwnerId,
                        firstName,
                        lastName,
                        father,
                        gender,
                        nationalId,
                        shenas,
                        phoneNumber,
                        city
                );
                UpdateOwnerTask task = new UpdateOwnerTask(selectedOwnerId, owner);

                task.setOnRunning((successEvent) -> {});

                task.setOnSucceeded((succeededEvent) -> {
                    int result = task.getValue();
                    if (result != -1) {
                        initializeOwnersTable("", "");
                        initializeOwnerInfoPanel(selectedOwnerId);
                        showOwnerInfoPanel();
                    }
                    else {
                        submitOwnerInfoBtn.setDisable(false);
                        addOwnerErrorMessageLbl.setText("خطایی در ویرایش مالک به وجود آمده است");
                    }
                });
                ExecutorService executorService
                        = Executors.newFixedThreadPool(1);
                executorService.execute(task);
                executorService.shutdown();
            }
            else {
                final Owner owner = new Owner(
                        firstName,
                        lastName,
                        father,
                        gender,
                        nationalId,
                        shenas,
                        phoneNumber,
                        city
                );
                InsertOwnerTask task = new InsertOwnerTask(owner);

                task.setOnRunning((successEvent) -> {});

                task.setOnSucceeded((succeededEvent) -> {
                    int result = task.getValue();
                    if (result != -1) {
                        initializeOwnersTable("", "");
                        showOwnerPanel();
                    }
                    else {
                        submitOwnerInfoBtn.setDisable(false);
                        addOwnerErrorMessageLbl.setText("خطایی در ثبت مالک به وجود آمده است");
                    }
                });
                ExecutorService executorService
                        = Executors.newFixedThreadPool(1);
                executorService.execute(task);
                executorService.shutdown();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            submitOwnerInfoBtn.setDisable(false);
            if (ownerOperation == Operations.EDIT) addOwnerErrorMessageLbl.setText("خطایی در ویرایش مالک به وجود آمده است");
            else addOwnerErrorMessageLbl.setText("خطایی در ثبت مالک به وجود آمده است");
        }
    }

    @FXML
    private void cancelOwnerSubmissionBtnClicked(ActionEvent event) {
        if (ownerOperation == Operations.EDIT) showOwnerInfoPanel();
        else showOwnerPanel();
    }

    @FXML
    private void propertySearchBtnClicked(ActionEvent event) {
        String searchKey = propertySearchParametersDbNames[propertySearchParamCombo.getSelectionModel().getSelectedIndex()];
        String searchValue = propertySearchText.getText();
        propertySearchBtn.setDisable(true);
        if (searchValue.trim().isEmpty())
            initializePropertiesTable("", "");
        else
            initializePropertiesTable(searchKey, searchValue);
    }

    @FXML
    private void propertyTableDoubleClicked(MouseEvent event) {
        if (event.getClickCount() >= 2) {
            int selectedId = ((Property)propertyTable.getSelectionModel().getSelectedItem()).getId();
            System.out.println("Double Clicked !! " + selectedId);
            //showPropertyInfoPanel();
            //initializePropertyInfoPanel(selectedId);
        }
    }

    @FXML
    private void homeBtnClicked(ActionEvent event) {
        showHomePanel();
    }

    @FXML
    private void ownerBtnClicked(ActionEvent event) {
        showOwnerPanel();
    }

    @FXML
    private void propertyBtnClicked(ActionEvent event) {
        showPropertyPanel();
    }

    @FXML
    private void showAddOwnerPanelBtnClicked(ActionEvent event) {
        showAddOwnerPanel();
    }

    @FXML
    private void showEditOwnerPanelBtnClicked(ActionEvent event) {
        int selectedId = selectedOwnerId;
        try {
            GetAllOwnersTask task;
            task = new GetAllOwnersTask(selectedId);

            task.setOnRunning((successEvent) -> {});

            task.setOnSucceeded((succeededEvent) -> {
                ObservableList<Owner> data = task.getValue();
                if (data != null) {
                    ownerFirstNameText.setText(((Owner)data.get(0)).getFirstName());
                    ownerLastNameText.setText(((Owner)data.get(0)).getLastName());
                    ownerFatherText.setText(((Owner)data.get(0)).getFather());
                    ownerMaleRadio.setSelected(((Owner)data.get(0)).getGender().equalsIgnoreCase("m"));
                    ownerFemaleRadio.setSelected(((Owner)data.get(0)).getGender().equalsIgnoreCase("f"));
                    ownerNationalIdText.setText(((Owner)data.get(0)).getNationalId());
                    ownerShenasText.setText(((Owner)data.get(0)).getShenas());
                    ownerPhoneText.setText(((Owner)data.get(0)).getPhoneNumber());
                    ownerCityText.setText(((Owner)data.get(0)).getCity());
                }
            });

            ExecutorService executorService
                    = Executors.newFixedThreadPool(1);
            executorService.execute(task);
            executorService.shutdown();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            //statusMessage.setValue("خطایی هنگام دریافت اطلاعات رخ داده است!");
        }
        showEditOwnerPanel();
    }

    @FXML
    private void showAddPropertyPanelBtnClicked(ActionEvent event) {
        showAddPropertyPanel();
    }

    @FXML
    public void initialize() {
        initializeOwnerSearchParamChoiceBox();
        initializePropertySearchParamComboBox();
        initializePropertyTypesComboBox();
        initializeOwnersTable("", "");
        initializePropertiesTable("", "");
    }


}

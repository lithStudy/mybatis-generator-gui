package com.zzg.mybatis.generator.controller;

import com.jcraft.jsch.Session;
import com.zzg.mybatis.generator.bridge.MybatisGeneratorBridge;
import com.zzg.mybatis.generator.enums.ConfNameEnum;
import com.zzg.mybatis.generator.model.DatabaseConfig;
import com.zzg.mybatis.generator.model.GeneratorConfig;
import com.zzg.mybatis.generator.model.PlusGeneratorInjectionConf;
import com.zzg.mybatis.generator.model.UITableColumnVO;
import com.zzg.mybatis.generator.util.ConfigHelper;
import com.zzg.mybatis.generator.util.DbUtil;
import com.zzg.mybatis.generator.util.MyStringUtils;
import com.zzg.mybatis.generator.view.AlertUtil;
import com.zzg.mybatis.generator.view.UIProgressCallback;

import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.sql.SQLRecoverableException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.mybatis.generator.config.ColumnOverride;
import org.mybatis.generator.config.IgnoredColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.util.Callback;

public class MainUIController extends BaseFXController {

    private static final Logger _LOG = LoggerFactory.getLogger(MainUIController.class);
    private static final String FOLDER_NO_EXIST = "部分目录不存在，是否创建";

    @FXML
    private TextField poPackage;
    @FXML
    private TextField poTargetProject;

    @FXML
    private TextField boPackage;
    @FXML
    private TextField boTargetProject;

    @FXML
    private TextField daoPackage;
    @FXML
    private TextField daoTargetProject;

    @FXML
    private TextField mapperPackage;
    @FXML
    private TextField mapperTargetProject;

    @FXML
    private TextField managerPackage;
    @FXML
    private TextField managerTargetProject;

    @FXML
    private TextField managerImplPackage;
    @FXML
    private TextField managerImplTargetProject;

    @FXML
    private TextField transPackage;
    @FXML
    private TextField transTargetProject;

    @FXML
    private TextField entityNameField;

    @FXML
    private Label entityNameLable;


    @FXML
    private CheckBox rpcGenerator;

    @FXML
    private GridPane rpcPane;

    @FXML
    private TextField dtoPackage;
    @FXML
    private TextField dtoTargetProject;

    @FXML
    private TextField rpcPackage;
    @FXML
    private TextField rpcTargetProject;

    @FXML
    private TextField rpcImplPackage;
    @FXML
    private TextField rpcImplTargetProject;

    @FXML
    private TextField rpcConverterPackage;
    @FXML
    private TextField rpcConverterTargetProject;






    // tool bar buttons
    @FXML
    private Label connectionLabel;
    @FXML
    private Label configsLabel;
    @FXML
    private TextField tableNameField;
    @FXML
    private TextField domainObjectNameField;
    //    @FXML
    //    private TextField generateKeysField;	//主键ID
    @FXML
    private TextField mapperName;
    @FXML
    private TextField projectFolderField;
    @FXML
    private CheckBox overrideFile;
    @FXML
    private CheckBox useLombokPlugin;
    @FXML
    private CheckBox annotationDAOCheckBox;
    @FXML
    private CheckBox jsr310Support;
    @FXML
    private TreeView<String> leftDBTree;
    // Current selected databaseConfig
    private DatabaseConfig selectedDatabaseConfig;
    // Current selected tableName
    private String tableName;

    private List<IgnoredColumn> ignoredColumns;

    private List<ColumnOverride> columnOverrides;

    @FXML
    private ChoiceBox<String> encodingChoice;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ImageView dbImage = new ImageView("icons/computer.png");
        dbImage.setFitHeight(40);
        dbImage.setFitWidth(40);
        connectionLabel.setGraphic(dbImage);
        connectionLabel.setOnMouseClicked(event -> {
            TabPaneController controller = (TabPaneController) loadFXMLPage("新建数据库连接", FXMLPage.NEW_CONNECTION, false);
            controller.setMainUIController(this);
            controller.showDialogStage();
        });
        ImageView configImage = new ImageView("icons/config-list.png");
        configImage.setFitHeight(40);
        configImage.setFitWidth(40);
        configsLabel.setGraphic(configImage);
        configsLabel.setOnMouseClicked(event -> {
            GeneratorConfigController controller = (GeneratorConfigController) loadFXMLPage("配置", FXMLPage.GENERATOR_CONFIG, false);
            controller.setMainUIController(this);
            controller.showDialogStage();
        });
        //勾选事件
        rpcGenerator.setOnMouseClicked(event -> {
            if (rpcGenerator.isSelected()) {
                rpcPane.setVisible(true);
            } else {
                rpcPane.setVisible(false);
            }
        });
        // selectedProperty().addListener 解决应用配置的时候未触发Clicked事件
        rpcGenerator.selectedProperty().addListener((observable, oldValue, newValue)->{
            if (newValue) {
                rpcPane.setVisible(true);
            } else {
                rpcPane.setVisible(false);
            }
        });

        leftDBTree.setShowRoot(false);
        leftDBTree.setRoot(new TreeItem<>());
        Callback<TreeView<String>, TreeCell<String>> defaultCellFactory = TextFieldTreeCell.forTreeView();
        leftDBTree.setCellFactory((TreeView<String> tv) -> {
            TreeCell<String> cell = defaultCellFactory.call(tv);
            cell.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                int level = leftDBTree.getTreeItemLevel(cell.getTreeItem());
                TreeCell<String> treeCell = (TreeCell<String>) event.getSource();
                TreeItem<String> treeItem = treeCell.getTreeItem();
                if (level == 1) {
                    final ContextMenu contextMenu = new ContextMenu();
                    MenuItem item1 = new MenuItem("关闭连接");
                    item1.setOnAction(event1 -> treeItem.getChildren().clear());
                    MenuItem item2 = new MenuItem("编辑连接");
                    item2.setOnAction(event1 -> {
                        DatabaseConfig selectedConfig = (DatabaseConfig) treeItem.getGraphic().getUserData();
                        TabPaneController controller = (TabPaneController) loadFXMLPage("编辑数据库连接", FXMLPage.NEW_CONNECTION, false);
                        controller.setMainUIController(this);
                        controller.setConfig(selectedConfig);
                        controller.showDialogStage();
                    });
                    MenuItem item3 = new MenuItem("删除连接");
                    item3.setOnAction(event1 -> {
                        DatabaseConfig selectedConfig = (DatabaseConfig) treeItem.getGraphic().getUserData();
                        try {
                            ConfigHelper.deleteDatabaseConfig(selectedConfig);
                            this.loadLeftDBTree();
                        } catch (Exception e) {
                            AlertUtil.showErrorAlert("Delete connection failed! Reason: " + e.getMessage());
                        }
                    });
                    contextMenu.getItems().addAll(item1, item2, item3);
                    cell.setContextMenu(contextMenu);
                }
                if (event.getClickCount() == 2) {
                    if(treeItem == null) {
                        return ;
                    }
                    treeItem.setExpanded(true);
                    if (level == 1) {
                        DatabaseConfig selectedConfig = (DatabaseConfig) treeItem.getGraphic().getUserData();
                        try {
                            List<String> tables = DbUtil.getTableNames(selectedConfig);
                            if (tables != null && tables.size() > 0) {
                                ObservableList<TreeItem<String>> children = cell.getTreeItem().getChildren();
                                children.clear();
                                for (String tableName : tables) {
                                    TreeItem<String> newTreeItem = new TreeItem<>();
                                    ImageView imageView = new ImageView("icons/table.png");
                                    imageView.setFitHeight(16);
                                    imageView.setFitWidth(16);
                                    newTreeItem.setGraphic(imageView);
                                    newTreeItem.setValue(tableName);
                                    children.add(newTreeItem);
                                }
                            }
                        } catch (SQLRecoverableException e) {
                            _LOG.error(e.getMessage(), e);
                            AlertUtil.showErrorAlert("连接超时");
                        } catch (Exception e) {
                            _LOG.error(e.getMessage(), e);
                            AlertUtil.showErrorAlert(e.getMessage());
                        }
                    } else if (level == 2) { // left DB tree level3
                        String tableName = treeCell.getTreeItem().getValue();
                        selectedDatabaseConfig = (DatabaseConfig) treeItem.getParent().getGraphic().getUserData();
                        this.tableName = tableName;
                        tableNameField.setText(tableName);
                        entityNameField.setText(MyStringUtils.dbStringToCamelStyle(tableName));
                        //                        domainObjectNameField.setText(MyStringUtils.dbStringToCamelStyle(tableName));
                        //                        mapperName.setText(domainObjectNameField.getText().concat("DAO"));
                    }
                }
            });
            return cell;
        });
        loadLeftDBTree();
        setTooltip();
        //默认选中第一个，否则如果忘记选择，没有对应错误提示
        encodingChoice.getSelectionModel().selectFirst();
    }

    private void setTooltip() {
        encodingChoice.setTooltip(new Tooltip("生成文件的编码，必选"));
        entityNameLable.setTooltip(new Tooltip("实体名决定了所有类和文件的名字前缀，例如 Person 将生成PersonPO PersonBO PersonDAO  等"));
        overrideFile.setTooltip(new Tooltip("重新生成时把原XML文件覆盖，否则是追加"));
        useLombokPlugin.setTooltip(new Tooltip("实体类使用Lombok @Data简化代码"));
        jsr310Support.setTooltip(new Tooltip("勾选时Date使用LocalDate替换"));
    }

    void loadLeftDBTree() {
        TreeItem rootTreeItem = leftDBTree.getRoot();
        rootTreeItem.getChildren().clear();
        try {
            List<DatabaseConfig> dbConfigs = ConfigHelper.loadDatabaseConfig();
            for (DatabaseConfig dbConfig : dbConfigs) {
                TreeItem<String> treeItem = new TreeItem<>();
                treeItem.setValue(dbConfig.getName());
                ImageView dbImage = new ImageView("icons/computer.png");
                dbImage.setFitHeight(16);
                dbImage.setFitWidth(16);
                dbImage.setUserData(dbConfig);
                treeItem.setGraphic(dbImage);
                rootTreeItem.getChildren().add(treeItem);
            }
        } catch (Exception e) {
            _LOG.error("connect db failed, reason: {}", e);
            AlertUtil.showErrorAlert(e.getMessage() + "\n" + ExceptionUtils.getStackTrace(e));
        }
    }

    @FXML
    public void chooseProjectFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedFolder = directoryChooser.showDialog(getPrimaryStage());
        if (selectedFolder != null) {
            projectFolderField.setText(selectedFolder.getAbsolutePath());
        }
    }

    @FXML
    public void generateCode() {
        if (tableName == null) {
            AlertUtil.showWarnAlert("请先在左侧选择数据库表");
            return;
        }
        String result = validateConfig();
        if (result != null) {
            AlertUtil.showErrorAlert(result);
            return;
        }
        GeneratorConfig generatorConfig = getGeneratorConfigFromUI();
        if (!checkDirs(generatorConfig)) {
            return;
        }

        MybatisGeneratorBridge bridge = new MybatisGeneratorBridge();
        bridge.setGeneratorConfig(generatorConfig);
        bridge.setDatabaseConfig(selectedDatabaseConfig);
        bridge.setIgnoredColumns(ignoredColumns);
        bridge.setColumnOverrides(columnOverrides);
        UIProgressCallback alert = new UIProgressCallback(Alert.AlertType.INFORMATION);
        bridge.setProgressCallback(alert);
        alert.show();
        PictureProcessStateController pictureProcessStateController = null;
        try {
            //Engage PortForwarding
            Session sshSession = DbUtil.getSSHSession(selectedDatabaseConfig);
            DbUtil.engagePortForwarding(sshSession, selectedDatabaseConfig);

            if (sshSession != null) {
                pictureProcessStateController = new PictureProcessStateController();
                pictureProcessStateController.setDialogStage(getDialogStage());
                pictureProcessStateController.startPlay();
            }

            bridge.generate();
            alert.done();

            if (pictureProcessStateController != null) {
                Task task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        Thread.sleep(3000);
                        return null;
                    }
                };
                PictureProcessStateController finalPictureProcessStateController = pictureProcessStateController;
                task.setOnSucceeded(event -> {
                    finalPictureProcessStateController.close();
                });
                task.setOnFailed(event -> {
                    finalPictureProcessStateController.close();
                });
                new Thread(task).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtil.showErrorAlert(e.getMessage());
            if (pictureProcessStateController != null) {
                pictureProcessStateController.close();
                pictureProcessStateController.playFailState(e.getMessage(), true);
            }
        }
    }

    private String validateConfig() {
        String projectFolder = projectFolderField.getText();
        if (StringUtils.isEmpty(projectFolder))  {
            return "项目目录不能为空";
        }
        if (StringUtils
            .isAnyEmpty(poPackage.getText(), boPackage.getText(), daoPackage.getText(), managerImplPackage.getText(),
                managerPackage.getText(),transPackage.getText())) {
            return "包名不能为空";
        }
        if (rpcGenerator.isSelected()) {
            if (StringUtils.isAnyEmpty(rpcPackage.getText(), rpcImplPackage.getText(), dtoPackage.getText(),
                rpcConverterPackage.getText())) {
                return "包名不能为空";
            }
        }

        return null;
    }

    @FXML
    public void saveGeneratorConfig() {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("保存当前配置");
        dialog.setContentText("请输入配置名称");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String name = result.get();
            if (StringUtils.isEmpty(name)) {
                AlertUtil.showErrorAlert("名称不能为空");
                return;
            }
            _LOG.info("user choose name: {}", name);
            try {
                GeneratorConfig generatorConfig = getGeneratorConfigFromUI();
                generatorConfig.setName(name);
                ConfigHelper.deleteGeneratorConfig(name);
                ConfigHelper.saveGeneratorConfig(generatorConfig);
            } catch (Exception e) {
                _LOG.error("保存配置失败", e);
                AlertUtil.showErrorAlert("保存配置失败");
            }
        }
    }

    public GeneratorConfig getGeneratorConfigFromUI() {
        GeneratorConfig generatorConfig = new GeneratorConfig();
        generatorConfig.setProjectFolder(projectFolderField.getText());


        List<PlusGeneratorInjectionConf> configList = new ArrayList<>();
        generatorConfig.setGeneratorPackConfigList(configList);

        PlusGeneratorInjectionConf boGeneratorPackConfig = PlusGeneratorInjectionConf.builder()
            .packagePath(boPackage.getText()).path(boTargetProject.getText()).confNameEnum(ConfNameEnum.BO).build();
        configList.add(boGeneratorPackConfig);

        PlusGeneratorInjectionConf poGeneratorPackConfig = PlusGeneratorInjectionConf.builder()
            .packagePath(poPackage.getText()).path(poTargetProject.getText()).confNameEnum(ConfNameEnum.PO).build();
        configList.add(poGeneratorPackConfig);

        PlusGeneratorInjectionConf daoGeneratorPackConfig = PlusGeneratorInjectionConf.builder()
            .packagePath(daoPackage.getText()).path(daoTargetProject.getText()).confNameEnum(ConfNameEnum.DAO).build();
        configList.add(daoGeneratorPackConfig);

        PlusGeneratorInjectionConf mapperGeneratorPackConfig = PlusGeneratorInjectionConf.builder()
            .packagePath(mapperPackage.getText()).path(mapperTargetProject.getText()).confNameEnum(ConfNameEnum.MAPPER).build();
        configList.add(mapperGeneratorPackConfig);


        PlusGeneratorInjectionConf managerGeneratorPackConfig = PlusGeneratorInjectionConf.builder()
            .packagePath(managerPackage.getText()).path(managerTargetProject.getText()).confNameEnum(ConfNameEnum.MANAGER).build();
        configList.add(managerGeneratorPackConfig);


        PlusGeneratorInjectionConf managerImplGeneratorPackConfig = PlusGeneratorInjectionConf.builder()
            .packagePath(managerImplPackage.getText()).path(managerImplTargetProject.getText()).confNameEnum(ConfNameEnum.MANAGER_IMPL).build();
        configList.add(managerImplGeneratorPackConfig);


        PlusGeneratorInjectionConf transGeneratorPackConfig = PlusGeneratorInjectionConf.builder()
            .packagePath(transPackage.getText()).path(transTargetProject.getText()).confNameEnum(ConfNameEnum.CONVERTER).build();
        configList.add(transGeneratorPackConfig);

        //rpc层生成
        generatorConfig.setRpcGenerator(rpcGenerator.isSelected());

        PlusGeneratorInjectionConf dtoGeneratorPackConfig = PlusGeneratorInjectionConf.builder()
            .packagePath(dtoPackage.getText()).path(dtoTargetProject.getText()).confNameEnum(ConfNameEnum.DTO).build();
        configList.add(dtoGeneratorPackConfig);


        PlusGeneratorInjectionConf rpcGeneratorPackConfig = PlusGeneratorInjectionConf.builder()
            .packagePath(rpcPackage.getText()).path(rpcTargetProject.getText()).confNameEnum(ConfNameEnum.RPC).build();
        configList.add(rpcGeneratorPackConfig);

        PlusGeneratorInjectionConf rpcImplGeneratorPackConfig = PlusGeneratorInjectionConf.builder()
            .packagePath(rpcImplPackage.getText()).path(rpcImplTargetProject.getText()).confNameEnum(ConfNameEnum.RPC_IMPL).build();
        configList.add(rpcImplGeneratorPackConfig);


        PlusGeneratorInjectionConf rpcConverterGeneratorPackConfig = PlusGeneratorInjectionConf.builder()
            .packagePath(rpcConverterPackage.getText()).path(rpcConverterTargetProject.getText()).confNameEnum(ConfNameEnum.RPC_CONVERTER).build();
        configList.add(rpcConverterGeneratorPackConfig);

        generatorConfig.setTableName(tableNameField.getText());
        generatorConfig.setEntityName(entityNameField.getText());
        generatorConfig.setOverrideFile(overrideFile.isSelected());
        generatorConfig.setUseLombokPlugin(useLombokPlugin.isSelected());
        generatorConfig.setEncoding(encodingChoice.getValue());
        generatorConfig.setJsr310Support(jsr310Support.isSelected());
        return generatorConfig;
    }

    public void setGeneratorConfigIntoUI(GeneratorConfig generatorConfig) {
        projectFolderField.setText(generatorConfig.getProjectFolder());
        //        generateKeysField.setText(generatorConfig.getGenerateKeys());

        poPackage.setText(generatorConfig.getPackage(ConfNameEnum.PO).getPackagePath());
        poTargetProject.setText(generatorConfig.getPackage(ConfNameEnum.PO).getPath());

        boPackage.setText(generatorConfig.getPackage(ConfNameEnum.BO).getPackagePath());
        boTargetProject.setText(generatorConfig.getPackage(ConfNameEnum.BO).getPath());

        daoPackage.setText(generatorConfig.getPackage(ConfNameEnum.DAO).getPackagePath());
        daoTargetProject.setText(generatorConfig.getPackage(ConfNameEnum.DAO).getPath());

        mapperPackage.setText(generatorConfig.getPackage(ConfNameEnum.MAPPER).getPackagePath());
        mapperTargetProject.setText(generatorConfig.getPackage(ConfNameEnum.MAPPER).getPath());

        managerPackage.setText(generatorConfig.getPackage(ConfNameEnum.MANAGER).getPackagePath());
        managerTargetProject.setText(generatorConfig.getPackage(ConfNameEnum.MANAGER).getPath());

        managerImplPackage.setText(generatorConfig.getPackage(ConfNameEnum.MANAGER_IMPL).getPackagePath());
        managerImplTargetProject.setText(generatorConfig.getPackage(ConfNameEnum.MANAGER_IMPL).getPath());

        transPackage.setText(generatorConfig.getPackage(ConfNameEnum.CONVERTER).getPackagePath());
        transTargetProject.setText(generatorConfig.getPackage(ConfNameEnum.CONVERTER).getPath());

        if (null != generatorConfig.getRpcGenerator()) {
            rpcGenerator.setSelected(generatorConfig.getRpcGenerator());

            dtoPackage.setText(generatorConfig.getPackage(ConfNameEnum.DTO).getPackagePath());
            dtoTargetProject.setText(generatorConfig.getPackage(ConfNameEnum.DTO).getPath());

            rpcPackage.setText(generatorConfig.getPackage(ConfNameEnum.RPC).getPackagePath());
            rpcTargetProject.setText(generatorConfig.getPackage(ConfNameEnum.RPC).getPath());

            rpcImplPackage.setText(generatorConfig.getPackage(ConfNameEnum.RPC_IMPL).getPackagePath());
            rpcImplTargetProject.setText(generatorConfig.getPackage(ConfNameEnum.RPC_IMPL).getPath());

            rpcConverterPackage.setText(generatorConfig.getPackage(ConfNameEnum.RPC_CONVERTER).getPackagePath());
            rpcConverterTargetProject.setText(generatorConfig.getPackage(ConfNameEnum.RPC_CONVERTER).getPath());
        }


        overrideFile.setSelected(generatorConfig.isOverrideFile());
        useLombokPlugin.setSelected(generatorConfig.isUseLombokPlugin());
        encodingChoice.setValue(generatorConfig.getEncoding());
        jsr310Support.setSelected(generatorConfig.isJsr310Support());

    }

    @FXML
    public void openTableColumnCustomizationPage() {
        if (tableName == null) {
            AlertUtil.showWarnAlert("请先在左侧选择数据库表");
            return;
        }
        SelectTableColumnController controller = (SelectTableColumnController) loadFXMLPage("定制列", FXMLPage.SELECT_TABLE_COLUMN, true);
        controller.setMainUIController(this);
        try {
            // If select same schema and another table, update table data
            if (!tableName.equals(controller.getTableName())) {
                List<UITableColumnVO> tableColumns = DbUtil.getTableColumns(selectedDatabaseConfig, tableName);
                controller.setColumnList(FXCollections.observableList(tableColumns));
                controller.setTableName(tableName);
            }
            controller.showDialogStage();
        } catch (Exception e) {
            _LOG.error(e.getMessage(), e);
            AlertUtil.showErrorAlert(e.getMessage());
        }
    }

    public void setIgnoredColumns(List<IgnoredColumn> ignoredColumns) {
        this.ignoredColumns = ignoredColumns;
    }

    public void setColumnOverrides(List<ColumnOverride> columnOverrides) {
        this.columnOverrides = columnOverrides;
    }

    /**
     * 检查并创建不存在的文件夹
     *
     * @return
     */
    private boolean checkDirs(GeneratorConfig config) {
        List<String> dirs = new ArrayList<>();
        dirs.add(config.getProjectFolder());

        for (PlusGeneratorInjectionConf conf : config.getGeneratorPackConfigList()) {
            if (StringUtils.isEmpty(conf.getPath())) {
                continue;
            }
            dirs.add(FilenameUtils.normalize(config.getProjectFolder().concat("/").concat(conf.getPath())));
        }

        boolean haveNotExistFolder = false;
        for (String dir : dirs) {
            File file = new File(dir);
            if (!file.exists()) {
                haveNotExistFolder = true;
            }
        }
        if (haveNotExistFolder) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(FOLDER_NO_EXIST);
            Optional<ButtonType> optional = alert.showAndWait();
            if (optional.isPresent()) {
                if (ButtonType.OK == optional.get()) {
                    try {
                        for (String dir : dirs) {
                            FileUtils.forceMkdir(new File(dir));
                        }
                        return true;
                    } catch (Exception e) {
                        AlertUtil.showErrorAlert("创建目录失败，请检查目录是否是文件而非目录");
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    @FXML
    public void openTargetFolder() {
        GeneratorConfig generatorConfig = getGeneratorConfigFromUI();
        String projectFolder = generatorConfig.getProjectFolder();
        try {
            Desktop.getDesktop().browse(new File(projectFolder).toURI());
        }catch (Exception e) {
            AlertUtil.showErrorAlert("打开目录失败，请检查目录是否填写正确" + e.getMessage());
        }

    }
}

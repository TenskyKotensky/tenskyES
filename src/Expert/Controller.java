package Expert;

import Database.Database;
import info.FoodInfo;
import info.Категория;
import info.Размер;
import info.Форма;
import info.Цвет;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class Controller
{
    @FXML
    TextField textFieldName;
    @FXML
    Button buttonAdd;
    @FXML
    ChoiceBox listCategory;
    @FXML
    ChoiceBox listSize;
    @FXML
    ChoiceBox listForm;
    @FXML
    ChoiceBox listColor;

    ObservableList<Категория> categories = FXCollections.observableArrayList(Категория.values());
    ObservableList<Размер> sizes = FXCollections.observableArrayList(Размер.values());
    ObservableList<Форма> forms = FXCollections.observableArrayList(Форма.values());
    ObservableList<Цвет> colors = FXCollections.observableArrayList(Цвет.values());


    @FXML
    private void initialize()
    {
        listCategory.setItems(categories);
        listCategory.setValue(categories.get(0));

        listSize.setItems(sizes);
        listSize.setValue(sizes.get(0));

        listForm.setItems(forms);
        listForm.setValue(forms.get(0));

        listColor.setItems(colors);
        listColor.setValue(colors.get(0));

    }

    public Controller()
    {

    }

    /**
     * Добавляет вид с указанными параметрами в базу данных
     * @param actionEvent нажатие на кнопку <code>Добавить в базу</code>
     */
    public void buttondAddAction(ActionEvent actionEvent)
    {
        String name = textFieldName.getText();
        Категория category = (Категория) listCategory.getSelectionModel().getSelectedItem();
        Размер size = (Размер) listSize.getSelectionModel().getSelectedItem();
        Форма form = (Форма) listForm.getSelectionModel().getSelectedItem();
        Цвет color = (Цвет) listColor.getSelectionModel().getSelectedItem();

        Database.writeRecord(name,category,size,form,color);

    }



}

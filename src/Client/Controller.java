package Client;

import Database.Database;
import info.FoodInfo;
import info.Категория;
import info.Размер;
import info.Форма;
import info.Цвет;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import java.util.List;


public class Controller
{

    @FXML
    ChoiceBox<Категория> listCategory;
    @FXML
    ChoiceBox listSize;
    @FXML
    ChoiceBox listForm;
    @FXML
    ChoiceBox listColor;
    @FXML
    ListView<String> listOfVariants;

    ObservableList<Категория> categories = FXCollections.observableArrayList(Категория.values());
    ObservableList<Размер> sizes = FXCollections.observableArrayList(Размер.values());
    ObservableList<Форма> forms = FXCollections.observableArrayList(Форма.values());
    ObservableList<Цвет> colors = FXCollections.observableArrayList(Цвет.values());

    @FXML
    public void initialize()
    {
        listCategory.setItems(categories);
        listSize.setItems(sizes);
        listForm.setItems(forms);
        listColor.setItems(colors);
        listCategory.setValue(categories.get(categories.size()-1));
        listSize.setValue(sizes.get(sizes.size()-1));
        listForm.setValue(forms.get(forms.size()-1));
        listColor.setValue(colors.get(colors.size()-1));

        listCategory.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Категория>()
        {
            @Override
            public void changed(ObservableValue<? extends Категория> observable, Категория oldValue, Категория newValue)
            {
                setList();
            }
        });

        listSize.getSelectionModel().selectedItemProperty().addListener(new ChangeListener()
        {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue)
            {
                setList();
            }
        });

        listForm.getSelectionModel().selectedItemProperty().addListener(new ChangeListener()
        {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue)
            {
                setList();
            }
        });

        listColor.getSelectionModel().selectedItemProperty().addListener(new ChangeListener()
        {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue)
            {
                setList();
            }
        });


    }

    private void setList()
    {
        List<String> variants = Database.getList((Категория) listCategory.getSelectionModel().getSelectedItem(), (Размер) listSize.getSelectionModel().getSelectedItem(), (Форма) listForm.getSelectionModel().getSelectedItem(), (Цвет) listColor.getSelectionModel().getSelectedItem());
        ObservableList<String> observableList=FXCollections.observableList(variants);
        listOfVariants.setItems(observableList);
    }


}

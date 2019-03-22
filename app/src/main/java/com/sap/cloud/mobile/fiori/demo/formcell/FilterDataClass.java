package com.sap.cloud.mobile.fiori.demo.formcell;

import android.support.annotation.NonNull;

import com.sap.cloud.mobile.fiori.formcell.DateTimePicker;
import com.sap.cloud.mobile.fiori.formcell.Duration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;


public class FilterDataClass implements Serializable {
    ArrayList<MyListItems> ItemList;
    private ArrayList<String> stringItemList;
    ArrayList<Integer> preselectedItems;
    private String switchKeyName = "Confirmed";
    private Boolean switchValue = true;
    private boolean switchIsEditable = false;
    private String gridKeyName = "Sort By";
    private String[] gridValueOptions;
    private int[] filterFormCellSelectedValue;
    private Boolean gridIsEditable = false;
    private String listPickerKeyName = "Choose Multiple Options";
    private int[] listPickerSelectedValue;
    private String listPickerDisplayText;
    private String listPickerActivityTitle;
    private ArrayList<MyListItems> listPickerItemList;
    private boolean listPickerIsEditable = false;
    private String numberPickerKeyName = "Choose from Number Picker";
    private int numberPickerValue = 15;
    private int numberPickerMax = 50;
    private int numberPickerMin = 0;
    private String numberPickerDisplayText = numberPickerValue + "$";
    private boolean numberPickerIsEditable = false;
    private String expListKeyName = "Choose Multiple Options";
    private String expListDisplayText;
    private int[] expListValues;
    private boolean expListIsEditable = false;
    private String sliderKeyName = "Distance Range";
    private int sliderValue = 15;
    private String sliderDisplayText = sliderValue + " miles";
    private int sliderMaxValue = 50;
    private int sliderMinValue = 0;
    private boolean sliderIsEditable = false;
    private String datetimeCellKeyName = "DateTime Test";
    private DateTimePicker.DateTimePickerMode dateTimePickerMode =
            DateTimePicker.DateTimePickerMode.DATE_TIME_PICKER;
    private String datetimeFormatter = "y/M/d H-mm-ss";
    private Date dateTimeValue;
    private boolean datetimeIsEditable = true;
    private String noteFormCellValue;
    private Boolean noteIsEditable;
    private Boolean noteHasBorder;
    private boolean noteIsSelectable;
    private int noteMaxLines;
    private int noteMinLines;
    private String noteHint;
    private String buttonKeyName;
    private CharSequence durationKeyName;
    private String durationTextField = "12 Hrs 45 Min";
    private int durationMinuteInterval = 10;
    private String durationTextFormat;
    private Duration durationValue;
    private Boolean durationIsEditable;
    private Boolean mTitleIsEditable = true;
    private CharSequence mTitleHint = "PlaceHolder for TitleCell";
    private CharSequence mChoiceCellKeyName = "Test for ChoiceFormCell";
    private Boolean mChoiceCellIsEditable = true;
    private int mChoiceCellValue = 1;
    private String[] mChoiceCellEntries;
    private int mTitleCellMaxLength = 20;

    public boolean isSwitchIsEditable() {
        return switchIsEditable;
    }

    public void setSwitchIsEditable(boolean switchIsEditable) {
        this.switchIsEditable = switchIsEditable;
    }

    public Boolean getGridIsEditable() {
        return gridIsEditable;
    }

    public void setGridIsEditable(Boolean gridIsEditable) {
        this.gridIsEditable = gridIsEditable;
    }

    public boolean isListPickerIsEditable() {
        return listPickerIsEditable;
    }

    public void setListPickerIsEditable(boolean listPickerIsEditable) {
        this.listPickerIsEditable = listPickerIsEditable;
    }

    public boolean isNumberPickerIsEditable() {
        return numberPickerIsEditable;
    }

    public void setNumberPickerIsEditable(boolean numberPickerIsEditable) {
        this.numberPickerIsEditable = numberPickerIsEditable;
    }

    public boolean isExpListIsEditable() {
        return expListIsEditable;
    }

    public void setExpListIsEditable(boolean expListIsEditable) {
        this.expListIsEditable = expListIsEditable;
    }

    public boolean isSliderIsEditable() {
        return sliderIsEditable;
    }

    public void setSliderIsEditable(boolean sliderIsEditable) {
        this.sliderIsEditable = sliderIsEditable;
    }

    public boolean isDatetimeIsEditable() {
        return datetimeIsEditable;
    }

    public void setDatetimeIsEditable(boolean datetimeIsEditable) {
        this.datetimeIsEditable = datetimeIsEditable;
    }

    public CharSequence getDurationKeyName() {
        return durationKeyName;
    }

    void setDurationKeyName(CharSequence durationKeyName) {
        this.durationKeyName = durationKeyName;
    }

    public String getDurationTextField() {
        return durationTextField;
    }

    void setDurationTextField(String durationTextField) {
        this.durationTextField = durationTextField;
    }

    public int getDurationMinuteInterval() {
        return durationMinuteInterval;
    }

    void setDurationMinuteInterval(int durationMinuteInterval) {
        this.durationMinuteInterval = durationMinuteInterval;
    }

    public Duration getDurationValue() {
        return durationValue;
    }

    void setDurationValue(Duration durationValue) {
        this.durationValue = durationValue;
    }

    public Boolean getDurationIsEditable() {
        return durationIsEditable;
    }

    void setDurationIsEditable(Boolean durationIsEditable) {
        this.durationIsEditable = durationIsEditable;
    }

    public String getDurationTextFormat() {
        return durationTextFormat;
    }

    public void setDurationTextFormat(String durationTextFormat) {
        this.durationTextFormat = durationTextFormat;
    }

    ArrayList<String> getStringItemList() {
        return stringItemList;
    }

    void setStringItemList() {
        ArrayList<String> list = new ArrayList<>();
        for (int it = 0; it < 1000; ++it) {
            list.add("Item " + it);
        }
        this.stringItemList = list;
    }

    public String getDatetimeCellKeyName() {
        return datetimeCellKeyName;
    }

    public void setDatetimeCellKeyName(String datetimeCellKeyName) {
        this.datetimeCellKeyName = datetimeCellKeyName;
    }

    public DateTimePicker.DateTimePickerMode getDateTimePickerMode() {
        return dateTimePickerMode;
    }

    public void setDateTimePickerMode(DateTimePicker.DateTimePickerMode dateTimePickerMode) {
        this.dateTimePickerMode = dateTimePickerMode;
    }

    public String getDatetimeFormatter() {
        return datetimeFormatter;
    }

    public void setDatetimeFormatter(String datetimeFormatter) {
        this.datetimeFormatter = datetimeFormatter;
    }

    public String getNoteFormCellValue() {
        return noteFormCellValue;
    }

    void setNoteFormCellValue(String noteFormCellValue) {
        this.noteFormCellValue = noteFormCellValue;
    }

    public Boolean getNoteIsEditable() {
        return noteIsEditable;
    }

    void setNoteIsEditable(Boolean noteIsEditable) {
        this.noteIsEditable = noteIsEditable;
    }

    public Boolean getNoteHasBorder() {
        return noteHasBorder;
    }

    void setNoteHasBorder(Boolean noteHasBorder) {
        this.noteHasBorder = noteHasBorder;
    }

    public boolean getNoteIsSelectable() {
        return noteIsSelectable;
    }

    public void setNoteIsSelectable(Boolean noteIsSelectable) {
        this.noteIsSelectable = noteIsSelectable;
    }

    public int getNoteMaxLines() {
        return noteMaxLines;
    }

    void setNoteMaxLines(int noteMaxLines) {
        this.noteMaxLines = noteMaxLines;
    }

    public int getNoteMinLines() {
        return noteMinLines;
    }

    void setNoteMinLines(int noteMinLines) {
        this.noteMinLines = noteMinLines;
    }

    public String getNoteHint() {
        return noteHint;
    }

    void setNoteHint(String noteHint) {
        this.noteHint = noteHint;
    }

    public String getListPickerActivityTitle() {
        return listPickerActivityTitle;
    }

    void setListPickerActivityTitle(String listPickerActivityTitle) {
        this.listPickerActivityTitle = listPickerActivityTitle;
    }

    ArrayList<MyListItems> getListPickerItemList() {
        return listPickerItemList;
    }

    void setListPickerItemList() {
        ArrayList<MyListItems> itemList = new ArrayList<>();
        ArrayList<String> list = setupData(false);
        for (int i = 0; i < list.size(); ++i) {
            MyListItems item = new MyListItems();
            item.setFuiItemSelected(false);
            int[] mselected = getListPickerSelectedValue();
            for (int aMselected : mselected) {
                if (aMselected == i) {
                    item.setFuiItemSelected(true);
                }
            }
            item.setFuiItemText(list.get(i));
            itemList.add(item);
        }

        listPickerItemList = itemList;
    }

    public String getListPickerKeyName() {
        return listPickerKeyName;
    }

    public void setListPickerKeyName(String listPickerKeyName) {
        this.listPickerKeyName = listPickerKeyName;
    }

    public int[] getListPickerSelectedValue() {
        return listPickerSelectedValue;
    }

    void setListPickerSelectedValue(int[] listPickerSelectedValue) {
        this.listPickerSelectedValue = listPickerSelectedValue;
    }

    public String getListPickerDisplayText() {
        return listPickerDisplayText;
    }

    void setListPickerDisplayText(String listPickerDisplayText) {
        this.listPickerDisplayText = listPickerDisplayText;
    }

    public String getGridKeyName() {
        return gridKeyName;
    }

    public void setGridKeyName(String gridKeyName) {
        this.gridKeyName = gridKeyName;
    }

    public int[] getFilterFormCellSelectedValue() {
        return filterFormCellSelectedValue;
    }

    public void setFilterFormCellSelectedValue(int[] filterFormCellSelectedValue) {
        this.filterFormCellSelectedValue = filterFormCellSelectedValue;
    }

    void setFilterSelectedValue(int[] gridSelectedValue) {
        this.filterFormCellSelectedValue = gridSelectedValue;
    }

    public String[] getGridValueOptions() {
        return gridValueOptions;
    }

    void setGridValueOptions(String[] gridValueOptions) {
        this.gridValueOptions = gridValueOptions;
    }

    ArrayList<MyListItems> getItemList() {
        return ItemList;
    }

    public String getNumberPickerKeyName() {
        return numberPickerKeyName;
    }

    public void setNumberPickerKeyName(String numberPickerKeyName) {
        this.numberPickerKeyName = numberPickerKeyName;
    }

    public int getNumberPickerValue() {
        return numberPickerValue;
    }

    void setNumberPickerValue(int numberPickerValue) {
        this.numberPickerValue = numberPickerValue;
    }

    public int getNumberPickerMax() {
        return numberPickerMax;
    }

    public void setNumberPickerMax(int numberPickerMax) {
        this.numberPickerMax = numberPickerMax;
    }

    public int getNumberPickerMin() {
        return numberPickerMin;
    }

    public void setNumberPickerMin(int numberPickerMin) {
        this.numberPickerMin = numberPickerMin;
    }

    public String getNumberPickerDisplayText() {
        return numberPickerDisplayText;
    }

    void setNumberPickerDisplayText(String numberPickerDisplayText) {
        this.numberPickerDisplayText = numberPickerDisplayText;
    }

    void setItemList() {
        ArrayList<String> itemList = setupData(false);
        ArrayList<MyListItems> ItemList = new ArrayList<>();
        for (int it = 0; it < itemList.size(); ++it) {
            MyListItems item = new MyListItems();
            item.setFuiItemText(itemList.get(it));
            if (preselectedItems.contains(it)) {
                item.setFuiItemSelected(true);
            } else {
                item.setFuiItemSelected(false);
            }
            ItemList.add(item);

        }
        this.ItemList = ItemList;
    }

    ArrayList<Integer> getPreselectedItems() {
        return preselectedItems;
    }

    void setPreselectedItems(@NonNull int[] selectedValue) {
        expListValues = selectedValue;
        preselectedItems = new ArrayList<>();
        for (int i = 0; i < selectedValue.length; ++i) {
            preselectedItems.add(selectedValue[i]);
        }
    }

    public String getExpListKeyName() {
        return expListKeyName;
    }

    public void setExpListKeyName(String expListKeyName) {
        this.expListKeyName = expListKeyName;
    }

    public String getExpListDisplayText() {
        return expListDisplayText;
    }

    void setExpListDisplayText(String expListDisplayText) {
        this.expListDisplayText = expListDisplayText;
    }

    public String getSliderKeyName() {
        return sliderKeyName;
    }

    public void setSliderKeyName(String sliderKeyName) {
        this.sliderKeyName = sliderKeyName;
    }

    public int getSliderValue() {
        return sliderValue;
    }

    void setSliderValue(int sliderValue) {
        this.sliderValue = sliderValue;
    }

    public String getSliderDisplayText() {
        return sliderDisplayText;
    }

    void setSliderDisplayText(String sliderDisplayText) {
        this.sliderDisplayText = sliderDisplayText;
    }

    public int getSliderMaxValue() {
        return sliderMaxValue;
    }

    public void setSliderMaxValue(int sliderMaxValue) {
        this.sliderMaxValue = sliderMaxValue;
    }

    public int getSliderMinValue() {
        return sliderMinValue;
    }

    public void setSliderMinValue(int sliderMinValue) {
        this.sliderMinValue = sliderMinValue;
    }

    public String getSwitchKeyName() {
        return switchKeyName;
    }

    public void setSwitchKeyName(String switchKeyName) {
        this.switchKeyName = switchKeyName;
    }

    public Boolean getSwitchValue() {
        return switchValue;
    }

    void setSwitchValue(Boolean switchValue) {
        this.switchValue = switchValue;
    }

    @NonNull
    ArrayList<String> setupData(boolean singleLine) {
        String msg = "- This is a very long message created for testing different UI elements in Fiori demo app. You can visualize this as a text view in"
                + "your layout where the message can take multiple lies depending on the width of layout.";
        if (singleLine) {
            msg = "- Item ";
        }
        ArrayList<String> itemList = new ArrayList<>();
        for (int it = 0; it < 1000; ++it) {
            String text = it + msg;
            itemList.add(text);
        }
        return itemList;
    }

    String setDescriptionOfSelected(@NonNull int[] selected,
            @NonNull ArrayList<MyListItems> listPickerItemList) {
        String selectedText = "";
        for (int i = 0; i < selected.length; ++i) {
            if (Objects.equals(selectedText, "")) {
                selectedText = listPickerItemList.get(selected[i]).getFuiItemText();
            } else {
                selectedText = selectedText + ", " + listPickerItemList.get(
                        selected[i]).getFuiItemText();
            }
        }
        return selectedText;
    }

    public int[] getExpListValues() {
        return expListValues;
    }

    public void setExpListValues(int[] expListValues) {
        this.expListValues = expListValues;
    }

    String setDescriptionOfSelectedForStringList(@NonNull int[] newValue,
            @NonNull ArrayList<String> stringItemList) {
        String selectedText = "";
        for (int i = 0; i < newValue.length; ++i) {
            if (Objects.equals(selectedText, "")) {
                selectedText = stringItemList.get(newValue[i]);
            } else {
                selectedText = selectedText + ", " + stringItemList.get(newValue[i]);
            }
        }
        return selectedText;
    }

    public String getButtonKeyName() {
        return buttonKeyName;
    }

    void setButtonKeyName(String buttonKeyName) {
        this.buttonKeyName = buttonKeyName;
    }

    public Boolean getTitleIsEditable() {
        return mTitleIsEditable;
    }

    public void setTitleIsEditable(Boolean titleIsEditable) {
        mTitleIsEditable = titleIsEditable;
    }

    public CharSequence getTitleHint() {
        return mTitleHint;
    }

    public void setTitleHint(CharSequence titleHint) {
        mTitleHint = titleHint;
    }

    public CharSequence getChoiceCellKeyName() {
        return mChoiceCellKeyName;
    }

    public void setChoiceCellKeyName(CharSequence choiceCellKeyName) {
        mChoiceCellKeyName = choiceCellKeyName;
    }

    public boolean getChoiceCellIsEditable() {
        return mChoiceCellIsEditable;
    }

    public void setChoiceCellIsEditable(Boolean choiceCellIsEditable) {
        mChoiceCellIsEditable = choiceCellIsEditable;
    }

    public int getChoiceCellValue() {
        return mChoiceCellValue;
    }

    public void setChoiceCellValue(int choiceCellValue) {
        mChoiceCellValue = choiceCellValue;
    }

    public String[] getChoiceCellEntries() {
        return mChoiceCellEntries;
    }

    public void setChoiceCellEntries(String[] choiceCellEntries) {
        mChoiceCellEntries = choiceCellEntries;
    }

    public int getTitleCellMaxLength() {
        return mTitleCellMaxLength;
    }

    public void setTitleCellMaxLength(int titleCellMaxLength) {
        mTitleCellMaxLength = titleCellMaxLength;
    }

    public Date getDateTimeValue() {
        return dateTimeValue;
    }

    public void setDateTimeValue(Date dateTimeValue) {
        this.dateTimeValue = dateTimeValue;
    }
}

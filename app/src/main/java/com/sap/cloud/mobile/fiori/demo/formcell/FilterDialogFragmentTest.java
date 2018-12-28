package com.sap.cloud.mobile.fiori.demo.formcell;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sap.cloud.mobile.fiori.demo.R;
import com.sap.cloud.mobile.fiori.formcell.ButtonFormCell;
import com.sap.cloud.mobile.fiori.formcell.FilterDialogFragment;
import com.sap.cloud.mobile.fiori.formcell.FilterFormCell;
import com.sap.cloud.mobile.fiori.formcell.FormCell;
import com.sap.cloud.mobile.fiori.formcell.SimplePropertyFormCell;
import com.sap.cloud.mobile.fiori.formcell.SliderFormCell;
import com.sap.cloud.mobile.fiori.formcell.SupportsKey;
import com.sap.cloud.mobile.fiori.formcell.SwitchFormCell;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FilterDialogFragmentTest extends FilterDialogFragment {
    @Nullable
    private FilterDataClass mFilter;
    @NonNull
    JSONObject changedValues = new JSONObject();
    private boolean mFilterChanged;

    public FilterDialogFragmentTest() {
        super();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mFilter == null) {
            mFilter = new FilterDataClass();
            mFilter.setPreselectedItems(getResources().getIntArray(R.array.selected_values));
            mFilter.setItemList();
            mFilter.setFilterFormCellSelectedValue(getResources().getIntArray(R.array.selected_values));
            mFilter.setGridValueOptions(
                    getResources().getStringArray(R.array.gridSortValueOptions));
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    public JSONObject getChangedValues() {
        return changedValues;
    }

    @Override
    protected void resetChangeHandler() {

        changedValues = new JSONObject();
        mFilterChanged = false;

        mFilter = new FilterDataClass();
        mFilter.setPreselectedItems(getResources().getIntArray(R.array.selected_values));
        mFilter.setItemList();
        mFilter.setFilterFormCellSelectedValue(getResources().getIntArray(R.array.selected_values));
        mFilter.setGridValueOptions(getResources().getStringArray(R.array.gridSortValueOptions));
    }

    @Override
    protected void fillCellAtPosition(int section, int row, @NonNull FormCell view) {
        switch (section) {
            case 0:
                switch (row) {
                    case 0:
                        view.setCellValueChangeListener(new FormCell.CellValueChangeListener<ArrayList<Integer>>() {
                            @Override
                            public void cellChangeHandler(ArrayList<Integer> value) {
                                int[] primitives = new int[value.size()];
                                for(int i = 0; i < value.size(); i++) {
                                    primitives[i] = value.get(i);
                                }
                                mFilter.setFilterFormCellSelectedValue(primitives);
                            }
                        });
                        if(view instanceof SupportsKey) {
                            ((SupportsKey)view).setKey(mFilter.getGridKeyName());
                        }
                        ((FilterFormCell) view).setValueOptions(mFilter.getGridValueOptions());
                        List<Integer> values = new ArrayList<>(mFilter.getFilterFormCellSelectedValue().length);
                        for (int value:mFilter.getFilterFormCellSelectedValue()) {
                            values.add(value);
                        }
                        ((FilterFormCell) view).setValue(values);
                        ((FilterFormCell) view).setError("Test validation for GridFormCell");
                        break;
                    default:
                        break;
                }
                break;
            case 1:
                switch (row) {
                    case 0:

                        view.setCellValueChangeListener(new FormCell.CellValueChangeListener<Integer>() {
                            @Override
                            public void cellChangeHandler(Integer value) {
                                mFilter.setSliderValue(value);
                                changedValues = new JSONObject();
                                try {
                                    changedValues.put("population", value);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                if (getActivity() instanceof FilterDemoMainActivity) {
                                    FilterOptionSelectedCallBack listener =
                                            (FilterOptionSelectedCallBack) getActivity();
                                    listener.refreshBackGround();
                                }
                            }

                            @Override
                            public CharSequence updatedDisplayText(Integer value) {
                                return (mFilter.getSliderValue() + " miles ");
                            }
                        });
                        if(view instanceof SupportsKey) {
                            ((SupportsKey)view).setKey(mFilter.getSliderKeyName());
                        }

                        ((SliderFormCell) view).setMinimumValue(mFilter.getSliderMinValue());
                        ((SliderFormCell) view).setMaximumValue(mFilter.getSliderMaxValue());
                        ((SliderFormCell) view).setValue(mFilter.getSliderValue());
                        ((SliderFormCell) view).setDisplayValue(mFilter.getSliderValue() + " miles ");
                        ((SliderFormCell) view).setError("Test validation for Slider");

                        break;
                    case 1:

                        view.setCellValueChangeListener(new FormCell.CellValueChangeListener<Boolean>() {
                            @Override
                            public void cellChangeHandler(Boolean value) {
                                mFilter.setSwitchValue((boolean) value);
                            }
                        });
                        if (view instanceof SupportsKey) {
                            ((SupportsKey)view).setKey(mFilter.getSwitchKeyName());
                        }

                        ((SwitchFormCell) view).setValue(mFilter.getSwitchValue());
                        ((SwitchFormCell) view).setError("Test Switch validation");
                        break;
                    default:
                        break;
                }
                break;
            case 2:
                switch (row) {
                    case 0:
                        ((ButtonFormCell)view).setText("Button section: " + section + " and row: " + row);
                        break;
                    default:
                        break;
                }
                break;
            case 3:
                switch (row) {
                    case 0:
                        ((SimplePropertyFormCell) view).setEditable(false);
                        ((SimplePropertyFormCell) view).setValue("Sample Text");
                        ((SimplePropertyFormCell) view).setError("Test validation for SimplePropertyFormCell");
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected int getNumberOfSections() {
        return 4;
    }

    @Override
    protected int getCountOfItemsInSection(int section) {
        switch (section) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 1;
            case 3:
                return 1;
            default:
                return 1;

        }
    }

    @Nullable
    @Override
    protected FormCell.WidgetType getCellTypeInSectionAtRow(int section, int row) {
        FormCell.WidgetType cellType = null;
        switch (section) {
            case 0:
                switch (row) {
                    case 0:
                        cellType = FormCell.WidgetType.FILTER;
                        break;
                    default:
                        break;
                }
                break;
            case 1:
                switch (row) {
                    case 0:
                        cellType = FormCell.WidgetType.SLIDER;
                        break;
                    case 1:
                        cellType = FormCell.WidgetType.SWITCH;
                        break;
                    default:
                        break;
                }
                break;
            case 2:
                switch (row) {
                    case 0:
                        cellType = FormCell.WidgetType.BUTTON;
                        break;
                    default:
                        break;
                }
                break;
            case 3:
                switch (row) {
                    case 0:
                        cellType = FormCell.WidgetType.SIMPLE_CELL;
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        return cellType;
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putSerializable("FilterData", mFilter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.get("FilterData") != null) {
            mFilter = (FilterDataClass) savedInstanceState.get("FilterData");
        }
    }

    @Override
    protected  boolean filterHasChanged() {
        return mFilterChanged;
    }
}

package com.sap.cloud.mobile.fiori.demo.object;

import com.sap.cloud.mobile.fiori.demo.R;

public class ObjectCellDisplayActivity extends BaseObjectCellActivity {
    private static final String TAG = "ObjectCellDisplayActivity";
    private ObjectCellSpec mObjectCellSpec;
    @Override
    protected ObjectCellSpec getObjectCellSpec() {
        if (mObjectCellSpec == null){
            mObjectCellSpec = new ObjectCellSpec(R.layout.object_cell_2, 50, 3, 3,
                    false,
                    false, false, false,
                    false, false, false);
        }
        return mObjectCellSpec;

    }
}

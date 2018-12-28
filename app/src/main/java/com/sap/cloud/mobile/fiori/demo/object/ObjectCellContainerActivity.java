package com.sap.cloud.mobile.fiori.demo.object;

import com.sap.cloud.mobile.fiori.demo.R;

public class ObjectCellContainerActivity extends BaseObjectCellActivity {
    private static final String TAG = "ObjectCell1LineActivity";
    private ObjectCellSpec mObjectCellSpec;
    @Override
    protected ObjectCellSpec getObjectCellSpec() {
        if (mObjectCellSpec == null) {
            mObjectCellSpec = new ObjectCellSpec(R.layout.object_cell_1, NUM_BIZ_OBJECTS, 2, 1,
                    true,
                    true, true, true,
                    false, true, true);
            mObjectCellSpec.setHasContainerLayout(true);
        }
        return mObjectCellSpec;

    }
}

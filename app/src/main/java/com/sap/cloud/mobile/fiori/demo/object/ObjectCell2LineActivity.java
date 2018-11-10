package com.sap.cloud.mobile.fiori.demo.object;

import com.sap.cloud.mobile.fiori.demo.R;

public class ObjectCell2LineActivity extends BaseObjectCellActivity {
    private static final String TAG = "ObjectCell2LineActivity";
    private ObjectCellSpec mObjectCellSpec;
    @Override
    protected ObjectCellSpec getObjectCellSpec() {
        if (mObjectCellSpec == null){
            mObjectCellSpec = new ObjectCellSpec(R.layout.object_cell_1, 50, 2, 1,
                    false,
                    true, false, true,
                    true, true, false);
            mObjectCellSpec.setShuffleStatus(true);
        }
        return mObjectCellSpec;

    }
}

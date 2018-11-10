package com.sap.cloud.mobile.fiori.demo.object;

import com.sap.cloud.mobile.fiori.demo.R;

public class ObjectCell1LineActivity extends BaseObjectCellActivity {
    private static final String TAG = "ObjectCell1LineActivity";
    private ObjectCellSpec mObjectCellSpec;
    @Override
    protected ObjectCellSpec getObjectCellSpec() {
        if (mObjectCellSpec == null){
            mObjectCellSpec = new ObjectCellSpec(R.layout.object_cell_1, 20, 1, 1,
                    false,
                    true, false, true,
                    true, true, true);
            mObjectCellSpec.setShuffleIconStack(false);
            mObjectCellSpec.setShuffleStatus(true);
        }
        return mObjectCellSpec;

    }
}

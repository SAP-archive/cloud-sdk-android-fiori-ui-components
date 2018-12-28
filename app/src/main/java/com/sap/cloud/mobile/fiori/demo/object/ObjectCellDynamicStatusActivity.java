package com.sap.cloud.mobile.fiori.demo.object;

import com.sap.cloud.mobile.fiori.demo.R;

public class ObjectCellDynamicStatusActivity extends BaseObjectCellActivity {
    private static final String TAG = "ObjectCellDisplayActivity";
    private ObjectCellSpec mObjectCellSpec;
    @Override
    protected ObjectCellSpec getObjectCellSpec() {
        if (mObjectCellSpec == null){
            mObjectCellSpec = new ObjectCellSpec(R.layout.object_cell_1, 100, 3, 1,
                    true,
                    true, true, true,
                    false, true, true);
            mObjectCellSpec.setDynamicStatus(true);
        }
        return mObjectCellSpec;

    }
}

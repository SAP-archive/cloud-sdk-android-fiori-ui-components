package com.sap.cloud.mobile.fiori.demo.object;

import com.sap.cloud.mobile.fiori.demo.R;

public class ObjectCell0LineActivity extends BaseObjectCellActivity {
    private static final String TAG = "ObjectCell0LineActivity";
    private ObjectCellSpec mObjectCellSpec;
    @Override
    protected ObjectCellSpec getObjectCellSpec() {
        if (mObjectCellSpec == null){
            mObjectCellSpec = new ObjectCellSpec(R.layout.object_cell_1, 100, 0, 1,
                    true,
                    true, false, true,
                    true, false, true);
            mObjectCellSpec.setShuffleIconStack(false);
            mObjectCellSpec.setShuffleStatus(false);
            mObjectCellSpec.setHideEmptyDescription(true);
        }
        return mObjectCellSpec;
    }
}

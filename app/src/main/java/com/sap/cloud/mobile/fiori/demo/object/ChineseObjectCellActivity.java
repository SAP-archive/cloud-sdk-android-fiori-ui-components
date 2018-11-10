package com.sap.cloud.mobile.fiori.demo.object;

public class ChineseObjectCellActivity extends BaseObjectCellActivity {
    private static final String TAG = "ChineseObjectCellActivity";
    private ObjectCellSpec mObjectCellSpec;
    @Override
    protected ObjectCellSpec getObjectCellSpec() {
        if (mObjectCellSpec == null){
            mObjectCellSpec = new ObjectCellSpec(0, 100, 3, 1,
                    true,
                    true, true, true,
                    true/*to test whether it would be ignored*/, true, false);
            mObjectCellSpec.setLanguage(BizObject.LANGUAGE.CHINSE);
        }
        return mObjectCellSpec;

    }
}

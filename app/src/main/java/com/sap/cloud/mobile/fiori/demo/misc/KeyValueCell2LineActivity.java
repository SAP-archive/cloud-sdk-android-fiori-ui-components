package com.sap.cloud.mobile.fiori.demo.misc;

import com.sap.cloud.mobile.fiori.demo.R;

/**
 * 2 line and 2 column layout.
 */

public class KeyValueCell2LineActivity extends BaseKeyValueCellActivity {
    private KeyValueSpec mKeyValueSpec;
    @Override
    public KeyValueSpec getKeyValueSpec() {
        if (mKeyValueSpec == null) {
            mKeyValueSpec = new KeyValueSpec(R.layout.key_value_cell, NUM_ITEMS, 2, "Item", false);
        }
        return mKeyValueSpec;
    }
}

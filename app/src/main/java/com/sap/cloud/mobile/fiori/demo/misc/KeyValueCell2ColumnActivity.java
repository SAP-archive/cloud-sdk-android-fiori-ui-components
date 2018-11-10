package com.sap.cloud.mobile.fiori.demo.misc;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;

import com.sap.cloud.mobile.fiori.demo.R;

/**
 * 0 line and 2 column layout. Content should be short.
 */

public class KeyValueCell2ColumnActivity extends BaseKeyValueCellActivity {
    private KeyValueSpec mKeyValueSpec;

    @Override
    protected int getContentLayout(){
        if (isTablet()){
            //this recycler view has 12dp left/right padding
            return R.layout.content_column_key_value_cell;
        }
        return super.getContentLayout();
    }

    @Override
    public KeyValueSpec getKeyValueSpec() {
        if (mKeyValueSpec == null) {
            if (isTablet()) {
                mKeyValueSpec = new KeyValueSpec(R.layout.key_value_cell_column, NUM_ITEMS, 0, "Item",
                        false);

            }else {
                mKeyValueSpec = new KeyValueSpec(R.layout.key_value_cell, NUM_ITEMS, 0, "Item",
                        false);
            }
            mKeyValueSpec.setExpandable(false);
            mKeyValueSpec.setMinWords(3);
            mKeyValueSpec.setMaxWords(10);
        }
        return mKeyValueSpec;
    }

    protected void configureRecyclerView() {
        if (isTablet()){
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.addItemDecoration(
                    new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        }else{
            super.configureRecyclerView();
        }
    }
}

package com.corner.apps;

import com.corner.apps.Entity.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestKlasmen {

        @SerializedName("table")
        @Expose
        private List<Table> table = null;

        public List<Table> getTable() {
            return table;
        }

        public void setTable(List<Table> table) {
            this.table = table;
        }
}

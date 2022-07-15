package com.istea.harrypottercompendium;

import com.google.gson.annotations.SerializedName;

public class Spell {

        @SerializedName("id")
        private int id;
        @SerializedName("hechizo")
        private String hechizo;
        @SerializedName("uso")
        private String uso;

        public String getName() { return hechizo; }

        public String getUso() { return uso; }
}

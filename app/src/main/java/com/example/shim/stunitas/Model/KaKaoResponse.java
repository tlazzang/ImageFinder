package com.example.shim.stunitas.Model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KaKaoResponse {

@SerializedName("documents")
@Expose
private ArrayList<Document> documents = null;
@SerializedName("meta")
@Expose
private Meta meta;

public ArrayList<Document> getDocuments() {
return documents;
}

public void setDocuments(ArrayList<Document> documents) {
this.documents = documents;
}

public Meta getMeta() {
return meta;
}

public void setMeta(Meta meta) {
this.meta = meta;
}

}

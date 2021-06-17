package ir.tdaapp.carpro.carpro.Models.ViewModels;

import android.net.Uri;

public class AddItemPhotoModel {
    Uri uri;

    public AddItemPhotoModel(Uri uri) {
        this.uri = uri;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}

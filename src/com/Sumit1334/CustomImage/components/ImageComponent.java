package com.Sumit1334.CustomImage.components;

import android.view.View;
import android.widget.ImageView;

import com.github.siyamed.shapeimageview.BubbleImageView;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.github.siyamed.shapeimageview.RoundedImageView;

import com.google.appinventor.components.runtime.AndroidViewComponent;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;

public class ImageComponent extends AndroidViewComponent implements Component {
    private final ImageView view;

    public ImageComponent(ComponentContainer container, String type) {
        super(container);

        switch (type){
            case Shapes.CIRCLE:
                view = new CircularImageView(container.$context());
                break;
            case Shapes.BUBBLE:
                view = new BubbleImageView(container.$context());
                break;
            default:
                view = new RoundedImageView(container.$context());
        }

        container.$add(this);
    }

    @Override
    public View getView() {
        return view;
    }
}

package com.Sumit1334.CustomImage;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.github.siyamed.shapeimageview.*;
import com.github.siyamed.shapeimageview.shader.BubbleShader;
import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.*;
import com.google.appinventor.components.runtime.errors.YailRuntimeError;
import com.google.appinventor.components.runtime.util.MediaUtil;

import java.io.IOException;
import java.util.HashMap;

@DesignerComponent(version = 1,  description = "This extension helps you to expand or collapse the android view component by Sumit Kumar",category = ComponentCategory.EXTENSION,
        nonVisible = true,   iconName = "https://community.kodular.io/user_avatar/community.kodular.io/sumit1334/120/82654_2.png")
@UsesLibraries("customimage.jar")
@SimpleObject(external = true)

public class CustomImage extends AndroidNonvisibleComponent implements Component {
    private Context context;
    private String arrow="Left";
    private int radius=2;
    private int bordercolor= Color.WHITE;
    private int borderwidth=2;
    private ViewGroup.LayoutParams layoutParams;
    private ComponentContainer componentContainer;
    private Activity activity;
    private HashMap<Integer,View> component=new HashMap<>();
    private HashMap<View,Integer> ids=new HashMap<>();
    public CustomImage(ComponentContainer container) {
        super(container.$form());
        this.context=container.$context();
        this.activity=container.$context();
        this.componentContainer=container;
    }
    @SimpleProperty
    public String BubbleShape(){
        return "bubble";
    }
    @SimpleProperty
    public String RoundedShape(){
        return "round";
    }
    @SimpleProperty
    public String CircularShape(){
        return "circular";
    }
    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_CHOICES,editorArgs = {"Left","Right"},defaultValue = "Left")
    public void BubbleArrowPosition(String position){
        this.arrow=position;
    }
    @SimpleFunction(description = "")
    public void Size(int id,int height,int width){
        View view=component.get(id);
        this.layoutParams=view.getLayoutParams();
        this.layoutParams.height=pixelstodp(height);
        this.layoutParams.width=pixelstodp(width);
        view.setLayoutParams(this.layoutParams);
    }
    @SimpleProperty(description = "")
    public int BorderColor(){
        return this.bordercolor;
    }@SimpleProperty(description = "")
    public int BorderRadius(){
        return this.radius;
    }@SimpleProperty(description = "")
    public int BorderWidth(){
        return this.borderwidth;
    }
    @SimpleEvent(description = "")
    public void Click(int id,Object image){
        EventDispatcher.dispatchEvent(this,"Click",id,image);
    }
    @SimpleFunction(description = "")
    public void DeleteImage(int id){
        ViewGroup viewGroup= (ViewGroup) component.get(id).getParent();
        viewGroup.removeView(component.get(id));
    }
    @SimpleProperty
    @DesignerProperty(defaultValue = "2",editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER)
    public void BorderWidth(int borderwidth){
        this.borderwidth=borderwidth;
    }
    @SimpleProperty
    @DesignerProperty(defaultValue = "2",editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER)
    public void BorderRadius(int radius){
        this.radius=radius;
    }
    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR,defaultValue = DEFAULT_VALUE_COLOR_WHITE)
    public void BorderColor(int bordercolor){
        this.bordercolor=bordercolor;
    }

    @SimpleEvent(description = "")
    public void LongClick(int id,Object image){
        EventDispatcher.dispatchEvent(this,"LongClick",id,image);
    }
    @SimpleFunction(description = "")
    public void CreateImage(AndroidViewComponent in,int id,String shape,String picture) throws IOException {

        if (!component.containsKey(id)) {
            if (shape == "circular") {
                CircularImageView imageView = new CircularImageView(this.context);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
                imageView.setImageDrawable(MediaUtil.getBitmapDrawable(this.form, picture));
                imageView.setBorderColor(this.bordercolor);
                imageView.setBorderWidth(pixelstodp(this.borderwidth));
                imageView.setClickable(true);
                imageView.setLongClickable(true);
                ((LinearLayout) ((ViewGroup) in.getView()).getChildAt(0)).addView(imageView);
                ids.put(imageView, id);
                component.put(id, imageView);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Click(ids.get(view), view);
                    }
                });
                imageView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        LongClick(ids.get(view), view);
                        return true;

                    }
                });
            } else if (shape == "bubble") {
                BubbleImageView imageView = new BubbleImageView(this.context);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setArrowPosition(BubbleShader.ArrowPosition.LEFT);
                imageView.setImageDrawable(MediaUtil.getBitmapDrawable(this.form, picture));
                imageView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
                imageView.setBorderColor(this.bordercolor);
                imageView.setBorderWidth(pixelstodp(this.borderwidth));
                imageView.setClickable(true);
                imageView.setLongClickable(true);
                if (this.arrow=="Left"){
                    imageView.setArrowPosition(BubbleShader.ArrowPosition.LEFT);
                }else
                    imageView.setArrowPosition(BubbleShader.ArrowPosition.RIGHT);
                ((LinearLayout) ((ViewGroup) in.getView()).getChildAt(0)).addView(imageView);
                ids.put(imageView, id);
                component.put(id, imageView);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Click(ids.get(view), view);
                    }
                });
                imageView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        LongClick(ids.get(view), view);
                        return true;

                    }
                });
            } else if (shape == "round") {
                RoundedImageView imageView = new RoundedImageView(this.context);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
                imageView.setImageDrawable(MediaUtil.getBitmapDrawable(this.form, picture));
                imageView.setBorderColor(this.bordercolor);
                imageView.setRadius(this.radius);
                imageView.setBorderWidth(pixelstodp(this.borderwidth));
                imageView.setClickable(true);
                imageView.setLongClickable(true);
                ((LinearLayout) ((ViewGroup) in.getView()).getChildAt(0)).addView(imageView);
                ids.put(imageView, id);
                component.put(id, imageView);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Click(ids.get(view), view);
                    }
                });
                imageView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        LongClick(ids.get(view), view);
                        return true;

                    }
                });
            } else if (shape == "star") {
                StarImageView imageView = new StarImageView(this.context);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
                imageView.setImageDrawable(MediaUtil.getBitmapDrawable(this.form, picture));
                imageView.setBorderColor(this.bordercolor);
                imageView.setBorderWidth(pixelstodp(this.borderwidth));
                imageView.setClickable(true);
                imageView.setLongClickable(true);
                ((LinearLayout) ((ViewGroup) in.getView()).getChildAt(0)).addView(imageView);
                ids.put(imageView, id);
                component.put(id, imageView);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Click(ids.get(view), view);
                    }
                });
                imageView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        LongClick(ids.get(view), view);
                        return true;

                    }
                });
            } else
                throw new YailRuntimeError("the given shape is invalid please recheck the shape", "Invalid Shape");
        } else throw new YailRuntimeError("Duplicate id are not allowed please use a different id","ID Already exist");

    }
    @SimpleFunction(description = "")
    public Object GetImageById(int id){
        if (component.containsKey(id))
        return this.component.get(id);
        else
            throw new YailRuntimeError("Id not found please enter valid id","Invalid ID");
    }
    @SimpleFunction(description = "")
    public int GetIdByImage(Object image){
        return this.ids.get((View) image);

    }
    private int pixelstodp(int size){
        return (int) (((float) size) * context.getResources().getDisplayMetrics().density);
    }

}
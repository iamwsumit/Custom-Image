package com.Sumit1334.CustomImage;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.siyamed.shapeimageview.RoundedImageView;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesLibraries;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.AndroidViewComponent;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.errors.YailRuntimeError;
import com.google.appinventor.components.runtime.util.MediaUtil;

import com.Sumit1334.CustomImage.components.ImageComponent;
import com.Sumit1334.CustomImage.components.Shapes;

import com.github.siyamed.shapeimageview.BubbleImageView;
import com.github.siyamed.shapeimageview.ShaderImageView;
import com.github.siyamed.shapeimageview.shader.BubbleShader;

import java.util.HashMap;

@DesignerComponent(
        version = 2,
        versionName = "1.2.0",
        description = "This extension allows you to create custom images with many types of shapes. <br> Developed By <a href=\"https://sumitkmr.com/\" target =\"_blank\">Sumit</a> with <a href=\"https://community.kodular.io/t/rush-a-new-and-improved-way-of-building-extensions/111470?u=sumit1334\" target=\"_blank\">Rush</a>",
        helpUrl = "https://sumitkmr.com/",
        category = ComponentCategory.EXTENSION,
        nonVisible = true,
        iconName = "aiwebres/icon.png"
)
@UsesLibraries("image.jar")
@UsesPermissions(permissionNames = "android.permission.INTERNET, android.permission.READ_EXTERNAL_STORAGE")
@SimpleObject(external = true)

public final class CustomImage extends AndroidNonvisibleComponent implements Component {

    private final String TAG = "Custom Image";
    private final Activity context;
    private final HashMap<String, AndroidViewComponent> component = new HashMap<>();
    private final HashMap<AndroidViewComponent, String> ids = new HashMap<>();
    private int radius = 2;
    private int borderColor = Color.WHITE;
    private int borderWidth = 2;

    public CustomImage(ComponentContainer container) {
        super(container.$form());
        this.context = container.$context();
        Log.i(TAG, "Extension Initialized");
    }

    @SimpleProperty
    public String BubbleShape() {
        return Shapes.BUBBLE;
    }

    @SimpleProperty
    public String RoundedShape() {
        return Shapes.ROUNDED;
    }

    @SimpleProperty
    public String CircularShape() {
        return Shapes.CIRCLE;
    }

    @SimpleProperty
    public String Left() {
        return "left";
    }

    @SimpleProperty
    public String Right() {
        return "right";
    }

//  All designer are deprecated and don't work they will be soon removed from code

    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_CHOICES, editorArgs = {"Left", "Right"}, defaultValue = "Left")
    public void BubbleArrowPosition(String position) {
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "2", editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER)
    public void BorderWidth(int width) {
        this.borderWidth = width;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "2", editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER)
    public void BorderRadius(int radius) {
        this.radius = radius;
    }

    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR, defaultValue = DEFAULT_VALUE_COLOR_WHITE)
    public void BorderColor(int color) {
        this.borderColor = color;
    }

    @SimpleProperty(description = "")
    public int BorderColor() {
        return this.borderColor;
    }

    @SimpleProperty(description = "")
    public int BorderRadius() {
        return this.radius;
    }

    @SimpleProperty(description = "")
    public int BorderWidth() {
        return this.borderWidth;
    }

    @SimpleEvent(description = "This event raises when an image is clicked")
    public void Click(String id, AndroidViewComponent image) {
        EventDispatcher.dispatchEvent(this, "Click", id, image);
    }

    @SimpleEvent(description = "This event raises when an image is long clicked")
    public void LongClick(String id, AndroidViewComponent image) {
        EventDispatcher.dispatchEvent(this, "LongClick", id, image);
    }

    @SimpleFunction(description = "")
    public void CreateImage(final AndroidViewComponent in, final String id, final String shape, final String picture) {
        if (!(in instanceof ComponentContainer)) {
            Toast.makeText(context, "Given arrangement is not a component container", Toast.LENGTH_SHORT).show();
            return;
        }

        ImageComponent image = new ImageComponent(((ComponentContainer) in), shape);
        addImage(id, image);
        setImage(id, picture);
    }

    @SimpleFunction(description = "This block set the border of the image view")
    public void SetBorder(String id, int borderColor, float borderWidth) {
        final ImageComponent image = getImage(id);
        final ShaderImageView imageView = (ShaderImageView) image.getView();
        imageView.setBorderColor(borderColor);
        imageView.setBorderWidth(d2p(borderWidth));
    }

    @SimpleFunction(description = "This block set the corner radius for the rectangle shape image view")
    public void SetCornerRadius(String id, int radius) {
        RoundedImageView imageView = (RoundedImageView) getImage(id).getView();
        imageView.setRadius(d2p(radius));
    }

    @SimpleFunction(description = "This block set the arrow position for bubble image view")
    public void SetArrowPosition(String id, String arrowPosition) {
        BubbleImageView imageView = (BubbleImageView) getImage(id).getView();
        imageView.setArrowPosition(
                arrowPosition.equals(Left())
                        ? BubbleShader.ArrowPosition.LEFT
                        : BubbleShader.ArrowPosition.RIGHT
        );
    }

    @SimpleFunction(description = "This block removes the image from its parent")
    public void DeleteImage(String id) {
        final View view = getImage(id).getView();
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        viewGroup.removeView(view);
        ids.remove(component.remove(id));
    }

    @SimpleFunction(description = "")
    public void Size(String id, int height, int width) {
        ImageComponent view = getImage(id);
        view.Height(d2p(height));
        view.Width(d2p(width));
    }

    @SimpleFunction(description = "")
    public AndroidViewComponent GetImageById(String id) {
        if (component.containsKey(id))
            return this.component.get(id);
        else
            throw new YailRuntimeError("Id not found please enter valid id", "Invalid ID");
    }

    @SimpleFunction(description = "")
    public String GetIdByImage(AndroidViewComponent image) {
        return this.ids.get(image);
    }

    @SimpleFunction(description = "Update the picture of an imageView")
    public void SetImage(String id, String picture) {
        setImage(id, picture);
    }

    private void addImage(final String id, final ImageComponent image) {
        final View view = image.getView();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Click(id, image);
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                LongClick(id, image);
                return false;
            }
        });

        ids.put(image, id);
        component.put(id, image);
    }

    private int d2p(float borderWidth) {
        return (int) (context.getResources().getDisplayMetrics().density * borderWidth);
    }

    private ImageComponent getImage(String id) {
        return (ImageComponent) component.get(id);
    }

    private void setImage(String id, String picture) {
        try {
            ImageView view = (ImageView) getImage(id).getView();
            view.setImageDrawable(MediaUtil.getBitmapDrawable(form, picture));
        } catch (Exception e) {
            Log.e(TAG, "setImage: ", e);
        }
    }
}
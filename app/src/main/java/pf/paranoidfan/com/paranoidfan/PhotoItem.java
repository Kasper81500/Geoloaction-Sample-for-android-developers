package pf.paranoidfan.com.paranoidfan;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import fr.rolandl.carousel.CarouselItem;

/**
 * Created by KasperStar on 9/11/2016.
 */
public final class PhotoItem
        extends CarouselItem<String> {

    private ImageView image;

    private Context context;

    public PhotoItem(Context context) {
        super(context, R.layout.cell_avatar);
        this.context = context;
    }

    @Override
    public void extractView(View view) {
        image = (ImageView) view.findViewById(R.id.image);
    }

    @Override
    public void update(String photo) {
        image.setImageResource(getResources().getIdentifier(photo, "mipmap", context.getPackageName()));
        Log.d("PhotoItem", "photo string update = " + photo);
    }
}

